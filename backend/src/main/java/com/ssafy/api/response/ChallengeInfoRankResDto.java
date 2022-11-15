package com.ssafy.api.response;


import lombok.Builder;
import lombok.Getter;

@Getter
public class ChallengeInfoRankResDto {
    private int myRank;

    private String winnerName;

    private Long userId;
    @Builder
    public ChallengeInfoRankResDto(String winnerName, int myRank,  Long userId) {
        this.myRank = myRank;
        this.winnerName = winnerName;
        this.userId = userId;
    }

    public static ChallengeInfoRankResDto toDto(String winnerName,int myRank, Long userId){
        return ChallengeInfoRankResDto.builder()
                .myRank(myRank)
                .winnerName(winnerName)
                .userId(userId)
                .build();
    }

}