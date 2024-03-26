package com.sulsul.suldaksuldak.domain.search;

import com.sulsul.suldaksuldak.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@ToString
@Table(
        name = "tb_recommend_search_text"
)
@Entity(name = "recommendSearchText")
public class RecommendSearchText extends BaseEntity {
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private Boolean isActive;

    @Setter
    @Column(nullable = false, columnDefinition = "VARCHAR(100)")
    private String text;

    protected RecommendSearchText () {}

    protected RecommendSearchText (
            Long id,
            Boolean isActive,
            String text
    ) {
        this.id = id;
        this.isActive = isActive;
        this.text = text;
    }

    public static RecommendSearchText of (
            Long id,
            Boolean isActive,
            String text
    ) {
        return new RecommendSearchText(
                id,
                isActive,
                text
        );
    }
}
