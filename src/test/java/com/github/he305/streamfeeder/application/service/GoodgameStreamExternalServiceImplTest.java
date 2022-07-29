package com.github.he305.streamfeeder.application.service;

import com.github.he305.streamfeeder.application.dto.goodgame.Channel;
import com.github.he305.streamfeeder.application.mapper.goodgame.GoodgameStreamExternalDataMapper;
import com.github.he305.streamfeeder.application.mapper.goodgame.JsonChannelMapper;
import com.github.he305.streamfeeder.common.entity.StreamExternalData;
import com.github.he305.streamfeeder.common.exception.StreamExternalServiceException;
import com.github.he305.streamfeeder.common.exception.StreamExternalServiceMappingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class GoodgameStreamExternalServiceImplTest {

    @Mock
    private RestTemplate restTemplate;
    @Mock
    private GoodgameStreamExternalDataMapper goodgameStreamExternalDataMapper;
    @Mock
    private JsonChannelMapper jsonChannelMapper;

    @InjectMocks
    private GoodgameStreamExternalServiceImpl underTest;

    @Test
    void getStreamExternalDataForNickname_throws4xx() {
        Mockito.when(restTemplate.getForEntity(Mockito.anyString(), eq(String.class))).thenThrow(HttpClientErrorException.class);

        assertThrows(StreamExternalServiceException.class, () ->
                underTest.getStreamExternalDataForNickname(""));
    }

    @Test
    void getStreamExternalDataForNickname_throws5xx() {
        Mockito.when(restTemplate.getForEntity(Mockito.anyString(), eq(String.class))).thenThrow(HttpServerErrorException.class);

        assertThrows(StreamExternalServiceException.class, () ->
                underTest.getStreamExternalDataForNickname(""));
    }

    @Test
    void getStreamExternalDataForNickname_throwsResourceAccessException() {
        Mockito.when(restTemplate.getForEntity(Mockito.anyString(), eq(String.class))).thenThrow(ResourceAccessException.class);

        assertThrows(StreamExternalServiceException.class, () ->
                underTest.getStreamExternalDataForNickname(""));
    }

    @Test
    void getStreamExternalDataForNickname_failParseShouldThrow() {
        String dataFromTemplate = "";
        Mockito.when(restTemplate.getForEntity(Mockito.anyString(), eq(String.class))).thenReturn(ResponseEntity.ok(dataFromTemplate));
        Mockito.when(jsonChannelMapper.getChannel(dataFromTemplate)).thenThrow(StreamExternalServiceMappingException.class);

        assertThrows(StreamExternalServiceException.class, () ->
                underTest.getStreamExternalDataForNickname(""));
    }

    @Test
    void getStreamExternalDataForNickname_success() {
        String dataFromTemplate = "";
        Channel dataFromMapper = new Channel();
        StreamExternalData expected = StreamExternalData.emptyData();
        Mockito.when(restTemplate.getForEntity(Mockito.anyString(), eq(String.class))).thenReturn(ResponseEntity.ok(dataFromTemplate));
        Mockito.when(jsonChannelMapper.getChannel(dataFromTemplate)).thenReturn(dataFromMapper);
        Mockito.when(goodgameStreamExternalDataMapper.getData(dataFromMapper)).thenReturn(expected);

        StreamExternalData actual = underTest.getStreamExternalDataForNickname("");
        assertEquals(expected, actual);
    }
}