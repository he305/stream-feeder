package com.github.he305.streamfeeder.application.service;

import com.github.he305.streamfeeder.application.exceptions.YoutubeStreamExternalDataMappingException;
import com.github.he305.streamfeeder.application.mapper.youtube.YoutubeStreamExternalDataMapper;
import com.github.he305.streamfeeder.common.entity.StreamExternalData;
import com.github.he305.streamfeeder.common.exception.StreamExternalServiceException;
import com.github.he305.streamfeeder.common.service.YoutubeStreamExternalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class YoutubeStreamExternalServiceImpl extends YoutubeStreamExternalService {

    private final RestTemplate restTemplate;
    private final YoutubeStreamExternalDataMapper youtubeMapper;

    @Override
    public StreamExternalData getStreamExternalDataForNickname(String nickname) throws StreamExternalServiceException {
        String url = String.format("https://youtube.com/channel/%s/live", nickname);
        ResponseEntity<String> response;
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        } catch (ResourceAccessException | HttpClientErrorException | HttpServerErrorException ex) {
            throw new StreamExternalServiceException("HTTP error: " + ex.getMessage());
        }

        if (response.getBody() == null) {
            throw new StreamExternalServiceException("Response body is null");
        }

        String body = response.getBody();

        try {
            return youtubeMapper.getData(body);
        } catch (YoutubeStreamExternalDataMappingException ex) {
            throw new StreamExternalServiceException("Parsing error: " + ex.getMessage());
        }
    }
}
