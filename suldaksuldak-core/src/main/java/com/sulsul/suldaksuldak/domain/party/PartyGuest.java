package com.sulsul.suldaksuldak.domain.party;

import com.sulsul.suldaksuldak.constant.party.GuestType;
import com.sulsul.suldaksuldak.domain.BaseEntity;
import com.sulsul.suldaksuldak.domain.user.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@ToString
@Table(
        name = "tb_party_guest"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "partyGuest")
public class PartyGuest extends BaseEntity {
    @Id
    private String id;

    @ManyToOne(optional = false)
    private Party party;

    @ManyToOne(optional = false)
    private User user;

    @Setter
    @Column(nullable = false, columnDefinition = "VARCHAR(10)")
    @Enumerated(EnumType.STRING)
    private GuestType confirm;

    protected PartyGuest () {}

    protected PartyGuest (
            String id,
            Party party,
            User user,
            GuestType confirm
    ) {
        this.id = id;
        this.party = party;
        this.user = user;
        this.confirm = confirm;
    }

    public static PartyGuest of (
            String id,
            Party party,
            User user,
            GuestType confirm
    ) {
        return new PartyGuest(
                id,
                party,
                user,
                confirm
        );
    }
}
