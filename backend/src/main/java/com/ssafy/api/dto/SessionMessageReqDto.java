package com.ssafy.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SessionMessageReqDto {
    @ApiModelProperty(name = "세션id", example = "1")
    Long sessionId;

    @ApiModelProperty(name = "메세지 내용", example = "메세지 내용")
    String field;

    @ApiModelProperty(name = "닉네임", example = "nickname")
    String nickname;

    @ApiModelProperty(name = "첨부이미지", example = "첨부이미지")
    String img;

    @Builder
    public SessionMessageReqDto(String img) {
        this.img = img;
    }
}
