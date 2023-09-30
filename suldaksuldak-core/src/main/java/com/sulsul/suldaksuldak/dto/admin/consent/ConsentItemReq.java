package com.sulsul.suldaksuldak.dto.admin.consent;


import com.sulsul.suldaksuldak.constant.admin.ConsentItemType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "동의 항목 Request")
public class ConsentItemReq {
    @ApiModelProperty(value = "동의 항목 기본키 (생략하면 생성)")
    Long id;
    @ApiModelProperty(value = "동의 항목 종류", required = true)
    ConsentItemType itemType;
    @ApiModelProperty(value = "동의 항목 순서", required = true)
    Integer itemSeq;
    @ApiModelProperty(value = "동의 항목 내용", required = true)
    String itemText;

    public ConsentItemDto toDto() {
        return ConsentItemDto.of(
                id,
                itemType,
                itemSeq,
                itemText
        );
    }
}
