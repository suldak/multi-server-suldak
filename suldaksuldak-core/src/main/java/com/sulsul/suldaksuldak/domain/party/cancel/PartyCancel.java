package com.sulsul.suldaksuldak.domain.party.cancel;

import com.sulsul.suldaksuldak.domain.BaseEntity;
import com.sulsul.suldaksuldak.domain.party.Party;
import com.sulsul.suldaksuldak.domain.user.User;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@ToString
@Table(
        name = "tb_party_cancel"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "partyCancel")
public class PartyCancel extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT UNSIGNED")
    private Long id;

    @ManyToOne(optional = false)
    private PartyCancelReason partyCancelReason;

    @Column(columnDefinition = "TEXT")
    private String detailReason;

    @ManyToOne(optional = false)
    private Party party;

    @ManyToOne(optional = false)
    private User user;

    protected PartyCancel () {}

    protected PartyCancel (
            Long id,
            PartyCancelReason partyCancelReason,
            String detailReason,
            Party party,
            User user
    ) {
        this.id = id;
        this.partyCancelReason = partyCancelReason;
        this.detailReason = detailReason;
        this.party = party;
        this.user = user;
    }

    public static PartyCancel of (
            Long id,
            PartyCancelReason partyCancelReason,
            String detailReason,
            Party party,
            User user
    ) {
        return new PartyCancel(
                id,
                partyCancelReason,
                detailReason,
                party,
                user
        );
    }
}
