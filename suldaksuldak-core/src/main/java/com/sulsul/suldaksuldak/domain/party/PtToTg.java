package com.sulsul.suldaksuldak.domain.party;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@ToString
@Table(
        name = "tb_pt_tg"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "ptToTg")
public class PtToTg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Party party;

    @ManyToOne(optional = false)
    private PartyTag partyTag;

    protected PtToTg() {}

    protected PtToTg(
            Long id,
            Party party,
            PartyTag partyTag
    ) {
        this.id = id;
        this.party = party;
        this.partyTag = partyTag;
    }

    public static PtToTg of (
            Long id,
            Party party,
            PartyTag partyTag
    ) {
        return new PtToTg(
                id,
                party,
                partyTag
        );
    }
}
