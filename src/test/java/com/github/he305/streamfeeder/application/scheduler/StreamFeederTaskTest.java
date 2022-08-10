package com.github.he305.streamfeeder.application.scheduler;

import com.github.he305.streamfeeder.common.entity.Channel;
import com.github.he305.streamfeeder.common.entity.Platform;
import com.github.he305.streamfeeder.common.entity.StreamExternalData;
import com.github.he305.streamfeeder.common.exception.ProducerExchangeException;
import com.github.he305.streamfeeder.common.exception.StreamExternalServiceException;
import com.github.he305.streamfeeder.common.factory.StreamExternalServiceFactory;
import com.github.he305.streamfeeder.common.service.ProducerExchangeService;
import com.github.he305.streamfeeder.common.service.StreamExternalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class StreamFeederTaskTest {

    @Mock
    private ProducerExchangeService service;
    @Mock
    private StreamExternalServiceFactory streamExternalServiceFactory;

    private StreamFeederTask underTest;

    @BeforeEach
    void setUp() {
        underTest = new StreamFeederTask(service, streamExternalServiceFactory);
    }

    @Test
    void doTask_fail_cantRetrieveChannels() {
        Mockito.when(service.getChannels()).thenThrow(ProducerExchangeException.class);
        boolean actual = underTest.doTask();
        assertFalse(actual);
    }

    @Test
    void doTask_fail_factoryCantCreate() {
        Channel channel = new Channel(UUID.randomUUID(), "name", true, Platform.TWITCH);
        List<Channel> channels = List.of(
                channel
        );

        Mockito.when(service.getChannels()).thenReturn(channels);
        Mockito.when(streamExternalServiceFactory.getStreamExternalService(channel.getPlatform())).thenThrow(UnsupportedOperationException.class);

        boolean actual = underTest.doTask();
        assertFalse(actual);
    }

    @Test
    void doTask_fail_externalServiceError() {
        Channel channel = new Channel(UUID.randomUUID(), "name", true, Platform.TWITCH);
        List<Channel> channels = List.of(
                channel
        );
        StreamExternalService mockService = Mockito.mock(StreamExternalService.class);

        Mockito.when(service.getChannels()).thenReturn(channels);
        Mockito.when(streamExternalServiceFactory.getStreamExternalService(channel.getPlatform())).thenReturn(mockService);
        Mockito.when(mockService.getStreamExternalDataForNickname(channel.getNickname())).thenThrow(StreamExternalServiceException.class);

        boolean actual = underTest.doTask();
        assertFalse(actual);
    }

    @Test
    void doTask_fail_addStreamData_Exception() {
        Channel channel = new Channel(UUID.randomUUID(), "name", true, Platform.TWITCH);
        List<Channel> channels = List.of(
                channel
        );
        StreamExternalService mockService = Mockito.mock(StreamExternalService.class);
        StreamExternalData data = StreamExternalData.newData("name", "title", 1);

        Mockito.when(service.getChannels()).thenReturn(channels);
        Mockito.when(streamExternalServiceFactory.getStreamExternalService(channel.getPlatform())).thenReturn(mockService);
        Mockito.when(mockService.getStreamExternalDataForNickname(channel.getNickname())).thenReturn(data);
        Mockito.when(service.addStreamData(Mockito.any())).thenThrow(ProducerExchangeException.class);

        boolean actual = underTest.doTask();
        assertFalse(actual);
    }

    @Test
    void doTask_fail_addStreamData_ErrorCode() {
        Channel channel = new Channel(UUID.randomUUID(), "name", true, Platform.TWITCH);
        List<Channel> channels = List.of(
                channel
        );
        StreamExternalService mockService = Mockito.mock(StreamExternalService.class);
        StreamExternalData data = StreamExternalData.newData("name", "title", 1);

        Mockito.when(service.getChannels()).thenReturn(channels);
        Mockito.when(streamExternalServiceFactory.getStreamExternalService(channel.getPlatform())).thenReturn(mockService);
        Mockito.when(mockService.getStreamExternalDataForNickname(channel.getNickname())).thenReturn(data);
        Mockito.when(service.addStreamData(Mockito.any())).thenReturn(false);

        boolean actual = underTest.doTask();
        assertFalse(actual);
    }

    @Test
    void doTask_fail_endStreamIfLive_Exception() {
        Channel channel = new Channel(UUID.randomUUID(), "name", true, Platform.TWITCH);
        List<Channel> channels = List.of(
                channel
        );
        StreamExternalService mockService = Mockito.mock(StreamExternalService.class);
        StreamExternalData data = StreamExternalData.emptyData();

        Mockito.when(service.getChannels()).thenReturn(channels);
        Mockito.when(streamExternalServiceFactory.getStreamExternalService(channel.getPlatform())).thenReturn(mockService);
        Mockito.when(mockService.getStreamExternalDataForNickname(channel.getNickname())).thenReturn(data);
        Mockito.when(service.endStream(Mockito.any())).thenThrow(ProducerExchangeException.class);

        boolean actual = underTest.doTask();
        assertFalse(actual);
    }

    @Test
    void doTask_fail_endStreamIfLive_ErrorCode() {
        Channel channel = new Channel(UUID.randomUUID(), "name", true, Platform.TWITCH);
        List<Channel> channels = List.of(
                channel
        );
        StreamExternalService mockService = Mockito.mock(StreamExternalService.class);
        StreamExternalData data = StreamExternalData.emptyData();

        Mockito.when(service.getChannels()).thenReturn(channels);
        Mockito.when(streamExternalServiceFactory.getStreamExternalService(channel.getPlatform())).thenReturn(mockService);
        Mockito.when(mockService.getStreamExternalDataForNickname(channel.getNickname())).thenReturn(data);
        Mockito.when(service.endStream(Mockito.any())).thenReturn(false);

        boolean actual = underTest.doTask();
        assertFalse(actual);
    }

    @Test
    void doTask_success_addStreamData() {
        Channel channel = new Channel(UUID.randomUUID(), "name", true, Platform.TWITCH);
        List<Channel> channels = List.of(
                channel
        );
        StreamExternalService mockService = Mockito.mock(StreamExternalService.class);
        StreamExternalData data = StreamExternalData.newData("name", "title", 1);

        Mockito.when(service.getChannels()).thenReturn(channels);
        Mockito.when(streamExternalServiceFactory.getStreamExternalService(channel.getPlatform())).thenReturn(mockService);
        Mockito.when(mockService.getStreamExternalDataForNickname(channel.getNickname())).thenReturn(data);
        Mockito.when(service.addStreamData(Mockito.any())).thenReturn(true);

        boolean actual = underTest.doTask();
        assertTrue(actual);
    }

    @Test
    void doTask_success_endStreamIfLive_ChannelOffline() {
        Channel channel = new Channel(UUID.randomUUID(), "name", false, Platform.TWITCH);
        List<Channel> channels = List.of(
                channel
        );
        StreamExternalService mockService = Mockito.mock(StreamExternalService.class);
        StreamExternalData data = StreamExternalData.emptyData();

        Mockito.when(service.getChannels()).thenReturn(channels);
        Mockito.when(streamExternalServiceFactory.getStreamExternalService(channel.getPlatform())).thenReturn(mockService);
        Mockito.when(mockService.getStreamExternalDataForNickname(channel.getNickname())).thenReturn(data);

        boolean actual = underTest.doTask();
        assertTrue(actual);
    }

    @Test
    void doTask_success_endStreamIfLive_endStream() {
        Channel channel = new Channel(UUID.randomUUID(), "name", true, Platform.TWITCH);
        List<Channel> channels = List.of(
                channel
        );
        StreamExternalService mockService = Mockito.mock(StreamExternalService.class);
        StreamExternalData data = StreamExternalData.emptyData();

        Mockito.when(service.getChannels()).thenReturn(channels);
        Mockito.when(streamExternalServiceFactory.getStreamExternalService(channel.getPlatform())).thenReturn(mockService);
        Mockito.when(mockService.getStreamExternalDataForNickname(channel.getNickname())).thenReturn(data);
        Mockito.when(service.endStream(Mockito.any())).thenReturn(true);

        boolean actual = underTest.doTask();
        assertTrue(actual);
    }
}