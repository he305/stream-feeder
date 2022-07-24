package com.github.he305.streamfeeder.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StreamEnd {
    private LocalDateTime time;
    private Long channelId;
}