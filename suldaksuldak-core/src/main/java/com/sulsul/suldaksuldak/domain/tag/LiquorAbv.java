package com.sulsul.suldaksuldak.domain.tag;

import com.sulsul.suldaksuldak.domain.liquor.Liquor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@ToString
@Table(
        name = "tb_liquor_abv"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "liquorAbv")
public class LiquorAbv implements TagEntity {
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false, unique = true, length = 10, columnDefinition = "VARCHAR(10)")
    private String name;

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "liquorAbv")
    private final Set<Liquor> liquors = new LinkedHashSet<>();

    protected LiquorAbv() {}

    protected LiquorAbv(
            Long id,
            String name
    ) {
        this.id = id;
        this.name = name;
    }

    public static LiquorAbv of (
            Long id,
            String name
    ) {
        return new LiquorAbv(
                id,
                name
        );
    }
}
