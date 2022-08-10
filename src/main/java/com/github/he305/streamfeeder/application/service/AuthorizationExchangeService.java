package com.github.he305.streamfeeder.application.service;

import org.springframework.http.HttpHeaders;

public interface AuthorizationExchangeService {
    HttpHeaders getAuthHeaders();
}
