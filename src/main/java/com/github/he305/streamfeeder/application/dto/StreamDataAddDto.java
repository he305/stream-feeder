package com.github.he305.streamfeeder.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class StreamDataAddDto {
    private String gameName;
    private String title;
    private Integer viewerCount;
    private LocalDateTime time;
}
