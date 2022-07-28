package com.github.he305.streamfeeder.common.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode
public class StreamExternalData {
    private String gameName;
    private String title;
    private Integer viewerCount;
    private Boolean isLive;

    public static StreamExternalData emptyData() {
        return StreamExternalData.builder().isLive(false).build();
    }

    public static StreamExternalData newData(String gameName, String title, Integer viewerCount) {
        return StreamExternalData.builder()
                .isLive(true)
                .gameName(gameName)
                .title(title)
                .viewerCount(viewerCount)
                .build();
    }

    private StreamExternalData(){}
    private StreamExternalData(String gameName, String title, Integer viewerCount, Boolean isLive) {
        this.gameName = gameName;
        this.title = title;
        this.viewerCount = viewerCount;
        this.isLive = isLive;
    }
}
