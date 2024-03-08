package com.sulsul.suldaksuldak.domain.question;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@ToString
@Table(
        name = "tb_liquor_answer"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "liquorAnswer")
public class LiquorAnswer {
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private Integer aIndex;

    @Setter
    @Column(nullable = false, columnDefinition = "VARCHAR(100)")
    private String aText;

    @ManyToOne(optional = false)
    private LiquorQuestion liquorQuestion;

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "liquorAnswer", cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<UserSelect> userSelects = new LinkedHashSet<>();

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "liquorAnswer", cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<AnswerWeight> answerWeights = new LinkedHashSet<>();

    protected LiquorAnswer() {}

    protected LiquorAnswer(
            Long id,
            Integer aIndex,
            String aText,
            LiquorQuestion liquorQuestion
    ) {
        this.id = id;
        this.aIndex = aIndex;
        this.aText = aText;
        this.liquorQuestion = liquorQuestion;
    }

    public static LiquorAnswer of (
            Long id,
            Integer aIndex,
            String aText,
            LiquorQuestion liquorQuestion
    ) {
        return new LiquorAnswer(
                id,
                aIndex,
                aText,
                liquorQuestion
        );
    }
}
