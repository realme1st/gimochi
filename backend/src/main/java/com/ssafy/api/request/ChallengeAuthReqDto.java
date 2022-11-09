package com.ssafy.api.request;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

    @Data
public class ChallengeAuthReqDto {
    @ApiModelProperty(name="챌린지 정보Id(FK)", example="1")
    private Long challengeInfoId;
    @ApiModelProperty(name="챌린지 인증 파일 경로", example="awerfd")
    private String challengePath;
    @ApiModelProperty(name="챌린지 인증 투표 수", example="0")
    private int voteCnt;
    @ApiModelProperty(name="챌린지 인증 날짜", example="2021-10-28")
    private LocalDate challengeDate;
    @ApiModelProperty(name="챌린지 달성 여부", example="0")
    private int isConfirm;
}
