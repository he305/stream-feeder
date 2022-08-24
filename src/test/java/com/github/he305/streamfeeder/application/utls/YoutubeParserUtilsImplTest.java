package com.github.he305.streamfeeder.application.utls;

import com.github.he305.streamfeeder.application.exceptions.YoutubeUtilParsingException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class YoutubeParserUtilsImplTest {

    private final YoutubeParserUtilsImpl underTest = new YoutubeParserUtilsImpl();

    @SneakyThrows
    private String getHtml() {
        String path = "src/test/resources/migochi.txt";

        File file = new File(path);
        String absolutePath = file.getAbsolutePath();

        return new String(Files.readAllBytes(Paths.get(absolutePath)));
    }

    @Test
    void getIsLive_live() {
        String liveString = getHtml();
        boolean expected = true;
        boolean actual = underTest.getIsLive(liveString);
        assertEquals(expected, actual);
    }

    @Test
    void getIsLive_false() {
        String liveString = getHtml();
        liveString = liveString.replace("\"OK\"", "\"SOME_ERROR\"");
        boolean expected = false;
        boolean actual = underTest.getIsLive(liveString);
        assertEquals(expected, actual);
    }

    @Test
    void getIsLive_brokenString() {
        String liveString = "sadasdad";
        boolean expected = false;
        boolean actual = underTest.getIsLive(liveString);
        assertEquals(expected, actual);
    }

    @Test
    void getTitle() {
        String titlePart = getHtml();
        String expected = "Minecraft migo";
        String actual = underTest.getTitle(titlePart);
        assertEquals(expected, actual);
    }

    @Test
    void getTitle_parsingError_returnNotSet() {
        String errorString = "asdada";
        assertThrows(YoutubeUtilParsingException.class, () ->
                underTest.getTitle(errorString));
    }

    @Test
    void getCategory() {
        String categoryPart = getHtml();

        String expected = "Minecraft";
        String actual = underTest.getCategory(categoryPart);
        assertEquals(expected, actual);
    }

    @Test
    void getCategory_parsingError() {
        String errorString = "asdada";
        String expected = "Not set";
        String actual = underTest.getCategory(errorString);
        assertEquals(expected, actual);
    }

    @Test
    void getViewerCount() {
        String viewerPart = getHtml();

        int expected = 20337;
        int actual = underTest.getViewerCount(viewerPart);
        assertEquals(expected, actual);
    }

    @Test
    void getViewerCount_badNumber() {
        String errorString = "\"viewCount\":{\"videoViewCountRenderer\":{\"viewCount\":{\"runs\":[{\"text\":\".*\"},{\"text\":\"not a number\"}]},\"isLive\":true}}";
        assertThrows(YoutubeUtilParsingException.class, () ->
                underTest.getViewerCount(errorString));
    }

    @Test
    void getViewerCount_parsingError() {
        String errorString = "asdada";
        assertThrows(YoutubeUtilParsingException.class, () ->
                underTest.getViewerCount(errorString));
    }
}