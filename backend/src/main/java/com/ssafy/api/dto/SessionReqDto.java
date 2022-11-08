package com.ssafy.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

/* Session 생성시 필요한 정보 Dto*/
@Getter
public class SessionReqDto {

    @ApiModelProperty(name = "세션명", example = "session_example")
    String name;

    @ApiModelProperty(name = "기념일", example = "2022-01-01")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    LocalDate anniversary;

    @ApiModelProperty(name = "유저 id", example = "1")
    Long userId;

    @ApiModelProperty(name = "세션 타입 id", example = "1")
    Long sessionTypeId;

    @Builder
    public SessionReqDto(String name, LocalDate anniversary, Long userId, Long sessionTypeId) {
        this.name = name;
        this.anniversary = anniversary;
        this.userId = userId;
        this.sessionTypeId = sessionTypeId;
    }
}
