package com.ssafy.api.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChallengeRewardReqDto {
    @ApiModelProperty(name = "챌린지 id", example = "1")
    Long challengeId;

    @ApiModelProperty(name ="기프티콘 id" , example = "1")
    Long gifticonId;

}
