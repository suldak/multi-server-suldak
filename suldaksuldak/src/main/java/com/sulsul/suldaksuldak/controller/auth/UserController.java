package com.sulsul.suldaksuldak.controller.auth;

import com.sulsul.suldaksuldak.component.auth.ToGoogle;
import com.sulsul.suldaksuldak.component.auth.ToKakao;
import com.sulsul.suldaksuldak.component.auth.ToNaver;
import com.sulsul.suldaksuldak.constant.auth.SDTokken;
import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.auth.*;
import com.sulsul.suldaksuldak.service.auth.UserService;
import com.sulsul.suldaksuldak.tool.TokenUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/auth")
@Api(tags = "[MAIN] 로그인 및 토큰 관리")
public class UserController {
    private final UserService userService;
    private final ToKakao toKakao;
    private final ToNaver toNaver;
    private final ToGoogle toGoogle;

//    @ApiOperation(
//            value = "[소셜] 카카오 로그인",
//            notes = "카카오톡 로그인"
//    )
//    @ApiImplicitParam(name = "accessToken", value = "엑세스 토큰", required = true, dataTypeClass = String.class, paramType = "path")
//    @GetMapping(value = "/kakao")
//    public ApiDataResponse<UserRes> loginKakao(
//            @RequestParam String code,
//            @RequestParam String state
////            @RequestParam String accessToken
//    ) {
//        String accessToken = toKakao.getAccessToken(code);
//        log.info(accessToken);
//        SocialUserDto socialUserDto = toKakao.getUserInfo(accessToken);
//        Optional<UserDto> optionalUserDto = userService.getUserEmail(socialUserDto.getUserEmail());
//        if (optionalUserDto.isPresent()) {
//            // 해당 이메일 있음
////            userService.login(socialUserDto.getUserEmail(), socialUserDto.getIdentity());
//            TokenMap tokenMap = TokenUtils.getTokenMap(optionalUserDto.get());
//            return ApiDataResponse.of(UserRes.from(optionalUserDto.get(), TokenRes.from(tokenMap)));
//        } else {
//            // 해당 이메일 없음
//            return ApiDataResponse.of(UserRes.from(socialUserDto));
//        }
//    }

    @ApiOperation(
            value = "[소셜] 카카오 로그인",
            notes = "카카오톡 로그인"
    )
    @ApiImplicitParam(name = "accessToken", value = "엑세스 토큰", required = true, dataTypeClass = String.class)
    @GetMapping(value = "/kakao")
    public ApiDataResponse<UserRes> loginKakao(
//            @RequestParam String code,
//            @RequestParam String state
            @RequestParam String accessToken
    ) {
//        String accessToken = toKakao.getAccessToken(code);
//        log.info(accessToken);
        SocialUserDto socialUserDto = toKakao.getUserInfo(accessToken);
        Optional<UserDto> optionalUserDto = userService.getUserEmail(socialUserDto.getUserEmail());
        if (optionalUserDto.isPresent()) {
            // 해당 이메일 있음
//            userService.login(socialUserDto.getUserEmail(), socialUserDto.getIdentity());
            TokenMap tokenMap = TokenUtils.getTokenMap(optionalUserDto.get());
            return ApiDataResponse.of(UserRes.from(optionalUserDto.get(), TokenRes.from(tokenMap)));
        } else {
            // 해당 이메일 없음
            return ApiDataResponse.of(UserRes.from(socialUserDto));
        }
    }

    @ApiOperation(
            value = "[소셜] 네이버 로그인",
            notes = "네이버 로그인"
    )
    @ApiImplicitParam(name = "accessToken", value = "엑세스 토큰", required = true, dataTypeClass = String.class)
    @GetMapping(value = "/naver")
    public ApiDataResponse<UserRes> loginNaver(
//            @RequestParam String code,
//            @RequestParam String state
            @RequestParam String accessToken
    ) {
//        String accessToken = toNaver.getAccessToken(code, state);
        SocialUserDto socialUserDto = toNaver.getUserInfo(accessToken);
        Optional<UserDto> optionalUserDto = userService.getUserEmail(socialUserDto.getUserEmail());
        if (optionalUserDto.isPresent()) {
            // 해당 이메일 있음
            TokenMap tokenMap = TokenUtils.getTokenMap(optionalUserDto.get());
            return ApiDataResponse.of(UserRes.from(optionalUserDto.get(), TokenRes.from(tokenMap)));
        } else {
            // 해당 이메일 없음
            return ApiDataResponse.of(UserRes.from(socialUserDto));
        }
    }

    @ApiOperation(
            value = "[소셜] 구글 로그인",
            notes = "구글 로그인"
    )
    @ApiImplicitParam(name = "accessToken", value = "엑세스 토큰", required = true, dataTypeClass = String.class)
    @GetMapping(value = "/google")
    public ApiDataResponse<UserRes> loginGoogle(
//            @RequestParam String code,
//            @RequestParam String state
            @RequestParam String accessToken
    ) {
//        String accessToken = toGoogle.getAccessToken(code);
        SocialUserDto socialUserDto = toGoogle.getUserInfo(accessToken);
        Optional<UserDto> optionalUserDto = userService.getUserEmail(socialUserDto.getUserEmail());
        if (optionalUserDto.isPresent()) {
            // 해당 이메일 있음
            TokenMap tokenMap = TokenUtils.getTokenMap(optionalUserDto.get());
            return ApiDataResponse.of(UserRes.from(optionalUserDto.get(), TokenRes.from(tokenMap)));
        } else {
            // 해당 이메일 없음
            return ApiDataResponse.of(UserRes.from(socialUserDto));
        }
    }

    @ApiOperation(
            value = "회원가입",
            notes = "소셜 회원가입 및 자체 회원가입"
    )
    @PostMapping(value = "/signup")
    public ApiDataResponse<Boolean> signup(
            @RequestBody UserReq userReq
    ) {
        return ApiDataResponse.of(
                userService.createUser(userReq.toDto())
        );
    }

    @ApiOperation(
            value = "자체 로그인",
            notes = "술닥술닥 자체 로그인"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userEmail", value = "Id", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "userPw", value = "Password", required = true, dataTypeClass = String.class)
    })
    @PostMapping(value = "/login")
    public void login(
            @RequestBody String userEmail,
            @RequestBody String userPw
    ) {

    }

    @ApiOperation(
            value = "로그아웃",
            notes = "Refresh Token을 이용한 로그아웃"
    )
    @PostMapping(value = "/logout")
    public ApiDataResponse<Boolean> logout(
            HttpServletRequest request
    ) {
        String refreshHeader = request.getHeader(SDTokken.REFRESH_HEADER.getText());
        TokenUtils.removeRefreshToken(refreshHeader);
        return ApiDataResponse.of(true);
    }

//    @ApiOperation(
//            value = "소셜 로그인",
//            notes = "소셜 로그인 (카카오 / 네이버)"
//    )
//    @PostMapping(value = "/login-social")
//    public ApiDataResponse<UserRes> loginKaKao(
//            @RequestBody UserReq userReq
//    ) {
//        Optional<UserDto> optionalUserDto = Optional.empty();
//        if (userReq.getRegistration().equals(Registration.KAKAO)) {
//            String accessToken = toKakao.getAccessToken(userReq.getCode());
//            UserDto userDto = toKakao.getUserInfo(accessToken);
//            optionalUserDto = userService.getUserBySocial(userDto);
//        } else if (userReq.getRegistration().equals(Registration.GOOGLE)) {
//            // TODO GOOGLE
//        } else if (userReq.getRegistration().equals(Registration.NAVER)) {
//            String accessToken = toNaver.getAccessToken(userReq.getCode(), userReq.getState());
//            UserDto userDto = toNaver.getUserInfo(accessToken);
//            optionalUserDto = userService.getUserBySocial(userDto);
//        } else if (userReq.getRegistration().equals(Registration.APPLE)) {
//            // TODO APPLE
//        } else {
//            throw new GeneralException(ErrorCode.BAD_REQUEST, "일반 로그인 API를 사용해주세요.");
//        }
//
//        if (optionalUserDto.isEmpty())
//            throw new GeneralException(ErrorCode.BAD_REQUEST, "해당 소셜로 회원가입한 유저가 없습니다.");
//
//        TokenMap tokenMap = TokenUtils.getTokenMap(optionalUserDto.get());
//        return ApiDataResponse.of(
//                UserRes.from(optionalUserDto.get(), TokenRes.from(tokenMap))
//        );
//    }



//    @GetMapping(value = "/kakao")
//    public ApiDataResponse<Boolean> kakaoAuth(
//            @RequestParam String code,
//            @RequestParam String state
//    ) {
//        String accessToken = toKakao.getAccessToken(code);
//        UserDto userDto = toKakao.getUserInfo(accessToken);
//        return ApiDataResponse.of(
//                true
//        );
//    }
//
//    @GetMapping(value = "/naver")
//    public ApiDataResponse<Boolean> naverAuth(
//            @RequestParam String code,
//            @RequestParam String state
//    ) {
//        String accessToken = toNaver.getAccessToken(code, state);
//        UserDto userDto = toNaver.getUserInfo(accessToken);
//        return ApiDataResponse.of(true);
//    }
}
