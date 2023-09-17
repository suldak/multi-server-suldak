package com.sulsul.suldaksuldak.dto.liquor.snack;

import lombok.Value;

@Value
public class LiquorSnackRes {
    Long id;
    String name;

    public static LiquorSnackRes from (
            LiquorSnackDto liquorSnackDto
    ) {
        return new LiquorSnackRes(
                liquorSnackDto.getId(),
                liquorSnackDto.getName()
        );
    }
}
