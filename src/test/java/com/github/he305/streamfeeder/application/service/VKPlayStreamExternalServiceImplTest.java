package com.github.he305.streamfeeder.application.service;

import com.github.he305.streamfeeder.application.dto.vkplay.VKPlayDto;
import com.github.he305.streamfeeder.application.mapper.vkplay.VKPlayStreamExternalDataMapper;
import com.github.he305.streamfeeder.common.entity.StreamExternalData;
import com.github.he305.streamfeeder.common.exception.StreamExternalServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class VKPlayStreamExternalServiceImplTest {

    @Mock
    private RestTemplate restTemplate;
    @Mock
    private VKPlayStreamExternalDataMapper mapper;

    @InjectMocks
    private VKPlayStreamExternalServiceImpl underTest;

    @Test
    void getStreamExternalDataForNickname_networkException() {
        String nickname = "test";

        String url = "https://api.vkplay.live/v1/blog/test/public_video_stream";
        Mockito.when(restTemplate.getForObject(url, VKPlayDto.class)).thenThrow(ResourceAccessException.class);

        assertThrows(StreamExternalServiceException.class, () -> underTest.getStreamExternalDataForNickname(nickname));
    }

    @Test
    void getStreamExternalDataForNickname_valid() {
        String nickname = "test";

        String url = "https://api.vkplay.live/v1/blog/test/public_video_stream";
        VKPlayDto response = new VKPlayDto();
        Mockito.when(restTemplate.getForObject(url, VKPlayDto.class)).thenReturn(response);

        StreamExternalData expected = StreamExternalData.emptyData();
        Mockito.when(mapper.getData(response)).thenReturn(expected);

        StreamExternalData actual = underTest.getStreamExternalDataForNickname(nickname);

        assertEquals(expected, actual);
    }
}