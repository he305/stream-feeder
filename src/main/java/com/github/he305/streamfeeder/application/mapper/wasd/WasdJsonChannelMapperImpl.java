package com.github.he305.streamfeeder.application.mapper.wasd;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.he305.streamfeeder.application.dto.wasd.Channel;
import com.github.he305.streamfeeder.common.exception.StreamExternalServiceMappingException;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class WasdJsonChannelMapperImpl implements WasdJsonChannelMapper {

    private final ObjectMapper mapper;

    public WasdJsonChannelMapperImpl() {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
    }

    @Override
    public Channel getChannel(String rawJson) throws StreamExternalServiceMappingException {
        try {
            return mapper.readValue(rawJson, Channel.class);
        } catch (JsonProcessingException | NoSuchElementException e) {
            throw new StreamExternalServiceMappingException("Error parsing raw json, ex: " + e.getMessage());
        }
    }
}
