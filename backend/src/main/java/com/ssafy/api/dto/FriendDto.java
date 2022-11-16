package com.ssafy.api.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FriendDto {
    private String userName;
    private Long userId;
    private String userProfile;
    private boolean isFriend;

    @Builder
    public FriendDto(String userName, Long userId,String userProfile, boolean isFriend) {
        this.userName = userName;
        this.userId = userId;
        this.userProfile=userProfile;
        this.isFriend = isFriend;
    }
}
