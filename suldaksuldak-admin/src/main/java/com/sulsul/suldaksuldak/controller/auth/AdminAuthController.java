package com.sulsul.suldaksuldak.controller.auth;

import com.sulsul.suldaksuldak.Service.auth.AdminAuthService;
import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.auth.AdminLoginReq;
import com.sulsul.suldaksuldak.dto.auth.AdminUserDto;
import com.sulsul.suldaksuldak.dto.auth.AdminUserRes;
import com.sulsul.suldaksuldak.dto.auth.TokenRes;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.tool.TokenUtils;
import com.sulsul.suldaksuldak.tool.UtilTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/admin/auth")
@Api(tags = "[ADMIN] 관리자 로그인 및 토큰 관리")
public class AdminAuthController {
    private final AdminAuthService adminAuthService;

    @ApiOperation(
            value = "관리자 로그인",
            notes = "관리자 페이지 로그인"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "adminId", value = "관리자 ID", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "adminPw", value = "관리자 PW", required = true, dataTypeClass = String.class)
    })
    @PostMapping(value = "/login")
    public ApiDataResponse<AdminUserRes> adminLogin(
            @RequestBody AdminLoginReq adminLoginReq
    ) {
        try {
            String encryptedPw = UtilTool.encryptPassword(adminLoginReq.getAdminPw(), adminLoginReq.getAdminId());
            Optional<AdminUserDto> adminUserDto =
                    adminAuthService.loginAdminUser(adminLoginReq.getAdminPw(), encryptedPw);
            if (adminUserDto.isEmpty()) {
                throw new GeneralException(ErrorCode.NOT_FOUND, "ID 또는 PW가 없습니다.");
            }
            return ApiDataResponse.of(
                    AdminUserRes.from(
                            adminUserDto.get(),
                            TokenRes.from(TokenUtils.getTokenMap(adminUserDto.get().changeDto()))
                    )
            );
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }

    @ApiOperation(
            value = "관리자 생성 및 수정",
            notes = "관리자 계정의 정보를 생성하거나 수정합니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "priKey", value = "관리자 계정 기본키 (없으면 생성)", dataTypeClass = Long.class),
            @ApiImplicitParam(name = "adminId", value = "관리자 ID", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "adminPw", value = "관리자 PW", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "adminNm", value = "관리자 이름", required = true, dataTypeClass = String.class)
    })
    @PostMapping(value = "/signup")
    public ApiDataResponse<Boolean> signUpAdminUser(
            Long priKey,
            String adminId,
            String adminPw,
            String adminNm
    ) {
        try {
            String encryptedPw = UtilTool.encryptPassword(adminPw, adminId);
            return ApiDataResponse.of(
                    adminAuthService.createAdminUser(
                            AdminUserDto.of(
                                    priKey,
                                    adminId,
                                    encryptedPw,
                                    adminNm
                            )
                    )
            );
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }

    @ApiOperation(
            value = "관리자 목록 조회",
            notes = "관리자 목록을 조회합니다."
    )
    @GetMapping("/admin-user")
    public ApiDataResponse<List<AdminUserRes>> getAllAdminUser() {
        return ApiDataResponse.of(
                adminAuthService.getAllAdminUser()
                        .stream()
                        .map(AdminUserRes::from)
                        .toList()
        );
    }

    @ApiOperation(
            value = "관리자 삭제",
            notes = "관리자 계정을 삭제 합니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "priKey", value = "관리자 계정 기본키", required = true, dataTypeClass = Long.class)
    })
    @DeleteMapping(value = "/admin-user")
    public ApiDataResponse<Boolean> deleteAdminUser(
            Long priKey
    ) {
        return ApiDataResponse.of(
                adminAuthService.deleteAdminUser(
                        priKey
                )
        );
    }
}
