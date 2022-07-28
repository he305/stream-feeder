package com.github.he305.streamfeeder.application.mapper.exchange;

import com.github.he305.streamfeeder.application.dto.ChannelDto;
import com.github.he305.streamfeeder.common.entity.Channel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ChannelMapperImpl implements ChannelMapper {

    private final ModelMapper mapper = new ModelMapper();

    @Override
    public Channel toChannel(ChannelDto dto) {
        return mapper.map(dto, Channel.class);
    }
}
