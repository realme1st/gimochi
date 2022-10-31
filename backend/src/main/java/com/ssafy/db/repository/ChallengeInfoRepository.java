package com.ssafy.db.repository;

import com.ssafy.db.entity.Challenge;
import com.ssafy.db.entity.ChallengeInfo;
import com.ssafy.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChallengeInfoRepository extends JpaRepository<ChallengeInfo, Long> {

    //Optional<List<ChallengeInfo>> findChallengeInfoByChallengeId(Long challengeId);
}
