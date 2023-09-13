package com.sulsul.suldaksuldak.dto.liquor.liquor;

import com.sulsul.suldaksuldak.domain.liquor.Liquor;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class LiquorDto {
    Long id;
    String name;
    String summaryExplanation;
    String detailExplanation;
    LocalDateTime createdAt;
    LocalDateTime modifiedAt;

    public static LiquorDto of (
            Long id,
            String name,
            String summaryExplanation,
            String detailExplanation
    ) {
        return new LiquorDto(
                id,
                name,
                summaryExplanation,
                detailExplanation,
                null,
                null
        );
    }

    public Liquor toEntity() {
        return Liquor.of(
                id,
                name,
                summaryExplanation,
                detailExplanation
        );
    }

    public Liquor updateEntity(
            Liquor liquor
    ) {
        if (name != null) liquor.setName(name);
        if (summaryExplanation != null) liquor.setSummaryExplanation(summaryExplanation);
        if (detailExplanation != null) liquor.setDetailExplanation(detailExplanation);

        return liquor;
    }
}
