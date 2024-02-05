package com.sulsul.suldaksuldak.domain.liquor;

import com.sulsul.suldaksuldak.domain.BaseEntity;
import com.sulsul.suldaksuldak.domain.bridge.*;
import com.sulsul.suldaksuldak.domain.stats.LiquorSearchLog;
import com.sulsul.suldaksuldak.domain.stats.UserLiquor;
import com.sulsul.suldaksuldak.domain.tag.DrinkingCapacity;
import com.sulsul.suldaksuldak.domain.tag.LiquorAbv;
import com.sulsul.suldaksuldak.domain.tag.LiquorDetail;
import com.sulsul.suldaksuldak.domain.tag.LiquorName;
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
    @Column(columnDefinition = "BIGINT UNSIGNED")
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

    @Setter
    @Column(nullable = false)
    private String searchTag;

    @Setter
    @Column
    private String liquorRecipe;

    @Setter
    @Column(nullable = false)
    private Double detailAbv;

    @Setter
    @ManyToOne
    private DrinkingCapacity drinkingCapacity;

    @Setter
    @ManyToOne
    private LiquorAbv liquorAbv;

    @Setter
    @ManyToOne
    private LiquorDetail liquorDetail;

    @Setter
    @ManyToOne
    private LiquorName liquorName;

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "liquor", cascade = CascadeType.REMOVE)
    private Set<SlToLi> slToLis = new LinkedHashSet<>();

//    @ToString.Exclude
//    @OneToOne(mappedBy = "liquor", cascade = CascadeType.REMOVE)
//    private LiquorRecipe liquorRecipe;

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

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "liquor", cascade = CascadeType.REMOVE)
    private Set<UserLiquor> userLiquors = new LinkedHashSet<>();

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "liquor", cascade = CascadeType.REMOVE)
    private Set<LiquorSearchLog> liquorSearchLogs = new LinkedHashSet<>();

    protected Liquor () {}

    protected Liquor (
            Long id,
            String name,
            String summaryExplanation,
            String detailExplanation,
            String searchTag,
            String liquorRecipe,
            Double detailAbv,
            LiquorAbv liquorAbv,
            LiquorDetail liquorDetail,
            DrinkingCapacity drinkingCapacity,
            LiquorName liquorName
    ) {
        this.id = id;
        this.name = name;
        this.summaryExplanation = summaryExplanation;
        this.detailExplanation = detailExplanation;
        this.searchTag = searchTag;
        this.liquorRecipe = liquorRecipe;
        this.detailAbv = detailAbv;
        this.liquorAbv = liquorAbv;
        this.liquorDetail = liquorDetail;
        this.drinkingCapacity = drinkingCapacity;
        this.liquorName = liquorName;
    }

    public static Liquor of (
            Long id,
            String name,
            String summaryExplanation,
            String detailExplanation,
            String searchTag,
            String liquorRecipe,
            Double detailAbv,
            LiquorAbv liquorAbv,
            LiquorDetail liquorDetail,
            DrinkingCapacity drinkingCapacity,
            LiquorName liquorName
    ) {
        return new Liquor(
                id,
                name,
                summaryExplanation,
                detailExplanation,
                searchTag,
                liquorRecipe,
                detailAbv,
                liquorAbv,
                liquorDetail,
                drinkingCapacity,
                liquorName
        );
    }
}
