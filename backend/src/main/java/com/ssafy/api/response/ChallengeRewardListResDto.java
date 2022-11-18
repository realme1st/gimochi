package com.ssafy.api.response;

import com.ssafy.db.entity.Gifticon;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChallengeRewardListResDto {

    private List<Gifticon> gifticonList;

    @Builder
    public ChallengeRewardListResDto(List<Gifticon> gifticonList) {
        this.gifticonList = gifticonList;
    }

}
