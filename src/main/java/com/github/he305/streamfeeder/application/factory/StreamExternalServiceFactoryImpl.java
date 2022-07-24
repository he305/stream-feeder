package com.github.he305.streamfeeder.application.factory;

import com.github.he305.streamfeeder.common.entity.Platform;
import com.github.he305.streamfeeder.common.factory.StreamExternalServiceFactory;
import com.github.he305.streamfeeder.common.service.GoodgameStreamExternalService;
import com.github.he305.streamfeeder.common.service.StreamExternalService;
import com.github.he305.streamfeeder.common.service.TwitchStreamExternalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StreamExternalServiceFactoryImpl implements StreamExternalServiceFactory {
    private final TwitchStreamExternalService twitchStreamExternalService;
    private final GoodgameStreamExternalService goodgameStreamExternalService;

    @Override
    public StreamExternalService getStreamExternalService(Platform platform) {
        switch (platform) {
            case TWITCH:
                return twitchStreamExternalService;
            case GOODGAME:
                return goodgameStreamExternalService;
            default:
                throw new UnsupportedOperationException();
        }
    }
}
