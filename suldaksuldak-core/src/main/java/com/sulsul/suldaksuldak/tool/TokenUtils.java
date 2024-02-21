package com.sulsul.suldaksuldak.tool;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.dto.auth.TokenMap;
import com.sulsul.suldaksuldak.dto.user.UserDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.*;

@Slf4j
public class TokenUtils {
    private static final HashMap<String, UserDto> jwtRefreshMap = new HashMap<>();
    private static final String jwtSecretKey = "exampleSecretKey";

    /**
     * UserDTO를 이용하여 AccessToken과 Refresh Token을 생성하여 Refresh Token을 반환합니다.
     * @param userDTO : UserDTO
     * @return Refresh Token
     */
    public static TokenMap getTokenMap(UserDto userDTO) {
        // 유효하지 않은 토큰 삭제
        checkRefreshToken();
        Claims claims = Jwts.claims().setSubject(userDTO.getUserEmail()); // JWT payload 에 저장되는 정보단위
        claims.put("priId", userDTO.getId());
        claims.put("email", userDTO.getUserEmail()); // 정보는 key / value 쌍으로 저장된다.
        claims.put("nickname", userDTO.getNickname());
        Date now = new Date();

//        String accessToken = Jwts.builder()
//                .setClaims(claims) // 정보 저장
//                .setIssuedAt(now) // 토큰 발행 시간 정보
//                .setHeader(createHeader())
//                .setExpiration(createExpiredDate()) // set Expire Time
//                .signWith(SignatureAlgorithm.HS256, createSignature())  // 사용할 암호화 알고리즘과
//                // signature 에 들어갈 secret값 세팅
//                .compact();

        String refreshUUIDToken = getUUIDStr();
        Claims refToken = Jwts.claims().setSubject(refreshUUIDToken);
        refToken.put("id", userDTO.getId());

        String refreshToken = Jwts.builder()
                .setClaims(refToken) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setHeader(createHeader())
//                .setExpiration(createRefreshDate()) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey)  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();

//        jwtRefreshMap.put(refreshUUIDToken, userDTO);
        jwtRefreshMap.put(refreshToken, userDTO);

        return TokenMap.of(
//                accessToken,
                refreshToken
//                refreshUUIDToken
        );
    }

    /**
     * Refresh Token을 이용해서 Refresh Token 재발급 및 TokenMap.class 반환
     */
    public static TokenMap getAccessTokenByRefreshToken(String refreshToken) {
        // 유효하지 않은 토큰 삭제
        checkRefreshToken();
        if (isValidToken(refreshToken)) {
            if (jwtRefreshMap.containsKey(refreshToken)) {
                UserDto userDTO = jwtRefreshMap.get(refreshToken);
                jwtRefreshMap.remove(refreshToken);
                return getTokenMap(userDTO);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * logout 합니다.
     * @param refreshToken : Refresh UUID Key
     */
    public static void removeAccessToken(String refreshToken) {
        // 유효하지 않은 토큰 삭제
        checkRefreshToken();
        if (jwtRefreshMap.containsKey(refreshToken)) {
            jwtRefreshMap.remove(refreshToken);
        }
    }

    /**
     * refresh token으로 UserDTO 조회
     * @param refreshToken refreshToken
     * @return Optional</UserDTO>
     */
    public static Optional<UserDto> getUserDTOFromRefreshToken(String refreshToken) {
        // 유효하지 않은 토큰 삭제
        checkRefreshToken();
        if (isValidToken(refreshToken)) {
            if (jwtRefreshMap.containsKey(refreshToken)) {
                return Optional.of(jwtRefreshMap.get(refreshToken));
            } else {
                return Optional.of(UserDto.idOnly(getUserIdFromToken(refreshToken)));
//                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }

    private static String getUUIDStr() {
        return UUID.randomUUID().toString();
    }

    /**
     * 유효한 토큰인지 확인 해주는 메서드
     *
     * @param token String  : 토큰
     * @return boolean      : 유효한지 여부 반환
     */
    public static boolean isValidToken(String token) {
        try {
            Claims claims = getClaimsFormToken(token);
//            log.info(".expireTime :" + claims.getExpiration());
//            log.info("SUB :" + claims.getSubject());
//            log.info("userNm :" + claims.get("userNm"));
//            log.info(">>>>>>>>>> isValidToken <<<<<<<<<<< 통과");
            return true;
        } catch (ExpiredJwtException exception) {
            log.error("Token Expired");
            removeRefreshToken(token);
            return false;
        } catch (JwtException exception) {
            log.error("Token Tampered");
            removeRefreshToken(token);
            return false;
        } catch (NullPointerException exception) {
            log.error("Token is null");
            removeRefreshToken(token);
            return false;
        }
    }

    /**
     * Header 내에 토큰을 추출합니다.
     *
     * @param header 헤더
     * @return String
     */
    public static String getTokenFromHeader(String header) {
        try {
            return header.split(" ")[1];
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.REFRESH_TOKEN_EXPIRATION, e.getMessage());
        }
    }

    public static Boolean createJwtRefreshMap (String token, UserDto userDto) {
        try {
            jwtRefreshMap.put(token, userDto);
            return true;
        } catch (Exception ignore) {
            return false;
        }
    }

    /**
     * Access Token의 만료기간을 지정하는 함수
     *
     * @return Date
     */
    private static Date createExpiredDate() {
        // 토큰 만료시간은 30일으로 설정
        Calendar c = Calendar.getInstance();
        // TODO: 여기서 AccessToken 만료시간 관리
        c.add(Calendar.MINUTE, 1);
        // c.add(Calendar.DATE, 1);         // 1일
        return c.getTime();
    }

    /**
     * Refresh Token의 만료기간을 지정하는 함수
     * @return Date
     */
    private static Date createRefreshDate() {
        // 토큰 만료시간은 30일으로 설정
        Calendar c = Calendar.getInstance();
        // TODO: 여기서 RefreshToken 만료시간 관리
//        c.add(Calendar.DATE, 30);
//        c.add(Calendar.HOUR, 3);
        c.add(Calendar.MINUTE, 30);
        // c.add(Calendar.DATE, 1);         // 1일
        return c.getTime();
    }

    /**
     * JWT의 "헤더" 값을 생성해주는 메서드
     *
     * @return HashMap<String, Object>
     */
    private static Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();

        header.put("typ", "JWT");
        header.put("alg", "HS256");
        header.put("regDate", System.currentTimeMillis());
        return header;
    }

    /**
     * 사용자 정보를 기반으로 클래임을 생성해주는 메서드
     *
     * @param userDto 사용자 정보
     * @return Map<String, Object>
     */
//    private static Map<String, Object> createClaims(UserDTO userDto) {
//        // 공개 클레임에 사용자의 이름과 이메일을 설정하여 정보를 조회할 수 있다.
//        Map<String, Object> claims = new HashMap<>();
//
//        log.info("userId :" + userDto.getUserId());
//        log.info("userNm :" + userDto.getUserNm());
//
//        claims.put("userId", userDto.getUserId());
//        claims.put("userNm", userDto.getUserNm());
//        return claims;
//    }

    /**
     * JWT "서명(Signature)" 발급을 해주는 메서드
     *
     * @return Key
     */
    private static Key createSignature() {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtSecretKey);
        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    /**
     * 토큰 정보를 기반으로 Claims 정보를 반환받는 메서드
     *
     * @param token : 토큰
     * @return Claims : Claims
     */
    private static Claims getClaimsFormToken(String token) {
        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(jwtSecretKey))
                .parseClaimsJws(token).getBody();
    }

    /**
     * 토큰을 기반으로 사용자 정보를 반환받는 메서드
     *
     * @param token : 토큰
     * @return String : 사용자 아이디
     */
    private static Long getUserIdFromToken(String token) {
//        Claims claims = getClaimsFormToken(token);
//        return claims.getSubject();
        try {
            Claims claims = getClaimsFormToken(token);
            Integer id = (Integer) claims.get("id");
            return Long.parseLong(id.toString());
        } catch (Exception e) {
            return null;
        }
    }

    public static void removeRefreshToken(String refreshToken) {
        jwtRefreshMap.remove(refreshToken);
    }

    public static void checkRefreshToken() {
        if (jwtRefreshMap.isEmpty()) {
            log.info("LOGIN USER IS EMPTY");
            return;
        }
        List<String> removeKeyList = new ArrayList<>();
        try {
            for (String key: jwtRefreshMap.keySet()) {
                if (!isValidToken(key)) {
//                log.info("{} << token is not valid", key);
//                jwtRefreshMap.remove(key);
                    removeKeyList.add(key);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        for (String key: removeKeyList) {
            log.info("{} << token is not valid", key);
            jwtRefreshMap.remove(key);
        }
    }
}
