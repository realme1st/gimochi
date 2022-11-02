package com.ssafy.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssafy.db.entity.SessionMessage;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.SwaggerDefinition;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

/* Session 생성시 필요한 정보 Dto*/
@Data
public class SessionReqDto {

    @ApiModelProperty(name = "세션명", example = "session_example")
    String name;

    @ApiModelProperty(name = "세션 생성 시간", example = "2022-01-01T00:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    LocalDateTime createTime;

    @ApiModelProperty(name = "세션 만료 시간", example = "2022-01-02T00:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    LocalDateTime expireTime;

    @ApiModelProperty(name = "세션 생성 시간", example = "2022-01-01")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    LocalDate anniversary;

    @ApiModelProperty(name = "유저 id", example = "1")
    Long userId;

    @ApiModelProperty(name = "세션 타입 id", example = "1")
    Long sessionTypeId;
}
