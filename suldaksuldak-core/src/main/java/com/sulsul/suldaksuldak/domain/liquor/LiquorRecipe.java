package com.sulsul.suldaksuldak.domain.liquor;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@ToString
@Table(
        name = "tb_liquor_recipe"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "liquorRecipe")
public class LiquorRecipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String content;

    @Setter
    @Column(nullable = false)
    private Integer contentCnt;

    @ManyToOne(optional = false)
    private Liquor liquor;

    protected LiquorRecipe() {}

    protected LiquorRecipe(
            Long id,
            String content,
            Integer contentCnt,
            Liquor liquor
    ) {
        this.id = id;
        this.content = content;
        this.contentCnt = contentCnt;
        this.liquor = liquor;
    }

    public static LiquorRecipe of (
            Long id,
            String content,
            Integer contentCnt,
            Liquor liquor
    ) {
        return new LiquorRecipe(
                id,
                content,
                contentCnt,
                liquor
        );
    }
}
