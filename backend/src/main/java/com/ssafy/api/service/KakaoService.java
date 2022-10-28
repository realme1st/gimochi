package com.ssafy.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.api.dto.KakaoFriends;
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

import java.util.Optional;

@Service("kakaoService")
//@Transactional(readOnly = true)
public class KakaoService {

    @Autowired
    UserRepository userRepository;

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
    @Transactional
    public User saveUser(OauthToken token) {

        KakaoProfile  profile = findProfile(token.getAccess_token());
        Optional<User> user = userRepository.findByUserEmail(profile.getKakao_account().getEmail());

        if(!user.isPresent()) {
            User newUser = User.builder()
                    .userKakaoId(profile.getId())
                    .userNickname(profile.getProperties().getNickname())
                    .userEmail(profile.getKakao_account().getEmail())
                    .userBirthday(profile.getKakao_account().getBirthday())
                    .userSocialToken(token.getAccess_token())
                    .build();
            userRepository.save(newUser);
            return newUser;

        }else{
            user.get().changeSocialTokenInfo(token.getAccess_token());
            return user.get();
        }
    }
    public KakaoProfile findProfile(String token) {

        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token); //(1-4)
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
                new HttpEntity<>(headers);

        // Http 요청 (POST 방식) 후, response 변수에 응답을 받음
        ResponseEntity<String> kakaoProfileResponse = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper.readValue(kakaoProfileResponse.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return kakaoProfile;

    }

    public KakaoFriends getKakaoFriends(String token) {

        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token); //(1-4)
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoFriendsRequest =
                new HttpEntity<>(headers);

        // Http 요청 (POST 방식) 후, response 변수에 응답을 받음
        ResponseEntity<String> kakaoFriendsResponse = rt.exchange(
                "https://kapi.kakao.com/v1/api/talk/friends",
                HttpMethod.GET,
                kakaoFriendsRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        KakaoFriends kakaoFriends = null;
        try {
            kakaoFriends = objectMapper.readValue(kakaoFriendsResponse.getBody(), KakaoFriends.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return kakaoFriends;

    }

    public KakaoProfile findFriend(Long userId) {

        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + "db844548151ae781f128d34c2cf7ec73"); //(1-4)
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("target_id_type", "user_id");
        params.add("target_id", String.valueOf(userId));

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
                new HttpEntity<>(params,headers);

        // Http 요청 (POST 방식) 후, response 변수에 응답을 받음
        ResponseEntity<String> kakaoProfileResponse = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

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
