package com.sulsul.suldaksuldak.dto.stats.user;

import com.sulsul.suldaksuldak.constant.stats.TagType;
import com.sulsul.suldaksuldak.domain.stats.UserTag;
import com.sulsul.suldaksuldak.domain.user.User;
import lombok.Value;

@Value
public class UserTagDto {
    String id;
    TagType tagType;
    Long tagId;
    Double weight;
    Long userPriKey;

    public static UserTagDto of (
            String id,
            TagType tagType,
            Long tagId,
            Double weight,
            Long userPriKey
    ) {
        return new UserTagDto(
                id,
                tagType,
                tagId,
                weight,
                userPriKey
        );
    }

    public UserTag toEntity(
            User user
    ) {
        return UserTag.of(
                id,
                tagType,
                tagId,
                weight,
                user
        );
    }

    public static UserTag updateWeight(
            UserTag userTag,
            Double weight
    ) {
        userTag.setWeight(userTag.getWeight() + weight);
        return userTag;
    }
}
