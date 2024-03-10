package com.sulsul.suldaksuldak.domain.party.cancel;

import com.sulsul.suldaksuldak.constant.party.PartyRoleType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@ToString
@Table(
        name = "tb_party_cancel_reason"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "partyCancelReason")
public class PartyCancelReason {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT UNSIGNED")
    private Long id;

    @Setter
    @Column(nullable = false, columnDefinition = "TEXT")
    private String reason;

    @Setter
    @Column(nullable = false, columnDefinition = "VARCHAR(10)")
    @Enumerated(EnumType.STRING)
    private PartyRoleType partyRoleType;

    protected PartyCancelReason () {}

    protected PartyCancelReason (
            Long id,
            String reason,
            PartyRoleType partyRoleType
    ) {
        this.id = id;
        this.reason = reason;
        this.partyRoleType = partyRoleType;
    }

    public static PartyCancelReason of (
            Long id,
            String reason,
            PartyRoleType partyRoleType
    ) {
        return new PartyCancelReason(
                id,
                reason,
                partyRoleType
        );
    }
}
