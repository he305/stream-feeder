package com.github.he305.streamfeeder.application.mapper.youtube;

import com.github.he305.streamfeeder.application.exceptions.YoutubeStreamExternalDataMappingException;
import com.github.he305.streamfeeder.application.exceptions.YoutubeUtilParsingException;
import com.github.he305.streamfeeder.application.utls.YoutubeParserUtils;
import com.github.he305.streamfeeder.common.entity.StreamExternalData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class YoutubeStreamExternalDataMapperImpl implements YoutubeStreamExternalDataMapper {
    private final YoutubeParserUtils youtubeParser;

    @Override
    public StreamExternalData getData(String htmlBody) {
        if (!youtubeParser.getIsLive(htmlBody)) {
            return StreamExternalData.emptyData();
        }

        try {
            String title = youtubeParser.getTitle(htmlBody);
            String category = youtubeParser.getCategory(htmlBody);
            int viewerCount = youtubeParser.getViewerCount(htmlBody);
            return StreamExternalData.newData(category, title, viewerCount);
        } catch (YoutubeUtilParsingException ex) {
            throw new YoutubeStreamExternalDataMappingException(ex.getMessage());
        }
    }
}
