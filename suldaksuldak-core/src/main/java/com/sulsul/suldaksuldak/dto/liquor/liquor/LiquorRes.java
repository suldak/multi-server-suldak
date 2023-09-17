package com.sulsul.suldaksuldak.dto.liquor.liquor;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class LiquorRes {
    Long id;
    String name;
    String summaryExplanation;
    String detailExplanation;
    Long liquorAbvId;
    Long liquorDetailId;
    Long drinkingCapacityId;
    LocalDateTime createdAt;
    LocalDateTime modifiedAt;

    public static LiquorRes from (LiquorDto liquorDto) {
        return new LiquorRes(
                liquorDto.getId(),
                liquorDto.getName(),
                liquorDto.getSummaryExplanation(),
                liquorDto.getDetailExplanation(),
                liquorDto.getLiquorAbvId(),
                liquorDto.getLiquorDetailId(),
                liquorDto.getDrinkingCapacityId(),
                liquorDto.getCreatedAt(),
                liquorDto.getModifiedAt()
        );
    }
}
