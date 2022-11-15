package com.ssafy.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenInforamtion {

    private Long id;
    private int expires_in;
    private int app_id;
    private int expiresInMillis;
    private int appId;
}
