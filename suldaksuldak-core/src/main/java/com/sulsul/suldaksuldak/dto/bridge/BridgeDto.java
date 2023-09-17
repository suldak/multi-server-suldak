package com.sulsul.suldaksuldak.dto.bridge;

import lombok.Value;

@Value
public class BridgeDto {
    Long id;
    Long liquorPriKey;
    String liquorName;
    Long tagEntityPriKey;
    String tagEntityName;
}
