package com.sulsul.suldaksuldak.domain.party;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@ToString
@Table(
        name = "tb_party_tag"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "partyTag")
public class PartyTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "partyTag")
    private final Set<Party> parties = new LinkedHashSet<>();

    protected PartyTag () {}

    protected PartyTag (
            Long id,
            String name
    ) {
        this.id = id;
        this.name = name;
    }

    public static PartyTag of (
            Long id,
            String name
    ) {
        return new PartyTag(
                id,
                name
        );
    }
}
