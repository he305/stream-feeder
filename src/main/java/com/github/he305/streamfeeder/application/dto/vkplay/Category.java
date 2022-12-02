package com.github.he305.streamfeeder.application.dto.vkplay;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Category {
    @JsonProperty("title")
    private String title;

    public String getTitle() {
        return title;
    }
}