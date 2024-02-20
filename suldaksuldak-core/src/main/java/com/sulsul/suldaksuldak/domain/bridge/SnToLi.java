package com.sulsul.suldaksuldak.domain.bridge;

import com.sulsul.suldaksuldak.domain.liquor.Liquor;
import com.sulsul.suldaksuldak.domain.tag.LiquorSnack;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@ToString
@Table(
        name = "tb_sn_li"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "snToLi")
public class SnToLi {
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private LiquorSnack liquorSnack;

    @ManyToOne(optional = false)
    private Liquor liquor;

    protected SnToLi() {}

    protected SnToLi(
            Long id,
            LiquorSnack liquorSnack,
            Liquor liquor
    ) {
        this.id = id;
        this.liquorSnack = liquorSnack;
        this.liquor = liquor;
    }

    public static SnToLi of (
            Long id,
            LiquorSnack liquorSnack,
            Liquor liquor
    ) {
        return new SnToLi(
                id,
                liquorSnack,
                liquor
        );
    }
}
