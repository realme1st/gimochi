package com.ssafy.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.api.dto.OauthToken;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service("kakaoService")
@Transactional(readOnly = true)
public class KakaoService {

    /*
    추가 해야 될 것 : 발급받은 AccessToken DB저장
     */
    @Transactional
    public OauthToken getAccessToken(String code) {

        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "0e3c9cecfd800e2aae8228d69a635959");
        //로컬에서 할 때
        params.add("redirect_uri", "http://localhost:3000/kakao/oauth");
        //ssl 인증서 설정 전
        //params.add("redirect_uri", "http://k7a205.p.ssafy.io/kakao/oauth");
        
        //ssl 인증서 설정 후
        //params.add("redirect_uri", "https://k7a205.p.ssafy.io/kakao/oauth");
        params.add("code", code);
        params.add("client_secret", "5YBXwlymi8l1I2H57ZObnlrOQ4NNUrnS"); // 생략 가능!

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        ResponseEntity<String> accessTokenResponse = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        OauthToken oauthToken = null;


        try {
            oauthToken = objectMapper.readValue(accessTokenResponse.getBody(), OauthToken.class);
            /*
            발급 받은 토큰 디비 저장 필요

             */
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return oauthToken; 
    }
}
