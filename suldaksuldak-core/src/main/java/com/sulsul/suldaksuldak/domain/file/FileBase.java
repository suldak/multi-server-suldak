package com.sulsul.suldaksuldak.domain.file;

import com.sulsul.suldaksuldak.domain.BaseEntity;
import com.sulsul.suldaksuldak.domain.liquor.Liquor;
import com.sulsul.suldaksuldak.domain.party.Party;
import com.sulsul.suldaksuldak.domain.tag.LiquorMaterial;
import com.sulsul.suldaksuldak.domain.tag.LiquorName;
import com.sulsul.suldaksuldak.domain.tag.LiquorSnack;
import com.sulsul.suldaksuldak.domain.user.User;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@ToString
@Table(
        name = "tb_file_base"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "fileBase")
public class FileBase extends BaseEntity {
    @Id
    private String fileNm;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String fileLocation;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String oriFileNm;

    @Column(nullable = false, columnDefinition = "VARCHAR(50)")
    private String fileExt;

    @ToString.Exclude
    @OneToOne(mappedBy = "fileBase")
    private User user;

    @ToString.Exclude
    @OneToOne(mappedBy = "fileBase")
    private LiquorName liquorName;

    @ToString.Exclude
    @OneToOne(mappedBy = "fileBase")
    private Party party;

    @ToString.Exclude
    @OneToOne(mappedBy = "fileBase")
    private Liquor liquor;

    @ToString.Exclude
    @OneToOne(mappedBy = "fileBase")
    private LiquorSnack liquorSnack;

    @ToString.Exclude
    @OneToOne(mappedBy = "fileBase")
    private LiquorMaterial liquorMaterial;

    protected FileBase() {}

    protected FileBase(
            String fileNm,
            String fileLocation,
            String oriFileNm,
            String fileExt
    ) {
        this.fileNm = fileNm;
        this.fileLocation = fileLocation;
        this.oriFileNm = oriFileNm;
        this.fileExt = fileExt;
    }

    public static FileBase of (
            String fileNm,
            String fileLocation,
            String oriFileNm,
            String fileExt
    ) {
        return new FileBase(
                fileNm,
                fileLocation,
                oriFileNm,
                fileExt
        );
    }
}
