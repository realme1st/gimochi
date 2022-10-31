package com.ssafy.api.dto;

import lombok.Getter;

@Getter
public class TokenInforamtion {

    private Long id;
    private int expires_in;
    private int app_id;
}
