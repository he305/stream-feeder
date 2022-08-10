package com.github.he305.streamfeeder.application.dto.v2;

import com.github.he305.streamfeeder.common.entity.Platform;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ChannelDtoV2 {
    private UUID id;
    private String channelName;
    private Platform platform;
    private boolean live;
}
