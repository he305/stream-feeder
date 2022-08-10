package com.github.he305.streamfeeder.application.service;

import com.github.he305.streamfeeder.application.dto.v2.ChannelDtoV2;
import com.github.he305.streamfeeder.application.dto.v2.StreamChannelListDtoV2;
import com.github.he305.streamfeeder.application.mapper.exchange.ChannelMapperV2;
import com.github.he305.streamfeeder.common.dto.StreamEnd;
import com.github.he305.streamfeeder.common.entity.Channel;
import com.github.he305.streamfeeder.common.entity.StreamData;
import com.github.he305.streamfeeder.common.exception.ProducerExchangeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class ProducerExchangeServiceV2Test {

    @Mock
    private RestTemplate restTemplate;
    @Mock
    private ChannelMapperV2 channelMapper;
    @Mock
    private AuthorizationExchangeService authorizationExchangeService;

    @InjectMocks
    private ProducerExchangeServiceV2 underTest;

    private void prepareValues() {
        ReflectionTestUtils.setField(underTest, "baseUrl", "test", String.class);
        HttpHeaders expected = new HttpHeaders();
        expected.setContentType(MediaType.APPLICATION_JSON);
        expected.set("Authorization", "Bearer test");
        Mockito.when(authorizationExchangeService.getAuthHeaders()).thenReturn(expected);
    }

    @Test
    void getChannels_networkError() {
        prepareValues();
        Mockito.when(restTemplate.exchange(Mockito.any(), eq(StreamChannelListDtoV2.class)))
                .thenThrow(HttpClientErrorException.class);

        assertThrows(ProducerExchangeException.class, () ->
                underTest.getChannels());
    }

    @Test
    void getChannels_nullList() {
        prepareValues();
        ResponseEntity<StreamChannelListDtoV2> res = ResponseEntity.ok(null);
        Mockito.when(restTemplate.exchange(Mockito.any(), eq(StreamChannelListDtoV2.class)))
                .thenReturn(res);

        assertThrows(ProducerExchangeException.class, () ->
                underTest.getChannels());
    }

    @Test
    void getChannels_success() {
        prepareValues();
        ChannelDtoV2 dto = new ChannelDtoV2();
        StreamChannelListDtoV2 list = new StreamChannelListDtoV2(List.of(dto));
        Channel expected = new Channel();

        ResponseEntity<StreamChannelListDtoV2> ret = ResponseEntity.ok(list);
        Mockito.when(restTemplate.exchange(Mockito.any(), eq(StreamChannelListDtoV2.class)))
                .thenReturn(ret);
        Mockito.when(channelMapper.toChannel(dto)).thenReturn(expected);

        List<Channel> actual = underTest.getChannels();
        assertEquals(1, actual.size());
        assertEquals(expected, actual.get(0));
    }

    @Test
    void addStreamData_networkError() {
        prepareValues();
        StreamData data = new StreamData("test", "title", 0, LocalDateTime.now(), UUID.randomUUID());
        Mockito.when(restTemplate.exchange(Mockito.anyString(), Mockito.any(), Mockito.any(), eq(Void.class)))
                .thenThrow(HttpClientErrorException.class);


        assertThrows(ProducerExchangeException.class, () ->
                underTest.addStreamData(data));
    }

    @Test
    void addStreamData_success() {
        prepareValues();
        StreamData data = new StreamData("test", "title", 0, LocalDateTime.now(), UUID.randomUUID());
        ResponseEntity<Void> response = ResponseEntity.ok().build();

        Mockito.when(restTemplate.exchange(Mockito.anyString(), Mockito.any(), Mockito.any(), eq(Void.class)))
                .thenReturn(response);

        boolean code = underTest.addStreamData(data);
        assertTrue(code);
    }

    @Test
    void endStream_networkError() {
        prepareValues();
        StreamEnd data = new StreamEnd(LocalDateTime.now(), UUID.randomUUID());
        Mockito.when(restTemplate.exchange(Mockito.anyString(), Mockito.any(), Mockito.any(), eq(Void.class)))
                .thenThrow(HttpClientErrorException.class);

        assertThrows(ProducerExchangeException.class, () ->
                underTest.endStream(data));
    }

    @Test
    void endStream_success() {
        prepareValues();
        StreamEnd data = new StreamEnd(LocalDateTime.now(), UUID.randomUUID());
        ResponseEntity<Void> response = ResponseEntity.ok().build();

        Mockito.when(restTemplate.exchange(Mockito.anyString(), Mockito.any(), Mockito.any(), eq(Void.class)))
                .thenReturn(response);

        boolean code = underTest.endStream(data);
        assertTrue(code);
    }
}