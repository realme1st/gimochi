package com.ssafy.api.dto;

import lombok.Builder;
import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
public class RefreshedTokenResDto {
    @Builder
    public RefreshedTokenResDto(String access_token, String token_type, String id_token,
                                String refresh_token, int expires_in, int refresh_token_expires_in) {
        this.accessToken = access_token;
        this.tokenType = token_type;
        this.idToken = id_token;
        this.refreshToken = refresh_token;
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date today = new Date();
        this.expiresIn = sf.format(today.getTime() + (long) (expires_in * 1000));
        this.refreshTokenExpiresIn = refresh_token_expires_in;
    }


    private String accessToken;
    private String tokenType;
    private String idToken;
    private String refreshToken;
    private String expiresIn;
    private int refreshTokenExpiresIn;
}
