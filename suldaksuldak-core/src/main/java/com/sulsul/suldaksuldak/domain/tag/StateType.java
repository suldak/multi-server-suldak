package com.sulsul.suldaksuldak.domain.tag;

import com.sulsul.suldaksuldak.domain.bridge.StToLi;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@ToString
@Table(
        name = "tb_state_type"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "stateType")
public class StateType implements TagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false, unique = true, length = 20)
    private String name;

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "stateType", cascade = CascadeType.REMOVE)
    private Set<StToLi> stToLis = new LinkedHashSet<>();

    protected StateType() {}

    protected StateType(
            Long id,
            String name
    ) {
        this.id = id;
        this.name = name;
    }

    public static StateType of (
            Long id,
            String name
    ) {
        return new StateType(
                id,
                name
        );
    }
}
