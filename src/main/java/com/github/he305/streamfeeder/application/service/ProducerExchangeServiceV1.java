package com.github.he305.streamfeeder.application.service;

import com.github.he305.streamfeeder.application.dto.ChannelList;
import com.github.he305.streamfeeder.application.dto.StreamDataAddDto;
import com.github.he305.streamfeeder.application.dto.StreamEndDto;
import com.github.he305.streamfeeder.application.dto.StreamResponseDto;
import com.github.he305.streamfeeder.application.mapper.ChannelMapper;
import com.github.he305.streamfeeder.common.dto.StreamEnd;
import com.github.he305.streamfeeder.common.entity.Channel;
import com.github.he305.streamfeeder.common.entity.StreamData;
import com.github.he305.streamfeeder.common.service.ProducerExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProducerExchangeServiceV1 implements ProducerExchangeService {

    private final RestTemplate restTemplate;
    private final ChannelMapper mapper;

    @Override
    public List<Channel> getChannels() {
        ChannelList channelList = restTemplate.getForObject(
                "http://localhost:8080/api/v1/channel",
                ChannelList.class
        );
        assert channelList != null;
        return channelList.getChannels().stream().map(mapper::toChannel).collect(Collectors.toList());
    }

    @Override
    public boolean addStreamData(StreamData data) {
        StreamDataAddDto dto = new StreamDataAddDto(
                data.getGameName(),
                data.getTitle(),
                data.getViewerCount(),
                data.getTime()
        );
        ResponseEntity<StreamResponseDto> response = restTemplate.postForEntity(String.format("http://localhost:8080/api/v1/channel/%d/streamData", data.getChannelId()), dto, StreamResponseDto.class);
        HttpStatus code = response.getStatusCode();
        return code == HttpStatus.OK;
    }

    @Override
    public boolean endStream(StreamEnd data) {
        StreamEndDto dto = new StreamEndDto(
                data.getTime()
        );
        restTemplate.put(String.format("http://localhost:8080/api/v1/channel/{channelId}/end", data.getChannelId()), dto);
        return true;
    }
}
