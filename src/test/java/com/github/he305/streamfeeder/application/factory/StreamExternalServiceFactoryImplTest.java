package com.github.he305.streamfeeder.application.factory;

import com.github.he305.streamfeeder.common.service.StreamExternalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@TestPropertySource(properties = "app.scheduling.enable=false")
class StreamExternalServiceFactoryImplTest {

    @Autowired
    private List<StreamExternalService> serviceList;
    @Autowired
    private StreamExternalServiceFactoryImpl underTest;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getStreamExternalService_success() {
        serviceList.forEach(service -> {
            StreamExternalService actual = underTest.getStreamExternalService(service.getServiceType());
            assertEquals(service.getServiceType(), actual.getServiceType());
        });
    }
}