package com.sulsul.suldaksuldak.dto.search;

import com.sulsul.suldaksuldak.domain.search.RecommendSearchText;
import lombok.Value;

@Value
public class RecommendSearchTextDto {
    Long id;
    Boolean isActive;
    String text;

    public static RecommendSearchTextDto of (
            Long id,
            Boolean isActive,
            String text
    ) {
        return new RecommendSearchTextDto(
                id,
                isActive,
                text
        );
    }

    public RecommendSearchText toEntity() {
        return RecommendSearchText.of(
                id,
                isActive,
                text
        );
    }

    public RecommendSearchText updateEntity(
            RecommendSearchText recommendSearchText
    ) {
//        if (isActive != null) recommendSearchText.setIsActive(isActive);
        if (text != null) recommendSearchText.setText(text);
        return recommendSearchText;
    }
}
