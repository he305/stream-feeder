package com.github.he305.streamfeeder.application.mapper.goodgame;

import com.github.he305.streamfeeder.application.dto.goodgame.Channel;
import com.github.he305.streamfeeder.common.exception.StreamExternalServiceMappingException;

public interface JsonChannelMapper {
    Channel getChannel(String rawJson) throws StreamExternalServiceMappingException;
}
