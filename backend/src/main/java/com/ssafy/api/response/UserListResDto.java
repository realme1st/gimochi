package com.ssafy.api.response;

import lombok.*;

@Getter
public class UserListResDto {
    private Long userId;

    private int successCnt;

    private String userNickname;
    private String userProfile;

    @Builder
    public UserListResDto(Long userId, int successCnt, String userNickname,String userProfile) {
        this.userId = userId;
        this.successCnt = successCnt;
        this.userNickname = userNickname;
        this.userProfile = userProfile;
    }


}
