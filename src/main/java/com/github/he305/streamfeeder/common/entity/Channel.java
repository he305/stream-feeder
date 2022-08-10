package com.github.he305.streamfeeder.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Channel {
    private UUID id;
    private String nickname;
    private Boolean isLive;
    private Platform platform;
}
