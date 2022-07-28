package com.github.he305.streamfeeder.application.mapper.goodgame;

import com.github.he305.streamfeeder.application.dto.goodgame.Channel;
import com.github.he305.streamfeeder.common.entity.StreamExternalData;
import org.springframework.stereotype.Component;

@Component
public class GoodgameStreamExternalDataMapperImpl implements GoodgameStreamExternalDataMapper {
    @Override
    public StreamExternalData getData(Channel rawData) {
        if (rawData.getStatus().equals("Dead")) {
            return StreamExternalData.emptyData();
        }

        return buildStreamExternalDataFromChannel(rawData);
    }

    private StreamExternalData buildStreamExternalDataFromChannel(Channel rawData) {
        return StreamExternalData.newData(rawData.getGame(), rawData.getTitle(), rawData.getViewers());
    }
}
