package com.sulsul.suldaksuldak.dto.admin.user;

import com.sulsul.suldaksuldak.dto.auth.TokenRes;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@ApiModel(value = "Admin User Response")
public class AdminUserRes {
    @ApiModelProperty(value = "관리자 유저")
    Long id;
    @ApiModelProperty(value = "관리자 ID")
    String adminId;
    @ApiModelProperty(value = "관리자 이름")
    String adminNm;
    @ApiModelProperty(value = "생성일자")
    LocalDateTime createdAt;
    @ApiModelProperty(value = "수정일자")
    LocalDateTime modifiedAt;
    @ApiModelProperty(value = "Refresh Token")
    String refreshToken;

    public static AdminUserRes from (
            AdminUserDto userDto,
            TokenRes tokenRes
    ) {
        return new AdminUserRes(
                userDto.getId(),
                userDto.getAdminId(),
                userDto.getAdminNm(),
                userDto.getCreatedAt(),
                userDto.getModifiedAt(),
                tokenRes.getRefreshToken()
        );
    }

    public static AdminUserRes from (
            AdminUserDto userDto
    ) {
        return new AdminUserRes(
                userDto.getId(),
                userDto.getAdminId(),
                userDto.getAdminNm(),
                userDto.getCreatedAt(),
                userDto.getModifiedAt(),
                null
        );
    }
}
