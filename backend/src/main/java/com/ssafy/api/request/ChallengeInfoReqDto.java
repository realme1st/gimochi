package com.ssafy.api.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ChallengeInfoReqDto {
    @ApiModelProperty(name="유저 id(FK)", example="1")
    private Long userId;

    @ApiModelProperty(name="챌린지 id(FK)", example="1")
    private Long challengeId;

    @ApiModelProperty(name="챌린지 달성 횟수", example="1")
    private int successCnt;
}
