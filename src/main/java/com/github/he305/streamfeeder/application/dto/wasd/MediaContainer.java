package com.github.he305.streamfeeder.application.dto.wasd;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class MediaContainer {
    @JsonProperty(value = "media_container_name")
    private String name;
    private Game game;
    @JsonProperty(value = "media_container_streams")
    private List<MediaContainerStream> streams;
}
