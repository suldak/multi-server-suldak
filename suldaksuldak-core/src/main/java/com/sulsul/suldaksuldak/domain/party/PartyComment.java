package com.sulsul.suldaksuldak.domain.party;

import com.sulsul.suldaksuldak.domain.BaseEntity;
import com.sulsul.suldaksuldak.domain.report.ReportPartyComment;
import com.sulsul.suldaksuldak.domain.user.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@ToString
@Table(
        name = "tb_party_comment"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "partyComment")
public class PartyComment extends BaseEntity {
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

    @Column(columnDefinition = "VARCHAR(100)", nullable = false)
    private String groupComment;

//    @Setter
//    @Column(columnDefinition = "INT UNSIGNED", nullable = false)
//    private Integer commentCnt;

    @Column(columnDefinition = "INT UNSIGNED", nullable = false)
    private Integer commentDep;

//    @Column(columnDefinition = "INT UNSIGNED", nullable = false)
//    private Integer commentIndex;

    @Setter
    @Column(nullable = false)
    private Boolean isDelete;

    @Setter
    @Column(nullable = false)
    private Boolean isModified;

//    @Setter
//    @Column(nullable = false)
//    private Integer warningCnt;

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "partyComment", cascade = CascadeType.REMOVE)
    private Set<ReportPartyComment> reportPartyComments = new LinkedHashSet<>();

    protected PartyComment() {}

    protected PartyComment(
            String id,
            String comment,
            User user,
            Party party,
            String groupComment,
//            Integer commentCnt,
            Integer commentDep,
//            Integer commentIndex,
            Boolean isDelete,
            Boolean isModified
//            Integer warningCnt
    ) {
        this.id = id;
        this.comment = comment;
        this.user = user;
        this.party = party;
        this.groupComment = groupComment;
//        this.commentCnt = commentCnt;
        this.commentDep = commentDep;
//        this.commentIndex = commentIndex;
        this.isDelete = isDelete;
        this.isModified = isModified;
//        this.warningCnt = warningCnt;
    }

    public static PartyComment of (
            String id,
            String comment,
            User user,
            Party party,
            String groupComment,
//            Integer commentCnt,
            Integer commentDep,
//            Integer commentIndex,
            Boolean isDelete,
            Boolean isModified
//            Integer warningCnt
    ) {
        return new PartyComment(
                id,
                comment,
                user,
                party,
                groupComment,
//                commentCnt,
                commentDep,
//                commentIndex,
                isDelete,
                isModified
//                warningCnt
        );
    }
}
