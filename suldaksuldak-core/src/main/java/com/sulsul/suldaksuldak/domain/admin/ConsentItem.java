package com.sulsul.suldaksuldak.domain.admin;

import com.sulsul.suldaksuldak.constant.admin.ConsentItemType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@ToString
@Table(
        name = "tb_consent_item"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "consentItem")
public class ConsentItem {
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ConsentItemType itemType;

    @Column(nullable = false)
    @Setter
    private Integer itemSeq;

    @Column(nullable = false)
    @Setter
    private String itemText;

    protected ConsentItem () {}

    protected ConsentItem (
            Long id,
            ConsentItemType itemType,
            Integer itemSeq,
            String itemText
    ) {
        this.id = id;
        this.itemType = itemType;
        this.itemSeq = itemSeq;
        this.itemText = itemText;
    }

    public static ConsentItem of (
            Long id,
            ConsentItemType consentItemType,
            Integer itemSeq,
            String itemText
    ) {
        return new ConsentItem(
                id,
                consentItemType,
                itemSeq,
                itemText
        );
    }
}
