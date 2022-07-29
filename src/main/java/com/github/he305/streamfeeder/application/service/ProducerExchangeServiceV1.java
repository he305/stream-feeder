package com.github.he305.streamfeeder.application.service;

import com.github.he305.streamfeeder.application.dto.ChannelList;
import com.github.he305.streamfeeder.application.dto.StreamDataAddDto;
import com.github.he305.streamfeeder.application.dto.StreamEndDto;
import com.github.he305.streamfeeder.application.dto.StreamResponseDto;
import com.github.he305.streamfeeder.application.mapper.exchange.ChannelMapper;
import com.github.he305.streamfeeder.common.dto.StreamEnd;
import com.github.he305.streamfeeder.common.entity.Channel;
import com.github.he305.streamfeeder.common.entity.StreamData;
import com.github.he305.streamfeeder.common.exception.ProducerExchangeException;
import com.github.he305.streamfeeder.common.exception.ProducerExchangeNetworkException;
import com.github.he305.streamfeeder.common.service.ProducerExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProducerExchangeServiceV1 implements ProducerExchangeService {

    public static final String CHANNEL_POINT = "/api/v1/channel/";
    @Value("${producer-api.v1:default}")
    public String baseUrl;

    private final RestTemplate restTemplate;
    private final ChannelMapper mapper;

    @Override
    public List<Channel> getChannels() throws ProducerExchangeException {
        String url = baseUrl + CHANNEL_POINT;
        ChannelList channelList;
        try {
            channelList = restTemplate.getForObject(url, ChannelList.class);
        } catch (ResourceAccessException | HttpClientErrorException | HttpServerErrorException ex) {
            throw new ProducerExchangeNetworkException(ex.getMessage());
        }

        if (channelList == null) {
            throw new ProducerExchangeException("Response object is null");
        }

        return channelList.getChannels().stream().map(mapper::toChannel).collect(Collectors.toList());
    }

    @Override
    public boolean addStreamData(StreamData data) throws ProducerExchangeException {
        StreamDataAddDto dto = new StreamDataAddDto(
                data.getGameName(),
                data.getTitle(),
                data.getViewerCount(),
                data.getTime()
        );
        String url = String.format("%s%s%d/streamData", baseUrl, CHANNEL_POINT, data.getChannelId());
        ResponseEntity<StreamResponseDto> response;
        try {
            response = restTemplate.postForEntity(url, dto, StreamResponseDto.class);
        } catch (ResourceAccessException | HttpClientErrorException | HttpServerErrorException ex) {
            throw new ProducerExchangeNetworkException(ex.getMessage());
        }

        HttpStatus code = response.getStatusCode();
        return code == HttpStatus.OK;
    }

    @Override
    public boolean endStream(StreamEnd data) throws ProducerExchangeException {
        StreamEndDto dto = new StreamEndDto(
                data.getTime()
        );

        String url = String.format("%s%s%d/end", baseUrl, CHANNEL_POINT, data.getChannelId());
        HttpEntity<StreamEndDto> request = new HttpEntity<>(dto);
        ResponseEntity<StreamResponseDto> response;
        try {
            response = restTemplate.exchange(url, HttpMethod.PUT, request, StreamResponseDto.class);
        } catch (ResourceAccessException | HttpClientErrorException | HttpServerErrorException ex) {
            throw new ProducerExchangeNetworkException(ex.getMessage());
        }

        HttpStatus code = response.getStatusCode();
        return code == HttpStatus.OK;
    }
}
