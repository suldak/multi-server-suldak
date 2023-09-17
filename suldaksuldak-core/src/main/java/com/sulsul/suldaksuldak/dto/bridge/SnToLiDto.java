package com.sulsul.suldaksuldak.dto.bridge;

import com.sulsul.suldaksuldak.domain.bridge.SnToLi;
import com.sulsul.suldaksuldak.domain.liquor.Liquor;
import com.sulsul.suldaksuldak.domain.liquor.LiquorSnack;
import lombok.Value;

@Value
public class SnToLiDto {
    Long id;
    Long liquorPriKey;
    String liquorName;
    Long liquorSnackPriKey;
    String liquorSnackName;

    public SnToLi toEntity(
            Liquor liquor,
            LiquorSnack liquorSnack
    ) {
        return SnToLi.of(
                id,
                liquorSnack,
                liquor
        );
    }
}
