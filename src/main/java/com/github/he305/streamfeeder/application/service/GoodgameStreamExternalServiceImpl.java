package com.github.he305.streamfeeder.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.he305.streamfeeder.application.dto.goodgame.Channel;
import com.github.he305.streamfeeder.common.entity.StreamExternalData;
import com.github.he305.streamfeeder.common.service.GoodgameStreamExternalService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class GoodgameStreamExternalServiceImpl extends GoodgameStreamExternalService {

    private final ObjectMapper mapper = new ObjectMapper();
    private final RestTemplate restTemplate;

    @SneakyThrows
    @Override
    public StreamExternalData getStreamExternalDataForNickname(String nickname) {
        ResponseEntity<String> response = restTemplate.getForEntity("http://goodgame.ru/api/getchannelstatus?fmt=json&id=" + nickname, String.class);

        String rawJson = response.getBody();

        JsonNode jsonNode = mapper.readTree(rawJson);
        String root = jsonNode.fieldNames().next();
        Channel result = mapper.treeToValue(jsonNode.get(root), Channel.class);

        return buildFromChannel(result);
    }

    private StreamExternalData buildFromChannel(Channel channel) {
        if (channel.status.equals("Dead")) {
            return StreamExternalData.emptyData();
        }

        return buildStreamExternalDataFromChannel(channel);
    }

    private StreamExternalData buildStreamExternalDataFromChannel(Channel channel) {
        return StreamExternalData.newData(channel.games, channel.title, Integer.parseInt(channel.viewers));
    }
}
