package com.github.he305.streamfeeder.application.mapper.wasd;

import com.github.he305.streamfeeder.application.dto.wasd.*;
import com.github.he305.streamfeeder.common.entity.StreamExternalData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WasdStreamerExternalDataMapperImplTest {

    private WasdStreamerExternalDataMapperImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new WasdStreamerExternalDataMapperImpl();
    }

    @Test
    void getData_someData() {
        ChannelData channelData = new ChannelData(225574, true);
        MediaContainer container = new MediaContainer(
                "Золотой Дождь [Мы СНОВА вернулись!]",
                new Game("IRL"),
                List.of(new MediaContainerStream(877))
        );

        Channel data = new Channel(channelData, container);

        StreamExternalData expected = StreamExternalData.newData("IRL", "Золотой Дождь [Мы СНОВА вернулись!]", 877);
        StreamExternalData actual = underTest.getData(data);
        assertEquals(expected, actual);
    }

    @Test
    void getData_empty() {
        ChannelData channelData = new ChannelData(225574, false);
        MediaContainer container = new MediaContainer();
        Channel data = new Channel(channelData, container);
        StreamExternalData expected = StreamExternalData.emptyData();

        StreamExternalData actual = underTest.getData(data);
        assertEquals(expected, actual);
    }
}