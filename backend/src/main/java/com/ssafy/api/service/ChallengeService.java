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
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("ChallengeService")
@Transactional(readOnly = true)
public class ChallengeService {

    @Autowired
    private ChallengeRepository challengeRepository;

    @Autowired
    private ChallengeInfoRepository challengeInfoRepository;
    @Autowired
    private ChallengeAuthRepository challengeAuthRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChallengeInviteRepository challengeInviteRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Transactional
    public boolean createChllenge(ChallengeReqDto challengeReqDto){

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
    public List<Challenge> getChallengeList(){

        return challengeRepository.findAll();
    }


    public Optional<Challenge> getChallengeListByChallengeId(Long challengeId) {
        return challengeRepository.findByChallengeId(challengeId);
    }

    //challengeInfo를 먼저 찾아서 삭제하고 해야됨
    @Transactional
    public boolean deleteChallenge(Long challengeId) {
        Challenge challenge = challengeRepository.findByChallengeId(challengeId).orElseThrow(() -> new CustomException(ErrorCode.CHALLENEGE_NOT_FOUND));
        try {
            challengeInfoRepository.delete(challengeInfoRepository.findByChallenge(challenge));
            challengeRepository.delete(challenge);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public Challenge findChallengeByChallengeId(Long challengeId) {
        return challengeRepository.findByChallengeId(challengeId).orElseThrow(() -> new CustomException(ErrorCode.CHALLENEGE_NOT_FOUND));
    }

    public User findUserByUserId(Long userId) {
        return userRepository.findByUserId(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    public User findBychallengeLeaderId(Long userId){
        return challengeRepository.findByChallengeLeaderId(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    @Transactional
    public ChallengeInfo createChallengeInfoFirst(Challenge challenge) {

        User user =findUserByUserId(challenge.getChallengeLeaderId());

        ChallengeInfo challengeInfo = ChallengeInfo.builder()
                .challenge(challenge)
                .user(user)
                .build();

        return challengeInfoRepository.save(challengeInfo);
    }


    public List<UserListRes> findUserListByChallengeId(Long challengeId){
        Challenge challenge = findChallengeByChallengeId(challengeId);
        List<ChallengeInfo> challengeInfoList = challengeInfoRepository.findUserListByChallengeId(challenge.getChallengeId()).orElseThrow(()->new CustomException(ErrorCode.CHALLENEGE_NOT_FOUND));

        List<UserListRes> listres =new ArrayList<>();
        for(ChallengeInfo challengeInfo : challengeInfoList){

            UserListRes userListRes = UserListRes.builder()
                    .userId(challengeInfo.getUser().getUserId())
                    .successCnt(challengeInfo.getSuccessCnt())
                    .build();

            listres.add(userListRes);
        }
        return listres;
    }

    //userId로 챌린지 List 가져오기
    public List<ChallengeListRes> findChallengeListByUserId(Long userId){
        User user = findUserByUserId(userId);
        List<ChallengeInfo> userInfoList = challengeInfoRepository.findChallengeListByUserId(user.getUserId()).orElseThrow(()->new CustomException(ErrorCode.CHALLENEGE_NOT_FOUND));

        List<ChallengeListRes> listres =new ArrayList<>();
        for(ChallengeInfo challengeInfo : userInfoList){

            ChallengeListRes challengeListRes = ChallengeListRes.builder()
                    .challengeId(challengeInfo.getChallenge().getChallengeId())
                    .challengeName(challengeInfo.getChallenge().getChallengeTitle())
                    .successCnt(challengeInfo.getSuccessCnt())
                    .build();

            listres.add(challengeListRes);
        }
        return listres;
    }

    public List<ChallengeInfo> getUserListByChallengeId(Long challengeId) {
        Challenge challenge = findChallengeByChallengeId(challengeId);
        List<ChallengeInfo> challengeInfoList = challenge.getChallengeInfoList();
        return challengeInfoList;
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
        try{
            challengeInviteRepository.save(challengeInvite);
        }catch (IllegalArgumentException e){
            throw new CustomException(ErrorCode.CHALLENGE_SAVE_ERROR);
        }

        return challengeInvite;
    }

    @Transactional
    public ChallengeAuth createChallengeAuth(ChallengeAuthReqDto challengeAuthReqDto){

        ChallengeInfo challengeInfo = challengeInfoRepository.findByChallengeInfoId(challengeAuthReqDto.getChallengeInfoId()).orElseThrow(() -> new CustomException(ErrorCode.CHALLENGEINFO_NOT_FOUND));

        // 사용자, 챌린지 유효성 체크
        ChallengeAuth challengeauth = ChallengeAuth.builder()
                .challengeInfo(challengeInfo)
                .challengePath(challengeAuthReqDto.getChallengePath())
                .voteCnt(0)
                .challengeDate(challengeAuthReqDto.getChallengeDate())
                .isConfirm(0)
                .build();

        return challengeAuthRepository.save(challengeauth);

    }

    //vote table 생성
    @Transactional



    public List<ChallengeInviteRes> findChallengeInviteList(Long userId) {
        // userId가 속한 ChallengeInvite 리스트 가져오기
        List<ChallengeInvite> challengeInviteList = challengeInviteRepository.findAllByChallengeInviteUserId(userId);

        // challengeInviteList에서 challengeId만 추출해서 그걸로 챌린지 정보 가져오기
        List<Long> challengeIdList = new ArrayList<>();
        for (ChallengeInvite challengeInvite : challengeInviteList) {
            challengeIdList.add(challengeInvite.getChallenge().getChallengeId());
        }
        List<ChallengeInviteRes> result = new ArrayList<>();
        for(Long id : challengeIdList){
            Challenge challenge = findChallengeByChallengeId(id);
            result.add(ChallengeInviteRes.builder()
                    .challengeLeaderName(challenge.getChallengeLeaderName())
                    .challengeTitle(challenge.getChallengeTitle())
                    .challengeId(challenge.getChallengeId())
                    .build());
        }
        return result;
    }

    @Transactional
    public boolean acceptChallengeInvite(Long challengeInviteId) {
        // challengeInviteId로 ChallengeInvite 가져오기
        ChallengeInvite challengeInvite = challengeInviteRepository.findByChallengeInviteId(challengeInviteId).orElseThrow(() -> new CustomException(ErrorCode.CHALLENGE_INVITE_NOT_FOUND));

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

        ChallengeAuth challengeAuth = challengeAuthRepository.findByChallengeAuthId(voteReqDto.getChallengeAuthId()).orElseThrow(() -> new CustomException(ErrorCode.CHALLENGE_AUTH_NOT_FOUND));

        Vote vote = Vote.builder()
                .challengeAuth(challengeAuth)
                .authUserId(voteReqDto.getAuthUserId())
                .voteUserId(voteReqDto.getVoteUserId())
                .build();

        // Vote 저장
        try{
            voteRepository.save(vote);
        }catch (IllegalArgumentException e){
            throw new CustomException(ErrorCode.CHALLENGE_SAVE_ERROR);
        }

        return vote;
    }
}

