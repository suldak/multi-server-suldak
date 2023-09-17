package com.sulsul.suldaksuldak.dto.liquor.liquor;

import com.sulsul.suldaksuldak.domain.liquor.Liquor;
import com.sulsul.suldaksuldak.domain.tag.LiquorAbv;
import com.sulsul.suldaksuldak.domain.tag.LiquorDetail;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class LiquorDto {
    Long id;
    String name;
    String summaryExplanation;
    String detailExplanation;
    Long liquorAbvId;
    Long liquorDetailId;
    LocalDateTime createdAt;
    LocalDateTime modifiedAt;

    public static LiquorDto of (
            Long id,
            String name,
            String summaryExplanation,
            String detailExplanation,
            Long liquorAbvId,
            Long liquorDetailId
    ) {
        return new LiquorDto(
                id,
                name,
                summaryExplanation,
                detailExplanation,
                liquorAbvId,
                liquorDetailId,
                null,
                null
        );
    }

    public Liquor toEntity(
            LiquorAbv liquorAbv,
            LiquorDetail liquorDetail
    ) {
        return Liquor.of(
                id,
                name,
                summaryExplanation,
                detailExplanation,
                liquorAbv,
                liquorDetail
        );
    }

    public Liquor updateEntity(
            Liquor liquor,
            LiquorAbv liquorAbv,
            LiquorDetail liquorDetail
    ) {
        if (name != null) liquor.setName(name);
        if (summaryExplanation != null) liquor.setSummaryExplanation(summaryExplanation);
        if (detailExplanation != null) liquor.setDetailExplanation(detailExplanation);
        if (liquorAbv != null) liquor.setLiquorAbv(liquorAbv);
        if (liquorDetail != null) liquor.setLiquorDetail(liquorDetail);

        return liquor;
    }
}
