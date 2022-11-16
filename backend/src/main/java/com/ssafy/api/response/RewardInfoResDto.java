package com.ssafy.api.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RewardInfoResDto {

    private Long challengeRewardId;

    private Long challengeId;

    private Long gifticonId;

}
