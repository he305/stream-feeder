package com.github.he305.streamfeeder.application.mapper.twitch;

import com.github.he305.streamfeeder.common.entity.StreamExternalData;
import com.github.twitch4j.helix.domain.Stream;
import com.github.twitch4j.helix.domain.StreamList;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TwitchHelixStreamExternalDataMapperImplTest {

    private TwitchHelixStreamExternalDataMapperImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new TwitchHelixStreamExternalDataMapperImpl();
    }

    @Test
    void getData_empty() {
        StreamList streamList = new StreamList();
        streamList.setStreams(Collections.emptyList());

        StreamExternalData expected = StreamExternalData.emptyData();
        StreamExternalData actual = underTest.getData(streamList);
        assertEquals(expected, actual);
    }

    @SneakyThrows
    private Stream getData() {
        Class<Stream> streamClass = com.github.twitch4j.helix.domain.Stream.class;
        Stream stream = streamClass.newInstance();
        Field gameName = streamClass.getDeclaredField("gameName");
        Field time = streamClass.getDeclaredField("startedAtInstant");
        Field title = streamClass.getDeclaredField("title");
        Field viewerCount = streamClass.getDeclaredField("viewerCount");
        gameName.setAccessible(true);
        gameName.set(stream, "gameName");
        time.setAccessible(true);
        time.set(stream, Instant.ofEpochMilli(0));
        title.setAccessible(true);
        title.set(stream, "title");
        viewerCount.setAccessible(true);
        viewerCount.set(stream, 0);
        return stream;
    }

    @Test
    void getData_data() {
        Stream data = getData();
        StreamList streamList = new StreamList();
        streamList.setStreams(List.of(data));

        StreamExternalData expected = StreamExternalData.newData(data.getGameName(), data.getTitle(), data.getViewerCount());
        StreamExternalData actual = underTest.getData(streamList);
        assertEquals(expected, actual);
    }
}