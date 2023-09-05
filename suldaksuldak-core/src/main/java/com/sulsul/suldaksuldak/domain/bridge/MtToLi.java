package com.sulsul.suldaksuldak.domain.bridge;

import com.sulsul.suldaksuldak.domain.liquor.Liquor;
import com.sulsul.suldaksuldak.domain.tag.MaterialType;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private MaterialType materialType;

    @ManyToOne(optional = false)
    private Liquor liquor;

    protected MtToLi() {}

    protected MtToLi(
            Long id,
            MaterialType materialType,
            Liquor liquor
    ) {
        this.id = id;
        this.materialType = materialType;
        this.liquor = liquor;
    }

    public static MtToLi of (
            Long id,
            MaterialType materialType,
            Liquor liquor
    ) {
        return new MtToLi(
                id,
                materialType,
                liquor
        );
    }
}
