package com.sulsul.suldaksuldak.domain.liquor;

import com.sulsul.suldaksuldak.domain.BaseEntity;
import com.sulsul.suldaksuldak.domain.bridge.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@ToString
@Table(
        name = "tb_liquor"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "liquor")
public class Liquor extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false, length = 50)
    private String name;

    @Setter
    @Column(nullable = false)
    private String summaryExplanation;

    @Setter
    @Column(nullable = false)
    private String detailExplanation;

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "liquor", cascade = CascadeType.REMOVE)
    private Set<DkToLi> dkToLis = new LinkedHashSet<>();

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "liquor", cascade = CascadeType.REMOVE)
    private Set<AbvToLi> abvToLis = new LinkedHashSet<>();

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "liquor", cascade = CascadeType.REMOVE)
    private Set<DtToLi> dtToLis = new LinkedHashSet<>();

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "liquor", cascade = CascadeType.REMOVE)
    private Set<NmToLi> nmToLis = new LinkedHashSet<>();

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "liquor", cascade = CascadeType.REMOVE)
    private Set<SlToLi> slToLis = new LinkedHashSet<>();

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "liquor", cascade = CascadeType.REMOVE)
    private Set<LiquorRecipe> liquorRecipes = new LinkedHashSet<>();

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "liquor", cascade = CascadeType.REMOVE)
    private Set<MtToLi> mtToLis = new LinkedHashSet<>();

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "liquor", cascade = CascadeType.REMOVE)
    private Set<StToLi> stToLis = new LinkedHashSet<>();

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "liquor", cascade = CascadeType.REMOVE)
    private Set<TtToLi> ttToLis = new LinkedHashSet<>();

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "liquor", cascade = CascadeType.REMOVE)
    private Set<SnToLi> snToLis = new LinkedHashSet<>();

    protected Liquor () {}

    protected Liquor (
            Long id,
            String name,
            String summaryExplanation,
            String detailExplanation
    ) {
        this.id = id;
        this.name = name;
        this.summaryExplanation = summaryExplanation;
        this.detailExplanation = detailExplanation;
    }

    public static Liquor of (
            Long id,
            String name,
            String summaryExplanation,
            String detailExplanation
    ) {
        return new Liquor(
                id,
                name,
                summaryExplanation,
                detailExplanation
        );
    }
}
