package com.sulsul.suldaksuldak.dto.tag.snack;

import com.sulsul.suldaksuldak.domain.file.FileBase;
import com.sulsul.suldaksuldak.domain.tag.LiquorSnack;
import lombok.Value;

@Value
public class LiquorSnackDto {
    Long id;
    String name;
    String fileBaseNm;

    public static LiquorSnackDto of (
            Long id,
            String name
    ) {
        return new LiquorSnackDto(
                id,
                name,
                null
        );
    }

    public static LiquorSnackDto of (LiquorSnack liquorSnack) {
        return new LiquorSnackDto(
                liquorSnack.getId(),
                liquorSnack.getName(),
                liquorSnack.getFileBase() == null ? null :
                        liquorSnack.getFileBase().getFileNm()
        );
    }

    public LiquorSnack toEntity(
            FileBase fileBase
    ) {
        return LiquorSnack.of(
                id,
                name,
                fileBase
        );
    }

    public LiquorSnack updateEntity(
            LiquorSnack liquorSnack,
            FileBase fileBase
    ) {
        if (name != null) liquorSnack.setName(name);
        if (fileBase != null) liquorSnack.setFileBase(fileBase);
        return liquorSnack;
    }
}
