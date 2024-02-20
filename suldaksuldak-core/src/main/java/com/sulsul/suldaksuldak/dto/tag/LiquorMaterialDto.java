package com.sulsul.suldaksuldak.dto.tag;

import com.sulsul.suldaksuldak.constant.file.FileUrl;
import com.sulsul.suldaksuldak.domain.tag.LiquorMaterial;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Setter
@Getter
@ToString
@EqualsAndHashCode
//@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "재료 Request")
public class LiquorMaterialDto {
    @ApiModelProperty(value = "재료 기본키 (생략하면 생성)")
    Long id;
    @ApiModelProperty(value = "재료 이름", required = true)
    String name;
    @ApiModelProperty(value = "재료 이름", hidden = true)
    String fileBaseNm;

    public LiquorMaterialDto (
            Long id,
            String name,
            String fileBaseNm
    ) {
        this.id = id;
        this.name = name;
        this.fileBaseNm = fileBaseNm;
    }

    public static LiquorMaterialDto of (LiquorMaterial liquorMaterial) {
        return new LiquorMaterialDto(
                liquorMaterial.getId(),
                liquorMaterial.getName(),
                liquorMaterial.getFileBase() == null ? null :
                        FileUrl.FILE_DOWN_URL.getUrl() + liquorMaterial.getFileBase().getFileNm()
        );
    }
}
