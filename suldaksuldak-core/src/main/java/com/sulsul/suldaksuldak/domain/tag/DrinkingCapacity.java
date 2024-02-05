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
        name = "tb_drinking_capacity"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "drinkingCapacity")
public class DrinkingCapacity implements TagEntity {
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false, unique = true, length = 10)
    private String name;

    @Setter
    @Column
    private String color;

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "drinkingCapacity")
    private final Set<Liquor> liquors = new LinkedHashSet<>();

    protected DrinkingCapacity() {}

    protected DrinkingCapacity(
            Long id,
            String name,
            String color
    ) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public static DrinkingCapacity of (
            Long id,
            String level,
            String color
    ) {
        return new DrinkingCapacity(
                id,
                level,
                color
        );
    }
}
