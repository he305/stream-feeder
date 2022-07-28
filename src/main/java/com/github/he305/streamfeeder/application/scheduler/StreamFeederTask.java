package com.github.he305.streamfeeder.application.scheduler;

import com.github.he305.streamfeeder.common.dto.StreamEnd;
import com.github.he305.streamfeeder.common.entity.Channel;
import com.github.he305.streamfeeder.common.entity.StreamData;
import com.github.he305.streamfeeder.common.entity.StreamExternalData;
import com.github.he305.streamfeeder.common.exception.ProducerExchangeException;
import com.github.he305.streamfeeder.common.exception.StreamExternalServiceException;
import com.github.he305.streamfeeder.common.factory.StreamExternalServiceFactory;
import com.github.he305.streamfeeder.common.scheduler.Task;
import com.github.he305.streamfeeder.common.service.ProducerExchangeService;
import com.github.he305.streamfeeder.common.service.StreamExternalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StreamFeederTask implements Task {
    private final ProducerExchangeService service;
    private final StreamExternalServiceFactory streamExternalServiceFactory;

    @Override
    public boolean doTask() {

        List<Channel> channels;
        try {
            channels = service.getChannels();
        } catch (ProducerExchangeException ex) {
            log.info("Exception retrieving channels, ex: " + ex.getMessage());
            return false;
        }
        long success = channels.stream().map(this::processChannel).filter(code -> code).count();
        log.info(String.format("Processed %d channels out of %d", success, channels.size()));
        return success == channels.size();
    }

    private boolean processChannel(Channel channel) {
        StreamExternalData data;

        try {
            StreamExternalService streamExternalService = streamExternalServiceFactory.getStreamExternalService(channel.getPlatform());
            data = streamExternalService.getStreamExternalDataForNickname(channel.getNickname());
        } catch (StreamExternalServiceException ex) {
            log.info(String.format("Exception while processing %s, ex: %s", channel, ex.getMessage()));
            return false;
        } catch (UnsupportedOperationException ex) {
            log.info("Can't create external service for " + channel);
            return false;
        }

        log.info(data.toString());
        if (Boolean.TRUE.equals(data.getIsLive())) {
            return addStreamData(channel, data);
        } else {
            return endStreamIfLive(channel);
        }
    }

    private boolean addStreamData(Channel channel, StreamExternalData data) {
        StreamData toSend = new StreamData(
                data.getGameName(),
                data.getTitle(),
                data.getViewerCount(),
                LocalDateTime.now(),
                channel.getId()
        );
        try {
            boolean hasSent = service.addStreamData(toSend);
            log.info("Sent add? " + hasSent);
            return hasSent;
        } catch (ProducerExchangeException ex) {
            log.info("Exception adding stream data, ex: " + ex.getMessage());
            return false;
        }
    }

    private boolean endStreamIfLive(Channel channel) {
        if (Boolean.FALSE.equals(channel.getIsLive())) {
            return true;
        }

        StreamEnd endRequest = new StreamEnd(LocalDateTime.now(), channel.getId());
        try {
            boolean hasSent = service.endStream(endRequest);
            log.info("Sent end? " + hasSent);
            return hasSent;
        } catch (ProducerExchangeException ex) {
            log.info("Exception ending stream, ex: " + ex.getMessage());
            return false;
        }
    }
}
