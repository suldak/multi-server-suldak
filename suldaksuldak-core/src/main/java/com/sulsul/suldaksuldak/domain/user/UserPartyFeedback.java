package com.sulsul.suldaksuldak.domain.user;

import com.sulsul.suldaksuldak.domain.party.PartyFeedback;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@ToString
@Table(
        name = "tb_user_party_feedback"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "userPartyFeedback")
public class UserPartyFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT UNSIGNED")
    private Long id;

    @ManyToOne(optional = false)
    private User user;

    @ManyToOne(optional = false)
    private PartyFeedback partyFeedback;

    protected UserPartyFeedback () {}

    protected UserPartyFeedback (
            Long id,
            User user,
            PartyFeedback partyFeedback
    ) {
        this.id = id;
        this.user = user;
        this.partyFeedback = partyFeedback;
    }

    public static UserPartyFeedback of (
            Long id,
            User user,
            PartyFeedback partyFeedback
    ) {
        return new UserPartyFeedback(
                id,
                user,
                partyFeedback
        );
    }
}
