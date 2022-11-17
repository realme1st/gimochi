package com.ssafy.api.service;

import com.ssafy.api.dto.UserResDto;
import com.ssafy.api.request.FollowReqDto;
import com.ssafy.api.dto.FriendDto;
import com.ssafy.api.dto.SingleMessageReqDto;
import com.ssafy.api.response.UserUsageResDto;
import com.ssafy.common.exception.CustomException;
import com.ssafy.common.exception.ErrorCode;
import com.ssafy.db.entity.FriendsList;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.FriendsListRepository;
import com.ssafy.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("userService")
@RequiredArgsConstructor
@Slf4j
public class UserService {
	private final UserRepository userRepository;
	private final FriendsListRepository friendsListRepository;
	private final NotificationService notificationService;

	/*
	 * description : 사용자 정보 조회 메소드
	 * @param userId : userId
	 * @return UserResDto :UserDto 객체
	 * */
	public UserResDto getUser(Long userId){
		User user = userRepository.findByUserId(userId).orElseThrow(() -> new CustomException(ErrorCode.INVALID_USER));
		UserResDto userResDto = UserResDto.builder()
				.userId(userId)
				.userBirthday(user.getUserBirthday())
				.userEmail(user.getUserEmail())
				.userKakaoId(user.getUserKakaoId())
				.userNickname(user.getUserNickname())
				.userPoint(user.getUserPoint())
				.userProfile(user.getUserProfile())
				.build();
		return userResDto;
	}

	/*
	 * description : 사용자 기프티콘 등록/사용 조회 메소드
	 * @param userId : userId
	 * @return UserUsageResDto :UserUsageResDto 객체
	 * */
	public UserUsageResDto getUsage(Long userId){
		User user = userRepository.findByUserId(userId).orElseThrow(() -> new CustomException(ErrorCode.INVALID_USER));
		UserUsageResDto userUsageResDto = UserUsageResDto.builder()
				.registCount(
						user.getGifticonsList()
								.stream()
								.filter(gifticon -> {return !gifticon.isGifticonUsed();}).collect(Collectors.toList()).size())
				.usedCount(user.getUsedCount())
				.build();
		return userUsageResDto;
	}

	/*
	 * description : 사용 기프티콘 카운트 업 메소드
	 * @param userId : userId
	 * @return boolean : 성공 여부
	 * */
	@Transactional
	public boolean countUpUsed(Long userId){
		User user = userRepository.findByUserId(userId).orElseThrow(() -> new CustomException(ErrorCode.INVALID_USER));
		user.countUpUsed();
		userRepository.save(user);
		return true;
	}

	/*
	* description : 팔로우 요청을 처리하는 메소드
	* @param followReqDto : 팔로우 요청을 위한 Dto
	* @return boolean : 팔로우 요청 성공 여부
	* */
	@Transactional
	public boolean followRequest(FollowReqDto followReqDto) {
		// 유효한 사용자들인지 확인
		if (!userRepository.existsByUserId(followReqDto.getFollowerUserId()) || !userRepository.existsByUserId(followReqDto.getFollowingUserId())) {
			throw new CustomException(ErrorCode.INVALID_USER);
		}
		// 이미 팔로우 했는지 확인
		if (friendsListRepository.existsByFollowerIdAndFollowingId(followReqDto.getFollowerUserId(), followReqDto.getFollowingUserId())) {
			throw new CustomException(ErrorCode.ALREADY_FOLLOW);
		}
		// 팔로우 추가
		FriendsList friendsList = FriendsList.builder()
				.followerId(followReqDto.getFollowerUserId())
				.followingId(followReqDto.getFollowingUserId())
				.isFriend(false)
				.build();
		friendsListRepository.save(friendsList);

		SingleMessageReqDto singleMessageReqDto = SingleMessageReqDto
				.builder()
				.senderId(followReqDto.getFollowerUserId())
				.receiverId(followReqDto.getFollowingUserId())
				.type(2)
				.build();

		notificationService.sendToUser(singleMessageReqDto);

		return true;
	}

	/*
	 * description : 팔로우 요청을 수락하는 메소드
	 * @param followReqDto : 팔로우 요청 수락을 위한 Dto
	 * @return boolean : 팔로우 요청 수락 성공 여부
	 * */
	@Transactional
	public boolean acceptFollow(FollowReqDto followReqDto) {
		// 유효한 사용자들인지 확인
		if (!userRepository.existsByUserId(followReqDto.getFollowerUserId()) || !userRepository.existsByUserId(followReqDto.getFollowingUserId())) {
			throw new CustomException(ErrorCode.INVALID_USER);
		}
		// 이미 팔로우 했는지 확인
		if (friendsListRepository.existsByFollowerIdAndFollowingId(followReqDto.getFollowerUserId(), followReqDto.getFollowingUserId())
		&&!friendsListRepository.findByFollowerIdAndFollowingId(followReqDto.getFollowerUserId(), followReqDto.getFollowingUserId()).get().isFriend()) {
			FriendsList friendsList = friendsListRepository.findByFollowerIdAndFollowingId(followReqDto.getFollowerUserId(), followReqDto.getFollowingUserId()).get();
			// 친구 상태 업데이트
			friendsList.acceptRequest(true);
			friendsListRepository.save(friendsList);

			FriendsList friendsListEntity = FriendsList.builder()
					.followerId(followReqDto.getFollowingUserId())
					.followingId(followReqDto.getFollowerUserId())
					.isFriend(true)
					.build();
			friendsListRepository.save(friendsListEntity);

			SingleMessageReqDto singleMessageReqDto = SingleMessageReqDto
					.builder()
					.senderId(followReqDto.getFollowingUserId())
					.receiverId(followReqDto.getFollowerUserId())
					.type(3)
					.build();
			notificationService.sendToUser(singleMessageReqDto);
			return true;
		}else{
			throw new CustomException(ErrorCode.USER_NOT_FOUND);
		}
	}

	/*
	 * description : 팔로우 요청을 거절하는 메소드
	 * @param followReqDto : 팔로우 요청 거절을 위한 Dto
	 * @return boolean : 팔로우 요청 거절 성공 여부
	 * */
	@Transactional
	public boolean rejectFollow(FollowReqDto followReqDto) {
		// 유효한 사용자들인지 확인
		if (!userRepository.existsByUserId(followReqDto.getFollowerUserId()) || !userRepository.existsByUserId(followReqDto.getFollowingUserId())) {
			throw new CustomException(ErrorCode.INVALID_USER);
		}
		// 이미 팔로우 했는지 확인
		if (friendsListRepository.existsByFollowerIdAndFollowingId(followReqDto.getFollowerUserId(), followReqDto.getFollowingUserId())
				&&!friendsListRepository.findByFollowerIdAndFollowingId(followReqDto.getFollowerUserId(), followReqDto.getFollowingUserId()).get().isFriend()) {
			FriendsList friendsList = friendsListRepository.findByFollowerIdAndFollowingId(followReqDto.getFollowerUserId(), followReqDto.getFollowingUserId()).get();
			friendsListRepository.delete(friendsList);
			return true;
		}else{
			throw new CustomException(ErrorCode.USER_NOT_FOUND);
		}
	}

	/*
	 * description : 팔로우 목록을 불러오는 메소드
	 * @param userId : 확인하고자 하는 사용자의 id
	 * @return List<User> : user가 팔로우 하고있는 사용자들의 목록(리스트)
	 * */
	@Transactional
	public List<FriendDto> getFollowingList(Long userId) {
		// 유효한 user인지 확인
		isVaildUser(userId);
		// 팔로잉 리스트 조회
		List<User> followingList = new ArrayList<>();
		List<FriendsList> friendsList = friendsListRepository.findAllByFollowerId(userId).orElseThrow(() -> new CustomException(ErrorCode.INVALID_USER));

		List<FriendDto> friendDtoList = new ArrayList<>();
		friendDtoList = friendsList.stream().map(friend ->{
			User user = userRepository.findByUserId(friend.getFollowingId()).orElseThrow(() -> new CustomException(ErrorCode.INVALID_USER));
			FriendDto friendDto = FriendDto.builder()
					.userId(friend.getFollowingId())
					.userName(user.getUserNickname())
					.isFriend(friend.isFriend())
					.userProfile(user.getUserProfile())
					.build();
			return friendDto;
		}).collect(Collectors.toList());

		return friendDtoList;
	}
	/*
	 * description : 팔로워 목록을 불러오는 메소드
	 * @param userId : 확인하고자 하는 사용자의 id
	 * @return List<User> : user를 팔로우 하고있는 사용자들의 목록(리스트)
	 * */
	public List<FriendDto> getFollowerList(Long userId) {
		// 유효한 user인지 확인
		isVaildUser(userId);
		// 팔로워 리스트 조회
		List<User> followerList = new ArrayList<>();
		List<FriendsList> friendsList = friendsListRepository.findAllByFollowingId(userId).orElseThrow(() -> new CustomException(ErrorCode.INVALID_USER));
		List<FriendDto> friendDtoList = new ArrayList<>();
		friendDtoList = friendsList.stream().map(friend ->{
			User user = userRepository.findByUserId(friend.getFollowerId()).orElseThrow(() -> new CustomException(ErrorCode.INVALID_USER));
			if(friend.isFriend()){
				FriendDto friendDto = FriendDto.builder()
						.userId(friend.getFollowerId())
						.userName(user.getUserNickname())
						.isFriend(friend.isFriend())
						.userProfile(user.getUserProfile())
						.build();
				return friendDto;
			}else{
				return null;
			}
		}).collect(Collectors.toList());
		friendDtoList = friendDtoList.stream().filter(el-> el != null).collect(Collectors.toList());
		return friendDtoList;

	}

	/*
	 * description : 팔로워 목록을 불러오는 메소드
	 * @param userId : 확인하고자 하는 사용자의 id
	 * @return List<User> : user를 팔로우 하고있는 사용자들의 목록(리스트)
	 * */
	public List<FriendDto> getFollowRequestList(Long userId) {
		// 유효한 user인지 확인
		isVaildUser(userId);
		// 팔로워 리스트 조회
		List<User> followerList = new ArrayList<>();
		List<FriendsList> friendsList = friendsListRepository.findAllByFollowingId(userId).orElseThrow(() -> new CustomException(ErrorCode.INVALID_USER));
		List<FriendDto> friendDtoList = new ArrayList<>();
		friendDtoList = friendsList.stream().map(friend ->{
			User user = userRepository.findByUserId(friend.getFollowerId()).orElseThrow(() -> new CustomException(ErrorCode.INVALID_USER));
			if(!friend.isFriend()){
				FriendDto friendDto = FriendDto.builder()
						.userId(friend.getFollowerId())
						.userName(user.getUserNickname())
						.isFriend(friend.isFriend())
						.userProfile(user.getUserProfile())
						.build();
				return friendDto;
			}else{
				return null;
			}
		}).collect(Collectors.toList());

		friendDtoList = friendDtoList.stream().filter(el-> el != null).collect(Collectors.toList());
		return friendDtoList;

	}
	/*
	 * description : 언팔로우 요청을 처리하는 메소드
	 * @param followReqDto : 언팔로우 요청을 위한 Dto
	 * @return boolean : 언팔로우 성공 여부
	 * */
	@Transactional
	public boolean unfollow(Long followerUserId, Long followingUserId) {
		// 유효한 요청인지 확인
		if (!friendsListRepository.existsByFollowerIdAndFollowingId(followerUserId, followingUserId)) {
			throw new CustomException(ErrorCode.INVALID_REQUEST);
		}
		// 언팔로우 하기
		friendsListRepository.deleteByFollowerIdAndFollowingId(followerUserId, followingUserId);
		friendsListRepository.deleteByFollowerIdAndFollowingId(followingUserId,followerUserId);

		return true;
	}

	/*
	 * description : 유효한 User 인지 확인하는 메소드
	 * @param followReqDto : 확인하고자 하는 사용자의 id
	 * @return boolean : 유효하지 않다면 에러 발생
	 * */
	public void isVaildUser(Long userId){
		if (!userRepository.existsByUserId(userId)) {
			throw new CustomException(ErrorCode.INVALID_USER);
		}
	}

}
