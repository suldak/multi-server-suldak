package com.sulsul.suldaksuldak.domain.tag;

import com.sulsul.suldaksuldak.domain.bridge.SnToLi;
import com.sulsul.suldaksuldak.domain.file.FileBase;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@ToString
@Table(
        name = "tb_liquor_snack"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "liquorSnack")
public class LiquorSnack implements TagEntity {
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false, unique = true, length = 20)
    private String name;

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "liquorSnack", cascade = CascadeType.REMOVE)
    private Set<SnToLi> snToLis = new LinkedHashSet<>();

    @OneToOne
    @Setter
    @JoinColumn(name = "file_base_nm")
    private FileBase fileBase;

    protected LiquorSnack() {}

    protected LiquorSnack(
            Long id,
            String name,
            FileBase fileBase
    ) {
        this.id = id;
        this.name = name;
        this.fileBase = fileBase;
    }

    public static LiquorSnack of (
            Long id,
            String name,
            FileBase fileBase
    ) {
        return new LiquorSnack(
                id,
                name,
                fileBase
        );
    }
}
