package com.sulsul.suldaksuldak.domain.tag;

import com.sulsul.suldaksuldak.domain.bridge.SlToLi;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@ToString
@Table(
        name = "tb_liquor_sell"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "liquorSell")
public class LiquorSell implements TagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false, unique = true, length = 20)
    private String name;

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "liquorSell", cascade = CascadeType.REMOVE)
    private Set<SlToLi> slToLis = new LinkedHashSet<>();

    protected LiquorSell() {}

    protected LiquorSell(
            Long id,
            String name
    ) {
        this.id = id;
        this.name = name;
    }

    public static LiquorSell of (
            Long id,
            String name
    ) {
        return new LiquorSell(
                id,
                name
        );
    }
}
