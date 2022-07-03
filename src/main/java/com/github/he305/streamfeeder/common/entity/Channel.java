package com.github.he305.streamfeeder.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Channel {
    private Long id;
    private String nickname;
    private String personFullName;
    private Platform platform;
}
