package com.github.he305.streamfeeder.application.dto.vkplay;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class VKPlayDto {
    @JsonProperty("count")
    private Count count;

    @JsonProperty("isOnline")
    private boolean isOnline;

    @JsonProperty("title")
    private String title;

    @JsonProperty("category")
    private Category category;

    public Count getCount() {
        return count;
    }

    public boolean isIsOnline() {
        return isOnline;
    }

    public String getTitle() {
        return title;
    }

    public Category getCategory() {
        return category;
    }
}