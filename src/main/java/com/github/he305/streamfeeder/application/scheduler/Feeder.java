package com.github.he305.streamfeeder.application.scheduler;

import com.github.he305.streamfeeder.common.dto.StreamEnd;
import com.github.he305.streamfeeder.common.entity.Channel;
import com.github.he305.streamfeeder.common.entity.StreamData;
import com.github.he305.streamfeeder.common.entity.StreamExternalData;
import com.github.he305.streamfeeder.common.factory.StreamExternalServiceFactory;
import com.github.he305.streamfeeder.common.service.ProducerExchangeService;
import com.github.he305.streamfeeder.common.service.StreamExternalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class Feeder {

    private final ProducerExchangeService service;
    private final StreamExternalServiceFactory streamExternalServiceFactory;
    private List<Channel> savedChannels;

    @Scheduled(fixedDelay = 60000)
    public void retrieveChannels() {
        List<Channel> channels = service.getChannels();
        if (!channels.equals(savedChannels)) {
            log.info("Save new channels");
            savedChannels = channels;
        }

        savedChannels
                .forEach(channel -> {
                    StreamExternalService streamExternalService = streamExternalServiceFactory.getStreamExternalService(channel.getPlatform());
                    StreamExternalData data = streamExternalService.getStreamExternalDataForNickname(channel.getNickname());
                    log.info(data.toString());
                    if (Boolean.TRUE.equals(data.getIsLive())) {
                        StreamData toSend = new StreamData(
                                data.getGameName(),
                                data.getTitle(),
                                data.getViewerCount(),
                                LocalDateTime.now(),
                                channel.getId()
                        );
                        boolean hasSent = service.addStreamData(toSend);
                        log.info("Sent add? " + hasSent);
                    } else {
                        if (Boolean.TRUE.equals(channel.getIsLive())) {
                            StreamEnd endRequest = new StreamEnd(LocalDateTime.now(), channel.getId());
                            boolean hasSent = service.endStream(endRequest);
                            log.info("Sent end? " + hasSent);
                        }
                    }
                });
    }
}
