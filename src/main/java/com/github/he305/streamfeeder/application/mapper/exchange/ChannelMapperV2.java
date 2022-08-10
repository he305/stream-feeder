package com.github.he305.streamfeeder.application.mapper.exchange;

import com.github.he305.streamfeeder.application.dto.v2.ChannelDtoV2;
import com.github.he305.streamfeeder.common.entity.Channel;

public interface ChannelMapperV2 {
    Channel toChannel(ChannelDtoV2 dto);
}
