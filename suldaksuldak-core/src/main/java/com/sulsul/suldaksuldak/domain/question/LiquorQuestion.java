package com.sulsul.suldaksuldak.domain.question;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@ToString
@Table(
        name = "tb_liquor_question"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "liquorQuestion")
public class LiquorQuestion {
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private Integer qIndex;

    @Setter
    @Column(nullable = false)
    private String qText;

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "liquorQuestion", cascade = CascadeType.REMOVE)
    private Set<LiquorAnswer> liquorAnswers = new LinkedHashSet<>();

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "liquorQuestion", cascade = CascadeType.REMOVE)
    private Set<UserSelect> userSelects = new LinkedHashSet<>();

    protected LiquorQuestion () {}

    protected LiquorQuestion (
            Long id,
            Integer qIndex,
            String qText
    ) {
        this.id = id;
        this.qIndex = qIndex;
        this.qText = qText;
    }

    public static LiquorQuestion of (
            Long id,
            Integer qIndex,
            String qText
    ) {
        return new LiquorQuestion(
                id,
                qIndex,
                qText
        );
    }
}
