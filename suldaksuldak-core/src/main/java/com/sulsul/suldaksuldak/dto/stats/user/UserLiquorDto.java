package com.sulsul.suldaksuldak.dto.stats.user;

import com.sulsul.suldaksuldak.domain.liquor.Liquor;
import com.sulsul.suldaksuldak.domain.stats.UserLiquor;
import com.sulsul.suldaksuldak.domain.user.User;
import lombok.Value;

@Value
public class UserLiquorDto {
    Long id;
    Long userId;
    Long liquorId;
    Double searchCnt;

    public static UserLiquorDto of (
            Long id,
            Long userId,
            Long liquorId,
            Double searchCnt
    ) {
        return new UserLiquorDto(
                id,
                userId,
                liquorId,
                searchCnt
        );
    }

    public UserLiquor toEntity(
            User user,
            Liquor liquor
    ) {
        return UserLiquor.of(
                id,
                user,
                liquor,
                searchCnt
        );
    }

    public static UserLiquor addSearchCnt(
            UserLiquor userLiquor
    ) {
        userLiquor.setSearchCnt(userLiquor.getSearchCnt() + 0.1);
        return userLiquor;
    }
}
