package com.ssafy.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GifticonUsedReq {
    @ApiModelProperty(name="회원 식별자", example="5")
    Long userId;

    @ApiModelProperty(name="기프티콘 식별자", example="1")
    Long gifticonId;
}
