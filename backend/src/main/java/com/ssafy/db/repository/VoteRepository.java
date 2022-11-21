package com.ssafy.db.repository;

import com.ssafy.db.entity.ChallengeAuth;
import com.ssafy.db.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findByAuthUserIdAndVoteUserId(Long authUserId, Long voteUserId);

    Optional<Vote> findByAuthUserIdAndVoteUserIdAndChallengeAuthChallengeAuthId(Long authUserId, Long voteUserId, Long challengeAuthId);
}
