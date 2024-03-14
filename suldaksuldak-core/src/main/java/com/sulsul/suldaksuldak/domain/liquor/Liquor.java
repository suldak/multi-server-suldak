package com.sulsul.suldaksuldak.domain.liquor;

import com.sulsul.suldaksuldak.domain.BaseEntity;
import com.sulsul.suldaksuldak.domain.bridge.*;
import com.sulsul.suldaksuldak.domain.file.FileBase;
import com.sulsul.suldaksuldak.domain.stats.LiquorSearchLog;
import com.sulsul.suldaksuldak.domain.stats.UserLiquor;
import com.sulsul.suldaksuldak.domain.tag.DrinkingCapacity;
import com.sulsul.suldaksuldak.domain.tag.LiquorAbv;
import com.sulsul.suldaksuldak.domain.tag.LiquorDetail;
import com.sulsul.suldaksuldak.domain.tag.LiquorName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @Column(nullable = false, length = 50, columnDefinition = "VARCHAR(50)")
    private String name;

    @Setter
    @Column(nullable = false, columnDefinition = "TEXT")
    private String summaryExplanation;

    @Setter
    @Column(nullable = false, columnDefinition = "TEXT")
    private String detailExplanation;

    @Setter
    @Column(nullable = false, columnDefinition = "TEXT")
    private String searchTag;

    @Setter
    @Column(columnDefinition = "TEXT")
    private String liquorRecipe;

    @Setter
    @Column(nullable = false, columnDefinition = "DOUBLE")
    private Double detailAbv;

    // 삭제 여부
    @Setter
    @Column(nullable = false)
    private Boolean isActive;


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

    @OneToOne
    @Setter
    @JoinColumn(name = "file_base_nm")
    private FileBase fileBase;

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "liquor", cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<SlToLi> slToLis = new LinkedHashSet<>();

//    @ToString.Exclude
//    @OneToOne(mappedBy = "liquor", cascade = CascadeType.REMOVE)
//    private LiquorRecipe liquorRecipe;

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "liquor", cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<MtToLi> mtToLis = new LinkedHashSet<>();

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "liquor", cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<StToLi> stToLis = new LinkedHashSet<>();

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "liquor", cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<TtToLi> ttToLis = new LinkedHashSet<>();

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "liquor", cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<SnToLi> snToLis = new LinkedHashSet<>();

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "liquor", cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<UserLiquor> userLiquors = new LinkedHashSet<>();

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "liquor", cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<LiquorSearchLog> liquorSearchLogs = new LinkedHashSet<>();

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "liquor", cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<LiquorLike> liquorLikes = new LinkedHashSet<>();

    protected Liquor () {}

    protected Liquor (
            Long id,
            String name,
            String summaryExplanation,
            String detailExplanation,
            String searchTag,
            String liquorRecipe,
            Double detailAbv,
            Boolean isActive,
            LiquorAbv liquorAbv,
            LiquorDetail liquorDetail,
            DrinkingCapacity drinkingCapacity,
            LiquorName liquorName,
            FileBase fileBase
    ) {
        this.id = id;
        this.name = name;
        this.summaryExplanation = summaryExplanation;
        this.detailExplanation = detailExplanation;
        this.searchTag = searchTag;
        this.liquorRecipe = liquorRecipe;
        this.detailAbv = detailAbv;
        this.isActive = isActive;
        this.liquorAbv = liquorAbv;
        this.liquorDetail = liquorDetail;
        this.drinkingCapacity = drinkingCapacity;
        this.liquorName = liquorName;
        this.fileBase = fileBase;
    }

    public static Liquor of (
            Long id,
            String name,
            String summaryExplanation,
            String detailExplanation,
            String searchTag,
            String liquorRecipe,
            Double detailAbv,
            Boolean isActive,
            LiquorAbv liquorAbv,
            LiquorDetail liquorDetail,
            DrinkingCapacity drinkingCapacity,
            LiquorName liquorName,
            FileBase fileBase
    ) {
        return new Liquor(
                id,
                name,
                summaryExplanation,
                detailExplanation,
                searchTag,
                liquorRecipe,
                detailAbv,
                isActive,
                liquorAbv,
                liquorDetail,
                drinkingCapacity,
                liquorName,
                fileBase
        );
    }
}
