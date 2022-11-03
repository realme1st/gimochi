package com.ssafy.api.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ChallengeAuthReqDto {
    @ApiModelProperty(name="챌린지 정보Id(FK)", example="1")
    private Long challengeInfoId;
    @ApiModelProperty(name="챌린지 인증 유저", example="1" )
    private Long authUserId;
    @ApiModelProperty(name="챌린지 인증 파일 경로", example="awerfd")
    private String challengePath;
    @ApiModelProperty(name="챌린지 인증 투표 수", example="0")
    private int voteCnt;
    @ApiModelProperty(name="챌린지 인증 확인 여부", example="0")
    private int isConfirm;
    @ApiModelProperty(name="챌린지 인증 날짜", example="2021-10-28")
    private Date challengeDate;


}
