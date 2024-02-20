package com.sulsul.suldaksuldak.dto.liquor.snack;

import com.sulsul.suldaksuldak.domain.tag.LiquorSnack;
import lombok.Value;

@Value
public class LiquorSnackDto {
    Long id;
    String name;

    public static LiquorSnackDto of (
            Long id,
            String name
    ) {
        return new LiquorSnackDto(
                id,
                name
        );
    }

    public static LiquorSnackDto of (LiquorSnack liquorSnack) {
        return new LiquorSnackDto(liquorSnack.getId(), liquorSnack.getName());
    }

    public LiquorSnack toEntity() {
        return LiquorSnack.of(
                id,
                name
        );
    }

    public LiquorSnack updateEntity(
            LiquorSnack liquorSnack
    ) {
        if (name != null) liquorSnack.setName(name);
        return liquorSnack;
    }
}
