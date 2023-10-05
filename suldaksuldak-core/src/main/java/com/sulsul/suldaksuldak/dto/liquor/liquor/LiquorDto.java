package com.sulsul.suldaksuldak.dto.liquor.liquor;

import com.sulsul.suldaksuldak.domain.liquor.Liquor;
import com.sulsul.suldaksuldak.domain.tag.DrinkingCapacity;
import com.sulsul.suldaksuldak.domain.tag.LiquorAbv;
import com.sulsul.suldaksuldak.domain.tag.LiquorDetail;
import com.sulsul.suldaksuldak.domain.tag.LiquorName;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class LiquorDto {
    Long id;
    String name;
    String summaryExplanation;
    String detailExplanation;
    String searchTag;
    String liquorRecipe;
    Double detailAbv;
    Long liquorAbvId;
    Long liquorDetailId;
    Long drinkingCapacityId;
    Long liquorNameId;
    LocalDateTime createdAt;
    LocalDateTime modifiedAt;

    public static LiquorDto of (
            Long id,
            String name,
            String summaryExplanation,
            String detailExplanation,
            String searchTag,
            String liquorRecipe,
            Double detailAbv,
            Long liquorAbvId,
            Long liquorDetailId,
            Long drinkingCapacityId,
            Long liquorNameId
    ) {
        return new LiquorDto(
                id,
                name,
                summaryExplanation,
                detailExplanation,
                searchTag,
                liquorRecipe,
                detailAbv,
                liquorAbvId,
                liquorDetailId,
                drinkingCapacityId,
                liquorNameId,
                null,
                null
        );
    }

    public static LiquorDto of (
            Liquor liquor
    ) {
        return new LiquorDto(
                liquor.getId(),
                liquor.getName(),
                liquor.getSummaryExplanation(),
                liquor.getDetailExplanation(),
                liquor.getSearchTag(),
                liquor.getLiquorRecipe(),
                liquor.getDetailAbv(),
                liquor.getLiquorAbv() != null ? liquor.getLiquorAbv().getId() : null,
                liquor.getLiquorDetail() != null ? liquor.getLiquorDetail().getId() : null,
                liquor.getDrinkingCapacity() != null ? liquor.getDrinkingCapacity().getId() : null,
                liquor.getLiquorName() != null ? liquor.getLiquorName().getId(): null,
                liquor.getCreatedAt(),
                liquor.getModifiedAt()
        );
    }

    public Liquor toEntity(
            LiquorAbv liquorAbv,
            LiquorDetail liquorDetail,
            DrinkingCapacity drinkingCapacity,
            LiquorName liquorName
    ) {
        return Liquor.of(
                id,
                name,
                summaryExplanation,
                detailExplanation,
                searchTag,
                liquorRecipe,
                detailAbv,
                liquorAbv,
                liquorDetail,
                drinkingCapacity,
                liquorName
        );
    }

    public Liquor updateEntity(
            Liquor liquor,
            LiquorAbv liquorAbv,
            LiquorDetail liquorDetail,
            DrinkingCapacity drinkingCapacity,
            LiquorName liquorName
    ) {
        if (name != null) liquor.setName(name);
        if (summaryExplanation != null) liquor.setSummaryExplanation(summaryExplanation);
        if (detailExplanation != null) liquor.setDetailExplanation(detailExplanation);
        if (searchTag != null) liquor.setSearchTag(searchTag);
        if (liquorRecipe != null) liquor.setLiquorRecipe(liquorRecipe);
        if (liquorAbv != null) liquor.setLiquorAbv(liquorAbv);
        if (liquorDetail != null) liquor.setLiquorDetail(liquorDetail);
        if (drinkingCapacity != null) liquor.setDrinkingCapacity(drinkingCapacity);
        if (liquorName != null) liquor.setLiquorName(liquorName);

        return liquor;
    }

    public static Liquor updateLiquorRecipe(Liquor liquor, String liquorRecipe) {
        liquor.setLiquorRecipe(liquorRecipe);
        return liquor;
    }

    public static Liquor updateDetailAbv(
            Liquor liquor,
            Double detailAbv
    ) {
        liquor.setDetailAbv(detailAbv);
        return liquor;
    }
}
