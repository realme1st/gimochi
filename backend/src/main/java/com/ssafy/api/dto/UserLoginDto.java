package com.ssafy.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginDto {
    private String userEmail;
    private String userNickname;
    private String userSocialToken;
    private String userSocialRefreshToken;
    private String userFbToken;
    private boolean isNewUser;
}
