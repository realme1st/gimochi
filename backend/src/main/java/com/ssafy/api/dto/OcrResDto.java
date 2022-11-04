package com.ssafy.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OcrResDto {

    @ApiModelProperty(name="회원 식별자", example="5")
    Long userId;

    @ApiModelProperty(name="기프티콘 사용처", example="바나프레소")
    String gifticonUsage;

    @ApiModelProperty(name="기프티콘 유효기간", example="2022-12-31")
    String gifticonExp;

    @Builder
    public OcrResDto(Long userId, String gifticonUsage, String gifticonExp) {
        this.userId = userId;
        this.gifticonUsage = gifticonUsage;
        this.gifticonExp = gifticonExp;
    }

}
