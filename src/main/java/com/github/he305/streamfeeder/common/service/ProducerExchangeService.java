package com.github.he305.streamfeeder.common.service;

import com.github.he305.streamfeeder.common.dto.StreamEnd;
import com.github.he305.streamfeeder.common.entity.Channel;
import com.github.he305.streamfeeder.common.entity.StreamData;
import com.github.he305.streamfeeder.common.exception.ProducerExchangeException;

import java.util.List;

public interface ProducerExchangeService {
    List<Channel> getChannels() throws ProducerExchangeException;

    boolean addStreamData(StreamData data) throws ProducerExchangeException;

    boolean endStream(StreamEnd request) throws ProducerExchangeException;
}
