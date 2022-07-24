package com.github.he305.streamfeeder.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class StreamEndDto {
    private LocalDateTime time;
}
