package com.sulsul.suldaksuldak.dto.liquor.liquor;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class LiquorRes {
    Long id;
    String name;
    String summaryExplanation;
    String detailExplanation;
    LocalDateTime createdAt;
    LocalDateTime modifiedAt;

    public static LiquorRes from (LiquorDto liquorDto) {
        return new LiquorRes(
                liquorDto.getId(),
                liquorDto.getName(),
                liquorDto.getSummaryExplanation(),
                liquorDto.getDetailExplanation(),
                liquorDto.getCreatedAt(),
                liquorDto.getModifiedAt()
        );
    }
}
