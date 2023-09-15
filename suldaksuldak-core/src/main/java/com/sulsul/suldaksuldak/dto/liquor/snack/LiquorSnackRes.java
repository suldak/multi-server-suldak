package com.sulsul.suldaksuldak.dto.liquor.snack;

import lombok.Value;

@Value
public class LiquorSnackRes {
    Long id;
    String name;

    public static LiquorSnackRes from (
            Long id,
            String name
    ) {
        return new LiquorSnackRes(
                id,
                name
        );
    }
}
