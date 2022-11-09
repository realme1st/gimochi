package com.ssafy.api.controller;

import com.ssafy.api.dto.FollowReqDto;
import com.ssafy.api.service.UserService;
import com.ssafy.common.response.BasicResponse;
import com.ssafy.common.response.CommonResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

// import javax.xml.ws.Response;

@Api(value = "User API", tags = {"User"})
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    // 팔로우
    @PostMapping("/follow-request")
    @ApiOperation(value = "followerId(팔로워)가 followingId(팔로잉)에게 친구 요청 전송", notes = "팔로우")
    public ResponseEntity<? extends BasicResponse> followRequest(@Validated @RequestBody FollowReqDto followReqDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseEntity<>(userService.followRequest(followReqDto)));
    }

    @PostMapping("/follow-accept")
    @ApiOperation(value = "followingId(팔로잉)이 followerId(팔로워)의 친구 요청 수락", notes = "팔로우")
    public ResponseEntity<? extends BasicResponse> acceptFollow(@Validated @RequestBody FollowReqDto followReqDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseEntity<>(userService.acceptFollow(followReqDto)));
    }

    @PostMapping("/follow-reject")
    @ApiOperation(value = "followingId(팔로잉)이 followerId(팔로워)의 친구 요청 거절", notes = "팔로우")
    public ResponseEntity<? extends BasicResponse> rejectFollow(@Validated @RequestBody FollowReqDto followReqDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseEntity<>(userService.rejectFollow(followReqDto)));
    }

    // userId를 기반으로 본인이 팔로우 한 목록 조회
    @GetMapping("/following/{userId}")
    @ApiOperation(value = "userId가 팔로우한 목록 조회", notes = "팔로우한 목록 조회")
    public ResponseEntity<? extends BasicResponse> getFollowingList(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK)
                    .body(new CommonResponseEntity<>(userService.getFollowingList(userId)));
    }

    // userId를 기반으로 본인을 팔로우 한 목록 조회
    @GetMapping("/follower/{userId}")
    @ApiOperation(value = "userId를 팔로우한 목록 조회", notes = "팔로우한 목록 조회")
    public ResponseEntity<? extends BasicResponse> getFollowerList(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK)
                    .body(new CommonResponseEntity<>(userService.getFollowerList(userId)));
    }

    // 팔로우 삭제
    @DeleteMapping("/follow")
    @ApiOperation(value = "followerId(팔로워)가 followingId(팔로잉)을 팔로우 취소", notes = "팔로우 취소")
    public ResponseEntity<? extends BasicResponse> unfollow(@Validated @RequestBody FollowReqDto followReqDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseEntity<>(userService.unfollow(followReqDto)));
    }
}
