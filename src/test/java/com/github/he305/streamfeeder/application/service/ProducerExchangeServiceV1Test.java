package com.github.he305.streamfeeder.application.service;

import com.github.he305.streamfeeder.application.dto.ChannelDto;
import com.github.he305.streamfeeder.application.dto.ChannelList;
import com.github.he305.streamfeeder.application.dto.StreamResponseDto;
import com.github.he305.streamfeeder.application.mapper.exchange.ChannelMapper;
import com.github.he305.streamfeeder.common.dto.StreamEnd;
import com.github.he305.streamfeeder.common.entity.Channel;
import com.github.he305.streamfeeder.common.entity.StreamData;
import com.github.he305.streamfeeder.common.exception.ProducerExchangeException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest
class ProducerExchangeServiceV1Test {

    @MockBean
    private RestTemplate restTemplate;
    @MockBean
    private ChannelMapper channelMapper;

    @Autowired
    private ProducerExchangeServiceV1 underTest;


    @Test
    void getChannels_networkError() {
        Mockito.when(restTemplate.getForObject(Mockito.anyString(), eq(ChannelList.class)))
                .thenThrow(HttpClientErrorException.class);

        assertThrows(ProducerExchangeException.class, () ->
                underTest.getChannels());
    }

    @Test
    void getChannels_nullList() {
        Mockito.when(restTemplate.getForObject(Mockito.anyString(), eq(ChannelList.class)))
                .thenReturn(null);

        assertThrows(ProducerExchangeException.class, () ->
                underTest.getChannels());
    }

    @Test
    void getChannels_success() {
        ChannelDto dto = new ChannelDto();
        ChannelList list = new ChannelList(List.of(dto));
        Channel expected = new Channel();

        Mockito.when(restTemplate.getForObject(Mockito.anyString(), eq(ChannelList.class)))
                .thenReturn(list);
        Mockito.when(channelMapper.toChannel(dto)).thenReturn(expected);

        List<Channel> actual = underTest.getChannels();
        assertEquals(1, actual.size());
        assertEquals(expected, actual.get(0));
    }

    @Test
    void addStreamData_networkError() {
        StreamData data = new StreamData("test", "title", 0, LocalDateTime.now(), 1L);
        Mockito.when(restTemplate.postForEntity(Mockito.anyString(), Mockito.any(), eq(StreamResponseDto.class)))
                .thenThrow(HttpClientErrorException.class);


        assertThrows(ProducerExchangeException.class, () ->
                underTest.addStreamData(data));
    }

    @Test
    void addStreamData_success() {
        StreamData data = new StreamData("test", "title", 0, LocalDateTime.now(), 1L);
        ResponseEntity<StreamResponseDto> response = ResponseEntity.ok(new StreamResponseDto(
                0L,
                LocalDateTime.now(),
                LocalDateTime.now(),
                0,
                1L
        ));

        Mockito.when(restTemplate.postForEntity(Mockito.anyString(), Mockito.any(), eq(StreamResponseDto.class)))
                .thenReturn(response);

        boolean code = underTest.addStreamData(data);
        assertTrue(code);
    }

    @Test
    void endStream_networkError() {
        StreamEnd data = new StreamEnd(LocalDateTime.now(), 1L);
        Mockito.when(restTemplate.exchange(Mockito.anyString(), eq(HttpMethod.PUT), Mockito.any(), eq(StreamResponseDto.class)))
                .thenThrow(HttpClientErrorException.class);

        assertThrows(ProducerExchangeException.class, () ->
                underTest.endStream(data));
    }

    @Test
    void endStream_success() {
        StreamEnd data = new StreamEnd(LocalDateTime.now(), 1L);
        ResponseEntity<StreamResponseDto> response = ResponseEntity.ok(new StreamResponseDto(
                0L,
                LocalDateTime.now(),
                LocalDateTime.now(),
                0,
                1L
        ));

        Mockito.when(restTemplate.exchange(Mockito.anyString(), eq(HttpMethod.PUT), Mockito.any(), eq(StreamResponseDto.class)))
                .thenReturn(response);

        boolean code = underTest.endStream(data);
        assertTrue(code);
    }
}