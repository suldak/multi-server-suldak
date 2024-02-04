package com.sulsul.suldaksuldak.dto.party;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sulsul.suldaksuldak.constant.party.PartyType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "모임 Request")
public class PartyReq {
    @ApiModelProperty(value = "모임 기본키 (생략하면 생성)")
    Long id;
    @ApiModelProperty(value = "모임 이름", required = true)
    String name;
    @ApiModelProperty(value = "모임 일시", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
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
    @ApiModelProperty(value = "주최자 기본키", required = true)
    Long hostUserPriKey;

    public PartyDto toDto() {
        return PartyDto.of(
                id,
                name,
                meetingDay,
                personnel,
                introStr,
                partyType,
                partyPlace,
                contactType,
                useProgram,
                onlineUrl,
                hostUserPriKey
        );
    }
}
