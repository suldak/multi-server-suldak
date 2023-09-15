package com.sulsul.suldaksuldak.domain.tag;

import com.sulsul.suldaksuldak.domain.bridge.DkToLi;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@ToString
@Table(
        name = "tb_drinking_capacity"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "drinkingCapacity")
public class DrinkingCapacity implements TagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false, unique = true, length = 10)
    private String name;

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "drinkingCapacity", cascade = CascadeType.REMOVE)
    private Set<DkToLi> dkToLis = new LinkedHashSet<>();

    protected DrinkingCapacity() {}

    protected DrinkingCapacity(
            Long id,
            String name
    ) {
        this.id = id;
        this.name = name;
    }

    public static DrinkingCapacity of (
            Long id,
            String level
    ) {
        return new DrinkingCapacity(
                id,
                level
        );
    }
}
