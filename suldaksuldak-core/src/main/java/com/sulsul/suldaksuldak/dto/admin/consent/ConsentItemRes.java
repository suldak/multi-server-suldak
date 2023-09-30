package com.sulsul.suldaksuldak.dto.admin.consent;

import com.sulsul.suldaksuldak.constant.admin.ConsentItemType;
import lombok.Value;

@Value
public class ConsentItemRes {
    Long id;
    ConsentItemType itemType;
    Integer itemSeq;
    String itemText;

    public static ConsentItemRes from (
            ConsentItemDto consentItemDto
    ) {
        return new ConsentItemRes(
                consentItemDto.getId(),
                consentItemDto.getItemType(),
                consentItemDto.getItemSeq(),
                consentItemDto.getItemText()
        );
    }

}
