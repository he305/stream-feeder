package com.github.he305.streamfeeder.application.mapper.exchange;

import com.github.he305.streamfeeder.application.dto.ChannelDto;
import com.github.he305.streamfeeder.common.entity.Channel;

public interface ChannelMapper {
    Channel toChannel(ChannelDto dto);
}
