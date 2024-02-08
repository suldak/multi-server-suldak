package com.sulsul.suldaksuldak.domain.report;


import com.sulsul.suldaksuldak.domain.BaseEntity;
import com.sulsul.suldaksuldak.domain.party.PartyComment;
import com.sulsul.suldaksuldak.domain.user.User;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@ToString
@Table(
        name = "tb_report_party_comment"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "reportPartyComment")
public class ReportPartyComment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT UNSIGNED")
    private Long id;

    @ManyToOne(optional = false)
    private User user;

    @ManyToOne(optional = false)
    private PartyComment partyComment;

    protected ReportPartyComment () {}

    protected ReportPartyComment (
            Long id,
            User user,
            PartyComment partyComment
    ) {
        this.id = id;
        this.user = user;
        this.partyComment = partyComment;
    }

    public static ReportPartyComment of (
            Long id,
            User user,
            PartyComment partyComment
    ) {
        return new ReportPartyComment(
                id,
                user,
                partyComment
        );
    }
}
