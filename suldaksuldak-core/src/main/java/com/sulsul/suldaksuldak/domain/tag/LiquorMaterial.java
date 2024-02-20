package com.sulsul.suldaksuldak.domain.tag;

import com.sulsul.suldaksuldak.domain.bridge.MtToLi;
import com.sulsul.suldaksuldak.domain.file.FileBase;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@ToString
@Table(
        name = "tb_liquor_material"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "liquorMaterial")
public class LiquorMaterial implements TagEntity {
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false, unique = true, length = 10)
    private String name;

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "liquorMaterial", cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<MtToLi> mtToLis = new LinkedHashSet<>();

    @OneToOne
    @Setter
    @JoinColumn(name = "file_base_nm")
    private FileBase fileBase;

    protected LiquorMaterial() {}

    protected LiquorMaterial(
            Long id,
            String name,
            FileBase fileBase
    ) {
        this.id = id;
        this.name = name;
        this.fileBase = fileBase;
    }

    public static LiquorMaterial of (
            Long id,
            String name,
            FileBase fileBase
    ) {
        return new LiquorMaterial(
                id,
                name,
                fileBase
        );
    }
}
