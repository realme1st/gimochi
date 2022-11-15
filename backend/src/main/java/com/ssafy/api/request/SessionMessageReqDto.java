package com.ssafy.api.request;

import io.swagger.annotations.Api;
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

    @ApiModelProperty(name ="기프티콘 id" , example = "1", required = false)
    Long gifticonId;

    @ApiModelProperty(name = "세션 메세지 타입", example = "1", required = false)
    Integer messageType;
}
