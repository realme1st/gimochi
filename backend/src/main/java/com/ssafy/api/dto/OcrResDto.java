package com.ssafy.api.dto;

import com.ssafy.db.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OcrResDto {

    @ApiModelProperty(name="회원 정보", example="회원 정보")
    User user;

    @ApiModelProperty(name="기프티콘 사용처", example="바나프레소")
    String gifticonStore;

    @ApiModelProperty(name="기프티콘 유효기간", example="2022-12-31")
    String gifticonPeriod;

    @ApiModelProperty(name="기프티콘 바코드", example="123456781234")
    String gifticonCode;

    @Builder
    public OcrResDto(User user, String gifticonStore, String gifticonPeriod, String gifticonCode) {
        this.user = user;
        this.gifticonStore = gifticonStore;
        this.gifticonPeriod = gifticonPeriod;
        this.gifticonCode = gifticonCode;
    }

}
