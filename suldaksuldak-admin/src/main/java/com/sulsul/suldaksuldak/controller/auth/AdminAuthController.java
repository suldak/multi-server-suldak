package com.sulsul.suldaksuldak.controller.auth;

import com.sulsul.suldaksuldak.Service.auth.AdminAuthService;
import com.sulsul.suldaksuldak.constant.auth.SDTokken;
import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.BasicPriKeyReq;
import com.sulsul.suldaksuldak.dto.auth.*;
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

import javax.servlet.http.HttpServletRequest;
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
    @PostMapping(value = "/signup")
    public ApiDataResponse<Boolean> signUpAdminUser(
            @RequestBody AdminUserReq adminUserReq
    ) {
        try {
            String encryptedPw = UtilTool.encryptPassword(adminUserReq.getAdminPw(), adminUserReq.getAdminId());
            return ApiDataResponse.of(
                    adminAuthService.createAdminUser(
                            AdminUserDto.of(
                                    adminUserReq.getPriKey(),
                                    adminUserReq.getAdminId(),
                                    encryptedPw,
                                    adminUserReq.getAdminNm()
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
    @DeleteMapping(value = "/admin-user")
    public ApiDataResponse<Boolean> deleteAdminUser(
            @RequestBody BasicPriKeyReq basicPriKeyReq
    ) {
        return ApiDataResponse.of(
                adminAuthService.deleteAdminUser(
                        basicPriKeyReq.getPriKey()
                )
        );
    }

    @ApiOperation(
            value = "관리자 로그아웃",
            notes = "관리자 계정의 로그아웃"
    )
    @PostMapping(value = "/logout")
    public ApiDataResponse<Boolean> logout(
            HttpServletRequest request
    ) {
        String refreshToken = request.getHeader(SDTokken.REFRESH_HEADER.getText());
        TokenUtils.removeRefreshToken(TokenUtils.getTokenFromHeader(refreshToken));
        return ApiDataResponse.of(true);
    }
}
