package com.sulsul.suldaksuldak.repo.admin.auth;

import com.sulsul.suldaksuldak.dto.auth.AdminUserDto;

import java.util.List;
import java.util.Optional;

public interface AdminUserRepositoryCustom {
    Optional<AdminUserDto> doLogin(
            String adminId,
            String adminPw
    );
    List<AdminUserDto> findAllAdminUser();
    List<AdminUserDto> findByUserId(String adminId);
}
