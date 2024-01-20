package com.sulsul.suldaksuldak.dto.search;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "유저가 검색한 검색어 조회")
public class SearchTextReq {
    @ApiModelProperty(value = "조회 시작 일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    LocalDateTime searchStartTime;

    @ApiModelProperty(value = "조회 종료 일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    LocalDateTime searchEndTime;

    @ApiModelProperty(value = "Token 유저로 검색 여부 (True: 해당 유저가 검색한 내용, False: 전체 유저가 검색한 내용)", required = true)
    Boolean tokenSearch;
}
