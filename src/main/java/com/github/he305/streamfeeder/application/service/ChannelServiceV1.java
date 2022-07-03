package com.github.he305.streamfeeder.application.service;

import com.github.he305.streamfeeder.application.dto.ChannelList;
import com.github.he305.streamfeeder.application.mapper.ChannelMapper;
import com.github.he305.streamfeeder.common.entity.Channel;
import com.github.he305.streamfeeder.common.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChannelServiceV1 implements ChannelService {

    private final RestTemplate restTemplate;
    private final ChannelMapper mapper;

    @Override
    public List<Channel> getChannels() {
        ChannelList channelList = restTemplate.getForObject(
                "http://localhost:8080/api/v1/channel",
                ChannelList.class
        );
        assert channelList != null;
        return channelList.getChannels().stream().map(mapper::toChannel).collect(Collectors.toList());
    }
}
