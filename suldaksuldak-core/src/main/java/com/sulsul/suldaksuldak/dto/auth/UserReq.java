package com.sulsul.suldaksuldak.dto.auth;


import com.sulsul.suldaksuldak.constant.auth.Gender;
import com.sulsul.suldaksuldak.constant.auth.Registration;
import lombok.*;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UserReq {
    Long id;
    String email;
    String password;
    String nickname;
    Gender gender;
    Integer birthdayYear;

    public UserDto toDto() {
        return UserDto.of(
                id,
                email,
                password,
                nickname,
                gender,
                birthdayYear,
                Registration.SULDAKSULDAK
        );
    }
}
