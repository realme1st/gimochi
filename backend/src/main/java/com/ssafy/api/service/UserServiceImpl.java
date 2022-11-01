package com.ssafy.api.service;

import com.ssafy.api.dto.FollowReqDto;
import com.ssafy.common.exception.CustomException;
import com.ssafy.common.exception.ErrorCode;
import com.ssafy.db.entity.FriendsList;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.FriendsListRepository;
import com.ssafy.db.repository.UserRepository;
import com.ssafy.db.repository.UserRepositorySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserRepositorySupport userRepositorySupport;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private  FriendsListRepository friendsListRepository;
	
//	@Override
//	public User createUser(UserRegisterPostReq userRegisterInfo) {
//		User user = new User();
//		user.setUserId(userRegisterInfo.getId());
//
//		return userRepository.save(user);
//	}

	@Override
	public User getUserByUserEmail(String userEmail) {
		// 디비에 유저 정보 조회 (userId 를 통한 조회).
		User user = userRepositorySupport.findUserByUserEmail(userEmail).get();
		return user;
	}


	@Transactional
	public boolean follow(FollowReqDto followReqDto) {
		// 이미 팔로우 했는지 확인
		if (friendsListRepository.existsByFollowerUserIdAndFollowingUserId(followReqDto.getFollowerUserId(), followReqDto.getFollowingUserId())) {
			throw new CustomException(ErrorCode.ALREADY_FOLLOW);
		}
		// 유효한 사용자들인지 확인
		if (!userRepository.existsByUserId(followReqDto.getFollowerUserId()) || !userRepository.existsByUserId(followReqDto.getFollowingUserId())) {
			throw new CustomException(ErrorCode.INVALID_USER);
		}
		// 팔로우 추가
		FriendsList friendsList = FriendsList.builder()
				.followerUserId(followReqDto.getFollowerUserId())
				.followingUserId(followReqDto.getFollowingUserId())
				.build();
		friendsListRepository.save(friendsList);
		return true;
	}



}
