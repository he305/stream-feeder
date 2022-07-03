package com.github.he305.streamfeeder.application.scheduler;

import com.github.he305.streamfeeder.common.entity.Channel;
import com.github.he305.streamfeeder.common.service.ChannelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class Feeder {

    private final ChannelService service;

    @Scheduled(fixedDelay = 1000)
    public void retrieveChannels() {
        List<Channel> channels = service.getChannels();
        channels.stream().map(Channel::toString).forEach(log::info);
    }
}
