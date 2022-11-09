package com.ssafy.api.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChallengeInviteReqDto {
    @ApiModelProperty(name = "초대 받을 유저 ID", example = "1")
    private Long userId;

    @ApiModelProperty(name = "초대 받을 Challenge ID", example = "1")
    private Long challengeId;

}
