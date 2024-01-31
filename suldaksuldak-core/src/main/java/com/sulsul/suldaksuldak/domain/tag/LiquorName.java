package com.sulsul.suldaksuldak.domain.tag;

import com.sulsul.suldaksuldak.domain.file.FileBase;
import com.sulsul.suldaksuldak.domain.liquor.Liquor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@ToString
@Table(
        name = "tb_liquor_name"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "liquorName")
public class LiquorName implements TagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false, unique = true, length = 10)
    private String name;

    @OneToOne
    @Setter
    @JoinColumn(name = "file_base_nm")
    private FileBase fileBase;

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "liquorName")
    private final Set<Liquor> liquors = new LinkedHashSet<>();

    protected LiquorName () {}

    protected LiquorName (
            Long id,
            String name,
            FileBase fileBase
    ) {
        this.id = id;
        this.name = name;
        this.fileBase = fileBase;
    }

    public static LiquorName of (
            Long id,
            String name,
            FileBase fileBase
    ) {
        return new LiquorName(
                id,
                name,
                fileBase
        );
    }
}
