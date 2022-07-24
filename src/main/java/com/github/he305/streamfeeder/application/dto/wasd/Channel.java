package com.github.he305.streamfeeder.application.dto.wasd;

import java.util.Date;

public class Channel {
    public Date created_at;
    public Object deleted_at;
    public Date updated_at;
    public int channel_id;
    public String channel_name;
    public int user_id;
    public int followers_count;
    public int channel_subscribers_count;
    public boolean channel_is_live;
    public String channel_description;
    public boolean channel_description_enabled;
    public String channel_donation_url;
    public ChannelImage channel_image;
    public String channel_status;
    public boolean channel_subscription_seller;
    public int channel_clips_count;
    public Object channel_alias;
    public int channel_priority;
    public Date last_activity_date;
    public Meta meta;
    public ChannelOwner channel_owner;
    public boolean notification;
    public boolean is_user_follower;
    public boolean is_partner;
}
