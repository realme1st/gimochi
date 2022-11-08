package com.ssafy.api.service;

import com.ssafy.api.dto.*;
import com.ssafy.api.response.ChallengeInviteRes;
import com.ssafy.api.response.ChallengeListRes;
import com.ssafy.api.response.UserListRes;
import com.ssafy.common.exception.CustomException;
import com.ssafy.common.exception.ErrorCode;
import com.ssafy.db.entity.*;
//import com.ssafy.db.repository.ChallengeInfoRepository;
import com.ssafy.db.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("ChallengeService")
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChallengeService {
    private final ChallengeRepository challengeRepository;
    private final ChallengeInfoRepository challengeInfoRepository;
    private final ChallengeAuthRepository challengeAuthRepository;
    private final UserRepository userRepository;
    private final ChallengeInviteRepository challengeInviteRepository;
    private final VoteRepository voteRepository;

    @Transactional
    public boolean createChllenge(ChallengeReqDto challengeReqDto) {

        User user = userRepository.findByUserId(challengeReqDto.getChallengeLeaderId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        //challenge id가 없는 경우(challenge 만들면서 info도 (방장이추가)
        Challenge challenge = Challenge.builder()
                .challengeTitle(challengeReqDto.getChallengeTitle())
                .challengeLeaderId(challengeReqDto.getChallengeLeaderId())
                .challengeDescription(challengeReqDto.getChallengeDescription())

                .challengeStartTime(challengeReqDto.getChallengeStartTime())
                .challengeEndTime(challengeReqDto.getChallengeEndTime())
                .challengeRewardType(challengeReqDto.getChallengeRewardType())
                .challengeLeaderName(user.getUserNickname())
                .build();

        challengeRepository.save(challenge);

        createChallengeInfoFirst(challenge);

        return true;
    }

    //challengeInfo를 먼저 찾아서 삭제하고 해야됨
    @Transactional
    public boolean deleteChallenge(Long challengeId) {

        Challenge challenge = challengeRepository.findByChallengeId(challengeId)
                .orElseThrow(() -> new CustomException(ErrorCode.CHALLENEGE_NOT_FOUND));

        try {
            challengeInfoRepository.delete(challengeInfoRepository.findByChallenge(challenge));
            challengeRepository.delete(challenge);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Transactional
    public void createChallengeInfoFirst(Challenge challenge) {

        User user = findUserByUserId(challenge.getChallengeLeaderId());

        ChallengeInfo challengeInfo = ChallengeInfo.builder()
                .challenge(challenge)
                .user(user)
                .build();

        challengeInfoRepository.save(challengeInfo);
    }


    public List<UserListRes> findUserListByChallengeId(Long challengeId) {

        Challenge challenge = findChallengeByChallengeId(challengeId);
        List<UserListRes> listRes = new ArrayList<>();
        List<ChallengeInfo> challengeInfoList = challengeInfoRepository.findUserListByChallengeId(challenge.getChallengeId())
                .orElseThrow(() -> new CustomException(ErrorCode.CHALLENEGE_NOT_FOUND));

        challengeInfoList.stream().forEach(challengeInfo -> {
            UserListRes userListRes = UserListRes.builder()
                    .userId(challengeInfo.getUser().getUserId())
                    .successCnt(challengeInfo.getSuccessCnt())
                    .build();

            listRes.add(userListRes);
        });

        return listRes;
    }

    //userId로 챌린지 List 가져오기
    public List<ChallengeListRes> findChallengeListByUserId(Long userId) {

        User user = findUserByUserId(userId);
        List<ChallengeInfo> userInfoList = challengeInfoRepository.findChallengeListByUserId(user.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.CHALLENEGE_NOT_FOUND));
        List<ChallengeListRes> listRes = new ArrayList<>();

        userInfoList.stream().forEach(challengeInfo -> {
            ChallengeListRes challengeListRes = ChallengeListRes.builder()
                    .challengeId(challengeInfo.getChallenge().getChallengeId())
                    .challengeName(challengeInfo.getChallenge().getChallengeTitle())
                    .successCnt(challengeInfo.getSuccessCnt())
                    .build();

            listRes.add(challengeListRes);
        });
        return listRes;
    }

    @Transactional
    public ChallengeInvite createChallengeInvite(ChallengeInviteReqDto challengeInviteReqDto) {
        // 사용자, 챌린지 유효성 체크
        User user = findUserByUserId(challengeInviteReqDto.getUserId());
        Challenge challenge = findChallengeByChallengeId(challengeInviteReqDto.getChallengeId());
        ChallengeInvite challengeInvite = ChallengeInvite.builder()
                .challenge(challenge)
                .user(user)
                .build();
        // ChallengeInvite 저장
        try {
            challengeInviteRepository.save(challengeInvite);
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.CHALLENGE_SAVE_ERROR);
        }
        return challengeInvite;
    }

    @Transactional
    public ChallengeAuth createChallengeAuth(ChallengeAuthReqDto challengeAuthReqDto) {

        ChallengeInfo challengeInfo = challengeInfoRepository.findByChallengeInfoId(challengeAuthReqDto.getChallengeInfoId())
                .orElseThrow(() -> new CustomException(ErrorCode.CHALLENGEINFO_NOT_FOUND));
        // 사용자, 챌린지 유효성 체크
        ChallengeAuth challengeauth = ChallengeAuth.builder()
                .challengeInfo(challengeInfo)
                .challengePath(challengeAuthReqDto.getChallengePath())
                .voteCnt(0)
                .challengeDate(challengeAuthReqDto.getChallengeDate())
                .isConfirm(0)
                .challengerCnt(challengeInfoRepository.findChallegerCntByChallengeId(challengeInfo.getChallenge().getChallengeId()))
                .build();

        return challengeAuthRepository.save(challengeauth);

    }

    //vote table 생성
    @Transactional
    public List<ChallengeInviteRes> findChallengeInviteList(Long userId) {
        // userId가 속한 ChallengeInvite 리스트 가져오기
        List<ChallengeInvite> challengeInviteList = challengeInviteRepository.findAllByChallengeInviteUserId(userId);
        if (challengeInviteList.isEmpty()){
            throw new CustomException(ErrorCode.CHALLENGEINVITE_USER_NOT_FOUND);
        }
        // challengeInviteList에서 challengeId만 추출해서 그걸로 챌린지 정보 가져오기
        List<Long> challengeIdList = new ArrayList<>();
        List<ChallengeInviteRes> result = new ArrayList<>();

        challengeInviteList.stream().forEach(challengeInvite -> {
            challengeIdList.add(challengeInvite.getChallenge().getChallengeId());
        });

        challengeIdList.stream().forEach(id -> {
            Challenge challenge = findChallengeByChallengeId(id);
            result.add(ChallengeInviteRes.builder()
                    .challengeLeaderName(challenge.getChallengeLeaderName())
                    .challengeTitle(challenge.getChallengeTitle())
                    .challengeId(challenge.getChallengeId())
                    .build());
        });

        return result;
    }

    @Transactional
    public boolean acceptChallengeInvite(Long challengeInviteId) {
        // challengeInviteId로 ChallengeInvite 가져오기
        ChallengeInvite challengeInvite = challengeInviteRepository.findByChallengeInviteId(challengeInviteId)
                .orElseThrow(() -> new CustomException(ErrorCode.CHALLENGE_INVITE_NOT_FOUND));
        // ChallengeInvite의 challengeId로 Challenge 가져오기
        Challenge challenge = challengeInvite.getChallenge();
        // Challenge의 challengeInfoList에 ChallengeInvite의 userId 추가
        challengeInfoRepository.save(ChallengeInfo.builder()
                .challenge(challenge)
                .user(challengeInvite.getUser())
                .successCnt(0)
                .build());
        // ChallengeInvite 삭제
        challengeInviteRepository.delete(challengeInvite);

        return true;
    }


    @Transactional
    public Vote createVote(VoteReqDto voteReqDto) {

        ChallengeAuth challengeAuth = challengeAuthRepository.findByChallengeAuthId(voteReqDto.getChallengeAuthId())
                .orElseThrow(() -> new CustomException(ErrorCode.CHALLENGE_AUTH_NOT_FOUND));
        Vote vote = Vote.builder()
                .challengeAuth(challengeAuth)
                .authUserId(voteReqDto.getAuthUserId())
                .voteUserId(voteReqDto.getVoteUserId())
                .build();

        // Vote 저장
        try {
            voteRepository.save(vote);
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.CHALLENGE_SAVE_ERROR);
        }

        return vote;
    }

    @Transactional
    public ChallengeAuth updateChallengeAuth(UpdateChallengeAuthReqDto updateChallengeAuthReqDto) {

        ChallengeAuth challengeAuth = challengeAuthRepository.findByChallengeAuthId(updateChallengeAuthReqDto.getChallengeAuthId())
                .orElseThrow(() -> new CustomException(ErrorCode.CHALLENGE_AUTH_NOT_FOUND));
        Long authUserId = updateChallengeAuthReqDto.getAuthUserId();
        Long voteUserId = updateChallengeAuthReqDto.getVoteUserId();
        // User 유효성 검사
        findUserByUserId(updateChallengeAuthReqDto.getAuthUserId());
        findUserByUserId(updateChallengeAuthReqDto.getVoteUserId());

        // 두 사용자 모두 해당 챌린지에 속해있는지에 대한 유효성 검사
        List<UserListRes> userList = findUserListByChallengeId(challengeAuth.getChallengeInfo().getChallenge().getChallengeId());
        List<Long> userIdList = new ArrayList<>();

        userList.stream().forEach(list -> userIdList.add(list.getUserId()));

        if (!userIdList.contains(authUserId)) {
            if (!userIdList.contains(voteUserId)) {
                throw new CustomException(ErrorCode.BOTH_USER_NOT_EXIST_CHALLENGE);
            }
            throw new CustomException(ErrorCode.AUTH_USER_NOT_EXIST_CHALLENGE);
        }
        if (!userIdList.contains(voteUserId)) throw new CustomException(ErrorCode.VOTE_USER_NOT_EXIST_CHALLENGE);

        /*   투표 진행
         *   1. vote 한적 있는지 확인
         *   2. 이미 과반수인지 확인
         *   3. 증가 이전에 이미 과반수를 넘겼다면 증가만 시키고 pass
         *   4. 증가 이후 과반수를 넘겼다면 해당 유저의 챌린지 info를 찾아  successCnt 증가시킨다.
         * */
        try {
            // 1
            voteRepository.findByAuthUserIdAndVoteUserId(authUserId, voteUserId);
            voteRepository.save(Vote.builder().authUserId(authUserId).voteUserId(voteUserId).build());
            // 2
            challengeAuth.voteCntUp();
            // 3
            if (challengeAuth.getIsConfirm() == 1) return challengeAuth;
            // 4
            int half = challengeAuth.getChallengerCnt() / 2;    // 과반수 판단용 변수
            int cnt = challengeAuth.getVoteCnt();
            if (cnt >= half) {
                ChallengeInfo challengeInfo = challengeAuth.getChallengeInfo();
                challengeInfo.successCntUp();
                challengeAuth.isConfirm();
                challengeInfoRepository.save(challengeInfo);
                challengeAuthRepository.save(challengeAuth);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new CustomException(ErrorCode.CHALLENGE_ALREADY_VOTED); // 이미 투표 했다면 에러가 발생하지 않으므로 여기서 예외처리
        }

        return challengeAuth;
    }

    public List<Challenge> getChallengeList() {
        return challengeRepository.findAll();
    }

    public Challenge findChallengeByChallengeId(Long challengeId) {
        return challengeRepository.findByChallengeId(challengeId).orElseThrow(() -> new CustomException(ErrorCode.CHALLENEGE_NOT_FOUND));
    }

    public User findUserByUserId(Long userId) {
        return userRepository.findByUserId(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

}

