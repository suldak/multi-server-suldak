package com.sulsul.suldaksuldak.domain.stats;

import com.sulsul.suldaksuldak.domain.liquor.Liquor;
import com.sulsul.suldaksuldak.domain.user.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@ToString
@Table(
        name = "tb_user_liquor"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "userLiquor")
public class UserLiquor {
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private User user;

    @ManyToOne(optional = false)
    private Liquor liquor;

    @Setter
    @Column(nullable = false)
    private Double searchCnt;

    protected UserLiquor () {}

    protected UserLiquor (
            Long id,
            User user,
            Liquor liquor,
            Double searchCnt
    ) {
        this.id = id;
        this.user = user;
        this.liquor = liquor;
        this.searchCnt = searchCnt;
    }

    public static UserLiquor of (
            Long id,
            User user,
            Liquor liquor,
            Double searchCnt
    ) {
        return new UserLiquor(
                id,
                user,
                liquor,
                searchCnt
        );
    }
}
