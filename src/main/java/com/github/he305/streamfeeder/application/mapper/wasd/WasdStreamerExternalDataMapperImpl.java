package com.github.he305.streamfeeder.application.mapper.wasd;

import com.github.he305.streamfeeder.application.dto.wasd.Channel;
import com.github.he305.streamfeeder.common.entity.StreamExternalData;
import org.springframework.stereotype.Component;

@Component
public class WasdStreamerExternalDataMapperImpl implements WasdStreamerExternalDataMapper {
    @Override
    public StreamExternalData getData(Channel channel) {
        if (!channel.isLive()) {
            return StreamExternalData.emptyData();
        }
        return StreamExternalData.newData(channel.getGame(), channel.getTitle(), channel.getViewers());
    }
}
