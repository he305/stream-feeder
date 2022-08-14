package com.github.he305.streamfeeder.application.service;

import com.github.he305.streamfeeder.application.dto.v2.StreamChannelListDtoV2;
import com.github.he305.streamfeeder.application.dto.v2.StreamDataDtoV2;
import com.github.he305.streamfeeder.application.dto.v2.StreamEndDtoV2;
import com.github.he305.streamfeeder.application.mapper.exchange.ChannelMapperV2;
import com.github.he305.streamfeeder.common.dto.StreamEnd;
import com.github.he305.streamfeeder.common.entity.Channel;
import com.github.he305.streamfeeder.common.entity.StreamData;
import com.github.he305.streamfeeder.common.exception.ProducerExchangeException;
import com.github.he305.streamfeeder.common.exception.ProducerExchangeNetworkException;
import com.github.he305.streamfeeder.common.service.ProducerExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProducerExchangeServiceV2 implements ProducerExchangeService {

    public static final String CHANNEL_POINT = "/stream";
    private final ChannelMapperV2 channelMapper;
    private final RestTemplate restTemplate;
    private final AuthorizationExchangeService authorizationExchangeService;
    @Value("${producer-api.v2.base_url:default}")
    public String baseUrl;

    @Override
    public List<Channel> getChannels() throws ProducerExchangeException {
        HttpHeaders headers = authorizationExchangeService.getAuthHeaders();

        String url = baseUrl + CHANNEL_POINT;

        ResponseEntity<StreamChannelListDtoV2> res;
        try {
            res = restTemplate.exchange(RequestEntity.get(url).headers(headers).build(), StreamChannelListDtoV2.class);
        } catch (ResourceAccessException | HttpClientErrorException | HttpServerErrorException ex) {
            throw new ProducerExchangeNetworkException(ex.getMessage());
        }


        StreamChannelListDtoV2 body = res.getBody();
        if (body == null) {
            throw new ProducerExchangeException("Result for getChannels is null");
        }

        return body.getChannels().stream().map(channelMapper::toChannel).collect(Collectors.toList());
    }

    @Override
    public boolean addStreamData(StreamData data) throws ProducerExchangeException {
        HttpHeaders headers = authorizationExchangeService.getAuthHeaders();
        String url = baseUrl + CHANNEL_POINT + "/data";

        StreamDataDtoV2 streamDataDtoV2 = new StreamDataDtoV2(
                data.getChannelId(),
                data.getGameName(),
                data.getTitle(),
                data.getViewerCount(),
                data.getTime()
        );

        HttpEntity<StreamDataDtoV2> entity = new HttpEntity<>(streamDataDtoV2, headers);

        ResponseEntity<Void> res;
        try {
            res = restTemplate.exchange(url, HttpMethod.POST, entity, Void.class);
        } catch (ResourceAccessException | HttpClientErrorException | HttpServerErrorException ex) {
            throw new ProducerExchangeNetworkException(ex.getMessage());
        }

        HttpStatus code = res.getStatusCode();
        return code == HttpStatus.OK;
    }

    @Override
    public boolean endStream(StreamEnd request) throws ProducerExchangeException {
        HttpHeaders headers = authorizationExchangeService.getAuthHeaders();
        String url = baseUrl + CHANNEL_POINT + "/end";

        StreamEndDtoV2 streamEndDtoV2 = new StreamEndDtoV2(
                request.getChannelId(),
                request.getTime()
        );

        HttpEntity<StreamEndDtoV2> entity = new HttpEntity<>(streamEndDtoV2, headers);

        ResponseEntity<Void> res;
        try {
            res = restTemplate.exchange(url, HttpMethod.POST, entity, Void.class);
        } catch (ResourceAccessException | HttpClientErrorException | HttpServerErrorException ex) {
            throw new ProducerExchangeNetworkException(ex.getMessage());
        }

        HttpStatus code = res.getStatusCode();
        return code == HttpStatus.OK;
    }
}
