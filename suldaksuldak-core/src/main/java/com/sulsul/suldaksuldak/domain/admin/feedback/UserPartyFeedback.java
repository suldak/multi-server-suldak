package com.sulsul.suldaksuldak.domain.admin.feedback;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.sulsul.suldaksuldak.constant.party.FeedbackType;
import com.sulsul.suldaksuldak.domain.party.Party;
import com.sulsul.suldaksuldak.domain.user.User;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FeedbackType feedbackType;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @ManyToOne(optional = false)
    private Party party;

    @ManyToOne(optional = false)
    private User writer;

    @ManyToOne(optional = false)
    private User targetUser;

    @CreatedDate
    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime feedbackAt;

    @PrePersist
    public void prePersist() {
        this.feedbackAt = LocalDateTime.now();
    }

    protected UserPartyFeedback () {}

    protected UserPartyFeedback (
            Long id,
            FeedbackType feedbackType,
            String comment,
            Party party,
            User writer,
            User targetUser
    ) {
        this.id = id;
        this.feedbackType = feedbackType;
        this.comment = comment;
        this.party = party;
        this.writer = writer;
        this.targetUser = targetUser;
    }

    public static UserPartyFeedback of (
            Long id,
            FeedbackType feedbackType,
            String comment,
            Party party,
            User writer,
            User targetUser
    ) {
        return new UserPartyFeedback(
                id,
                feedbackType,
                comment,
                party,
                writer,
                targetUser
        );
    }
}
