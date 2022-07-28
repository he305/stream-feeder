package com.github.he305.streamfeeder.application.mapper.wasd;

import com.github.he305.streamfeeder.application.dto.wasd.Channel;
import com.github.he305.streamfeeder.common.exception.StreamExternalServiceMappingException;

public interface WasdJsonChannelMapper {
    Channel getChannel(String rawJson) throws StreamExternalServiceMappingException;
}
