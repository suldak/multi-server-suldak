package com.sulsul.suldaksuldak.domain.bridge;

import com.sulsul.suldaksuldak.domain.liquor.Liquor;
import com.sulsul.suldaksuldak.domain.tag.DrinkingCapacity;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@ToString
@Table(
        name = "tb_dc_li"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "dcToLi")
public class DkToLi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private DrinkingCapacity drinkingCapacity;

    @ManyToOne(optional = false)
    private Liquor liquor;

    protected DkToLi () {}

    protected DkToLi (
            Long id,
            DrinkingCapacity drinkingCapacity,
            Liquor liquor
    ) {
        this.id = id;
        this.drinkingCapacity = drinkingCapacity;
        this.liquor = liquor;
    }

    public static DkToLi of (
            Long id,
            DrinkingCapacity drinkingCapacity,
            Liquor liquor
    ) {
        return new DkToLi(
                id,
                drinkingCapacity,
                liquor
        );
    }
}
