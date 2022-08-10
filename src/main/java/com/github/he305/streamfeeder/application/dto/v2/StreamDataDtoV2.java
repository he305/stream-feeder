package com.github.he305.streamfeeder.application.dto.v2;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class StreamDataDtoV2 {
    private final UUID streamChannelId;
    private final String name;
    private final String title;
    private final int viewerCount;
    private final LocalDateTime time;
}
