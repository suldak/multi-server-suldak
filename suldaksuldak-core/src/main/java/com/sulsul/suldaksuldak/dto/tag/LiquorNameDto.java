package com.sulsul.suldaksuldak.dto.tag;

import com.sulsul.suldaksuldak.constant.file.FileUrl;
import com.sulsul.suldaksuldak.domain.tag.LiquorName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Setter
@Getter
@ToString
@EqualsAndHashCode
//@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "1차 분류 Request")
public class LiquorNameDto {
    @ApiModelProperty(value = "1차 분류 기본키 (생략하면 생성)", hidden = true)
    Long id;
    @ApiModelProperty(value = "1차 분류 이름", required = true)
    String name;
    @ApiModelProperty(value = "파일 경로", hidden = true)
    String fileBaseNm;

    public LiquorNameDto (
            Long id,
            String name,
            String fileBaseNm
    ) {
        this.id = id;
        this.name = name;
        this.fileBaseNm = fileBaseNm == null ? null : FileUrl.FILE_DOWN_URL.getUrl() + fileBaseNm;
    }


    public static LiquorNameDto of (LiquorName liquorName) {
        return new LiquorNameDto(
                liquorName.getId(),
                liquorName.getName(),
                liquorName.getFileBase() == null ?
                        null : FileUrl.FILE_DOWN_URL.getUrl() + liquorName.getFileBase().getFileNm()
        );
    }
}
