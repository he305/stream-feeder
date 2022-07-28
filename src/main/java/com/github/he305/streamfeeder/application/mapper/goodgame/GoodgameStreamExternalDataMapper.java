package com.github.he305.streamfeeder.application.mapper.goodgame;

import com.github.he305.streamfeeder.application.dto.goodgame.Channel;
import com.github.he305.streamfeeder.common.entity.StreamExternalData;

public interface GoodgameStreamExternalDataMapper {
    StreamExternalData getData(Channel rawData);
}
