package com.github.he305.streamfeeder.application.service;

import com.github.he305.streamfeeder.common.entity.StreamExternalData;
import com.github.he305.streamfeeder.common.service.TwitchStreamExternalService;
import com.github.twitch4j.helix.TwitchHelix;
import com.github.twitch4j.helix.domain.Stream;
import com.github.twitch4j.helix.domain.StreamList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TwitchStreamExternalHelixService implements TwitchStreamExternalService {

    private final TwitchHelix twitchClient;

    @Override
    public StreamExternalData getStreamExternalDataForNickname(String nickname) {
        StreamList streamList = twitchClient.getStreams(null, null, null, 100, null, null, null, List.of(nickname)).execute();
        return buildStreamExternalData(streamList);
    }

    private StreamExternalData buildStreamExternalData(StreamList streamList) {
        if (streamList.getStreams().isEmpty()) {
            return StreamExternalData.emptyData();
        }

        Stream stream = streamList.getStreams().get(0);

        return StreamExternalData.newData(stream.getGameName(), stream.getTitle(), stream.getViewerCount());
    }
}
