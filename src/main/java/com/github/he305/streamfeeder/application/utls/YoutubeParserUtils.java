package com.github.he305.streamfeeder.application.utls;

public interface YoutubeParserUtils {
    boolean getIsLive(String htmlBody);

    String getTitle(String htmlBody);

    String getCategory(String htmlBody);

    int getViewerCount(String htmlBody);
}
