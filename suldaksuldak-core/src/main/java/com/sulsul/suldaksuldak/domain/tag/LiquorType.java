package com.sulsul.suldaksuldak.domain.tag;

import com.sulsul.suldaksuldak.domain.bridge.TpToLi;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@ToString
@Table(
        name = "tb_liquor_type"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "liquorType")
public class LiquorType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false, unique = true, length = 10)
    private String name;

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "liquorType", cascade = CascadeType.REMOVE)
    private Set<TpToLi> tpToLis = new LinkedHashSet<>();

    protected LiquorType() {}

    protected LiquorType(
            Long id,
            String name
    ) {
        this.id = id;
        this.name = name;
    }

    public static LiquorType of (
            Long id,
            String name
    ) {
        return new LiquorType(
                id,
                name
        );
    }
}
