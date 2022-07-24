package com.github.he305.streamfeeder.common.service;

import com.github.he305.streamfeeder.common.entity.Channel;

import java.util.List;

public interface ChannelService {
    List<Channel> getAllChannels();
    List<Channel> getLiveChannels();
}
