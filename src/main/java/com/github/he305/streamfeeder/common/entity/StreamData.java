package com.github.he305.streamfeeder.common.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StreamData {
    private String gameName;
    private String title;
    private Integer viewerCount;
    private LocalDateTime time;
    private Long channelId;
}
