package com.github.he305.streamfeeder.application.service;

import com.github.he305.streamfeeder.application.dto.vkplay.VKPlayDto;
import com.github.he305.streamfeeder.application.mapper.vkplay.VKPlayStreamExternalDataMapper;
import com.github.he305.streamfeeder.common.entity.StreamExternalData;
import com.github.he305.streamfeeder.common.exception.StreamExternalServiceException;
import com.github.he305.streamfeeder.common.service.VKPlayStreamExternalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class VKPlayStreamExternalServiceImpl extends VKPlayStreamExternalService {

    private static final String STREAMER_INFO_URL = "https://api.vkplay.live/v1/blog/%s/public_video_stream";
    private final RestTemplate restTemplate;
    private final VKPlayStreamExternalDataMapper mapper;

    @Override
    public StreamExternalData getStreamExternalDataForNickname(String nickname) throws StreamExternalServiceException {
        String url = String.format(STREAMER_INFO_URL, nickname);

        VKPlayDto response;
        try {
            response = restTemplate.getForObject(url, VKPlayDto.class);
        } catch (ResourceAccessException | HttpClientErrorException | HttpServerErrorException ex) {
            throw new StreamExternalServiceException("HTTP error: " + ex.getMessage());
        }

        return mapper.getData(response);
    }
}
