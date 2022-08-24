package com.github.he305.streamfeeder.application.exceptions;

public class YoutubeStreamExternalDataMappingException extends RuntimeException {
    public YoutubeStreamExternalDataMappingException(String message) {
        super("Error while mapping youtube data, ex: " + message);
    }
}
