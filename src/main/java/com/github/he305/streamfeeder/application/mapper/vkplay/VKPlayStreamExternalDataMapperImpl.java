package com.github.he305.streamfeeder.application.mapper.vkplay;

import com.github.he305.streamfeeder.application.dto.vkplay.VKPlayDto;
import com.github.he305.streamfeeder.common.entity.StreamExternalData;
import org.springframework.stereotype.Component;

@Component
public class VKPlayStreamExternalDataMapperImpl implements VKPlayStreamExternalDataMapper {
    @Override
    public StreamExternalData getData(VKPlayDto dto) {
        if (!dto.isIsOnline()) {
            return StreamExternalData.emptyData();
        }

        return StreamExternalData.newData(
                dto.getCategory().getTitle(),
                dto.getTitle(),
                dto.getCount().getViewers()
        );
    }
}
