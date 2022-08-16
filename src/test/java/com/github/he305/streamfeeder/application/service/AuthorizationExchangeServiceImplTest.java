package com.github.he305.streamfeeder.application.service;

import com.github.he305.streamfeeder.application.dto.v2.JwtResponseDto;
import com.github.he305.streamfeeder.common.exception.ProducerExchangeNetworkException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class AuthorizationExchangeServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private AuthorizationExchangeServiceImpl underTest;

    private void prepareValues() {
        ReflectionTestUtils.setField(underTest, "baseUrl", "test", String.class);
        ReflectionTestUtils.setField(underTest, "username", "test", String.class);
        ReflectionTestUtils.setField(underTest, "password", "test", String.class);
        ReflectionTestUtils.setField(underTest, "serviceRegisterKey", "test", String.class);
    }

    @Test
    void getAuthHeaders_getAccessTokenByLogin_restTemplateException() {
        prepareValues();
        Mockito.when(restTemplate.postForObject(Mockito.anyString(), Mockito.any(), Mockito.any())).thenThrow(ResourceAccessException.class);
        assertThrows(ProducerExchangeNetworkException.class, () ->
                underTest.getAuthHeaders());
    }

    @Test
    void getAuthHeaders_getAccessTokenByLogin_loginFail_registerFail() {
        prepareValues();
        String loginUrl = "test/api/auth/login";
        Mockito.when(restTemplate.postForObject(eq(loginUrl), Mockito.any(), Mockito.any())).thenThrow(HttpClientErrorException.class);
        String registerServiceUrl = "test/api/auth/registerService";
        Mockito.when(restTemplate.postForObject(eq(registerServiceUrl), Mockito.any(), Mockito.any())).thenThrow(HttpClientErrorException.class);

        assertThrows(ProducerExchangeNetworkException.class, () ->
                underTest.getAuthHeaders());
    }

    @Test
    void getAuthHeaders_getAccessTokenByLogin_loginFail_registerSuccess() {
        prepareValues();
        HttpHeaders expected = new HttpHeaders();
        expected.setContentType(MediaType.APPLICATION_JSON);
        expected.set("Authorization", "Bearer test");

        String loginUrl = "test/api/auth/login";
        Mockito.when(restTemplate.postForObject(eq(loginUrl), Mockito.any(), Mockito.any())).thenThrow(HttpClientErrorException.class);
        String registerServiceUrl = "test/api/auth/registerService";

        JwtResponseDto resultDto = new JwtResponseDto("test", "refresh");
        Mockito.when(restTemplate.postForObject(eq(registerServiceUrl), Mockito.any(), Mockito.any())).thenReturn(resultDto);

        HttpHeaders actual = underTest.getAuthHeaders();
        assertEquals(expected, actual);
    }

    @Test
    void getAuthHeaders_getAccessTokenByLogin_valid() {
        prepareValues();
        HttpHeaders expected = new HttpHeaders();
        expected.setContentType(MediaType.APPLICATION_JSON);
        expected.set("Authorization", "Bearer test");

        JwtResponseDto resultDto = new JwtResponseDto("test", "refresh");

        Mockito.when(restTemplate.postForObject(Mockito.anyString(), Mockito.any(), eq(JwtResponseDto.class))).thenReturn(resultDto);
        HttpHeaders actual = underTest.getAuthHeaders();
        assertEquals(expected, actual);
    }

    @Test
    void getAuthHeaders_getAccessTokenByRefresh_failedButGotFromLogin() {
        prepareValues();
        ReflectionTestUtils.setField(underTest, "refreshToken", "test", String.class);
        HttpHeaders expected = new HttpHeaders();
        expected.setContentType(MediaType.APPLICATION_JSON);
        expected.set("Authorization", "Bearer test");

        String refreshUrl = "test/api/auth/refresh";
        Mockito.when(restTemplate.postForObject(eq(refreshUrl), Mockito.any(), Mockito.any())).thenThrow(HttpClientErrorException.class);

        String loginUrl = "test/api/auth/login";
        JwtResponseDto resultDto = new JwtResponseDto("test", "refresh");

        Mockito.when(restTemplate.postForObject(eq(loginUrl), Mockito.any(), Mockito.any())).thenReturn(resultDto);
        HttpHeaders actual = underTest.getAuthHeaders();
        assertEquals(expected, actual);
    }

    @Test
    void getAuthHeaders_getAccessTokenByRefresh_totalFail() {
        prepareValues();
        ReflectionTestUtils.setField(underTest, "refreshToken", "test", String.class);
        Mockito.when(restTemplate.postForObject(Mockito.anyString(), Mockito.any(), Mockito.any())).thenThrow(ResourceAccessException.class);
        assertThrows(ProducerExchangeNetworkException.class, () ->
                underTest.getAuthHeaders());
    }

    @Test
    void getAuthHeaders_getAccessTokenByRefresh_valid() {
        prepareValues();
        ReflectionTestUtils.setField(underTest, "refreshToken", "test", String.class);
        HttpHeaders expected = new HttpHeaders();
        expected.setContentType(MediaType.APPLICATION_JSON);
        expected.set("Authorization", "Bearer test");

        JwtResponseDto resultDto = new JwtResponseDto("test", "refresh");

        Mockito.when(restTemplate.postForObject(Mockito.anyString(), Mockito.any(), eq(JwtResponseDto.class))).thenReturn(resultDto);
        HttpHeaders actual = underTest.getAuthHeaders();
        assertEquals(expected, actual);
    }
}