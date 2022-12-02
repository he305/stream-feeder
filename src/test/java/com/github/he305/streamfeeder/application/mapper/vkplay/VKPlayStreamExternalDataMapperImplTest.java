package com.github.he305.streamfeeder.application.mapper.vkplay;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.he305.streamfeeder.application.dto.vkplay.VKPlayDto;
import com.github.he305.streamfeeder.common.entity.StreamExternalData;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VKPlayStreamExternalDataMapperImplTest {

    private final VKPlayStreamExternalDataMapperImpl underTest = new VKPlayStreamExternalDataMapperImpl();

    @Test
    @SneakyThrows
    void getData_offline() {
        String offlineData = "{\"createdAt\":1670003726,\"isChatModerator\":false,\"title\":\"БЕСПЛАТНЫЙ ОБЗОР АККОВ\",\"startTime\":1670003726,\"isLiked\":false,\"isBlackListedByUser\":false,\"embedUrl\":\"https://vkplay.live/app/embed/nuke73\",\"isEnded\":true,\"previewUrl\":\"https://images.boosty.to/public_video_stream/6258718/preview?change_time=1670011352\",\"isCreated\":true,\"wsStreamChannel\":\"public-stream:6258718\",\"wsChatChannel\":\"public-chat:6258718\",\"data\":[],\"category\":{\"coverUrl\":\"https://images.boosty.to/public_video_stream/category/e9b87824-7ee9-4e21-a78f-4d047a4b98cd?change_time=1666835884\",\"type\":\"game\",\"id\":\"e9b87824-7ee9-4e21-a78f-4d047a4b98cd\",\"title\":\"Genshin Impact\"},\"hasAccess\":true,\"user\":{\"nick\":\"Nuke73\",\"avatarUrl\":\"https://images.boosty.to/user/9383442/avatar?change_time=1660903030\",\"name\":\"Nuke73\",\"id\":6258718,\"hasAvatar\":true,\"displayName\":\"Nuke73\"},\"chatSettings\":{\"remainingTime\":null,\"blogId\":6258718,\"allowAccessAfter\":0,\"allowAccess\":\"any\"},\"id\":\"eb2ac818-20c7-47e8-bef2-679c88e4aa37\",\"hasChat\":true,\"count\":{\"viewers\":245,\"likes\":111,\"views\":1849},\"isOnline\":false,\"endTime\":1670011361,\"daNick\":\"nuke73\"}";

        ObjectMapper mapper = new ObjectMapper();

        VKPlayDto dto = mapper.readValue(offlineData, VKPlayDto.class);

        StreamExternalData expected = StreamExternalData.emptyData();
        StreamExternalData actual = underTest.getData(dto);
        assertEquals(expected, actual);
    }

    @Test
    @SneakyThrows
    void getData_online() {
        String onlineData = "{\"id\":\"ff8d3fca-ad09-4930-a9e7-7a31eb4f0ad5\",\"embedUrl\":\"https://vkplay.live/app/embed/maddyson\",\"createdAt\":1669984524,\"count\":{\"viewers\":2399,\"likes\":515,\"views\":10879},\"title\":\"Kaleasto Proctocol\",\"isLiked\":false,\"isCreated\":true,\"startTime\":1669997807,\"daNick\":\"etozhemad\",\"isEnded\":false,\"endTime\":null,\"isChatModerator\":false,\"isOnline\":true,\"hasAccess\":true,\"user\":{\"name\":\"Илья Мэддисон\",\"nick\":\"Илья Мэддисон\",\"hasAvatar\":true,\"avatarUrl\":\"https://images.boosty.to/user/9543417/avatar?change_time=1661510815\",\"id\":6808257,\"displayName\":\"Илья Мэддисон\"},\"chatSettings\":{\"allowAccess\":\"any\",\"blogId\":6808257,\"allowAccessAfter\":0,\"remainingTime\":null},\"wsChatChannel\":\"public-chat:6808257\",\"previewUrl\":\"https://images.boosty.to/public_video_stream/6808257/preview?change_time=1670017090\",\"isBlackListedByUser\":false,\"hasChat\":true,\"wsStreamChannel\":\"public-stream:6808257\",\"data\":[{\"playerUrls\":[{\"type\":\"ultra_hd\",\"url\":\"\"},{\"url\":\"\",\"type\":\"dash_uni\"},{\"url\":\"https://vsd113.mycdn.me/dash/stream_3152304343645_offset_p/stream.manifest/sig/1C8SymnQtko/expires/1670103556015/srcIp/178.167.5.161/urls/45.136.21.58/clientType/18/srcAg/UNKNOWN/mid/4049457130845/video\",\"type\":\"live_playback_dash\"},{\"type\":\"hls\",\"url\":\"\"},{\"url\":\"\",\"type\":\"lowest\"},{\"type\":\"live_playback_hls\",\"url\":\"https://vsd113.mycdn.me/hls/3152304343645_offset_p.m3u8/sig/1C8SymnQtko/expires/1670103556015/srcIp/178.167.5.161/urls/45.136.21.58/clientType/18/srcAg/UNKNOWN/mid/4049457130845/video.m3u8?p\"},{\"type\":\"medium\",\"url\":\"\"},{\"type\":\"low\",\"url\":\"\"},{\"type\":\"high\",\"url\":\"\"},{\"url\":\"\",\"type\":\"quad_hd\"},{\"type\":\"tiny\",\"url\":\"\"},{\"url\":\"https://vsd113.mycdn.me/hls/3152304343645.m3u8/sig/1C8SymnQtko/expires/1670103556015/srcIp/178.167.5.161/urls/45.136.21.58/clientType/18/srcAg/UNKNOWN/mid/4049457130845/video.m3u8?p\",\"type\":\"live_hls\"},{\"url\":\"\",\"type\":\"dash\"},{\"type\":\"live_dash\",\"url\":\"https://vsd113.mycdn.me/dash/stream_3152304343645/stream.manifest/sig/1C8SymnQtko/expires/1670103556015/srcIp/178.167.5.161/urls/45.136.21.58/clientType/18/srcAg/UNKNOWN/mid/4049457130845/video\"},{\"type\":\"full_hd\",\"url\":\"\"}],\"vid\":\"4049457130845\",\"title\":\"Kaleasto Proctocol\",\"failoverHost\":\"\",\"type\":\"ok_stream\",\"url\":\"https://ok.ru/videoembed/4049457130845?sig=xyplBD-zsIo&uid=580692047577&t=1670017156003&cip=178.167.5.161&ua=insomnia%2F2022.5.1&promo=boosty\"}],\"category\":{\"coverUrl\":\"https://images.boosty.to/public_video_stream/category/28071b11-a3d2-4145-8737-98e6a5d56f2b?change_time=1669881489\",\"id\":\"28071b11-a3d2-4145-8737-98e6a5d56f2b\",\"title\":\"The Callisto Protocol\",\"type\":\"game\"}}";

        ObjectMapper mapper = new ObjectMapper();

        VKPlayDto dto = mapper.readValue(onlineData, VKPlayDto.class);

        StreamExternalData expected = StreamExternalData.newData(
                "The Callisto Protocol",
                "Kaleasto Proctocol",
                2399
        );
        StreamExternalData actual = underTest.getData(dto);
        assertEquals(expected, actual);
    }
}