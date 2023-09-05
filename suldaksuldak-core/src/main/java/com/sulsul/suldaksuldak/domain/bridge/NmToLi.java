package com.sulsul.suldaksuldak.domain.bridge;

import com.sulsul.suldaksuldak.domain.liquor.Liquor;
import com.sulsul.suldaksuldak.domain.tag.LiquorName;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@ToString
@Table(
        name = "tb_nm_li"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "nmToLi")
public class NmToLi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private LiquorName liquorName;

    @ManyToOne(optional = false)
    private Liquor liquor;

    protected NmToLi() {}

    protected NmToLi(
            Long id,
            LiquorName liquorName,
            Liquor liquor
    ) {
        this.id = id;
        this.liquorName = liquorName;
        this.liquor = liquor;
    }

    public static NmToLi of (
            Long id,
            LiquorName liquorName,
            Liquor liquor
    ) {
        return new NmToLi(
                id,
                liquorName,
                liquor
        );
    }
}
