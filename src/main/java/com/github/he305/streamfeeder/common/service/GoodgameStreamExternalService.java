package com.github.he305.streamfeeder.common.service;

import com.github.he305.streamfeeder.common.entity.Platform;

public abstract class GoodgameStreamExternalService implements StreamExternalService {
    private static final Platform SERVICE_TYPE = Platform.GOODGAME;

    @Override
    public Platform getServiceType() {
        return SERVICE_TYPE;
    }
}
