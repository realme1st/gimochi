package com.ssafy.api.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenInforamtion {

    private Long id;
    private int expires_in;
    private int app_id;

    @Builder
    public TokenInforamtion(Long id, int expires_in, int app_id) {
        this.id = id;
        this.expires_in = expires_in;
        this.app_id = app_id;
    }
}
