package com.sulsul.suldaksuldak.domain.tag;

import com.sulsul.suldaksuldak.domain.bridge.TtToLi;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@ToString
@Table(
        name = "tb_taste_type"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "tasteType")
public class TasteType implements TagEntity {
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false, unique = true, length = 10)
    private String name;

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "tasteType", cascade = CascadeType.REMOVE)
    private Set<TtToLi> ttToLis = new LinkedHashSet<>();

    protected TasteType() {}

    protected TasteType(
            Long id,
            String name
    ) {
        this.id = id;
        this.name = name;
    }

    public static TasteType of (
            Long id,
            String name
    ) {
        return new TasteType(
                id,
                name
        );
    }
}
