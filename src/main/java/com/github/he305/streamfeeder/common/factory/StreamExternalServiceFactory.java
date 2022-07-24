package com.github.he305.streamfeeder.common.factory;

import com.github.he305.streamfeeder.common.entity.Platform;
import com.github.he305.streamfeeder.common.service.StreamExternalService;

public interface StreamExternalServiceFactory {
    StreamExternalService getStreamExternalService(Platform platform);
}
