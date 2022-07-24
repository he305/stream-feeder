package com.github.he305.streamfeeder.application.dto.wasd;

import java.util.ArrayList;
import java.util.Date;

public class MediaContainer {
    public int media_container_id;
    public String media_container_name;
    public String media_container_description;
    public String media_container_type;
    public String media_container_status;
    public String media_container_online_status;
    public int user_id;
    public int channel_id;
    public Date created_at;
    public boolean is_mature_content;
    public Date published_at;
    public Game game;
    public ArrayList<MediaContainerStream> media_container_streams;
    public ArrayList<Tag> tags;
}
