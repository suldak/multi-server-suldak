package com.sulsul.suldaksuldak.domain.user;

import com.sulsul.suldaksuldak.constant.auth.Gender;
import com.sulsul.suldaksuldak.constant.auth.Registration;
import com.sulsul.suldaksuldak.domain.BaseEntity;
import com.sulsul.suldaksuldak.domain.admin.feedback.UserPartyFeedback;
import com.sulsul.suldaksuldak.domain.file.FileBase;
import com.sulsul.suldaksuldak.domain.liquor.LiquorLike;
import com.sulsul.suldaksuldak.domain.party.Party;
import com.sulsul.suldaksuldak.domain.party.PartyComment;
import com.sulsul.suldaksuldak.domain.party.PartyComplete;
import com.sulsul.suldaksuldak.domain.party.PartyGuest;
import com.sulsul.suldaksuldak.domain.question.UserSelect;
import com.sulsul.suldaksuldak.domain.report.ReportParty;
import com.sulsul.suldaksuldak.domain.report.ReportPartyComment;
import com.sulsul.suldaksuldak.domain.search.SearchText;
import com.sulsul.suldaksuldak.domain.stats.PartySearchLog;
import com.sulsul.suldaksuldak.domain.stats.UserLiquor;
import com.sulsul.suldaksuldak.domain.stats.UserTag;
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
        name = "tb_user"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "user")
public class User extends BaseEntity {
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, columnDefinition = "VARCHAR(255)")
    private String userEmail;

    @Column(nullable = false, columnDefinition = "VARCHAR(255)")
    private String userPw;

    @Column(unique = true, nullable = false, columnDefinition = "VARCHAR(100)")
    @Setter
    private String nickname;

    @Column(nullable = false, columnDefinition = "VARCHAR(2)")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    private Integer birthdayYear;

    @Column(nullable = false, columnDefinition = "VARCHAR(15)")
    @Enumerated(EnumType.STRING)
    private Registration registration;

    @Column(nullable = false, columnDefinition = "DOUBLE")
    @Setter
    private Double level;

    @Column(nullable = false, columnDefinition = "DOUBLE")
    @Setter
    private Double warningCnt;

    @Column(nullable = false)
    @Setter
    private Boolean isActive;

    @Column(nullable = false, columnDefinition = "TEXT")
    @Setter
    private String selfIntroduction;

    @Column(nullable = false)
    @Setter
    private Boolean alarmActive;

    @Column(nullable = false)
    @Setter
    private Boolean soundActive;

    @Column(nullable = false)
    @Setter
    private Boolean vibrationActive;

    @Column(nullable = false)
    @Setter
    private Boolean pushActive;

    @Column(nullable = false)
    @Setter
    private Boolean marketingActive;

    @Column(columnDefinition = "TIMESTAMP")
    @Setter
    private LocalDateTime suspensionStartDate;

    @Column(columnDefinition = "TIMESTAMP")
    @Setter
    private LocalDateTime suspensionEndDate;

    @OneToOne
    @Setter
    @JoinColumn(name = "file_base_nm")
    private FileBase fileBase;

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<CutOffUser> cutOffUsers = new LinkedHashSet<>();

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<CutOffUser> cutOffCutUsers = new LinkedHashSet<>();

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<UserLiquor> userLiquors = new LinkedHashSet<>();

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<UserSelect> userSelects = new LinkedHashSet<>();

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<UserTag> userTags = new LinkedHashSet<>();

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<SearchText> searchTexts = new LinkedHashSet<>();

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Party> parties = new LinkedHashSet<>();

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<PartyGuest> partyGuests = new LinkedHashSet<>();

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<PartyComment> partyComments = new LinkedHashSet<>();

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<ReportParty> reportParties = new LinkedHashSet<>();

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<ReportPartyComment> reportPartyComments = new LinkedHashSet<>();

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<LiquorLike> liquorLikes = new LinkedHashSet<>();

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "writer", cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<UserPartyFeedback> userPartyFeedbacks = new LinkedHashSet<>();

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "targetUser", cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<UserPartyFeedback> targetUserPartyFeedbacks = new LinkedHashSet<>();

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<PartySearchLog> partySearchLogs = new LinkedHashSet<>();

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<PartyComplete> partyCompletes = new LinkedHashSet<>();

    protected User () {}

    protected User (
            Long id,
            String userEmail,
            String userPw,
            String nickname,
            Gender gender,
            Integer birthdayYear,
            Registration registration,
            Double level,
            Double warningCnt,
            Boolean isActive,
            String selfIntroduction,
            Boolean alarmActive,
            Boolean soundActive,
            Boolean vibrationActive,
            Boolean pushActive,
            Boolean marketingActive,
            LocalDateTime suspensionStartDate,
            LocalDateTime suspensionEndDate,
            FileBase fileBase
    ) {
        this.id = id;
        this.userEmail = userEmail;
        this.userPw = userPw;
        this.nickname = nickname;
        this.gender = gender;
        this.birthdayYear = birthdayYear;
        this.registration = registration;
        this.level = level;
        this.warningCnt = warningCnt;
        this.isActive = isActive;
        this.selfIntroduction = selfIntroduction;
        this.alarmActive = alarmActive;
        this.soundActive = soundActive;
        this.vibrationActive = vibrationActive;
        this.pushActive = pushActive;
        this.marketingActive = marketingActive;
        this.suspensionStartDate = suspensionStartDate;
        this.suspensionEndDate = suspensionEndDate;
        this.fileBase = fileBase;
    }

    public static User of (
            Long id,
            String userEmail,
            String userPw,
            String nickName,
            Gender gender,
            Integer birthdayYear,
            Registration registration,
            Double level,
            Double warningCnt,
            Boolean isActive,
            String selfIntroduction,
            Boolean alarmActive,
            Boolean soundActive,
            Boolean vibrationActive,
            Boolean pushActive,
            Boolean marketingActive,
            LocalDateTime suspensionStartDate,
            LocalDateTime suspensionEndDate,
            FileBase picture
    ) {
        return new User(
                id,
                userEmail,
                userPw,
                nickName,
                gender,
                birthdayYear,
                registration,
                level,
                warningCnt,
                isActive,
                selfIntroduction,
                alarmActive,
                soundActive,
                vibrationActive,
                pushActive,
                marketingActive,
                suspensionStartDate,
                suspensionEndDate,
                picture
        );
    }
}
