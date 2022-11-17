package com.ssafy.api.controller;

import com.ssafy.api.request.FollowReqDto;
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

    @GetMapping("/info/{userId}")
    @ApiOperation(value = "userId로 사용자 정보 조회", notes = "사용자 정보 조회")
    public ResponseEntity<? extends BasicResponse> getUser(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseEntity<>(userService.getUser(userId)));
    }

    @GetMapping("/usage/{userId}")
    @ApiOperation(value = "userId로 등록/ 사용 기프티콘 수 조회", notes = "사용자 정보 조회")
    public ResponseEntity<? extends BasicResponse> getUsageInfo(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseEntity<>(userService.getUsage(userId)));
    }

    @GetMapping("/usage/used/{userId}")
    @ApiOperation(value = "userId의 사용 기프티콘 카운트 증가", notes = "사용자 정보 수정")
    public ResponseEntity<? extends BasicResponse> countUpUsed(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseEntity<>(userService.countUpUsed(userId)));
    }
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

    @GetMapping("/following-request/{userId}")
    @ApiOperation(value = "userId에게 들어온 팔로우 요청 조회", notes = "사용자에게 들어온 팔로우 요청 목록 조회")
    public ResponseEntity<? extends BasicResponse> getFollowRequestList(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseEntity<>(userService.getFollowRequestList(userId)));
    }

    // userId를 기반으로 본인을 팔로우 한 목록 조회
    @GetMapping("/follower/{userId}")
    @ApiOperation(value = "userId를 팔로우한 목록 조회", notes = "팔로우한 목록 조회")
    public ResponseEntity<? extends BasicResponse> getFollowerList(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK)
                    .body(new CommonResponseEntity<>(userService.getFollowerList(userId)));
    }

    // 팔로우 삭제
    @DeleteMapping("/follow/{follower-user-id}/{following-user-id}")
    @ApiOperation(value = "followerId(팔로워)가 followingId(팔로잉)을 팔로우 취소", notes = "팔로우 취소")
    public ResponseEntity<? extends BasicResponse> unfollow(@PathVariable Long followerUserId, @PathVariable Long followingUserId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseEntity<>(userService.unfollow(followerUserId, followingUserId)));
    }
}
