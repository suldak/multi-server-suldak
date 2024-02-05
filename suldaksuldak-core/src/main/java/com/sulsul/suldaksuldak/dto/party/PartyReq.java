package com.sulsul.suldaksuldak.dto.party;

import com.sulsul.suldaksuldak.constant.party.PartyType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "모임 Request")
public class PartyReq {
    @ApiModelProperty(value = "모임 이름", required = true)
    String name;
    @ApiModelProperty(value = "모임 일시", required = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime meetingDay;
    @ApiModelProperty(value = "모임 인원", required = true)
    Integer personnel;
    @ApiModelProperty(value = "모임 설명")
    String introStr;
    @ApiModelProperty(value = "모임 타입 (온라인 / 오프라인)", required = true)
    PartyType partyType;
    @ApiModelProperty(value = "모임 장소 (오프라인)")
    String partyPlace;
    @ApiModelProperty(value = "연락 수단", required = true)
    String contactType;
    @ApiModelProperty(value = "사용 프로그램 (온라인)")
    String useProgram;
    @ApiModelProperty(value = "URL (온라인)")
    String onlineUrl;
    @ApiModelProperty(value = "모임 태그 기본키", required = true)
    Long tagPriKey;

    public PartyDto toDto(Long hostUserPriKey) {
        return PartyDto.of(
                null,
                name,
                meetingDay,
                personnel,
                introStr,
                partyType,
                partyPlace,
                contactType,
                useProgram,
                onlineUrl,
                hostUserPriKey,
                tagPriKey
        );
    }
}
