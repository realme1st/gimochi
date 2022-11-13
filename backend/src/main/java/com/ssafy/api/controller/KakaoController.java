package com.ssafy.api.controller;

import com.ssafy.api.dto.*;
import com.ssafy.api.service.KakaoService;
import com.ssafy.common.response.BasicResponse;
import com.ssafy.common.response.CommonResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "소셜 토큰 발급 API", tags = {"Oauth"})
@RestController
@RequestMapping("/api/kakao")
@RequiredArgsConstructor
public class KakaoController {
    private final KakaoService kakaoService;

    @GetMapping("/oauth/token")
    @ApiOperation(value = "AccessToken 발급 및 회원가입/로그인", notes = "인가코드로 카카오 AccessToken을 발급받고 로그인 한다. 비회원은 자동 회원가입 처리")
    public ResponseEntity<? extends BasicResponse> getAccessToken(
            @RequestHeader(value = "code") String code) {

        OauthToken oauthToken = kakaoService.getAccessToken(code);

        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponseEntity<>(kakaoService.saveUser(oauthToken)));
    }

    @GetMapping("/oauth/login")
    @ApiOperation(value = "AccessToken 발급 및 회원가입/로그인", notes = "인가코드로 카카오 AccessToken을 발급받고 로그인 한다. 비회원은 자동 회원가입 처리")
    public ResponseEntity<? extends BasicResponse> getLogin(
            @RequestHeader(value = "AccessToken") String accessToken, @RequestHeader(value = "RefreshToken") String refreshToken ) {
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponseEntity<>(kakaoService.saveUser(accessToken, refreshToken)));
    }

    @GetMapping("/oauth/logout")
    @ApiOperation(value = "소셜 로그아웃", notes = "해당 사용자의 AccessToken을 만료시킨다.")
    public ResponseEntity<? extends BasicResponse> getLogout(
            @RequestHeader(value = "token") String token) {
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponseEntity<>(kakaoService.expireToken(token)));
    }

    @GetMapping("/oauth/refreshToken")
    @ApiOperation(value = "AccessToken토큰 갱신", notes = "해당 사용자의 AccessToken을 갱신한다.")
    public ResponseEntity<? extends BasicResponse> refreshToken(
            @RequestHeader(value = "token") String token) {
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponseEntity<>(kakaoService.refreshToken(token)));
    }

    @GetMapping("/friends")
    @ApiOperation(value = "카카오톡 친구목록 불러오기", notes = "AccessToken 사용해서 카카오 친구목록 호출")
    public ResponseEntity<? extends BasicResponse> getKakaoFriends(
            @RequestHeader(value = "token") String token) {
        RefreshedTokenResDto tokenInfo = kakaoService.checkToken(token);
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponseEntity<>(kakaoService.getKakaoFriends(tokenInfo.getAccessToken())));
    }

    @GetMapping("/oauth/user/info")
    @ApiOperation(value = "사용자 카카오 프로필 조회", notes = "카카오 userId로 카카오프로필을 조회한다.")
    public ResponseEntity<? extends BasicResponse> getUserInfoByKakaoId(
            @RequestParam("userKakaoId") Long userKakaoId) {
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponseEntity<>(kakaoService.findUserByKakao(userKakaoId)));
    }

}
