package com.ssafy.api.controller;

import com.ssafy.api.dto.OauthToken;
import com.ssafy.api.service.KakaoService;
import com.ssafy.common.model.response.BaseResponseBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping
    @ApiOperation(value = "AccessToken 발급", notes = "인가코드로 카카오 AccessToken을 발급받는다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "발급 실패"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> getLogin(
            @RequestParam("code") String code) {

        // 넘어온 인가 코드를 통해 access_token 발급 //(5)
        OauthToken oauthToken = kakaoService.getAccessToken(code);

        if (true) {
            return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
        } else {
            return ResponseEntity.status(404).body(BaseResponseBody.of(404, "Fail"));
        }
    }

}
