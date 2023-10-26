package com.sulsul.suldaksuldak.domain.question;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@ToString
@Table(
        name = "tb_liquor_answer"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "liquorAnswer")
public class LiquorAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private Integer aIndex;

    @Setter
    @Column(nullable = false)
    private String aText;

    @ManyToOne(optional = false)
    private LiquorQuestion liquorQuestion;

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
