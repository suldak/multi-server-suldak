package com.sulsul.suldaksuldak.domain.party;

import com.sulsul.suldaksuldak.domain.BaseEntity;
import com.sulsul.suldaksuldak.domain.user.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@ToString
@Table(
        name = "tb_party_comment"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "partyComment")
public class PartyComments extends BaseEntity {
    @Id
    @Column(columnDefinition = "VARCHAR(100)")
    private String id;

    @Setter
    @Column(nullable = false, columnDefinition = "TEXT")
    private String comment;

    @ManyToOne(optional = false)
    private User user;

    @ManyToOne(optional = false)
    private Party party;

    @Column(columnDefinition = "VARCHAR(20)", nullable = false)
    private String groupComment;

    @Setter
    @Column(columnDefinition = "INT UNSIGNED", nullable = false)
    private Integer commentDep;

    @Column(columnDefinition = "INT UNSIGNED", nullable = false)
    private Integer commentIndex;

    @Setter
    @Column(nullable = false)
    private Boolean isDelete;

    @Column(nullable = false)
    private Integer warningCnt;

    protected PartyComments () {}

    protected PartyComments (
            String id,
            String comment,
            User user,
            Party party,
            String groupComment,
            Integer commentDep,
            Integer commentIndex,
            Boolean isDelete,
            Integer warningCnt
    ) {
        this.id = id;
        this.comment = comment;
        this.user = user;
        this.party = party;
        this.groupComment = groupComment;
        this.commentDep = commentDep;
        this.commentIndex = commentIndex;
        this.isDelete = isDelete;
        this.warningCnt = warningCnt;
    }

    public static PartyComments of (
            String id,
            String comment,
            User user,
            Party party,
            String groupComment,
            Integer commentDep,
            Integer commentIndex,
            Boolean isDelete,
            Integer warningCnt
    ) {
        return new PartyComments(
                id,
                comment,
                user,
                party,
                groupComment,
                commentDep,
                commentIndex,
                isDelete,
                warningCnt
        );
    }
}
