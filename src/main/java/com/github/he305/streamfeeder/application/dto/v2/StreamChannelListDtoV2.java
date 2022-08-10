package com.github.he305.streamfeeder.application.dto.v2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StreamChannelListDtoV2 {
    private List<ChannelDtoV2> channels;
}
