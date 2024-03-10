package com.sulsul.suldaksuldak.domain.party.batch;

import com.sulsul.suldaksuldak.constant.party.PartyBatchType;
import com.sulsul.suldaksuldak.domain.party.Party;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@ToString
@Table(
        name = "tb_party_schedule"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "partySchedule")
public class PartySchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT UNSIGNED")
    private Long id;

    @ManyToOne(optional = false)
    private Party party;

    @Setter
    @Column(nullable = false, columnDefinition = "VARCHAR(50)")
    private String cronStr;

    @Setter
    @Column(nullable = false)
    private Boolean isActive;

    @Column(nullable = false, columnDefinition = "VARCHAR(30)")
    @Enumerated(EnumType.STRING)
    private PartyBatchType partyBatchType;

    @Column(nullable = false, columnDefinition = "VARCHAR(20)")
    private String serverIp;

    protected PartySchedule () {}

    protected PartySchedule (
            Long id,
            Party party,
            String cronStr,
            Boolean isActive,
            PartyBatchType partyBatchType,
            String serverIp
    ) {
        this.id = id;
        this.party = party;
        this.cronStr = cronStr;
        this.isActive = isActive;
        this.partyBatchType = partyBatchType;
        this.serverIp = serverIp;
    }

    public static PartySchedule of (
            Long id,
            Party party,
            String cronStr,
            Boolean isActive,
            PartyBatchType partyBatchType,
            String serverIp
    ) {
        return new PartySchedule(
                id,
                party,
                cronStr,
                isActive,
                partyBatchType,
                serverIp
        );
    }

    public String toSimpleString() {
        return "id: " + id + "\n"
                + "cronStr: " + cronStr + "\n"
                + "isActive: " + isActive + "\n"
                + "partyBatchType: " + partyBatchType + "\n"
                + "serverIp: " + serverIp + "\n"
                + "partyPriKey: " + party.getId() + "\n"
                + "partyName: " + party.getName() + "\n";
    }
}
