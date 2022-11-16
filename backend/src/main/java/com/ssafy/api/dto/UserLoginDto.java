package com.ssafy.api.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserLoginDto {
    @Builder
    public UserLoginDto(Long userId,String userProfile, String userEmail, String userNickname, String userSocialToken, String userSocialRefreshToken, String userFbToken, boolean isNewUser, String expiresIn) {
        this.userId = userId;
        this.userProfile= userProfile;
        this.userEmail = userEmail;
        this.userNickname = userNickname;
        this.userSocialToken = userSocialToken;
        this.userSocialRefreshToken = userSocialRefreshToken;
        this.userFbToken = userFbToken;
        this.isNewUser = isNewUser;
        this.expiresIn = expiresIn;

    }

    private Long userId;
    private String userProfile;
    private String userEmail;
    private String userNickname;
    private String userSocialToken;
    private String userSocialRefreshToken;
    private String userFbToken;
    private boolean isNewUser;
    private String expiresIn;
}
