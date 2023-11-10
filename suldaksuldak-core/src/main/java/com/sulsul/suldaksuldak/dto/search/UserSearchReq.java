package com.sulsul.suldaksuldak.dto.search;

import com.sulsul.suldaksuldak.constant.auth.Gender;
import com.sulsul.suldaksuldak.constant.auth.Registration;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "유저 검색 Request")
public class UserSearchReq {
    @ApiModelProperty(value = "유저 이메일 (아이디)")
    String userEmail;
    @ApiModelProperty(value = "유저 이름")
    String nickname;
    @ApiModelProperty(value = "유저 성별")
    Gender gender;
    @ApiModelProperty(value = "유저 출생 연도 (정확한 검색)")
    Integer birthdayYear;
    @ApiModelProperty(value = "유저 출생 연도 범위 시작 (출생 연도 범위 검색을 위한 시작 연도)")
    Integer startYear;
    @ApiModelProperty(value = "유저 출생 연도 범위 끝 (출생 연도 범위 검색을 위한 끝 연도)")
    Integer endYear;
    @ApiModelProperty(value = "가입 방법")
    Registration registration;
    @ApiModelProperty(value = "유저 레빌 리스트 (검색하고 싶은 레벨을 리스트에 넣어주세요)")
    List<Integer> levelList;
    @ApiModelProperty(value = "유저 경고 점수 (검색하고 싶은 경고 점수를 리스트에 넣어주세요)")
    List<Integer> warningCntList;
    @ApiModelProperty(value = "유저 탈퇴 여부")
    Boolean isActive;
}
