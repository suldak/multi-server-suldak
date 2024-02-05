package com.sulsul.suldaksuldak.domain.tag;

import com.sulsul.suldaksuldak.domain.bridge.MtToLi;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@ToString
@Table(
        name = "tb_liquor_material"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "liquorMaterial")
public class LiquorMaterial implements TagEntity {
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false, unique = true, length = 10)
    private String name;

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "liquorMaterial", cascade = CascadeType.REMOVE)
    private Set<MtToLi> mtToLis = new LinkedHashSet<>();

    protected LiquorMaterial() {}

    protected LiquorMaterial(
            Long id,
            String name
    ) {
        this.id = id;
        this.name = name;
    }

    public static LiquorMaterial of (
            Long id,
            String name
    ) {
        return new LiquorMaterial(
                id,
                name
        );
    }
}
