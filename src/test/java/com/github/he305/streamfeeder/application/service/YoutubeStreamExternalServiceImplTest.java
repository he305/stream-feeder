package com.github.he305.streamfeeder.application.service;

import com.github.he305.streamfeeder.application.exceptions.YoutubeStreamExternalDataMappingException;
import com.github.he305.streamfeeder.application.mapper.youtube.YoutubeStreamExternalDataMapper;
import com.github.he305.streamfeeder.common.entity.StreamExternalData;
import com.github.he305.streamfeeder.common.exception.StreamExternalServiceException;
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
class YoutubeStreamExternalServiceImplTest {

    @Mock
    private RestTemplate restTemplate;
    @Mock
    private YoutubeStreamExternalDataMapper youtubeMapper;

    @InjectMocks
    private YoutubeStreamExternalServiceImpl underTest;


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
    void getStreamExternalDataForNickname_parseError() {
        String body = "";
        ResponseEntity<String> response = ResponseEntity.ok(body);
        Mockito.when(restTemplate.exchange(Mockito.anyString(), eq(HttpMethod.GET), Mockito.any(), eq(String.class)))
                .thenReturn(response);
        Mockito.when(youtubeMapper.getData(body)).thenThrow(YoutubeStreamExternalDataMappingException.class);

        assertThrows(StreamExternalServiceException.class, () ->
                underTest.getStreamExternalDataForNickname(""));
    }

    @Test
    void getStreamExternalDataForNickname_valid() {
        StreamExternalData expected = StreamExternalData.emptyData();

        String body = "";
        ResponseEntity<String> response = ResponseEntity.ok(body);
        Mockito.when(restTemplate.exchange(Mockito.anyString(), eq(HttpMethod.GET), Mockito.any(), eq(String.class)))
                .thenReturn(response);
        Mockito.when(youtubeMapper.getData(body)).thenReturn(expected);

        StreamExternalData actual = underTest.getStreamExternalDataForNickname("");
        assertEquals(expected, actual);
    }
}