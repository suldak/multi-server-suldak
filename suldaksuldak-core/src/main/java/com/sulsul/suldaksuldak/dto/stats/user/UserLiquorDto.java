package com.sulsul.suldaksuldak.dto.stats.user;

import com.sulsul.suldaksuldak.domain.liquor.Liquor;
import com.sulsul.suldaksuldak.domain.stats.UserLiquor;
import com.sulsul.suldaksuldak.domain.user.User;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class UserLiquorDto {
    Long id;
    Long userId;
    Long liquorId;
    Double searchCnt;
    LocalDateTime lastSearchTime;

    public static UserLiquorDto of (
            Long id,
            Long userId,
            Long liquorId,
            Double searchCnt,
            LocalDateTime lastSearchTime
    ) {
        return new UserLiquorDto(
                id,
                userId,
                liquorId,
                searchCnt,
                lastSearchTime
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
                searchCnt,
                lastSearchTime
        );
    }

    public static UserLiquor addSearchCnt(
            UserLiquor userLiquor
    ) {
        userLiquor.setSearchCnt(userLiquor.getSearchCnt() + 0.1);
        userLiquor.setLastSearchTime(LocalDateTime.now());
        return userLiquor;
    }
}
