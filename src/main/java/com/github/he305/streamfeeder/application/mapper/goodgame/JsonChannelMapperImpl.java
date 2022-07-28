package com.github.he305.streamfeeder.application.mapper.goodgame;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.he305.streamfeeder.application.dto.goodgame.Channel;
import com.github.he305.streamfeeder.common.exception.StreamExternalServiceMappingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class JsonChannelMapperImpl implements JsonChannelMapper {
    private final ObjectMapper mapper;

    @Override
    public Channel getChannel(String rawJson) throws StreamExternalServiceMappingException {
        try {
            JsonNode jsonNode = mapper.readTree(rawJson);
            String root = jsonNode.fieldNames().next();
            return mapper.treeToValue(jsonNode.get(root), Channel.class);
        } catch (JsonProcessingException | NoSuchElementException e) {
            throw new StreamExternalServiceMappingException("Error parsing raw json, ex: " + e.getMessage());
        }
    }
}
