package com.github.he305.streamfeeder.application.mapper.wasd;

import com.github.he305.streamfeeder.application.dto.wasd.*;
import com.github.he305.streamfeeder.common.exception.StreamExternalServiceMappingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WasdJsonChannelMapperImplTest {

    private WasdJsonChannelMapperImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new WasdJsonChannelMapperImpl();
    }

    @Test
    void getChannel_success() {
        String rawJson = "{\n\t\"result\": {\n\t\t\"channel\": {\n\t\t\t\"created_at\": \"2020-06-23T13:40:56.560Z\",\n\t\t\t\"deleted_at\": null,\n\t\t\t\"updated_at\": \"2022-07-24T17:58:04.741Z\",\n\t\t\t\"channel_id\": 225574,\n\t\t\t\"channel_name\": \"DailyShow\",\n\t\t\t\"user_id\": 381873,\n\t\t\t\"followers_count\": 13703,\n\t\t\t\"channel_subscribers_count\": 0,\n\t\t\t\"channel_is_live\": true,\n\t\t\t\"channel_description\": \"Daily Show - это авторские проекты от популярных стримеров и WASD.tv. Подкасты, видео-трансляции, необычные игровые стримы и IRL-эфиры с подписчиками. Подключайся!\",\n\t\t\t\"channel_description_enabled\": true,\n\t\t\t\"channel_donation_url\": \"https://www.donationalerts.com/r/khovanskytoday\",\n\t\t\t\"channel_image\": {\n\t\t\t\t\"large\": \"https://st.wasd.tv/upload/avatars/01b9c117-da50-48d4-b75b-e24c6f5e62c0/original.jpeg\",\n\t\t\t\t\"medium\": \"https://st.wasd.tv/upload/avatars/01b9c117-da50-48d4-b75b-e24c6f5e62c0/original.jpeg\",\n\t\t\t\t\"small\": \"https://st.wasd.tv/upload/avatars/01b9c117-da50-48d4-b75b-e24c6f5e62c0/original.jpeg\"\n\t\t\t},\n\t\t\t\"channel_status\": \"ACTIVE\",\n\t\t\t\"channel_subscription_seller\": false,\n\t\t\t\"channel_clips_count\": 2,\n\t\t\t\"channel_alias\": null,\n\t\t\t\"channel_priority\": 999,\n\t\t\t\"last_activity_date\": \"2022-07-24T17:00:04.000Z\",\n\t\t\t\"meta\": {\n\t\t\t\t\"required_isso\": false\n\t\t\t},\n\t\t\t\"channel_owner\": {\n\t\t\t\t\"created_at\": \"2020-06-23T13:40:56.560Z\",\n\t\t\t\t\"deleted_at\": null,\n\t\t\t\t\"updated_at\": \"2022-07-24T17:58:04.741Z\",\n\t\t\t\t\"user_id\": 381873,\n\t\t\t\t\"user_login\": \"DailyShow\",\n\t\t\t\t\"profile_description\": \"Daily Show - это авторские проекты от популярных стримеров и WASD.tv. Подкасты, видео-трансляции, необычные игровые стримы и IRL-эфиры с подписчиками. Подключайся!\",\n\t\t\t\t\"profile_image\": {\n\t\t\t\t\t\"large\": \"https://st.wasd.tv/upload/avatars/01b9c117-da50-48d4-b75b-e24c6f5e62c0/original.jpeg\",\n\t\t\t\t\t\"medium\": \"https://st.wasd.tv/upload/avatars/01b9c117-da50-48d4-b75b-e24c6f5e62c0/original.jpeg\",\n\t\t\t\t\t\"small\": \"https://st.wasd.tv/upload/avatars/01b9c117-da50-48d4-b75b-e24c6f5e62c0/original.jpeg\"\n\t\t\t\t},\n\t\t\t\t\"profile_background\": {\n\t\t\t\t\t\"large\": \"https://static.wasd.tv/avatars/channel/dailyshow.png\",\n\t\t\t\t\t\"medium\": \"https://static.wasd.tv/avatars/channel/dailyshow.png\",\n\t\t\t\t\t\"small\": \"https://static.wasd.tv/avatars/channel/dailyshow.png\"\n\t\t\t\t},\n\t\t\t\t\"channel_id\": 225574,\n\t\t\t\t\"profile_is_live\": true\n\t\t\t},\n\t\t\t\"notification\": false,\n\t\t\t\"is_user_follower\": false,\n\t\t\t\"is_partner\": false\n\t\t},\n\t\t\"media_container\": {\n\t\t\t\"media_container_id\": 1074937,\n\t\t\t\"media_container_name\": \"Золотой Дождь [Мы СНОВА вернулись!]\",\n\t\t\t\"media_container_description\": \"Daily Show - это авторские проекты от популярных стримеров и WASD.tv. Подкасты, видео-трансляции, необычные игровые стримы и IRL-эфиры с подписчиками. Подключайся!\",\n\t\t\t\"media_container_type\": \"SINGLE\",\n\t\t\t\"media_container_status\": \"RUNNING\",\n\t\t\t\"media_container_online_status\": \"PUBLIC\",\n\t\t\t\"user_id\": 381873,\n\t\t\t\"channel_id\": 225574,\n\t\t\t\"created_at\": \"2022-07-24T17:00:04.000Z\",\n\t\t\t\"is_mature_content\": false,\n\t\t\t\"published_at\": \"2022-07-24T17:00:04.528Z\",\n\t\t\t\"game\": {\n\t\t\t\t\"game_id\": 33,\n\t\t\t\t\"game_name\": \"IRL\",\n\t\t\t\t\"game_icon\": {\n\t\t\t\t\t\"large\": \"https://static.wasd.tv/games/original/IRL.jpg\",\n\t\t\t\t\t\"medium\": \"https://static.wasd.tv/games/original/IRL.jpg\",\n\t\t\t\t\t\"small\": \"https://static.wasd.tv/games/original/IRL.jpg\"\n\t\t\t\t},\n\t\t\t\t\"game_color_hex\": \"0951a0\"\n\t\t\t},\n\t\t\t\"media_container_streams\": [\n\t\t\t\t{\n\t\t\t\t\t\"stream_id\": 1071119,\n\t\t\t\t\t\"stream_total_viewers\": 2510,\n\t\t\t\t\t\"stream_current_viewers\": 877,\n\t\t\t\t\t\"stream_current_active_viewers\": 297,\n\t\t\t\t\t\"stream_media\": [\n\t\t\t\t\t\t{\n\t\t\t\t\t\t\t\"media_id\": 1068991,\n\t\t\t\t\t\t\t\"media_type\": \"HLS\",\n\t\t\t\t\t\t\t\"media_meta\": {\n\t\t\t\t\t\t\t\t\"media_url\": \"https://cdn.wasd.tv/live/381873/index.m3u8\",\n\t\t\t\t\t\t\t\t\"media_archive_url\": null,\n\t\t\t\t\t\t\t\t\"media_preview_url\": \"https://cdn.wasd.tv/large/live/381873/preview.jpg\",\n\t\t\t\t\t\t\t\t\"media_preview_images\": {\n\t\t\t\t\t\t\t\t\t\"large\": \"https://cdn.wasd.tv/large/live/381873/preview.jpg\",\n\t\t\t\t\t\t\t\t\t\"small\": \"https://cdn.wasd.tv/small/live/381873/preview.jpg\",\n\t\t\t\t\t\t\t\t\t\"medium\": \"https://cdn.wasd.tv/medium/live/381873/preview.jpg\"\n\t\t\t\t\t\t\t\t},\n\t\t\t\t\t\t\t\t\"media_preview_archive_images\": null\n\t\t\t\t\t\t\t},\n\t\t\t\t\t\t\t\"media_duration\": 0,\n\t\t\t\t\t\t\t\"media_status\": \"RUNNING\"\n\t\t\t\t\t\t}\n\t\t\t\t\t]\n\t\t\t\t}\n\t\t\t],\n\t\t\t\"tags\": [\n\t\t\t\t{\n\t\t\t\t\t\"tag_id\": 3,\n\t\t\t\t\t\"tag_name\": \"WASD\",\n\t\t\t\t\t\"tag_description\": \"Трансляции фанатов сервиса WASD.tv с эпичным дизайном и анимацией тэга\",\n\t\t\t\t\t\"tag_meta\": null,\n\t\t\t\t\t\"tag_type\": \"DEFAULT\",\n\t\t\t\t\t\"tag_media_containers_online_count\": 222\n\t\t\t\t},\n\t\t\t\t{\n\t\t\t\t\t\"tag_id\": 23,\n\t\t\t\t\t\"tag_name\": \"Видеоблог\",\n\t\t\t\t\t\"tag_description\": \"Трансляции, посвященные повседневной жизни и взаимодействию со зрителями\",\n\t\t\t\t\t\"tag_meta\": null,\n\t\t\t\t\t\"tag_type\": \"DEFAULT\",\n\t\t\t\t\t\"tag_media_containers_online_count\": 19\n\t\t\t\t},\n\t\t\t\t{\n\t\t\t\t\t\"tag_id\": 86,\n\t\t\t\t\t\"tag_name\": \"Новости\",\n\t\t\t\t\t\"tag_description\": \"Трансляции, посвящённые изучению или обсуждению текущих событий\",\n\t\t\t\t\t\"tag_meta\": null,\n\t\t\t\t\t\"tag_type\": \"DEFAULT\",\n\t\t\t\t\t\"tag_media_containers_online_count\": 5\n\t\t\t\t},\n\t\t\t\t{\n\t\t\t\t\t\"tag_id\": 113,\n\t\t\t\t\t\"tag_name\": \"Разогрев с чатом\",\n\t\t\t\t\t\"tag_description\": \"Трансляции, которые начинаются с ознакомительных бесед или обсуждений между стримером и его сообществом\",\n\t\t\t\t\t\"tag_meta\": null,\n\t\t\t\t\t\"tag_type\": \"DEFAULT\",\n\t\t\t\t\t\"tag_media_containers_online_count\": 23\n\t\t\t\t},\n\t\t\t\t{\n\t\t\t\t\t\"tag_id\": 159,\n\t\t\t\t\t\"tag_name\": \"Рекомендованный\",\n\t\t\t\t\t\"tag_description\": \"Internal tag\",\n\t\t\t\t\t\"tag_meta\": null,\n\t\t\t\t\t\"tag_type\": \"RECOMMENDATION\",\n\t\t\t\t\t\"tag_media_containers_online_count\": 20\n\t\t\t\t}\n\t\t\t]\n\t\t}\n\t}\n}";

        ChannelData channelData = new ChannelData(225574, true);
        MediaContainer container = new MediaContainer(
                "Золотой Дождь [Мы СНОВА вернулись!]",
                new Game("IRL"),
                List.of(new MediaContainerStream(877))
        );

        Channel expected = new Channel(channelData, container);
        Channel actual = underTest.getChannel(rawJson);
        assertEquals(expected, actual);
    }

    @Test
    void getChannel_emptyString_shouldThrow() {
        String rawJson = "";
        assertThrows(StreamExternalServiceMappingException.class, () ->
                underTest.getChannel(rawJson));

    }
}