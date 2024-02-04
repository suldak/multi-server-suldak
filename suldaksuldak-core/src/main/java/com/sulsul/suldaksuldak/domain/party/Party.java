package com.sulsul.suldaksuldak.domain.party;

import com.sulsul.suldaksuldak.constant.party.PartyType;
import com.sulsul.suldaksuldak.domain.BaseEntity;
import com.sulsul.suldaksuldak.domain.user.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
    private Long id;

    @Setter
    @Column(nullable = false)
    private String name;

    @Setter
    @Column(nullable = false)
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
    @Column(nullable = false)
    private String partyPlace;

    @Setter
    @Column(nullable = false)
    private String contactType;

    @Setter
    @Column(nullable = false)
    private String useProgram;

    @Setter
    @Column(nullable = false)
    private String onlineUrl;

    @ManyToOne(optional = false)
    private User user;

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "party", cascade = CascadeType.REMOVE)
    private Set<PtToTg> ptToTgs = new LinkedHashSet<>();

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
            User user
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
        this.user = user;
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
            User user
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
                user
        );
    }
}
