package com.ssafy.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FollowReqDto {
    @ApiModelProperty(name="팔로우 하는 사람", example="1")
    Long followerUserId;
    @ApiModelProperty(name="팔로우 당하는 사람", example="2")
    Long followingUserId;
}
