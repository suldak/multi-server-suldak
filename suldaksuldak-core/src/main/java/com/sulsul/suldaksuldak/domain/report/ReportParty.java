package com.sulsul.suldaksuldak.domain.report;

import com.sulsul.suldaksuldak.domain.BaseEntity;
import com.sulsul.suldaksuldak.domain.party.Party;
import com.sulsul.suldaksuldak.domain.user.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@ToString
@Table(
        name = "tb_report_party"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "reportParty")
public class ReportParty extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT UNSIGNED")
    private Long id;

    @ManyToOne(optional = false)
    private ReportPartyReason reportReason;

    @ManyToOne(optional = false)
    private User user;

    @ManyToOne(optional = false)
    private Party party;

    @Setter
    @Column(nullable = false)
    private Boolean complete;

    protected ReportParty() {}

    protected ReportParty(
            Long id,
            ReportPartyReason reportReason,
            User user,
            Party party,
            Boolean complete
    ) {
        this.id = id;
        this.reportReason = reportReason;
        this.user = user;
        this.party = party;
        this.complete = complete;
    }

    public static ReportParty of (
            Long id,
            ReportPartyReason reportReason,
            User user,
            Party party,
            Boolean complete
    ) {
        return new ReportParty(
                id,
                reportReason,
                user,
                party,
                complete
        );
    }
}
