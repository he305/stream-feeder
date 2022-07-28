package com.github.he305.streamfeeder.application.factory;

import com.github.he305.streamfeeder.common.entity.Platform;
import com.github.he305.streamfeeder.common.factory.StreamExternalServiceFactory;
import com.github.he305.streamfeeder.common.service.StreamExternalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class StreamExternalServiceFactoryImpl implements StreamExternalServiceFactory {
    private static final Map<Platform, StreamExternalService> serviceMap = new EnumMap<>(Platform.class);
    private final List<StreamExternalService> services;

    @PostConstruct
    public void initServiceMap() {
        services.forEach(s -> serviceMap.put(s.getServiceType(), s));
    }

    @Override
    public StreamExternalService getStreamExternalService(Platform platform) throws UnsupportedOperationException {
        StreamExternalService service = serviceMap.get(platform);
        if (service == null) throw new UnsupportedOperationException();
        return service;
    }
}
