package com.sulsul.suldaksuldak.domain.report;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@ToString
@Table(
        name = "tb_report_party_reason"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "reportPartyReason")
public class ReportPartyReason {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT UNSIGNED")
    private Long id;


    @Setter
    @Column(nullable = false, columnDefinition = "TEXT")
    private String reason;

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "reportReason", cascade = CascadeType.REMOVE)
    private Set<ReportParty> reportParties = new LinkedHashSet<>();

    protected ReportPartyReason() {}

    protected ReportPartyReason(
            Long id,
            String reason
    ) {
        this.id = id;
        this.reason = reason;
    }

    public static ReportPartyReason of (
            Long id,
            String reason
    ) {
        return new ReportPartyReason(
                id,
                reason
        );
    }
}
