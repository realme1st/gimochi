package com.ssafy.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GifticonInfoReqDto {
    @ApiModelProperty(name="회원 식별자", example="5")
    Long userId;

    @ApiModelProperty(name="기프티콘 사용처", example="바나프레소")
    String gifticonStore;

    @ApiModelProperty(name="기프티콘 유효기간", example="2022-12-31")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    LocalDate gifticonPeriod; 

    @ApiModelProperty(name="기프티콘 사용여부", example="false")
    boolean gifticonUsed;
}
