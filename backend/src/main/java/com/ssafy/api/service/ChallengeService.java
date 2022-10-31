package com.ssafy.api.service;

import com.ssafy.api.request.ChallengePostReq;
import com.ssafy.db.entity.Challenge;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.ChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service("ChallengeService")
@Transactional(readOnly = true)
public class ChallengeService {

    @Autowired
    ChallengeRepository challengeRepository;

    @Transactional
    public Challenge createChllenge(ChallengePostReq challengePostReq){
        Challenge challenge = Challenge.builder()
                .challengeUserId(challengePostReq.getChallengeUserId())
                .challengeTitle(challengePostReq.getChallengeTitle())
                .challengeDescription(challengePostReq.getChallengeDescription())
                .challengeParticipant(challengePostReq.getChallengeParticipant())
                .challengeStartTime(challengePostReq.getChallengeStartTime())
                .challengeEndTime(challengePostReq.getChallengeEndTime())
                .build();
        return challengeRepository.save(challenge);
    }

}

