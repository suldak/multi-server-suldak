package com.sulsul.suldaksuldak.domain.bridge;

import com.sulsul.suldaksuldak.domain.liquor.Liquor;
import com.sulsul.suldaksuldak.domain.tag.LiquorType;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@ToString
@Table(
        name = "tb_tp_li"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "tpToLi")
public class TpToLi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private LiquorType liquorType;

    @ManyToOne(optional = false)
    private Liquor liquor;

    protected TpToLi() {}

    protected TpToLi(
            Long id,
            LiquorType liquorType,
            Liquor liquor
    ) {
        this.id = id;
        this.liquorType = liquorType;
        this.liquor = liquor;
    }

    public static TpToLi of (
            Long id,
            LiquorType liquorType,
            Liquor liquor
    ) {
        return new TpToLi(
                id,
                liquorType,
                liquor
        );
    }
}
