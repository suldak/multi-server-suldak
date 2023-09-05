package com.sulsul.suldaksuldak.domain.bridge;

import com.sulsul.suldaksuldak.domain.liquor.Liquor;
import com.sulsul.suldaksuldak.domain.tag.LiquorAbv;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@ToString
@Table(
        name = "tb_abv_li"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "abvToLi")
public class AbvToLi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private LiquorAbv liquorAbv;

    @ManyToOne(optional = false)
    private Liquor liquor;

    protected AbvToLi() {}

    protected AbvToLi(
            Long id,
            LiquorAbv liquorAbv,
            Liquor liquor
    ) {
        this.id = id;
        this.liquorAbv = liquorAbv;
        this.liquor = liquor;
    }

    public static AbvToLi of (
            Long id,
            LiquorAbv liquorAbv,
            Liquor liquor
    ) {
        return new AbvToLi(
                id,
                liquorAbv,
                liquor
        );
    }
}
