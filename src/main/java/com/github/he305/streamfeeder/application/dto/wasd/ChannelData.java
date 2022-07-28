package com.github.he305.streamfeeder.application.dto.wasd;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChannelData {
    @JsonProperty(value = "channel_id")
    private int channelId;
    @JsonProperty(value = "channel_is_live")
    private boolean isLive;
}
