package com.sulsul.suldaksuldak.dto.question;

import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Value
public class UserSelectRes {
    String priKey;
    Long userPriKey;
    @ApiModelProperty(value = "질문에 대한 답변 기본키", required = true)
    HashMap<Long, List<Long>> questionAnswerMap;

    public static UserSelectRes of (
            List<UserSelectDto> userSelectDtos
    ) {
        if (userSelectDtos == null || userSelectDtos.isEmpty()) {
            return new UserSelectRes(
                    null,
                    null,
                    null
            );
        }
        String priKey = userSelectDtos.get(0).getId();
        Long userPriKey = userSelectDtos.get(0).getUserId();
        HashMap<Long, List<Long>> map = new HashMap<>();
        for (UserSelectDto dto: userSelectDtos) {
            if (map.containsKey(dto.getLiquorQuestionId())) {
                map.get(dto.getLiquorQuestionId())
                        .add(dto.getLiquorAnswerId());
            } else {
                List<Long> longs = new ArrayList<>();
                longs.add(dto.getLiquorAnswerId());
                map.put(dto.getLiquorQuestionId(), longs);
            }
        }
        return new UserSelectRes(
                priKey,
                userPriKey,
                map
        );
    }
}
