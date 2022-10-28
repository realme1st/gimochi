package com.ssafy.api.controller;

import com.ssafy.api.dto.KakaoFriends;
import com.ssafy.api.dto.KakaoProfile;
import com.ssafy.api.dto.OauthToken;
import com.ssafy.api.service.KakaoService;
import com.ssafy.db.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "소셜 토큰 발급 API", tags = {"Oauth"})
@RestController
@RequestMapping("/api/kakao")
public class KakaoController {
    @Autowired
    KakaoService kakaoService;

    @GetMapping("/oauth/token")
    @ApiOperation(value = "AccessToken 발급", notes = "인가코드로 카카오 AccessToken을 발급받는다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "발급 실패"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public String getLogin(
            @RequestParam("code") String code) {

        // 넘어온 인가 코드를 통해 access_token 발급
        OauthToken oauthToken = kakaoService.getAccessToken(code);
        User user = kakaoService.saveUser(oauthToken);

        return user.getUserSocialToken();
    }

    @GetMapping("/friends")
    @ApiOperation(value = "카카오톡 친구목록 불러오기", notes = "AccessToken 사용해서 카카오 친구목록 호출")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "호출 실패"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public KakaoFriends getKakaoFriends(
            @RequestParam("token") String token) {

        KakaoFriends kakaoFriends = kakaoService.getKakaoFriends(token);

        return kakaoFriends;
    }

    @GetMapping("/oauth/user/info")
    @ApiOperation(value = "사용자 정보 조회", notes = "카카오userId로 사용자 정보를 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "발급 실패"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public KakaoProfile getFriendInfo(
            @RequestParam("userId") Long userId) {

        // 넘어온 인가 코드를 통해 access_token 발급
        KakaoProfile oauthToken = kakaoService.findFriend(userId);
//        User user = kakaoService.saveUser(oauthToken);

        return oauthToken;
    }

}
