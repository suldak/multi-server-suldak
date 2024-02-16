package com.sulsul.suldaksuldak.domain.party;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

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
    @Column(nullable = false, columnDefinition = "INTEGER")
    private Integer score;

    protected PartyFeedback () {}

    protected PartyFeedback (
            Long id,
            String feedBackText,
            Integer score
    ) {
        this.id = id;
        this.feedBackText = feedBackText;
        this.score = score;
    }

    public static PartyFeedback of (
            Long id,
            String feedBackText,
            Integer score
    ) {
        return new PartyFeedback(
                id,
                feedBackText,
                score
        );
    }
}
