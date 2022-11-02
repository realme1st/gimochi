package com.ssafy.api.controller;

import com.ssafy.api.dto.*;
import com.ssafy.api.service.KakaoService;
import com.ssafy.db.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "소셜 토큰 발급 API", tags = {"Oauth"})
@RestController
@RequestMapping("/api/kakao")
public class KakaoController {
    @Autowired
    KakaoService kakaoService;

    @GetMapping("/oauth/token")
    @ApiOperation(value = "AccessToken 발급 및 회원가입/로그인", notes = "인가코드로 카카오 AccessToken을 발급받고 로그인 한다. 비회원은 자동 회원가입 처리")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "발급 실패"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public User getAccessToken(
            @RequestHeader(value = "code") String code) {
        // 넘어온 인가 코드를 통해 access_token 발급
        OauthToken oauthToken = kakaoService.getAccessToken(code);
        
        //db에 없다면 회원 가입처리
        //있다면 그냥 진행하기
        User user = kakaoService.saveUser(oauthToken);
        return user;
    }

    @GetMapping("/oauth/token")
    @ApiOperation(value = "AccessToken 발급 및 회원가입/로그인", notes = "인가코드로 카카오 AccessToken을 발급받고 로그인 한다. 비회원은 자동 회원가입 처리")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "발급 실패"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public User getLogin(
            @RequestHeader(value = "AccessToken") String accessToken, @RequestHeader(value = "RefreshToken") String refreshToken ) {
        //db에 없다면 회원 가입처리
        //있다면 그냥 진행하기
        User user = kakaoService.saveUser(accessToken, refreshToken);
        return user;
    }

    @GetMapping("/oauth/logout")
    @ApiOperation(value = "소셜 로그아웃", notes = "해당 사용자의 AccessToken을 만료시킨다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "발급 실패"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public UserIdDto getLogout(
            @RequestHeader(value = "token") String token) {
        UserIdDto userIdDto = kakaoService.expireToken(token);
        return userIdDto;
    }

    @GetMapping("/friends")
    @ApiOperation(value = "카카오톡 친구목록 불러오기", notes = "AccessToken 사용해서 카카오 친구목록 호출")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "호출 실패"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public KakaoFriends getKakaoFriends(
            @RequestHeader(value = "token") String token) {
        TokenInforamtion tokenInfo = kakaoService.checkToken(token);
        KakaoFriends kakaoFriends = kakaoService.getKakaoFriends(token);
        return kakaoFriends;
    }

    @GetMapping("/oauth/user/info")
    @ApiOperation(value = "사용자 카카오 프로필 조회", notes = "카카오 userId로 카카오프로필을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "발급 실패"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public KakaoProfile getUserInfoByKakaoId(
            @RequestParam("userKakaoId") Long userKakaoId) {

        KakaoProfile kakaoProfile = kakaoService.findUserByKakao(userKakaoId);
//        User user = kakaoService.saveUser(oauthToken);

        return kakaoProfile;
    }

}
