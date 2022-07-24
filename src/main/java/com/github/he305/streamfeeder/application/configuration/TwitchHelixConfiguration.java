package com.github.he305.streamfeeder.application.configuration;

import com.github.twitch4j.TwitchClientBuilder;
import com.github.twitch4j.helix.TwitchHelix;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class TwitchHelixConfiguration {
    @Value("${twitch-client-id-env:default}")
    private String clientId;

    @Value("${twitch-client-secret-env:default}")
    private String clientSecret;

    @Bean
    public TwitchHelix getTwitchClient() {
        log.info("Client id: " + clientId);
        log.info("Client secret: " + clientSecret);
        return TwitchClientBuilder.builder()
                .withEnableHelix(true)
                .withClientId(clientId)
                .withClientSecret(clientSecret)
                .build().getHelix();
    }
}
