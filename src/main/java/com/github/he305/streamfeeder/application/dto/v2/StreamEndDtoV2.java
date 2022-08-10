package com.github.he305.streamfeeder.application.dto.v2;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class StreamEndDtoV2 {
    private final UUID streamerChannelId;
    private final LocalDateTime endTime;
}
