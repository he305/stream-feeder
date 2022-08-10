package com.github.he305.streamfeeder.application.mapper.exchange;

import com.github.he305.streamfeeder.application.dto.v2.ChannelDtoV2;
import com.github.he305.streamfeeder.common.entity.Channel;
import org.springframework.stereotype.Component;

@Component
public class ChannelMapperV2Impl implements ChannelMapperV2 {
    @Override
    public Channel toChannel(ChannelDtoV2 dto) {
        return new Channel(
                dto.getId(),
                dto.getChannelName(),
                dto.isLive(),
                dto.getPlatform()
        );
    }
}
