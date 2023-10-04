package com.sulsul.suldaksuldak.service.file;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.domain.file.FileBase;
import com.sulsul.suldaksuldak.dto.file.FileBaseDto;
import com.sulsul.suldaksuldak.dto.file.FileDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.file.FileBaseRepository;
import com.sulsul.suldaksuldak.tool.FileTool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {
    private final FileBaseRepository fileBaseRepository;

    public FileBase saveFile(
            MultipartFile file
    ) {
        try {
            FileDto saveFile = FileDto.of(
                    file,
                    FileTool.getTempFileNm(),
                    FileTool.getSavePath()
            );
            Boolean saveResult = FileTool.saveFile(saveFile);
            if (!saveResult) throw new GeneralException(ErrorCode.INTERNAL_ERROR, "File 저장에 실패했습니다.");
            return fileBaseRepository.save(FileBaseDto.of(saveFile).toEntity());
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    public Optional<FileBaseDto> getFileNm(
            String fileNm
    ) {
        try {
            return fileBaseRepository.findByPriKey(fileNm);
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    public Boolean deleteFile(FileBase fileBase) {
        try {
            fileBaseRepository.deleteById(fileBase.getFileNm());
            FileTool.deleteFile(FileDto.of(fileBase));
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
        return true;
    }
}
