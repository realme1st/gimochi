package com.ssafy.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import java.util.List;

@Getter
public class KakaoFriends {

    private List<Friend> elements;
    @JsonIgnore
    private int total_count;
    @JsonIgnore
    private String after_url;
    @JsonIgnore
    private int favorite_count;
    @Getter
    static public class Friend {
        private String profile_nickname;
        @JsonIgnore
        private String profile_thumbnail_image;
        @JsonIgnore
        private boolean allowed_msg;
        private Long id;
        @JsonIgnore
        private String uuid;
        @JsonIgnore
        private boolean favorite;
        @JsonIgnore
        private String thumbnail_image;
    }
}
