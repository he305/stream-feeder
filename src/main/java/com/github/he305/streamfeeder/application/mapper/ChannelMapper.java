package com.github.he305.streamfeeder.application.mapper;

import com.github.he305.streamfeeder.application.dto.ChannelDto;
import com.github.he305.streamfeeder.common.entity.Channel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ChannelMapper {

    private ModelMapper mapper = new ModelMapper();

    public Channel toChannel(ChannelDto dto) {
        return mapper.map(dto, Channel.class);
    }
}
