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
        this.access_token = access_token;
        this.token_type = token_type;
        this.id_token = id_token;
        this.refresh_token = refresh_token;
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date today = new Date();
        this.expires_in = sf.format(today.getTime() + (long) (expires_in * 1000));
        this.refresh_token_expires_in = refresh_token_expires_in;
    }


    private String access_token;
    private String token_type;
    private String id_token;
    private String refresh_token;
    private String expires_in;
    private int refresh_token_expires_in;
}
