package com.ssafy.api.response;


import lombok.Builder;
import lombok.Getter;

@Getter
public class ChallengeInfoRankResDto {
    private int myRank;

    private String winnerName;

    private Long userId;

    private int successCnt;

    private Integer totalCnt;
    @Builder
    public ChallengeInfoRankResDto(String winnerName, int myRank,  Long userId, int successCnt,Integer totalCnt) {
        this.myRank = myRank;
        this.winnerName = winnerName;
        this.userId = userId;
        this.successCnt = successCnt;
        this.totalCnt=totalCnt;

    }

    public static ChallengeInfoRankResDto toDto(String winnerName,int myRank, Long userId, int successCnt,Integer totalCnt) {
        return ChallengeInfoRankResDto.builder()
                .myRank(myRank)
                .winnerName(winnerName)
                .userId(userId)
                .successCnt(successCnt)
                .totalCnt(totalCnt)
                .build();
    }

}
