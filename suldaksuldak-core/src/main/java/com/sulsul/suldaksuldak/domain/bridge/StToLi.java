package com.sulsul.suldaksuldak.domain.bridge;

import com.sulsul.suldaksuldak.domain.liquor.Liquor;
import com.sulsul.suldaksuldak.domain.tag.StateType;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@ToString
@Table(
        name = "tb_st_li"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "stToLi")
public class StToLi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private StateType stateType;

    @ManyToOne(optional = false)
    private Liquor liquor;

    protected StToLi() {}

    protected StToLi(
            Long id,
            StateType stateType,
            Liquor liquor
    ) {
        this.id = id;
        this.stateType = stateType;
        this.liquor = liquor;
    }

    public static StToLi of (
            Long id,
            StateType stateType,
            Liquor liquor
    ) {
        return new StToLi(
                id,
                stateType,
                liquor
        );
    }
}
