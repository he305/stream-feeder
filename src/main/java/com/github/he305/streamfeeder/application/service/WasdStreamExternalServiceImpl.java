package com.github.he305.streamfeeder.application.service;

import com.github.he305.streamfeeder.application.dto.wasd.ChannelResult;
import com.github.he305.streamfeeder.application.dto.wasd.MediaContainer;
import com.github.he305.streamfeeder.common.entity.StreamExternalData;
import com.github.he305.streamfeeder.common.service.WasdStreamExternalService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class WasdStreamExternalServiceImpl extends WasdStreamExternalService {

    private final RestTemplate restTemplate;

    @Value("${wasd-token:default}")
    private String wasdToken;

    private static final String STREAMER_INFO_URL = "https://wasd.tv/api/v2/broadcasts/public?channel_name=";

    @Override
    public StreamExternalData getStreamExternalDataForNickname(String nickname) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Token " + wasdToken);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<ChannelResult> response = restTemplate.exchange(STREAMER_INFO_URL + nickname, HttpMethod.GET, requestEntity, ChannelResult.class);

        if (response.getStatusCode().isError()) {
            // TODO
            throw new RuntimeException();
        }

        return buildFromChannelResult(response.getBody());
    }

    private StreamExternalData buildFromChannelResult(ChannelResult result) {
        if (!result.result.channel.channel_is_live) {
            return StreamExternalData.emptyData();
        }
        return createStreamExternalDataFromMediaContainer(result.result.media_container);
    }

    private StreamExternalData createStreamExternalDataFromMediaContainer(MediaContainer container) {
        return StreamExternalData.newData(container.game.game_name, container.media_container_name, container.media_container_streams.get(0).stream_current_viewers);
    }
}
