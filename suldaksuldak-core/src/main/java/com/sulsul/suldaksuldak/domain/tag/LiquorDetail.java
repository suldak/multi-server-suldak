package com.sulsul.suldaksuldak.domain.tag;

import com.sulsul.suldaksuldak.domain.bridge.DtToLi;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@ToString
@Table(
        name = "tb_liquor_detail"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "liquorDetail")
public class LiquorDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false, unique = true, length = 20)
    private String name;

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "liquorDetail", cascade = CascadeType.REMOVE)
    private Set<DtToLi> dtToLis = new LinkedHashSet<>();

    protected LiquorDetail() {}

    protected LiquorDetail(
            Long id,
            String name
    ) {
        this.id = id;
        this.name = name;
    }

    public static LiquorDetail of (
            Long id,
            String name
    ) {
        return new LiquorDetail(
                id,
                name
        );
    }
}
