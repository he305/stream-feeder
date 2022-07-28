package com.github.he305.streamfeeder.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Channel {
    private Long id;
    private String nickname;
    private Boolean isLive;
    private Platform platform;
    private Long personId;
}
