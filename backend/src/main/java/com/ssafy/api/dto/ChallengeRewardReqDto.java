package com.ssafy.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ChallengeRewardReqDto {

    @ApiModelProperty(name="챌린지 id", example="1")
    private Long challengeId;
    @ApiModelProperty(name="챌린지 타입 (0이면 포인트 1이면 기프티콘)", example="1")
    private Long challengeRewardType;
}
