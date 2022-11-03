package com.ssafy.api.response;

import lombok.*;

@Getter
public class UserListRes {
    Long userId;

    int successCnt;

    @Builder
    public UserListRes(Long userId, int successCnt) {
        this.userId = userId;
        this.successCnt = successCnt;
    }


}
