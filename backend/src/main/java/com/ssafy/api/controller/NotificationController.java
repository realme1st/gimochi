package com.ssafy.api.controller;

import com.ssafy.api.service.NotificationService;
import com.ssafy.db.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
