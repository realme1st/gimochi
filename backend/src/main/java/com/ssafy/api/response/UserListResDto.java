package com.ssafy.api.response;

import lombok.*;

@Getter
public class UserListResDto {
    private Long userId;

    private int successCnt;

    @Builder
    public UserListResDto(Long userId, int successCnt) {
        this.userId = userId;
        this.successCnt = successCnt;
    }


}
