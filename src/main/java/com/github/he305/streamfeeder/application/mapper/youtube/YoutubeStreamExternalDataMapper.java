package com.github.he305.streamfeeder.application.mapper.youtube;

import com.github.he305.streamfeeder.common.entity.StreamExternalData;

public interface YoutubeStreamExternalDataMapper {
    StreamExternalData getData(String htmlBody);
}
