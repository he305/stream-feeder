package com.github.he305.streamfeeder.application.mapper.youtube;

import com.github.he305.streamfeeder.application.exceptions.YoutubeStreamExternalDataMappingException;
import com.github.he305.streamfeeder.application.exceptions.YoutubeUtilParsingException;
import com.github.he305.streamfeeder.application.utls.YoutubeParserUtils;
import com.github.he305.streamfeeder.common.entity.StreamExternalData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class YoutubeStreamExternalDataMapperImplTest {

    @Mock
    private YoutubeParserUtils youtubeParser;
    @InjectMocks
    private YoutubeStreamExternalDataMapperImpl underTest;

    @Test
    void getData_emptyData() {
        String body = "";
        when(youtubeParser.getIsLive(body)).thenReturn(false);
        StreamExternalData expected = StreamExternalData.emptyData();
        StreamExternalData actual = underTest.getData(body);
        assertEquals(expected, actual);
    }

    @Test
    void getData_parsingError() {
        String body = "";
        when(youtubeParser.getIsLive(body)).thenReturn(true);
        when(youtubeParser.getTitle(body)).thenThrow(YoutubeUtilParsingException.class);
        assertThrows(YoutubeStreamExternalDataMappingException.class, () ->
                underTest.getData(body));
    }

    @Test
    void getData_valid() {
        String body = "";
        String title = "title";
        String category = "category";
        int viewerCount = 322;
        StreamExternalData expected = StreamExternalData.newData(category, title, viewerCount);

        when(youtubeParser.getIsLive(body)).thenReturn(true);
        when(youtubeParser.getTitle(body)).thenReturn(title);
        when(youtubeParser.getCategory(body)).thenReturn(category);
        when(youtubeParser.getViewerCount(body)).thenReturn(viewerCount);

        StreamExternalData actual = underTest.getData(body);
        assertEquals(expected, actual);
    }
}