package com.sulsul.suldaksuldak.service.file;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.domain.file.FileBase;
import com.sulsul.suldaksuldak.dto.file.FileBaseDto;
import com.sulsul.suldaksuldak.dto.file.FileDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.file.FileBaseRepository;
import com.sulsul.suldaksuldak.tool.FileTool;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class S3FileService {
    private final AmazonS3 amazonS3;
    private final FileBaseRepository fileBaseRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public FileBase saveFile(MultipartFile file) {
        try {
            if (file == null || file.getOriginalFilename() == null || file.getOriginalFilename().isBlank())
                return null;
//            String fileNm = file.getOriginalFilename();
            String fileNm = FileTool.getTempFileNm();

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());
            amazonS3.putObject(
                    bucket,
                    fileNm,
                    file.getInputStream(),
                    metadata
            );
            String filePath =
                    amazonS3.getUrl(bucket, fileNm).toString();
            FileDto saveFile = FileDto.of(
                    file,
                    FileTool.getTempFileNm(),
                    filePath
            );
            return fileBaseRepository.save(FileBaseDto.of(saveFile).toEntity());
        } catch (GeneralException e) {
            throw new GeneralException(
                    e.getErrorCode(),
                    e.getMessage()
            );
        } catch (Exception e) {
            throw new GeneralException(
                    ErrorCode.INTERNAL_ERROR,
                    e.getMessage()
            );
        }
    }

    public UrlResource getFileDownUrl(
            String fileNm
    ) {
        try {
            Optional<FileBaseDto> fileBaseDto =
                    fileBaseRepository.findByPriKey(fileNm);
            if (fileBaseDto.isEmpty())
                throw new GeneralException(
                        ErrorCode.NOT_FOUND,
                        "해당 파일을 찾을 수 없습니다."
                );
            return new UrlResource(
                            amazonS3.getUrl(bucket, fileBaseDto.get().getFileNm())
                    );
        } catch (GeneralException e) {
            throw new GeneralException(
                    e.getErrorCode(),
                    e.getMessage()
            );
        } catch (Exception e) {
            throw new GeneralException(
                    ErrorCode.INTERNAL_ERROR,
                    e.getMessage()
            );
        }
    }

    public Boolean deleteFile(String fileNm) {
        try {
            Optional<FileBaseDto> deleteFile =
                    fileBaseRepository.findByPriKey(fileNm);
            if (deleteFile.isEmpty()) return true;
            amazonS3.deleteObject(bucket, deleteFile.get().getFileNm());
            return true;
        } catch (GeneralException e) {
            throw new GeneralException(
                    e.getErrorCode(),
                    e.getMessage()
            );
        } catch (Exception e) {
            throw new GeneralException(
                    ErrorCode.INTERNAL_ERROR,
                    e.getMessage()
            );
        }
    }
}
