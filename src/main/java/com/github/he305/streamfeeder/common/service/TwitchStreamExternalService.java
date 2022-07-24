package com.github.he305.streamfeeder.common.service;

import com.github.he305.streamfeeder.common.entity.Platform;

public abstract class TwitchStreamExternalService implements StreamExternalService {
    private static final Platform SERVICE_TYPE = Platform.TWITCH;

    @Override
    public Platform getServiceType() {
        return SERVICE_TYPE;
    }
}
