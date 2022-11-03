package com.ssafy.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GifticonReqDto {

    @ApiModelProperty(name="회원 식별자", example="5")
    Long userId;

    @ApiModelProperty(name="기프티콘 이미지(base64 인코딩)", example="Z2lmdGljb255ZXNjb21lb25naW1vY2hp")
    String gifticonEnc;

}
