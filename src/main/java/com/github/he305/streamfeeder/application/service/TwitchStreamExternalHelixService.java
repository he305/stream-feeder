package com.github.he305.streamfeeder.application.service;

import com.github.he305.streamfeeder.application.mapper.twitch.TwitchHelixStreamExternalDataMapper;
import com.github.he305.streamfeeder.common.entity.StreamExternalData;
import com.github.he305.streamfeeder.common.exception.StreamExternalServiceException;
import com.github.he305.streamfeeder.common.service.TwitchStreamExternalService;
import com.github.twitch4j.helix.TwitchHelix;
import com.github.twitch4j.helix.domain.StreamList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TwitchStreamExternalHelixService extends TwitchStreamExternalService {

    private final TwitchHelix twitchClient;
    private final TwitchHelixStreamExternalDataMapper twitchHelixStreamExternalDataMapper;

    @Override
    public StreamExternalData getStreamExternalDataForNickname(String nickname) {
        try {
            StreamList streamList = twitchClient.getStreams(null, null, null, 100, null, null, null, List.of(nickname)).execute();
            return twitchHelixStreamExternalDataMapper.getData(streamList);
        } catch (RuntimeException e) {
            throw new StreamExternalServiceException("Error while retrieving twitch data, ex: " + e.getMessage());
        }
    }
}
