package com.sulsul.suldaksuldak.domain.stats;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.sulsul.suldaksuldak.domain.liquor.Liquor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@ToString
@Table(
        name = "tb_liquor_search_log"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "liquorSearchLog")
public class LiquorSearchLog {
    @Id
    private String id;

    @ManyToOne(optional = false)
    private Liquor liquor;

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

    protected LiquorSearchLog () {}

    protected LiquorSearchLog (
            String id,
            Liquor liquor
    ) {
        this.id = id;
        this.liquor = liquor;
    }

    public static LiquorSearchLog of (
            String id,
            Liquor liquor
    ) {
        return new LiquorSearchLog(
                id,
                liquor
        );
    }
}
