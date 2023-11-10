package com.sulsul.suldaksuldak.dto.admin.user;

import com.sulsul.suldaksuldak.domain.admin.AdminUser;
import com.sulsul.suldaksuldak.dto.auth.UserDto;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class AdminUserDto {
    Long id;
    String adminId;
    String adminPw;
    String adminNm;
    LocalDateTime createdAt;
    LocalDateTime modifiedAt;

    public static AdminUserDto of (
            Long id,
            String adminId,
            String adminPw,
            String adminNm
    ) {
        return new AdminUserDto(
                id,
                adminId,
                adminPw,
                adminNm,
                null,
                null
        );
    }

    public AdminUser toEntity() {
        return AdminUser.of(
                id,
                adminId,
                adminPw,
                adminNm
        );
    }

    public AdminUser updateEntity(AdminUser user) {
        if (adminId != null) user.setAdminId(adminId);
        if (adminNm != null) user.setAdminNm(adminNm);

        return user;
    }

    public UserDto changeDto() {
        return UserDto.of(
                id,
                adminId,
                adminPw,
                adminNm,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }
}
