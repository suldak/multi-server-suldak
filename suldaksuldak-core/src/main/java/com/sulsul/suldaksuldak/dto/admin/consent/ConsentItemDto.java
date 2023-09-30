package com.sulsul.suldaksuldak.dto.admin.consent;

import com.sulsul.suldaksuldak.constant.admin.ConsentItemType;
import com.sulsul.suldaksuldak.domain.admin.ConsentItem;
import lombok.Value;

@Value
public class ConsentItemDto {
    Long id;
    ConsentItemType itemType;
    Integer itemSeq;
    String itemText;

    public static ConsentItemDto of (
            Long id,
            ConsentItemType itemType,
            Integer itemSeq,
            String itemText
    ) {
        return new ConsentItemDto(
                id,
                itemType,
                itemSeq,
                itemText
        );
    }

    public ConsentItem toEntity() {
        return ConsentItem.of(
                id,
                itemType,
                itemSeq,
                itemText
        );
    }

    public ConsentItem updateEntity(
            ConsentItem consentItem
    ) {
        if (itemSeq != null) consentItem.setItemSeq(itemSeq);
        if (itemText != null) consentItem.setItemText(itemText);
        return consentItem;
    }
}
