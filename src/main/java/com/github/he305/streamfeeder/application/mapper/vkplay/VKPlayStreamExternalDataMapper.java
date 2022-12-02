package com.github.he305.streamfeeder.application.mapper.vkplay;

import com.github.he305.streamfeeder.application.dto.vkplay.VKPlayDto;
import com.github.he305.streamfeeder.common.entity.StreamExternalData;

public interface VKPlayStreamExternalDataMapper {
    StreamExternalData getData(VKPlayDto dto);
}
