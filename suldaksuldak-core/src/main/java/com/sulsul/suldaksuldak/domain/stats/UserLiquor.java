package com.sulsul.suldaksuldak.domain.stats;

import com.sulsul.suldaksuldak.domain.liquor.Liquor;
import com.sulsul.suldaksuldak.domain.user.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    @Column(nullable = false, columnDefinition = "DOUBLE")
    private Double searchCnt;

    @Setter
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime lastSearchTime;


    protected UserLiquor () {}

    protected UserLiquor (
            Long id,
            User user,
            Liquor liquor,
            Double searchCnt,
            LocalDateTime lastSearchTime
    ) {
        this.id = id;
        this.user = user;
        this.liquor = liquor;
        this.searchCnt = searchCnt;
        this.lastSearchTime = lastSearchTime;
    }

    public static UserLiquor of (
            Long id,
            User user,
            Liquor liquor,
            Double searchCnt,
            LocalDateTime lastSearchTime
    ) {
        return new UserLiquor(
                id,
                user,
                liquor,
                searchCnt,
                lastSearchTime
        );
    }
}
