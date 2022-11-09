package com.ssafy.api.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FriendDto {
    private String userName;
    private Long userId;

    private boolean isFriend;

    @Builder
    public FriendDto(String userName, Long userId, boolean isFriend) {
        this.userName = userName;
        this.userId = userId;
        this.isFriend = isFriend;
    }
}
