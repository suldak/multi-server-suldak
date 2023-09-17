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
        name = "tb_liquor_name"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "liquorName")
public class LiquorName implements TagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false, unique = true, length = 10)
    private String name;

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "liquorName")
    private final Set<Liquor> liquors = new LinkedHashSet<>();

    protected LiquorName () {}

    protected LiquorName (
            Long id,
            String name
    ) {
        this.id = id;
        this.name = name;
    }

    public static LiquorName of (
            Long id,
            String name
    ) {
        return new LiquorName(
                id,
                name
        );
    }
}
