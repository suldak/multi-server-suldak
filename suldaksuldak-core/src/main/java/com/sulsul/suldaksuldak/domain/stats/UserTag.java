package com.sulsul.suldaksuldak.domain.stats;

import com.sulsul.suldaksuldak.constant.stats.TagType;
import com.sulsul.suldaksuldak.domain.user.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@ToString
@Table(
        name = "tb_user_tag"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "userTag")
public class UserTag {
    @Id
    @Column(columnDefinition = "VARCHAR(255)")
    private String id;

    @Column(nullable = false, columnDefinition = "VARCHAR(20)")
    @Enumerated(EnumType.STRING)
    private TagType tagType;

    @Column(nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long tagId;

    @Setter
    @Column(nullable = false, columnDefinition = "DOUBLE")
    private Double weight;

    @ManyToOne(optional = false)
    private User user;

    protected UserTag () {}

    protected UserTag (
            String id,
            TagType tagType,
            Long tagId,
            Double weight,
            User user
    ) {
        this.id = id;
        this.tagType = tagType;
        this.tagId = tagId;
        this.weight = weight;
        this.user = user;
    }

    public static UserTag of (
            String id,
            TagType tagType,
            Long tagId,
            Double weight,
            User user
    ) {
        return new UserTag(
                id,
                tagType,
                tagId,
                weight,
                user
        );
    }
}
