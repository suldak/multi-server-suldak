package com.sulsul.suldaksuldak.dto.file;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.domain.file.FileBase;
import com.sulsul.suldaksuldak.exception.GeneralException;
import lombok.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

@Value
public class FileDto {
    ByteArrayResource fileByte;
    String fileLocation;
    String fileNm;
    String oriFileNm;
    String fileExt;

    public static FileDto of (
            ByteArrayResource fileByte,
            String fileLocation,
            String fileNm,
            String oriFileNm,
            String fileExt
    ) {
        return new FileDto(
                fileByte,
                fileLocation,
                fileNm,
                oriFileNm,
                fileExt
        );
    }

    public static FileDto of (
            MultipartFile file,
            String fileNm,
            String fileLocation
    ) {
        if (file == null || file.getOriginalFilename() == null || file.getOriginalFilename().isBlank())
            return null;
        try {
            return new FileDto(
                    new ByteArrayResource(file.getBytes()),
                    fileLocation,
                    fileNm,
                    file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf(".")),
                    file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."))
            );
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }

    public static FileDto of (
            FileBase fileBase
    ) {
        return new FileDto(
                null,
                fileBase.getFileLocation(),
                fileBase.getFileNm(),
                fileBase.getOriFileNm(),
                fileBase.getFileExt()
        );
    }

    public static FileDto of (
            FileBaseDto fileBase
    ) {
        return new FileDto(
                null,
                fileBase.getFileLocation(),
                fileBase.getFileNm(),
                fileBase.getOriFileNm(),
                fileBase.getFileExt()
        );
    }
}
