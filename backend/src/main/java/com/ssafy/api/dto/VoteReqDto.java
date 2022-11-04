package com.ssafy.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class VoteReqDto {
    @ApiModelProperty(name="챌린지 인증Id(FK)", example="1")
    private Long challengeAuthId;
    @ApiModelProperty(name="인증 올리는 사람 userId",example="1")
    private Long authUserId;
    @ApiModelProperty(name= "투표한 사람 userId", example="2")
    private Long voteUserId;


}
