package com.sulsul.suldaksuldak.repo.file;

import com.sulsul.suldaksuldak.dto.file.FileBaseDto;

import java.util.Optional;

public interface FileBaseRepositoryCustom {
    Optional<FileBaseDto> findByPriKey(String priKey);
}
