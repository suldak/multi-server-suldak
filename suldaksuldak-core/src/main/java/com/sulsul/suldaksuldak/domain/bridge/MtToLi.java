package com.sulsul.suldaksuldak.domain.bridge;

import com.sulsul.suldaksuldak.domain.liquor.Liquor;
import com.sulsul.suldaksuldak.domain.tag.LiquorMaterial;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@ToString
@Table(
        name = "tb_mt_li"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "mtToLi")
public class MtToLi {
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private LiquorMaterial liquorMaterial;

    @ManyToOne(optional = false)
    private Liquor liquor;

    protected MtToLi() {}

    protected MtToLi(
            Long id,
            LiquorMaterial liquorMaterial,
            Liquor liquor
    ) {
        this.id = id;
        this.liquorMaterial = liquorMaterial;
        this.liquor = liquor;
    }

    public static MtToLi of (
            Long id,
            LiquorMaterial liquorMaterial,
            Liquor liquor
    ) {
        return new MtToLi(
                id,
                liquorMaterial,
                liquor
        );
    }
}
