package com.github.he305.streamfeeder.application.service;

import com.github.he305.streamfeeder.application.dto.wasd.Channel;
import com.github.he305.streamfeeder.application.mapper.wasd.WasdJsonChannelMapper;
import com.github.he305.streamfeeder.application.mapper.wasd.WasdStreamerExternalDataMapper;
import com.github.he305.streamfeeder.common.entity.StreamExternalData;
import com.github.he305.streamfeeder.common.exception.StreamExternalServiceException;
import com.github.he305.streamfeeder.common.exception.StreamExternalServiceMappingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class WasdStreamExternalServiceImplTest {

    @Mock
    private RestTemplate restTemplate;
    @Mock
    private WasdJsonChannelMapper wasdJsonChannelMapper;
    @Mock
    private WasdStreamerExternalDataMapper wasdStreamerExternalDataMapper;

    @InjectMocks
    private WasdStreamExternalServiceImpl underTest;


    @Test
    void getStreamExternalDataForNickname_networkError() {
        Mockito.when(restTemplate.exchange(Mockito.anyString(), eq(HttpMethod.GET), Mockito.any(), eq(String.class)))
                .thenThrow(HttpClientErrorException.class);
        assertThrows(StreamExternalServiceException.class, () ->
                underTest.getStreamExternalDataForNickname(""));
    }

    @Test
    void getStreamExternalDataForNickname_notFoundCode() {
        ResponseEntity<String> response = ResponseEntity.notFound().build();
        Mockito.when(restTemplate.exchange(Mockito.anyString(), eq(HttpMethod.GET), Mockito.any(), eq(String.class)))
                .thenReturn(response);

        assertThrows(StreamExternalServiceException.class, () ->
                underTest.getStreamExternalDataForNickname(""));
    }

    @Test
    void getStreamExternalDataForNickname_nullBody() {
        ResponseEntity<String> response = ResponseEntity.ok(null);
        Mockito.when(restTemplate.exchange(Mockito.anyString(), eq(HttpMethod.GET), Mockito.any(), eq(String.class)))
                .thenReturn(response);

        assertThrows(StreamExternalServiceException.class, () ->
                underTest.getStreamExternalDataForNickname(""));
    }

    @Test
    void getStreamExternalDataForNickname_jsonParseError() {
        String body = "";
        ResponseEntity<String> response = ResponseEntity.ok(body);
        Mockito.when(restTemplate.exchange(Mockito.anyString(), eq(HttpMethod.GET), Mockito.any(), eq(String.class)))
                .thenReturn(response);
        Mockito.when(wasdJsonChannelMapper.getChannel(body))
                .thenThrow(StreamExternalServiceMappingException.class);

        assertThrows(StreamExternalServiceException.class, () ->
                underTest.getStreamExternalDataForNickname(""));
    }

    @Test
    void getStreamExternalDataForNickname_success() {
        String body = "";
        ResponseEntity<String> response = ResponseEntity.ok(body);
        Channel channel = new Channel();
        StreamExternalData expected = StreamExternalData.emptyData();
        Mockito.when(restTemplate.exchange(Mockito.anyString(), eq(HttpMethod.GET), Mockito.any(), eq(String.class)))
                .thenReturn(response);
        Mockito.when(wasdJsonChannelMapper.getChannel(body))
                .thenReturn(channel);
        Mockito.when(wasdStreamerExternalDataMapper.getData(channel))
                .thenReturn(expected);

        StreamExternalData actual = underTest.getStreamExternalDataForNickname("");
        assertEquals(expected, actual);
    }
}