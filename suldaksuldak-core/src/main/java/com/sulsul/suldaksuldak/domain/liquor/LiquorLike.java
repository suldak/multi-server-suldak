package com.sulsul.suldaksuldak.domain.liquor;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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
        name = "tb_liquor_like"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "liquorLike")
public class LiquorLike {
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime likeTime;

    @ManyToOne(optional = false)
    private User user;

    @ManyToOne(optional = false)
    private Liquor liquor;

    @PrePersist
    public void prePersist() {
        this.likeTime = LocalDateTime.now();
    }

    protected LiquorLike () {}

    protected LiquorLike (
            Long id,
            User user,
            Liquor liquor
    ) {
        this.id = id;
        this.user = user;
        this.liquor = liquor;
    }

    public static LiquorLike of (
            Long id,
            User user,
            Liquor liquor
    ) {
        return new LiquorLike(
                id,
                user,
                liquor
        );
    }
}
