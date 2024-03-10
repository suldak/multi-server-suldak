package com.sulsul.suldaksuldak.domain.question;

import com.sulsul.suldaksuldak.domain.user.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@ToString
@Table(
        name = "tb_user_select"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "userSelect")
public class UserSelect {
    @Id
    @Column(columnDefinition = "VARCHAR(255)")
    private String id;

    @ManyToOne(optional = false)
    private LiquorQuestion liquorQuestion;

    @Setter
    @ManyToOne(optional = false)
    private LiquorAnswer liquorAnswer;

    @ManyToOne(optional = false)
    private User user;

    protected UserSelect() {}

    protected UserSelect(
            String id,
            LiquorQuestion liquorQuestion,
            LiquorAnswer liquorAnswer,
            User user
    ) {
        this.id = id;
        this.liquorQuestion = liquorQuestion;
        this.liquorAnswer = liquorAnswer;
        this.user = user;
    }

    public static UserSelect of (
            String id,
            LiquorQuestion liquorQuestion,
            LiquorAnswer liquorAnswer,
            User user
    ) {
        return new UserSelect(
                id,
                liquorQuestion,
                liquorAnswer,
                user
        );
    }
}
