package com.ssafy.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshedToken {
    private String access_token;
    private String token_type;
    private String id_token;
    private String refresh_token;
    private int expires_in;
    private int refresh_token_expires_in;
}
