package com.ssafy.api.controller;

import com.ssafy.api.service.NotificationService;
import com.ssafy.db.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(value = "푸시 알림 API", tags = {"Notification"})
@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    @Autowired
    NotificationService notificationService;
    @PostMapping
    @ApiOperation(value = "Firebase 토큰 저장", notes = "Firebase 토큰 서버에 저장")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "발급 실패"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public User registFirebaseToken(
            @RequestHeader(value = "AccessToken") String accessToken, @RequestHeader(value = "FirebaseToken") String firebaseToken) {
        User user = notificationService.saveToken(accessToken,firebaseToken);
        return user;
    }

    //cd6e7_ZHRRewNpQK3yndXN:APA91bGuDmUdfG4mgkFsvc-m3T-JJ2U1h4oLlNJAZjCfiq6SYwX1EHBRZmbi6Sqnoq_tv8H5HnrwXmhbB8ZbgBpkjqLOb6L-c26J70THIEW1iKuz-H_l0nUfaEk9yOABn5CqZqbguf2u
    @PostMapping("/message")
    @ApiOperation(value = "메세지 보내기", notes = "test")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "발급 실패"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public User registFirebaseToken(
            @RequestParam String token) {
        List<String> tokens = new ArrayList<>();
        tokens.add(token);
        notificationService.sendByTokenList(tokens);
        return null;
    }
}
