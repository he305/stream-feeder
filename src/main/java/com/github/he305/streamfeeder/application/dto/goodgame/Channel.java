package com.github.he305.streamfeeder.application.dto.goodgame;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class Channel {
    @JsonProperty(value = "stream_id")
    private String streamId;
    private String title;
    private String status;
    private Integer viewers;
    @JsonProperty(value = "games")
    private String game;
}
