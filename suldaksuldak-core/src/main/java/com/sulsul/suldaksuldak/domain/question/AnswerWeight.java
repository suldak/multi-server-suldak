package com.sulsul.suldaksuldak.domain.question;

import com.sulsul.suldaksuldak.constant.stats.TagType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@ToString
@Table(
        name = "tb_answer_weight"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "answerWeight")
public class AnswerWeight {
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TagType tagType;

    @Column(nullable = false)
    private Long tagId;

    @Setter
    @Column(nullable = false)
    private Double weight;

    @ManyToOne(optional = false)
    @JoinColumn(name = "answer_id")
    private LiquorAnswer liquorAnswer;

    protected AnswerWeight () {}

    protected AnswerWeight (
            Long id,
            TagType tagType,
            Long tagId,
            Double weight,
            LiquorAnswer liquorAnswer
    ) {
        this.id = id;
        this.tagType = tagType;
        this.tagId = tagId;
        this.weight = weight;
        this.liquorAnswer = liquorAnswer;
    }

    public static AnswerWeight of (
            Long id,
            TagType tagType,
            Long tagId,
            Double weight,
            LiquorAnswer liquorAnswer
    ) {
        return new AnswerWeight(
                id,
                tagType,
                tagId,
                weight,
                liquorAnswer
        );
    }
}
