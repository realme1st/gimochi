package com.ssafy.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingleMessageReqDto {

    @ApiModelProperty(name="알림을 발생시키는 주체(이벤트를 발생시킨 사람)", example="1")
    Long senderId;
    @ApiModelProperty(name="알림을 받는 주체", example="1")
    Long receiverId;
    @ApiModelProperty(name="알림 종류", example="1/2/3/4..")
    int type;
}
