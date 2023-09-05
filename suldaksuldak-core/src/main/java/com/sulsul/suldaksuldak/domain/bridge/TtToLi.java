package com.sulsul.suldaksuldak.domain.bridge;

import com.sulsul.suldaksuldak.domain.liquor.Liquor;
import com.sulsul.suldaksuldak.domain.tag.TasteType;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@ToString
@Table(
        name = "tb_tt_li"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "ttToLi")
public class TtToLi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private TasteType tasteType;

    @ManyToOne(optional = false)
    private Liquor liquor;

    protected TtToLi() {}

    protected TtToLi(
            Long id,
            TasteType tasteType,
            Liquor liquor
    ) {
        this.id = id;
        this.tasteType = tasteType;
        this.liquor = liquor;
    }

    public static TtToLi of (
            Long id,
            TasteType tasteType,
            Liquor liquor
    ) {
        return new TtToLi(
                id,
                tasteType,
                liquor
        );
    }
}
