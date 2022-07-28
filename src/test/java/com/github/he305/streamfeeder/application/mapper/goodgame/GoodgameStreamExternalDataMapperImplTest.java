package com.github.he305.streamfeeder.application.mapper.goodgame;

import com.github.he305.streamfeeder.application.dto.goodgame.Channel;
import com.github.he305.streamfeeder.common.entity.StreamExternalData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GoodgameStreamExternalDataMapperImplTest {

    private GoodgameStreamExternalDataMapperImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new GoodgameStreamExternalDataMapperImpl();
    }

    @Test
    void getData_someData() {
        Channel data = new Channel("0", "title", "Live", 456, "game");
        StreamExternalData expected = StreamExternalData.newData("game", "title", 456);

        StreamExternalData actual = underTest.getData(data);
        assertEquals(expected, actual);
    }

    @Test
    void getData_emptyData() {
        Channel data = new Channel("0", "title", "Dead", 456, "game");
        StreamExternalData expected = StreamExternalData.emptyData();
        StreamExternalData actual = underTest.getData(data);
        assertEquals(expected, actual);
    }
}