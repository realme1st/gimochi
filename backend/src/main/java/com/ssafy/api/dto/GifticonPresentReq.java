package com.ssafy.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GifticonPresentReq {

    @ApiModelProperty(name="선물하는 회원 식별자 (sender)", example="5")
    Long userIdSend;

    @ApiModelProperty(name="선물받는 회원 식별자 (sender)", example="3")
    Long userIdReceive;

    @ApiModelProperty(name="기프티콘 식별자", example="1")
    Long gifticonId;

}
