package com.sulsul.suldaksuldak.dto.bridge;

import lombok.Value;

@Value
public class AbvToLiDto {
    Long id;
    Long abvId;
    String abvName;
    Long liquorId;
    String liquorName;
}
