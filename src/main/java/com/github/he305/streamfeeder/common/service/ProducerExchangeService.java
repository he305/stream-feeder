package com.github.he305.streamfeeder.common.service;

import com.github.he305.streamfeeder.common.dto.StreamEnd;
import com.github.he305.streamfeeder.common.entity.Channel;
import com.github.he305.streamfeeder.common.entity.StreamData;

import java.util.List;

public interface ProducerExchangeService {
    List<Channel> getChannels();
    boolean addStreamData(StreamData data);
    boolean endStream(StreamEnd request);
}
