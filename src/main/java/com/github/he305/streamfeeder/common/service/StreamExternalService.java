package com.github.he305.streamfeeder.common.service;

import com.github.he305.streamfeeder.common.entity.Platform;
import com.github.he305.streamfeeder.common.entity.StreamExternalData;
import com.github.he305.streamfeeder.common.exception.StreamExternalServiceException;

public interface StreamExternalService {
    StreamExternalData getStreamExternalDataForNickname(String nickname) throws StreamExternalServiceException;

    Platform getServiceType();
}
