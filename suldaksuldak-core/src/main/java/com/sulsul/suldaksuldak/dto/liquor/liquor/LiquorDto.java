package com.sulsul.suldaksuldak.dto.liquor.liquor;

import com.sulsul.suldaksuldak.domain.liquor.Liquor;
import com.sulsul.suldaksuldak.domain.tag.LiquorAbv;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class LiquorDto {
    Long id;
    String name;
    String summaryExplanation;
    String detailExplanation;
    Long liquorAbvId;
    LocalDateTime createdAt;
    LocalDateTime modifiedAt;

    public static LiquorDto of (
            Long id,
            String name,
            String summaryExplanation,
            String detailExplanation,
            Long liquorAbvId
    ) {
        return new LiquorDto(
                id,
                name,
                summaryExplanation,
                detailExplanation,
                liquorAbvId,
                null,
                null
        );
    }

    public Liquor toEntity(LiquorAbv liquorAbv) {
        return Liquor.of(
                id,
                name,
                summaryExplanation,
                detailExplanation,
                liquorAbv
        );
    }

    public Liquor updateEntity(
            Liquor liquor,
            LiquorAbv liquorAbv
    ) {
        if (name != null) liquor.setName(name);
        if (summaryExplanation != null) liquor.setSummaryExplanation(summaryExplanation);
        if (detailExplanation != null) liquor.setDetailExplanation(detailExplanation);
        if (liquorAbv != null) liquor.setLiquorAbv(liquorAbv);

        return liquor;
    }
}
