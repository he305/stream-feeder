package com.github.he305.streamfeeder.application.mapper.exchange;

import com.github.he305.streamfeeder.application.dto.v2.ChannelDtoV2;
import com.github.he305.streamfeeder.common.entity.Channel;
import com.github.he305.streamfeeder.common.entity.Platform;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChannelMapperV2ImplTest {

    ChannelMapperV2Impl underTest;

    @BeforeEach
    void setUp() {
        underTest = new ChannelMapperV2Impl();
    }

    @Test
    void toChannel() {
        UUID id = UUID.randomUUID();
        ChannelDtoV2 dto = new ChannelDtoV2(
                id,
                "name",
                Platform.TWITCH,
                true
        );

        Channel expected = new Channel(
                id,
                "name",
                true,
                Platform.TWITCH
        );

        Channel actual = underTest.toChannel(dto);
        assertEquals(expected, actual);
    }
}