package com.sulsul.suldaksuldak.controller.file;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.dto.file.FileBaseDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.service.file.FileService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/file")
@Api(tags = "[COMMON] 파일 관련 API")
public class FileController {
    private final FileService fileService;

    @GetMapping(value = "/download/{fileNm}")
    public ResponseEntity<ByteArrayResource> downloadFile(
            @PathVariable String fileNm
    ) {
        try {
            Optional<FileBaseDto> fileBaseDto = fileService.getFileNm(fileNm);
            if (fileBaseDto.isEmpty()) throw new GeneralException(ErrorCode.BAD_REQUEST, "파일을 찾지 못하였습니다.");

            Path fileFullPath = Paths.get(fileBaseDto.get().getFileLocation(), fileBaseDto.get().getFileNm());
            byte[] fileBytes = Files.readAllBytes(fileFullPath);

            String attachment = "attachment; filename=\"" +
                    URLEncoder.encode(fileBaseDto.get().getOriFileNm() + fileBaseDto.get().getFileExt(), StandardCharsets.UTF_8).replaceAll("\\+", "%20") +
                    "\";";

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, attachment);
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(new ByteArrayResource(fileBytes));
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }
}
