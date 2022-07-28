package com.github.he305.streamfeeder.application.mapper.goodgame;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.he305.streamfeeder.application.dto.goodgame.Channel;
import com.github.he305.streamfeeder.common.exception.StreamExternalServiceMappingException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JsonChannelMapperImplTest {

    private final ObjectMapper mapper = new ObjectMapper();

    private JsonChannelMapperImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new JsonChannelMapperImpl(mapper);
    }

    @Test
    @SneakyThrows
    void getChannel_success() {
        String rawJson = "{\"5\":{\"stream_id\":\"5\",\"key\":\"miker\",\"premium\":\"true\",\"title\":\"Варкрафт Меча и Магии\",\"status\":\"Dead\",\"viewers\":\"401\",\"max_viewers\":false,\"usersinchat\":\"38\",\"embed\":\"<iframeframeborder=\\\"0\\\"width=\\\"100%\\\"height=\\\"100%\\\"src=\\\"https:\\/\\/goodgame.ru\\/player?6\\\"><\\/iframe>\",\"img\":\"https:\\/\\/goodgame.ru\\/files\\/logotypes\\/ch_5_d50J.jpg\",\"thumb\":\"\\/\\/hls.goodgame.ru\\/previews\\/6_240.jpg\",\"description\":\"\",\"adult\":\"0\",\"games\":\"Heroes of Might and Magic III: The Shadow of Death\",\"url\":\"https:\\/\\/goodgame.ru\\/channel\\/Miker\\/\"}}";

        Channel expected = Channel.builder()
                .game("Heroes of Might and Magic III: The Shadow of Death")
                .status("Dead")
                .title("Варкрафт Меча и Магии")
                .streamId("5")
                .viewers(401)
                .build();
        Channel actual = underTest.getChannel(rawJson);
        assertEquals(expected, actual);
    }

    @Test
    @SneakyThrows
    void getChannel_emptyStringShouldThrow() {
        String rawJson = "";
        assertThrows(StreamExternalServiceMappingException.class, () ->
                underTest.getChannel(rawJson));
    }
}