package com.github.he305.streamfeeder.application.mapper.twitch;

import com.github.he305.streamfeeder.common.entity.StreamExternalData;
import com.github.twitch4j.helix.domain.StreamList;

public interface TwitchHelixStreamExternalDataMapper {
    StreamExternalData getData(StreamList streamList);
}
