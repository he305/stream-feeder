package com.github.he305.streamfeeder.application.dto.v2;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RegisterServiceRequestDto {
    private String username;
    private String password;
    private String serviceRegisterKey;
}
