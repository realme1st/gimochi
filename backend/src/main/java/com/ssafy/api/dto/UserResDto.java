package com.ssafy.api.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResDto {
    @Builder
    public UserResDto(Long userId, Long userKakaoId, String userEmail, int userPoint, String userNickname, String userBirthday) {
        this.userId = userId;
        this.userKakaoId = userKakaoId;
        this.userEmail = userEmail;
        this.userPoint = userPoint;
        this.userNickname = userNickname;
        this.userBirthday = userBirthday;
    }

    private Long userId;
    private Long userKakaoId;
    private String userEmail;
    private int userPoint;
    private String userNickname;
    private String userBirthday;

}
