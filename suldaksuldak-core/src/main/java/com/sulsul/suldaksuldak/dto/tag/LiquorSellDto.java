package com.sulsul.suldaksuldak.dto.tag;

import com.sulsul.suldaksuldak.domain.tag.LiquorSell;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "판매처 Request")
public class LiquorSellDto {
    @ApiModelProperty(value = "재료 기본키 (생략하면 생성)")
    Long id;
    @ApiModelProperty(value = "재료 이름", required = true)
    String name;

    public static LiquorSellDto of (LiquorSell liquorSell) {
        return new LiquorSellDto(liquorSell.getId(), liquorSell.getName());
    }
}
