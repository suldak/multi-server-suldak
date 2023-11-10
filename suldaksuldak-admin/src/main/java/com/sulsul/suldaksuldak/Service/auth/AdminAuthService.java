package com.sulsul.suldaksuldak.Service.auth;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.constant.error.ErrorMessage;
import com.sulsul.suldaksuldak.dto.admin.user.AdminUserDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.admin.auth.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminAuthService {
    private final AdminUserRepository adminUserRepository;

    public Optional<AdminUserDto> loginAdminUser(
            String adminId,
            String adminPw
    ) {
        try {
            return adminUserRepository.doLogin(
                    adminId,
                    adminPw
            );
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    public Boolean createAdminUser(
            AdminUserDto adminUserDto
    ) {
        try {
            List<AdminUserDto> idList = adminUserRepository.findByUserId(adminUserDto.getAdminId());
            if (adminUserDto.getId() == null) {
                if (!idList.isEmpty()) {
                    throw new GeneralException(ErrorCode.BAD_REQUEST, "ID가 중복됩니다.");
                }
                adminUserRepository.save(
                        adminUserDto.toEntity()
                );
            } else {
                adminUserRepository.findById(adminUserDto.getId())
                        .ifPresentOrElse(
                                findEntity -> {
                                    if (!findEntity.getAdminId().equals(adminUserDto.getAdminId())) {
                                        if (!idList.isEmpty()) {
                                            throw new GeneralException(ErrorCode.BAD_REQUEST, "ID가 중복됩니다.");
                                        }
                                    }
                                    adminUserRepository.save(
                                            adminUserDto.updateEntity(findEntity)
                                    );
                                },
                                () -> {
                                    throw new GeneralException(
                                            ErrorCode.NOT_FOUND,
                                            ErrorMessage.NOT_FOUND_USER
                                    );
                                }
                        );
            }
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
        return true;
    }

    public List<AdminUserDto> getAllAdminUser() {
        try {
            return adminUserRepository.findAllAdminUser();
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    public Boolean deleteAdminUser(
            Long priKey
    ) {
        try {
            if (priKey == null)
                throw new GeneralException(ErrorCode.BAD_REQUEST, "ID NULL");
            adminUserRepository.deleteById(priKey);
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
        return true;
    }
}
