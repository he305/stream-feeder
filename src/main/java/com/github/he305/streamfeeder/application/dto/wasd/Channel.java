package com.github.he305.streamfeeder.application.dto.wasd;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonRootName(value = "result")
public class Channel {
    @JsonProperty(value = "channel")
    private ChannelData channelData;
    @JsonProperty(value = "media_container")
    private MediaContainer container;

    public boolean isLive() {
        return channelData.isLive();
    }

    public String getGame() {
        return container.getGame().getName();
    }

    public String getTitle() {
        return container.getName();
    }

    public int getViewers() {
        if (container.getStreams().isEmpty())
            return 0;

        return container.getStreams().get(0).getViewers();
    }
}
