package com.github.he305.streamfeeder.application.dto;

import com.github.he305.streamfeeder.common.entity.Platform;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChannelDto {
    private Long id;
    private String nickname;
    private String personFullName;
    private Platform platform;
    private Boolean isLive;
}
