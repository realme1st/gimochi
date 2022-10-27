package com.ssafy.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.api.dto.KakaoProfile;
import com.ssafy.api.dto.OauthToken;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Optional;

@Service("kakaoService")
//@Transactional(readOnly = true)
public class KakaoService {

    @Autowired
    UserRepository userRepository;

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

    public User saveUser(String token) {

        //(1)
        KakaoProfile  profile = findProfile(token);

        //(2)

        Optional<User> user = userRepository.findByUserEmail(profile.getKakao_account().getEmail());


        //(3)
        if(!user.isPresent()) {
            SimpleDateFormat formatter = new SimpleDateFormat("MMdd");
            try{
                User newUser = User.builder()
                        .userNickname(profile.getProperties().getNickname())
                        .userEmail(profile.getKakao_account().getEmail())
                        .userBirthday(formatter.parse(profile.getKakao_account().getBirthday()))
                        .build();
                userRepository.save(newUser);
                return newUser;
            }catch (java.text.ParseException e){
                e.printStackTrace();
            }
        }

        return null;
    }
    public KakaoProfile findProfile(String token) {

        //(1-2)
        RestTemplate rt = new RestTemplate();

        //(1-3)
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token); //(1-4)
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //(1-5)
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
                new HttpEntity<>(headers);

        //(1-6)
        // Http 요청 (POST 방식) 후, response 변수에 응답을 받음
        ResponseEntity<String> kakaoProfileResponse = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        //(1-7)
        ObjectMapper objectMapper = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper.readValue(kakaoProfileResponse.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return kakaoProfile;

    }

}
