package com.sulsul.suldaksuldak.dto.tag;

import com.sulsul.suldaksuldak.domain.tag.DrinkingCapacity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "주량 Request")
public class DrinkingCapacityDto {
    @ApiModelProperty(value = "주량 TAG 기본키 (생략하면 생성)")
    Long id;
    @ApiModelProperty(value = "주량 Level 이름", required = true)
    String name;

    public static DrinkingCapacityDto of (DrinkingCapacity drinkingCapacity) {
        return new DrinkingCapacityDto(
                drinkingCapacity.getId(),
                drinkingCapacity.getName()
        );
    }
}
