package com.github.he305.streamfeeder.application.dto.v2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class JwtResponseDto {
    private final String type = "Bearer";
    private String token;
    private String refreshToken;
}
