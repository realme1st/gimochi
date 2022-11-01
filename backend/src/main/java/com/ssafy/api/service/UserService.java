package com.ssafy.api.service;

import com.ssafy.api.dto.FollowReqDto;
import com.ssafy.db.entity.User;

import java.util.List;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface UserService {
    User getUserByUserEmail(String userEmail);
    boolean follow(FollowReqDto followReqDto);
    boolean unfollow(FollowReqDto followReqDto);
    List<User> getFollowingList(Long userId);
    List<User> getFollowerList(Long userId);

}
