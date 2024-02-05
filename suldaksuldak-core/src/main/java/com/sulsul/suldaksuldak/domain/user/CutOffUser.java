package com.sulsul.suldaksuldak.domain.user;

import com.sulsul.suldaksuldak.domain.BaseEntity;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@ToString
@Table(
        name = "tb_cut_off_user"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "cutOffUser")
public class CutOffUser extends BaseEntity {
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private User user;

    @ManyToOne(optional = false)
    private User cutUser;

    protected CutOffUser () {}

    protected CutOffUser (
            Long id,
            User user,
            User cutUser
    ) {
        this.id = id;
        this.user = user;
        this.cutUser = cutUser;
    }

    public static CutOffUser of (
            Long id,
            User user,
            User cutUser
    ) {
        return new CutOffUser(
                id,
                user,
                cutUser
        );
    }
}
