package com.github.he305.streamfeeder.application.service;

import com.github.he305.streamfeeder.application.dto.goodgame.Channel;
import com.github.he305.streamfeeder.application.mapper.goodgame.GoodgameStreamExternalDataMapper;
import com.github.he305.streamfeeder.application.mapper.goodgame.JsonChannelMapper;
import com.github.he305.streamfeeder.common.entity.StreamExternalData;
import com.github.he305.streamfeeder.common.exception.StreamExternalServiceException;
import com.github.he305.streamfeeder.common.exception.StreamExternalServiceMappingException;
import com.github.he305.streamfeeder.common.service.GoodgameStreamExternalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class GoodgameStreamExternalServiceImpl extends GoodgameStreamExternalService {

    private static final String GET_CHANNEL_STATUS_URL = "http://goodgame.ru/api/getchannelstatus?fmt=json&id=";
    private final JsonChannelMapper jsonChannelMapper;
    private final GoodgameStreamExternalDataMapper goodgameStreamExternalDataMapper;
    private final RestTemplate restTemplate;

    @Override
    public StreamExternalData getStreamExternalDataForNickname(String nickname) throws StreamExternalServiceException {
        ResponseEntity<String> response;
        try {
            response = restTemplate.getForEntity(GET_CHANNEL_STATUS_URL + nickname, String.class);
        } catch (HttpClientErrorException ex) {
            throw new StreamExternalServiceException("HTTP status: 4xx: " + ex.getMessage());
        } catch (HttpServerErrorException ex) {
            throw new StreamExternalServiceException("HTTP status: 5xx: " + ex.getMessage());
        } catch (ResourceAccessException ex) {
            throw new StreamExternalServiceException("HTTP error: " + ex.getMessage());
        }

        String rawJson = response.getBody();
        Channel channel;
        try {
            channel = jsonChannelMapper.getChannel(rawJson);
        } catch (StreamExternalServiceMappingException ex) {
            throw new StreamExternalServiceException("Couldn't process raw json, ex: " + ex.getMessage());
        }

        return goodgameStreamExternalDataMapper.getData(channel);
    }
}
