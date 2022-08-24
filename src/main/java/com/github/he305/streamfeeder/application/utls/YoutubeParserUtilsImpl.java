package com.github.he305.streamfeeder.application.utls;

import com.github.he305.streamfeeder.application.exceptions.YoutubeUtilParsingException;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class YoutubeParserUtilsImpl implements YoutubeParserUtils {


    private static final Pattern streamStatusPattern = Pattern.compile("\\{\"status\":\"(.*?)\",");
    private static final Pattern titlePattern = Pattern.compile("\\{\"contents\":\\[\\{\"videoPrimaryInfoRenderer\":\\{\"title\":\\{\"runs\":\\[\\{\"text\":\"(.*?)\"}");
    private static final Pattern categoryPattern = Pattern.compile("},\"title\":\\{\"simpleText\":\"(.*?)\"},\"subtitle\":\\{\"");
    private static final Pattern viewerPattern = Pattern.compile("\"viewCount\":\\{\"videoViewCountRenderer\":\\{\"viewCount\":\\{\"runs\":\\[\\{\"text\":\".*\"},\\{\"text\":\"(.*?)\"}]},\"isLive\":true}}");
    private static final String STREAM_IS_LIVE_STRING = "OK";

    @Override
    public boolean getIsLive(String htmlBody) {
        Matcher matcher = streamStatusPattern.matcher(htmlBody);
        if (matcher.find()) {
            return matcher.group(1).equals(STREAM_IS_LIVE_STRING);
        }
        return false;
    }

    @Override
    public String getTitle(String htmlBody) {
        Matcher matcher = titlePattern.matcher(htmlBody);
        if (matcher.find()) {
            return matcher.group(1);
        }
        throw new YoutubeUtilParsingException("Title not found");
    }

    @Override
    public String getCategory(String htmlBody) {
        Matcher matcher = categoryPattern.matcher(htmlBody);
        if (matcher.find()) {
            return matcher.group(1);
        }
        throw new YoutubeUtilParsingException("Category not found");
    }

    @Override
    public int getViewerCount(String htmlBody) {
        Matcher matcher = viewerPattern.matcher(htmlBody);
        if (matcher.find()) {
            try {
                return Integer.parseInt(matcher.group(1).trim().replace("\u00a0", " ").replace(" ", ""));
            } catch (NumberFormatException ex) {
                throw new YoutubeUtilParsingException("Error while parsing viewer count: " + ex.getMessage());
            }
        }
        throw new YoutubeUtilParsingException("Viewer count not found");
    }
}
