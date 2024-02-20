package com.sulsul.suldaksuldak.dto.question;

import com.sulsul.suldaksuldak.constant.file.FileUrl;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Value
@ApiModel(value = "유저가 선택한 질문 답변 Res")
public class UserSelectRes {
    @ApiModelProperty(value = "유저 선택에 대한 기본키")
    String id;
    @ApiModelProperty(value = "유저 기본키")
    Long userPriKey;
    @ApiModelProperty(value = "유저 닉네임")
    String userNickname;
    @ApiModelProperty(value = "유저 프로필 사진 Url")
    String userPictureUrl;
    List<QList> userSelectSet;

    @Getter
    @ToString
    @EqualsAndHashCode
    @AllArgsConstructor
    @NoArgsConstructor
    @ApiModel(value = "유저가 선택한 질문 정보")
    public static class QList {
        @ApiModelProperty(value = "질문의 기본키")
        Long questionPriKey;
        @ApiModelProperty(value = "질문 번호")
        Integer questionIndex;
        @ApiModelProperty(value = "질문 내용")
        String questionText;
        List<AList> answerList;

        public static QList of (
                List<UserSelectDto> userSelectDtos
        ) {
            if (userSelectDtos == null || userSelectDtos.isEmpty())
                return new QList(
                        null,
                        null,
                        null,
                        List.of()
                );
            List<AList> aLists = new ArrayList<>();
            for (UserSelectDto dto: userSelectDtos) {
                aLists.add(
                        AList.of(dto)
                );
            }
            return new QList(
                    userSelectDtos.get(0).getLiquorQuestionId(),
                    userSelectDtos.get(0).getLiquorQuestionIndex(),
                    userSelectDtos.get(0).getLiquorQuestionText(),
                    aLists
            );
        }

        @Getter
        @ToString
        @EqualsAndHashCode
        @AllArgsConstructor
        @NoArgsConstructor
        @ApiModel(value = "유저가 선택한 답변 정보")
        public static class AList {
            @ApiModelProperty(value = "답변 기본키")
            Long answerPriKey;
            @ApiModelProperty(value = "답변 번호")
            Integer answerIndex;
            @ApiModelProperty(value = "답변 내용")
            String answerText;

            public static AList of (
                    UserSelectDto userSelectDto
            ) {
                return new AList(
                        userSelectDto.getLiquorAnswerId(),
                        userSelectDto.getLiquorAnswerIndex(),
                        userSelectDto.getLiquorAnswerText()
                );
            }
        }
    }

    public static UserSelectRes from(
            List<UserSelectDto> userSelectDtos
    ) {
        if (userSelectDtos == null || userSelectDtos.isEmpty())
            return null;
        List<QList> qLists = new ArrayList<>();
        Map<Long, List<UserSelectDto>> qPriKeySelectDtoMap =
                userSelectDtos.stream().collect(
                        Collectors.groupingBy(UserSelectDto::getLiquorQuestionId)
                );
        for (Long qPriKey: qPriKeySelectDtoMap.keySet()) {
            List<UserSelectDto> dto = qPriKeySelectDtoMap.get(qPriKey);
            qLists.add(
                    QList.of(dto)
            );
        }
        return new UserSelectRes(
                userSelectDtos.get(0).getId(),
                userSelectDtos.get(0).getUserId(),
                userSelectDtos.get(0).getUserNickname(),
                userSelectDtos.get(0).getUserFileNm() == null ? null :
                        FileUrl.FILE_DOWN_URL.getUrl() + userSelectDtos.get(0).getUserFileNm(),
                qLists
        );
    }
}
