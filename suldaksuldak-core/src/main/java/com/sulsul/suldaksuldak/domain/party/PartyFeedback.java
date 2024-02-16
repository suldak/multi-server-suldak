package com.sulsul.suldaksuldak.domain.party;

import com.sulsul.suldaksuldak.domain.user.UserPartyFeedback;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@ToString
@Table(
        name = "tb_party_feedback"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "partyFeedback")
public class PartyFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT UNSIGNED")
    private Long id;

    @Setter
    @Column(nullable = false, unique = true, length = 200)
    private String feedBackText;

    @Setter
    @Column(nullable = false, columnDefinition = "DOUBLE")
    private Double score;

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "partyFeedback", cascade = CascadeType.REMOVE)
    private Set<UserPartyFeedback> userPartyFeedbacks = new LinkedHashSet<>();


    protected PartyFeedback () {}

    protected PartyFeedback (
            Long id,
            String feedBackText,
            Double score
    ) {
        this.id = id;
        this.feedBackText = feedBackText;
        this.score = score;
    }

    public static PartyFeedback of (
            Long id,
            String feedBackText,
            Double score
    ) {
        return new PartyFeedback(
                id,
                feedBackText,
                score
        );
    }
}
