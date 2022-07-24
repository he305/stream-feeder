package com.github.he305.streamfeeder.common.service;

import com.github.he305.streamfeeder.common.entity.StreamExternalData;

public interface StreamExternalService {
    StreamExternalData getStreamExternalDataForNickname(String nickname);
}
