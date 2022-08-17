package com.github.he305.streamfeeder.application.service;

import com.github.he305.streamfeeder.application.dto.v2.JwtRefreshTokenDto;
import com.github.he305.streamfeeder.application.dto.v2.JwtResponseDto;
import com.github.he305.streamfeeder.application.dto.v2.LoginRequestDto;
import com.github.he305.streamfeeder.application.dto.v2.RegisterServiceRequestDto;
import com.github.he305.streamfeeder.common.exception.ProducerExchangeNetworkException;
import com.github.he305.streamfeeder.common.exception.ProducerExchangeNetworkNullResponseException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class AuthorizationExchangeServiceImpl implements AuthorizationExchangeService {

    public static final String AUTH_POINT = "/auth";
    private final RestTemplate restTemplate;
    @Value("${producer-api.v2.base_url:default}")
    public String baseUrl;

    @Value("${producer-api.v2.username:default}")
    public String username;

    @Value("${producer-api.v2.password:default}")
    public String password;

    @Value("${producer-api.v2.service-register-key}")
    public String serviceRegisterKey;

    private String refreshToken;

    private String getAccessTokenByLogin() {
        String url = baseUrl + AUTH_POINT + "/login";
        LoginRequestDto dto = new LoginRequestDto(
                username,
                password
        );

        JwtResponseDto response;
        try {
            response = restTemplate.postForObject(url, dto, JwtResponseDto.class);
        } catch (ResourceAccessException | HttpServerErrorException ex) {
            throw new ProducerExchangeNetworkException(ex.getMessage());
        } catch (HttpClientErrorException ex) {
            return registerService();
        }

        if (response == null) {
            throw new ProducerExchangeNetworkNullResponseException(url);
        }

        refreshToken = response.getRefreshToken();
        return response.getToken();
    }

    private String registerService() {
        String url = baseUrl + AUTH_POINT + "/register-service";
        RegisterServiceRequestDto dto = new RegisterServiceRequestDto(
                username,
                password,
                serviceRegisterKey
        );

        JwtResponseDto response;
        try {
            response = restTemplate.postForObject(url, dto, JwtResponseDto.class);
        } catch (ResourceAccessException | HttpClientErrorException | HttpServerErrorException ex) {
            throw new ProducerExchangeNetworkException(ex.getMessage());
        }

        if (response == null) {
            throw new ProducerExchangeNetworkNullResponseException(url);
        }

        refreshToken = response.getRefreshToken();
        return response.getToken();
    }

    private String getAccessTokenByRefresh() {
        String url = baseUrl + AUTH_POINT + "/refresh";
        JwtRefreshTokenDto jwtRefreshTokenDto = new JwtRefreshTokenDto(
                refreshToken
        );

        JwtResponseDto response;
        try {
            response = restTemplate.postForObject(url, jwtRefreshTokenDto, JwtResponseDto.class);
        } catch (ResourceAccessException | HttpClientErrorException | HttpServerErrorException ex) {
            return getAccessTokenByLogin();
        }

        if (response == null) {
            throw new ProducerExchangeNetworkNullResponseException(url);
        }

        refreshToken = response.getRefreshToken();
        return response.getToken();
    }

    private String getAccessToken() {
        if (refreshToken != null) {
            return getAccessTokenByRefresh();
        }
        return getAccessTokenByLogin();
    }

    @Override
    public HttpHeaders getAuthHeaders() {
        String token = getAccessToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);
        return headers;
    }
}
