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
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Boolean isTag;

    @Column(nullable = false, columnDefinition = "VARCHAR(100)")
    private String content;

    @ManyToOne
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
            Long id,
            Boolean isTag,
            String content,
            User user
    ) {
        this.id = id;
        this.isTag = isTag;
        this.content = content;
        this.user = user;
    }

    public static SearchText of (
            Long id,
            Boolean isTag,
            String content,
            User user
    ) {
        return new SearchText(
                id,
                isTag,
                content,
                user
        );
    }
}
