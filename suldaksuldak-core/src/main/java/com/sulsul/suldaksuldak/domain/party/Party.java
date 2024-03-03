package com.sulsul.suldaksuldak.domain.party;

import com.sulsul.suldaksuldak.constant.party.PartyStateType;
import com.sulsul.suldaksuldak.constant.party.PartyType;
import com.sulsul.suldaksuldak.domain.BaseEntity;
import com.sulsul.suldaksuldak.domain.admin.feedback.UserPartyFeedback;
import com.sulsul.suldaksuldak.domain.file.FileBase;
import com.sulsul.suldaksuldak.domain.party.batch.PartySchedule;
import com.sulsul.suldaksuldak.domain.report.ReportParty;
import com.sulsul.suldaksuldak.domain.stats.PartySearchLog;
import com.sulsul.suldaksuldak.domain.user.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@ToString
@Table(
        name = "tb_party"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "party")
public class Party extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT UNSIGNED")
    private Long id;

    @Setter
    @Column(nullable = false)
    private String name;

    @Setter
    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime meetingDay;

    @Setter
    @Column(nullable = false)
    private Integer personnel;

    @Setter
    @Column
    private String introStr;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PartyType partyType;

    @Setter
    @Column
    private String partyPlace;

    @Setter
    @Column(nullable = false)
    private String contactType;

    @Setter
    @Column
    private String useProgram;

    @Setter
    @Column
    private String onlineUrl;

    @Setter
    @Column(nullable = false, columnDefinition = "VARCHAR(20)")
    @Enumerated(EnumType.STRING)
    private PartyStateType partyStateType;

    @ManyToOne(optional = false)
    private User user;

    @OneToOne(cascade = CascadeType.PERSIST)
    @Setter
    @JoinColumn(name = "file_base_nm")
    private FileBase fileBase;

    @ManyToOne(optional = false)
    private PartyTag partyTag;

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "party", cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<PartyGuest> partyGuests = new LinkedHashSet<>();

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "party", cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<PartyComment> partyComments = new LinkedHashSet<>();

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "party", cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<ReportParty> reportParties = new LinkedHashSet<>();

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "party", cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<PartySchedule> partySchedules = new LinkedHashSet<>();

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "party", cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<UserPartyFeedback> userPartyFeedbacks = new LinkedHashSet<>();

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "party", cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<PartySearchLog> partySearchLogs = new LinkedHashSet<>();
    protected Party () {}

    protected Party (
            Long id,
            String name,
            LocalDateTime meetingDay,
            Integer personnel,
            String introStr,
            PartyType partyType,
            String partyPlace,
            String contactType,
            String useProgram,
            String onlineUrl,
            PartyStateType partyStateType,
            User user,
            FileBase fileBase,
            PartyTag partyTag
    ) {
        this.id = id;
        this.name = name;
        this.meetingDay = meetingDay;
        this.personnel = personnel;
        this.introStr = introStr;
        this.partyType = partyType;
        this.partyPlace = partyPlace;
        this.contactType = contactType;
        this.useProgram = useProgram;
        this.onlineUrl = onlineUrl;
        this.partyStateType = partyStateType;
        this.user = user;
        this.fileBase = fileBase;
        this.partyTag = partyTag;
    }

    public static Party of (
            Long id,
            String name,
            LocalDateTime meetingDay,
            Integer personnel,
            String introStr,
            PartyType partyType,
            String partyPlace,
            String contactType,
            String useProgram,
            String onlineUrl,
            PartyStateType partyStateType,
            User user,
            FileBase fileBase,
            PartyTag partyTag
    ) {
        return new Party(
                id,
                name,
                meetingDay,
                personnel,
                introStr,
                partyType,
                partyPlace,
                contactType,
                useProgram,
                onlineUrl,
                partyStateType,
                user,
                fileBase,
                partyTag
        );
    }
}
