package com.sulsul.suldaksuldak.dto.stats.user;

import lombok.Value;

@Value
public class UserLiquorTagDto {
    Long id;
    Long userId;
    Long liquorId;
    Double searchCnt;
    Long liquorAbvPriKey;
    Long liquorDetailPriKey;
    Long drinkingCapacityPriKey;
    Long liquorNamePriKey;
}
