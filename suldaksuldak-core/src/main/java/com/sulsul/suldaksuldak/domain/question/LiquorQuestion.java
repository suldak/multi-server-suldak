package com.sulsul.suldaksuldak.domain.question;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@ToString
@Table(
        name = "tb_liquor_question"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "liquorQuestion")
public class LiquorQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private Integer qIndex;

    @Setter
    @Column(nullable = false)
    private String qText;

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
