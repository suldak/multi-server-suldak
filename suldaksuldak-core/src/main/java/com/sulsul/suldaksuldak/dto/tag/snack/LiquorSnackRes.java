package com.sulsul.suldaksuldak.dto.tag.snack;

import com.sulsul.suldaksuldak.constant.file.FileUrl;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

//@Value
@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "안주 정보")
public class LiquorSnackRes {
    @ApiModelProperty(value = "안주 기본키")
    Long id;
    @ApiModelProperty(value = "안주 이름")
    String name;
    @ApiModelProperty(value = "안주 사진 Url")
    String fileBaseNm;

    public static LiquorSnackRes from (
            LiquorSnackDto liquorSnackDto
    ) {
        return new LiquorSnackRes(
                liquorSnackDto.getId(),
                liquorSnackDto.getName(),
                liquorSnackDto.getFileBaseNm() == null ? null :
                        FileUrl.FILE_DOWN_URL.getUrl() + liquorSnackDto.getFileBaseNm()
        );
    }
}
