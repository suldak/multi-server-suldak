package com.sulsul.suldaksuldak.repo.file;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sulsul.suldaksuldak.dto.file.FileBaseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.sulsul.suldaksuldak.domain.file.QFileBase.fileBase;

@Repository
@RequiredArgsConstructor
public class FileBaseRepositoryImpl implements FileBaseRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<FileBaseDto> findByPriKey(String priKey) {
        return Optional.ofNullable(
                getFileBaseDtoQuery()
                        .from(fileBase)
                        .where(fileBase.fileNm.eq(priKey))
                        .fetchFirst()
        );
    }

    private JPAQuery<FileBaseDto> getFileBaseDtoQuery() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                FileBaseDto.class,
                                fileBase.fileNm,
                                fileBase.fileLocation,
                                fileBase.oriFileNm,
                                fileBase.fileExt,
                                fileBase.createdAt,
                                fileBase.modifiedAt
                        )
                );
    }
}
