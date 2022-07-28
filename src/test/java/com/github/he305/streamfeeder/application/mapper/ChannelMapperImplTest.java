package com.github.he305.streamfeeder.application.mapper;

import com.github.he305.streamfeeder.application.dto.ChannelDto;
import com.github.he305.streamfeeder.application.mapper.exchange.ChannelMapperImpl;
import com.github.he305.streamfeeder.common.entity.Channel;
import com.github.he305.streamfeeder.common.entity.Platform;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChannelMapperImplTest {

    private ChannelMapperImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new ChannelMapperImpl();
    }

    @Test
    void toChannel() {
        ChannelDto dto = new ChannelDto(
                0L,
                "nickname",
                Platform.TWITCH,
                true,
                1L
        );

        Channel expected = new Channel(
                0L,
                "nickname",
                true,
                Platform.TWITCH,
                1L
        );

        Channel actual = underTest.toChannel(dto);
        assertEquals(expected, actual);
    }
}