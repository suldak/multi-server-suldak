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
        name = "tb_material_type"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "materialType")
public class MaterialType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false, unique = true, length = 10)
    private String name;

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "materialType", cascade = CascadeType.REMOVE)
    private Set<MtToLi> mtToLis = new LinkedHashSet<>();

    protected MaterialType() {}

    protected MaterialType(
            Long id,
            String name
    ) {
        this.id = id;
        this.name = name;
    }

    public static MaterialType of (
            Long id,
            String name
    ) {
        return new MaterialType(
                id,
                name
        );
    }
}
