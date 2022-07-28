package com.github.he305.streamfeeder.application.service;

import com.github.he305.streamfeeder.application.dto.wasd.Channel;
import com.github.he305.streamfeeder.application.mapper.wasd.WasdJsonChannelMapper;
import com.github.he305.streamfeeder.application.mapper.wasd.WasdStreamerExternalDataMapper;
import com.github.he305.streamfeeder.common.entity.StreamExternalData;
import com.github.he305.streamfeeder.common.exception.StreamExternalServiceException;
import com.github.he305.streamfeeder.common.service.WasdStreamExternalService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class WasdStreamExternalServiceImpl extends WasdStreamExternalService {

    private final RestTemplate restTemplate;
    private final WasdJsonChannelMapper wasdJsonChannelMapper;
    private final WasdStreamerExternalDataMapper wasdStreamerExternalDataMapper;
    private final HttpHeaders headers = new HttpHeaders();

    private static final String STREAMER_INFO_URL = "https://wasd.tv/api/v2/broadcasts/public?channel_name=";
    @Value("${wasd-token}")
    private String wasdToken;

    @PostConstruct
    private void initRequest() {
        headers.set("Authorization", "Token " + wasdToken);
    }

    @Override
    public StreamExternalData getStreamExternalDataForNickname(String nickname) throws StreamExternalServiceException {
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response;
        try {
            response = restTemplate.exchange(STREAMER_INFO_URL + nickname, HttpMethod.GET, requestEntity, String.class);
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            throw new StreamExternalServiceException("HTTP error: " + ex.getMessage());
        }

        if (response.getStatusCode().isError()) {
            throw new StreamExternalServiceException("Error retrieving wasd data, code: " + response.getStatusCode().value());
        }

        String body = response.getBody();
        if (body == null) {
            throw new StreamExternalServiceException("Response contains empty body");
        }

        Channel channel = wasdJsonChannelMapper.getChannel(body);

        return wasdStreamerExternalDataMapper.getData(channel);
    }
}
