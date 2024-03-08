package com.sulsul.suldaksuldak.domain.stats;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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
        name = "tb_party_search_log"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "partySearchLog")
public class PartySearchLog {
    @Id
    @Column(columnDefinition = "VARCHAR(255)")
    private String id;

    @ManyToOne(optional = false)
    private User user;

    @ManyToOne(optional = false)
    private Party party;

    @CreatedDate
    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime searchAt;

    @PrePersist
    public void prePersist() {
        this.searchAt = LocalDateTime.now();
    }

    protected PartySearchLog () {}

    protected PartySearchLog (
            String id,
            User user,
            Party party
    ) {
        this.id = id;
        this.user = user;
        this.party = party;
    }

    public static PartySearchLog of (
            String id,
            User user,
            Party party
    ) {
        return new PartySearchLog(
                id,
                user,
                party
        );
    }
}
