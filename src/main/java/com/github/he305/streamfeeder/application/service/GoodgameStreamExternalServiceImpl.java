package com.github.he305.streamfeeder.application.service;

import com.github.he305.streamfeeder.common.entity.StreamExternalData;
import com.github.he305.streamfeeder.common.service.GoodgameStreamExternalService;
import org.springframework.stereotype.Component;

@Component
public class GoodgameStreamExternalServiceImpl implements GoodgameStreamExternalService {
    @Override
    public StreamExternalData getStreamExternalDataForNickname(String nickname) {
        throw new UnsupportedOperationException();
    }
}
