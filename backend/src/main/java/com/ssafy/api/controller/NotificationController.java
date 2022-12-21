package com.ssafy.api.controller;

import com.ssafy.api.dto.FriendDto;
import com.ssafy.api.dto.MultiMessageReqDto;
import com.ssafy.api.dto.SingleMessageReqDto;
import com.ssafy.api.service.NotificationService;
import com.ssafy.api.service.UserService;
import com.ssafy.common.response.BasicResponse;
import com.ssafy.common.response.CommonResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "푸시 알림 API", tags = {"Notification"})
@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final UserService userService;

    @GetMapping("/token")
    @ApiOperation(value = "Firebase 토큰 저장", notes = "Firebase 토큰 서버에 저장")
    public  ResponseEntity<? extends BasicResponse> registFirebaseToken(
            @RequestHeader(value = "AccessToken") String accessToken, @RequestHeader(value = "FirebaseToken") String firebaseToken) {
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponseEntity<>(notificationService.saveToken(accessToken,firebaseToken)));
    }

    //알림 type에 따라서 단체 메세지 전송
    @PostMapping("/message/multi")
    @ApiOperation(value = "여러 디바이스에 메세지 전송", notes = "알림 타입에 따른 단체 알림 전송\n1 : 추카포카함 개설")
    public ResponseEntity<? extends BasicResponse> sendMultiMessage(@RequestBody MultiMessageReqDto multiMessageReqDto){
        List<FriendDto> userList = userService.getFollowerList(multiMessageReqDto.getUserId());
        return ResponseEntity.ok().body(new CommonResponseEntity<>(notificationService.sendToUserList(multiMessageReqDto, userList)));
    }

    //내 코드 : e1_Psj_qTka2UcQS4c56Hg:APA91bGgxlEeoH086bIdZVmcmiMmtmhIeCHhMPlyYFTMBcqTK_z5VZ8vP9w7JHHEsy-440dIIUgUjqbLfXpUzfDkqZW9976PyW6a9gmsuTBhzlVW6XlY-cC5xg07Lbfa6sib1o07rJbs
    //cd6e7_ZHRRewNpQK3yndXN:APA91bGuDmUdfG4mgkFsvc-m3T-JJ2U1h4oLlNJAZjCfiq6SYwX1EHBRZmbi6Sqnoq_tv8H5HnrwXmhbB8ZbgBpkjqLOb6L-c26J70THIEW1iKuz-H_l0nUfaEk9yOABn5CqZqbguf2u

    @PostMapping("/message/single")
    @ApiOperation(value = "특정 디바이스에 메세지 전송", notes = "알림 타입에 따른 알림 전송\n1 : 챌린지 초대\n2 : 친구 요청\n3 : 친구 요청 수락")
    public ResponseEntity<? extends BasicResponse> sendSingleMessage(
            @RequestBody SingleMessageReqDto singleMessageReqDto) {
        return ResponseEntity.ok().body(new CommonResponseEntity<>(notificationService.sendToUser(singleMessageReqDto)));
    }
}
