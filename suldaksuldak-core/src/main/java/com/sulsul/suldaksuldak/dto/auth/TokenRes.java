package com.sulsul.suldaksuldak.dto.auth;

import com.sulsul.suldaksuldak.constant.auth.SDTokken;
import lombok.Value;

@Value
public class TokenRes {
    String refreshToken;

    public static TokenRes of(
            String refreshToken
    ) {
        return new TokenRes(
                refreshToken
        );
    }

    public static TokenRes from(TokenMap tokenMap) {
        return new TokenRes(
                SDTokken.TOKEN_TYPE.getText() + " " + tokenMap.getRefreshToken()
        );
    }
}
