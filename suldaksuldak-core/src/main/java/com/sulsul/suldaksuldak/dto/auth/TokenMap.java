package com.sulsul.suldaksuldak.dto.auth;

import lombok.Value;

@Value
public class TokenMap {
//    String accessToken;
    String refreshToken;
//    String refreshTokenKey;

    public static TokenMap of(
//            String accessToken,
            String refreshToken
//            String refreshTokenKey
    ) {
        return new TokenMap(
//                accessToken,
                refreshToken
//                refreshTokenKey
        );
    }
}
