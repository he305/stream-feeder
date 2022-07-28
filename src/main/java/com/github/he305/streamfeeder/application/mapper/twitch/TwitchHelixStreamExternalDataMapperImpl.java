package com.github.he305.streamfeeder.application.mapper.twitch;

import com.github.he305.streamfeeder.common.entity.StreamExternalData;
import com.github.twitch4j.helix.domain.Stream;
import com.github.twitch4j.helix.domain.StreamList;
import org.springframework.stereotype.Component;

@Component
public class TwitchHelixStreamExternalDataMapperImpl implements TwitchHelixStreamExternalDataMapper {
    @Override
    public StreamExternalData getData(StreamList streamList) {
        if (streamList.getStreams().isEmpty()) {
            return StreamExternalData.emptyData();
        }

        Stream stream = streamList.getStreams().get(0);

        return StreamExternalData.newData(stream.getGameName(), stream.getTitle(), stream.getViewerCount());
    }
}
