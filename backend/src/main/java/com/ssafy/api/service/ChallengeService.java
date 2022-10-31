package com.ssafy.api.service;

import com.ssafy.api.dto.ChallengeReqDto;
import com.ssafy.api.request.ChallengePostReq;
import com.ssafy.db.entity.Challenge;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.ChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service("ChallengeService")
@Transactional(readOnly = true)
public class ChallengeService {

    @Autowired
    ChallengeRepository challengeRepository;

    @Transactional
    public Challenge createChllenge(ChallengeReqDto challengeReqDto){
        Challenge challenge = Challenge.builder()
                .challengeTitle(challengeReqDto.getChallengeTitle())
                .challengeUserId(challengeReqDto.getChallengeUserId())
                .challengeDescription(challengeReqDto.getChallengeDescription())
                .challengeParticipant(challengeReqDto.getChallengeParticipant())
                .challengeStartTime(challengeReqDto.getChallengeStartTime())
                .challengeEndTime(challengeReqDto.getChallengeEndTime())
                .build();

        return challengeRepository.save(challenge);
    }
    public List<Challenge> getChallengeList(){
        return challengeRepository.findAll();
    }
}

