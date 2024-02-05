package com.sulsul.suldaksuldak.domain.bridge;

import com.sulsul.suldaksuldak.domain.liquor.Liquor;
import com.sulsul.suldaksuldak.domain.tag.LiquorName;
import com.sulsul.suldaksuldak.domain.tag.LiquorSell;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@ToString
@Table(
        name = "tb_sl_li"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "slToLi")
public class SlToLi {
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private LiquorSell liquorSell;

    @ManyToOne(optional = false)
    private Liquor liquor;

    protected SlToLi() {}

    protected SlToLi(
            Long id,
            LiquorSell liquorSell,
            Liquor liquor
    ) {
        this.id = id;
        this.liquorSell = liquorSell;
        this.liquor = liquor;
    }

    public static SlToLi of (
            Long id,
            LiquorSell liquorSell,
            Liquor liquor
    ) {
        return new SlToLi(
                id,
                liquorSell,
                liquor
        );
    }
}
