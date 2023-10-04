package com.sulsul.suldaksuldak.tool;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.dto.file.FileDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

public class FileTool {
    private static final String userDirLocation = System.getProperty("user.dir");

    public static String getTempFileNm() {
        return UUID.randomUUID().toString().replace("-", "") + "_"
                + LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli();
    }

    public static String getPath(String fileNm) {
        Path tempPath = Paths.get(userDirLocation, "temp");
        File folder = new File(tempPath.toString());
        if (!folder.isDirectory()) {
            try {
                folder.mkdirs();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Path filePath = Paths.get(tempPath.toString(), fileNm);
        return filePath.toString();
    }

    public static String getSavePath() {
        Path tempPath = Paths.get(userDirLocation, "temp");
        File folder = new File(tempPath.toString());
        if (!folder.isDirectory()) {
            try {
                folder.mkdirs();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return tempPath.toString();
    }

    public static Boolean saveFile(
            FileDto fileDto
    ) {
        try {
            File directory = new File(fileDto.getFileLocation());
            if (!directory.exists()) {
                directory.mkdirs(); // 경로가 없다면 디렉토리 생성
            }
            File file = new File(Paths.get(fileDto.getFileLocation(), fileDto.getFileNm()).toString());
            try (FileOutputStream fos = new FileOutputStream(file)) {
                FileCopyUtils.copy(fileDto.getFileByte().getInputStream(), fos);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new GeneralException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }

    public static Boolean deleteFile(
            FileDto fileDto
    ) {
        try {
            String path = Paths.get(fileDto.getFileLocation(), fileDto.getFileNm()).toString();
            System.out.println("DELETE > " + path);
            File deleteFile = new File(path);
            deleteFile.delete();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new GeneralException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }

    public static Boolean isFile(String filePath, String fileName) {
        File file = new File(filePath, fileName);
        return file.exists();
    }
}
