package com.ssafy.api.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserUsageResDto {
    private int registCount;
    private int usedCount;

    @Builder
    public UserUsageResDto(int registCount, int usedCount) {
        this.registCount = registCount;
        this.usedCount = usedCount;
    }
}
