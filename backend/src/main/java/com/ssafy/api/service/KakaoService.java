package com.ssafy.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.api.dto.*;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service("kakaoService")
@Slf4j
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
//        params.add("redirect_uri", "http://localhost:3000/kakao/oauth");
        //ssl 인증서 설정 전
        //params.add("redirect_uri", "http://k7a205.p.ssafy.io/kakao/oauth");

        //ssl 인증서 설정 후
        params.add("redirect_uri", "https://k7a205.p.ssafy.io/kakao/oauth");
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
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return oauthToken;
    }



    @Transactional
    public UserLoginDto saveUser(OauthToken token) {

        KakaoProfile profile = findProfile(token.getAccess_token());
        Optional<User> user = userRepository.findByUserEmail(profile.getKakao_account().getEmail());

        if (!user.isPresent()) {
            User newUser = User.builder()
                    .userKakaoId(profile.getId())
                    .userNickname(profile.getProperties().getNickname())
                    .userEmail(profile.getKakao_account().getEmail())
                    .userBirthday(profile.getKakao_account().getBirthday())
                    .userSocialToken(token.getAccess_token())
                    .userSocialRefreshToken(token.getRefresh_token())
                    .build();
            userRepository.save(newUser);
            UserLoginDto userLoginDto = new UserLoginDto();
            userLoginDto.setUserEmail(newUser.getUserEmail());
            userLoginDto.setUserNickname(newUser.getUserNickname());
            userLoginDto.setUserSocialToken(newUser.getUserSocialToken());
            userLoginDto.setUserSocialRefreshToken(newUser.getUserSocialRefreshToken());
            userLoginDto.setNewUser(true);

            return userLoginDto;

        } else {
            user.get().changeSocialTokenInfo(token.getAccess_token(),token.getRefresh_token());
            UserLoginDto userLoginDto = new UserLoginDto();
            userLoginDto.setUserEmail(user.get().getUserEmail());
            userLoginDto.setUserNickname(user.get().getUserNickname());
            userLoginDto.setUserSocialToken(user.get().getUserSocialToken());
            userLoginDto.setUserSocialRefreshToken(user.get().getUserSocialRefreshToken());
            userLoginDto.setNewUser(true);
            return userLoginDto;
        }
    }

    //saveUser 오버로드 메소드
    @Transactional
    public User saveUser(String accessToken, String refreshToken) {

        KakaoProfile profile = findProfile(accessToken);
        Optional<User> user = userRepository.findByUserEmail(profile.getKakao_account().getEmail());

        if (!user.isPresent()) {
            User newUser = User.builder()
                    .userKakaoId(profile.getId())
                    .userNickname(profile.getProperties().getNickname())
                    .userEmail(profile.getKakao_account().getEmail())
                    .userBirthday(profile.getKakao_account().getBirthday())
                    .userSocialToken(accessToken)
                    .userSocialRefreshToken(refreshToken)
                    .build();
            userRepository.save(newUser);
            return newUser;

        } else {
            user.get().changeSocialTokenInfo(accessToken,refreshToken);
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
        ObjectMapper objectMapper = new ObjectMapper();
        KakaoFriends kakaoFriends = null;
        // Http 요청 (POST 방식) 후, response 변수에 응답을 받음
        ResponseEntity<String> kakaoFriendsResponse = rt.exchange(
                "https://kapi.kakao.com/v1/api/talk/friends",
                HttpMethod.GET,
                kakaoFriendsRequest,
                String.class
        );
        try {
            kakaoFriends = objectMapper.readValue(kakaoFriendsResponse.getBody(), KakaoFriends.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        return kakaoFriends;

    }

    public KakaoProfile findUserByKakao(Long userKakaoId) {

        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + "db844548151ae781f128d34c2cf7ec73"); //(1-4)
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("target_id_type", "user_id");
        params.add("target_id", String.valueOf(userKakaoId));

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
                new HttpEntity<>(params, headers);

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

    public TokenInforamtion checkToken(String token) {
        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token); //(1-4)
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> tokenRequest =
                new HttpEntity<>(headers);

        ObjectMapper objectMapper = new ObjectMapper();
        TokenInforamtion tokenInforamtion = null;

        try {
            ResponseEntity<String> tokenInformationResponse = rt.exchange(
                    "https://kapi.kakao.com/v1/user/access_token_info",
                    HttpMethod.GET,
                    tokenRequest,
                    String.class
            );
            try {
                tokenInforamtion = objectMapper.readValue(tokenInformationResponse.getBody(), TokenInforamtion.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } catch (HttpClientErrorException e) {
            refreshToken(token);
        }


        return tokenInforamtion;
    }
    @Transactional
    public RefreshedTokenResDto refreshToken(String token) {
        User user = userRepository.findByUserSocialToken(token).get();
        String refreshToken = user.getUserSocialRefreshToken();
        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "refresh_token");
        params.add("refresh_token", refreshToken);
        params.add("client_secret", "5YBXwlymi8l1I2H57ZObnlrOQ4NNUrnS");
        params.add("client_id", "0e3c9cecfd800e2aae8228d69a635959");

        HttpEntity<MultiValueMap<String, String>> refreshRequest =
                new HttpEntity<>(params,headers);

        ObjectMapper objectMapper = new ObjectMapper();
        RefreshedToken refreshedToken = null;
        RefreshedTokenResDto refreshedTokenResDto = null;

        ResponseEntity<String> tokenInformationResponse = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                refreshRequest,
                String.class
        );
        try {
            refreshedToken = objectMapper.readValue(tokenInformationResponse.getBody(), RefreshedToken.class);
            refreshedTokenResDto = RefreshedTokenResDto.builder()
                    .id_token(refreshedToken.getId_token())
                    .access_token(refreshedToken.getAccess_token())
                    .token_type(refreshedToken.getToken_type())
                    .refresh_token(refreshedToken.getRefresh_token())
                    .expires_in(refreshedToken.getExpires_in())
                    .refresh_token_expires_in(refreshedToken.getRefresh_token_expires_in())
                    .build();
            if(refreshedToken.getRefresh_token() == null){
                user.changeSocialTokenInfo(refreshedToken.getAccess_token(), user.getUserSocialRefreshToken());
            }else{
                user.changeSocialTokenInfo(refreshedToken.getAccess_token(), refreshedToken.getRefresh_token());
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return refreshedTokenResDto;
    }

    public UserIdDto expireToken(String token) {

        User user = userRepository.findByUserSocialToken(token).get();
        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token); //(1-4)
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("target_id_type", "user_id");
        params.add("target_id", String.valueOf(user.getUserKakaoId()));

        HttpEntity<MultiValueMap<String, String>> logOutRequest =
                new HttpEntity<>(params, headers);

        // Http 요청 (POST 방식) 후, response 변수에 응답을 받음
        ResponseEntity<String> logoutResponse = rt.exchange(
                "https://kapi.kakao.com/v1/user/logout",
                HttpMethod.POST,
                logOutRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        UserIdDto dto = new UserIdDto();
        try {
            dto = objectMapper.readValue(logoutResponse.getBody(), UserIdDto.class);
            user.changeSocialTokenInfo(user.getUserSocialToken(),"expired");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return dto;

    }

}
