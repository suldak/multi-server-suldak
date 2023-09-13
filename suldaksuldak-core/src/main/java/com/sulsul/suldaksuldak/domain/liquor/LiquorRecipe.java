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

//    @ManyToOne(optional = false)
    @OneToOne(optional = false)
    @JoinColumn(name = "liquor_id")
    private Liquor liquor;

    protected LiquorRecipe() {}

    protected LiquorRecipe(
            Long id,
            String content,
            Liquor liquor
    ) {
        this.id = id;
        this.content = content;
        this.liquor = liquor;
    }

    public static LiquorRecipe of (
            Long id,
            String content,
            Liquor liquor
    ) {
        return new LiquorRecipe(
                id,
                content,
                liquor
        );
    }
}
