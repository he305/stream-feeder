package com.github.he305.streamfeeder.application.dto.wasd;

import java.util.ArrayList;

public class MediaContainerStream {
    public int stream_id;
    public int stream_total_viewers;
    public int stream_current_viewers;
    public int stream_current_active_viewers;
    public ArrayList<StreamMedium> stream_media;
}
