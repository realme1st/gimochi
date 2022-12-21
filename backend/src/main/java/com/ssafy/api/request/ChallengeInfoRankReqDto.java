package com.ssafy.api.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class ChallengeInfoRankReqDto {
    @ApiModelProperty(name = "유저 Id", example = "1")
    private Long userId;
    @ApiModelProperty(name = "챌린지 Id", example = "1")
    private Long challengeId;
}
