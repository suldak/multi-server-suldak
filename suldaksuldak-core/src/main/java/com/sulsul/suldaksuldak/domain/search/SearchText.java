package com.sulsul.suldaksuldak.domain.search;

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
        name = "tb_search_text"
)
@Entity(name = "searchText")
public class SearchText {
    @Id
    @Column(columnDefinition = "VARCHAR(255)")
    private String id;

    @Column(nullable = false, columnDefinition = "VARCHAR(100)")
    private String content;

    @ManyToOne(optional = false)
    private User user;

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

    protected SearchText () {}

    protected SearchText (
            String id,
            String content,
            User user
    ) {
        this.id = id;
        this.content = content;
        this.user = user;
    }

    public static SearchText of (
            String id,
            String content,
            User user
    ) {
        return new SearchText(
                id,
                content,
                user
        );
    }
}
