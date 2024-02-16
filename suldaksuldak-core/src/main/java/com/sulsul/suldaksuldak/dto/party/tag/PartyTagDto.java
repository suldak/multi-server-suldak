package com.sulsul.suldaksuldak.dto.party.tag;

import com.sulsul.suldaksuldak.domain.party.PartyTag;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "모임 태그 Request")
public class PartyTagDto {
    @ApiModelProperty(value = "모임 TAG 기본키 (생략하면 생성)")
    Long id;
    @ApiModelProperty(value = "태그 이름", required = true)
    String name;

    public static PartyTagDto of (
            PartyTag partyTag
    ) {
        return new PartyTagDto(
                partyTag.getId(),
                partyTag.getName()
        );
    }
}
