package com.sulsul.suldaksuldak.domain.bridge;

import com.sulsul.suldaksuldak.domain.liquor.Liquor;
import com.sulsul.suldaksuldak.domain.tag.LiquorDetail;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@ToString
@Table(
        name = "tb_dt_li"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "dtToLi")
public class DtToLi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private LiquorDetail liquorDetail;

    @ManyToOne(optional = false)
    private Liquor liquor;

    protected DtToLi() {}

    protected DtToLi(
            Long id,
            LiquorDetail liquorDetail,
            Liquor liquor
    ) {
        this.id = id;
        this.liquorDetail = liquorDetail;
        this.liquor = liquor;
    }

    public static DtToLi of (
            Long id,
            LiquorDetail liquorDetail,
            Liquor liquor
    ) {
        return new DtToLi(
                id,
                liquorDetail,
                liquor
        );
    }
}
