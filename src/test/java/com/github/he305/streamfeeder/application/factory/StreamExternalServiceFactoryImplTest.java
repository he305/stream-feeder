package com.github.he305.streamfeeder.application.factory;

import com.github.he305.streamfeeder.common.entity.Platform;
import com.github.he305.streamfeeder.common.entity.StreamExternalData;
import com.github.he305.streamfeeder.common.service.GoodgameStreamExternalService;
import com.github.he305.streamfeeder.common.service.StreamExternalService;
import com.github.he305.streamfeeder.common.service.TwitchStreamExternalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class StreamExternalServiceFactoryImplTest {
    @Mock
    private TwitchStreamExternalService twitchStreamExternalService;
    @Mock
    private GoodgameStreamExternalService goodgameStreamExternalService;

    private StreamExternalServiceFactoryImpl underTest;
    @BeforeEach
    void setUp() {
        underTest = new StreamExternalServiceFactoryImpl(twitchStreamExternalService, goodgameStreamExternalService);
    }

    @Test
    void getStreamExternalService_unsupported() {
        Platform platform = Platform.WASD;
        assertThrows(UnsupportedOperationException.class, () ->
                underTest.getStreamExternalService(platform));
    }

}