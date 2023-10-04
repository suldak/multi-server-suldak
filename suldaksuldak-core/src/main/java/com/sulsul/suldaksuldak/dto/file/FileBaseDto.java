package com.sulsul.suldaksuldak.dto.file;

import com.sulsul.suldaksuldak.domain.file.FileBase;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class FileBaseDto {
    String fileNm;
    String fileLocation;
    String oriFileNm;
    String fileExt;
    LocalDateTime createdAt;
    LocalDateTime modifiedAt;

    public static FileBaseDto of (
            FileDto fileDto
    ) {
        return new FileBaseDto(
                fileDto.getFileNm(),
                fileDto.getFileLocation(),
                fileDto.getOriFileNm(),
                fileDto.getFileExt(),
                null,
                null
        );
    }

    public static FileBaseDto of (
            String fileNm,
            String fileLocation,
            String oriFileNm,
            String fileExt
    ) {
        return new FileBaseDto(
                fileNm,
                fileLocation,
                oriFileNm,
                fileExt,
                null,
                null
        );
    }

    public FileBase toEntity() {
        return FileBase.of(
                fileNm,
                fileLocation,
                oriFileNm,
                fileExt
        );
    }
}
