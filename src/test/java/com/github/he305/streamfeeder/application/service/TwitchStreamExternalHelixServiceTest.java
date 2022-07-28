package com.github.he305.streamfeeder.application.service;

import com.github.he305.streamfeeder.application.mapper.twitch.TwitchHelixStreamExternalDataMapper;
import com.github.he305.streamfeeder.common.entity.StreamExternalData;
import com.github.he305.streamfeeder.common.exception.StreamExternalServiceException;
import com.github.twitch4j.helix.TwitchHelix;
import com.github.twitch4j.helix.domain.StreamList;
import com.netflix.hystrix.HystrixCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class TwitchStreamExternalHelixServiceTest {

    @Mock
    private TwitchHelix twitchHelix;
    @Mock
    private TwitchHelixStreamExternalDataMapper twitchHelixStreamExternalDataMapper;

    @InjectMocks
    private TwitchStreamExternalHelixService underTest;

    @Test
    void getStreamExternalDataForNickname_errorInTwitchHelix_shouldThrow() {
        String nickname = "";
        Mockito.when(twitchHelix.getStreams(null, null, null, 100, null, null, null,
                List.of(nickname)

        )).thenThrow(RuntimeException.class);

        assertThrows(StreamExternalServiceException.class, () ->
                underTest.getStreamExternalDataForNickname(nickname));
    }

    @Test
    void getStreamExternalDataForNickname_success() {
        String nickname = "";
        HystrixCommand<StreamList> command = Mockito.mock(HystrixCommand.class);

        StreamList streamList = new StreamList();
        streamList.setStreams(List.of());
        StreamExternalData expected = StreamExternalData.emptyData();

        Mockito.when(twitchHelix.getStreams(
                null, null, null, 100, null, null, null,
                List.of(nickname)

        )).thenReturn(command);
        Mockito.when(command.execute()).thenReturn(streamList);
        Mockito.when(twitchHelixStreamExternalDataMapper.getData(streamList)).thenReturn(expected);

        StreamExternalData actual = underTest.getStreamExternalDataForNickname(nickname);
        assertEquals(expected, actual);
    }
}