package com.sulsul.suldaksuldak.domain.party;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.sulsul.suldaksuldak.domain.user.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@ToString
@Table(
        name = "tb_party_complete"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "partyComplete")
public class PartyComplete {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT UNSIGNED")
    private Long id;

    @Setter
    @Column(nullable = false)
    private Boolean isCompleteProcessed;

    @Setter
    @Column(nullable = false)
    private Boolean isHostProcessed;

    @Column(nullable = false)
    private Boolean isHost;

    @CreatedDate
    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime partyCompleteAt;

    @Setter
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime completeProcessedAt;

    @Setter
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime hostProcessedAt;

    @ManyToOne(optional = false)
    private Party party;

    @ManyToOne(optional = false)
    private User user;

    @PrePersist
    public void prePersist() {
        this.partyCompleteAt = LocalDateTime.now();
    }

    protected PartyComplete () {}

    protected PartyComplete (
            Long id,
            Boolean isCompleteProcessed,
            Boolean isHostProcessed,
            Boolean isHost,
            LocalDateTime completeProcessedAt,
            LocalDateTime hostProcessedAt,
            Party party,
            User user
    ) {
        this.id = id;
        this.isCompleteProcessed = isCompleteProcessed;
        this.isHostProcessed = isHostProcessed;
        this.isHost = isHost;
        this.completeProcessedAt = completeProcessedAt;
        this.hostProcessedAt = hostProcessedAt;
        this.party = party;
        this.user = user;
    }

    public static PartyComplete of (
            Long id,
            Boolean isCompleteProcessed,
            Boolean isHostProcessed,
            Boolean isHost,
            LocalDateTime completeProcessedAt,
            LocalDateTime hostProcessedAt,
            Party party,
            User user
    ) {
        return new PartyComplete(
                id,
                isCompleteProcessed,
                isHostProcessed,
                isHost,
                completeProcessedAt,
                hostProcessedAt,
                party,
                user
        );
    }
}
