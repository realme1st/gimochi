package com.ssafy.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SessionMessageReqDto {
    @ApiModelProperty(name = "세션id", example = "1")
    Long sessionId;

    @ApiModelProperty(name = "메세지 내용", example = "메세지 내용")
    String field;

    @ApiModelProperty(name = "메세지 생성 시간", example = "2022-01-01T00:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    LocalDateTime createTime;

    @ApiModelProperty(name = "메세지 만료 시간", example = "2022-01-02T00:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    LocalDateTime expireTime;

    @ApiModelProperty(name = "닉네임", example = "nickname")
    String nickname;

    @ApiModelProperty(name = "첨부이미지", example = "첨부이미지")
    String img;

    @Builder
    public SessionMessageReqDto(String img) {
        this.img = img;
    }
}
