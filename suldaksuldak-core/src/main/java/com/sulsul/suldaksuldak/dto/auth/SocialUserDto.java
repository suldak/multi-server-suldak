package com.sulsul.suldaksuldak.dto.auth;

import com.sulsul.suldaksuldak.constant.auth.Registration;
import lombok.Value;

@Value
public class SocialUserDto {
    String userEmail;
    String identity;
    String nickname;
    Registration registration;

    public static SocialUserDto of (
            String userEmail,
            String identity,
            String nickname,
            Registration registration
    ) {
        return new SocialUserDto(userEmail, identity, nickname, registration);
    }
}
