package com.sulsul.suldaksuldak.dto.cut;

import com.sulsul.suldaksuldak.domain.user.CutOffUser;
import com.sulsul.suldaksuldak.domain.user.User;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class CutOffUserDto {
    Long id;
    Long userId;
    Long cutUserId;
    String cutUserNickname;
    String cutUserFileNm;
    LocalDateTime createdAt;
    LocalDateTime modifiedAt;

    public static CutOffUser toEntity(
            User user,
            User cutUser
    ) {
        return CutOffUser.of(
                null,
                user,
                cutUser
        );
    }
}
