package com.github.he305.streamfeeder.application.mapper.wasd;

import com.github.he305.streamfeeder.application.dto.wasd.Channel;
import com.github.he305.streamfeeder.common.entity.StreamExternalData;

public interface WasdStreamerExternalDataMapper {
    StreamExternalData getData(Channel channel);
}
