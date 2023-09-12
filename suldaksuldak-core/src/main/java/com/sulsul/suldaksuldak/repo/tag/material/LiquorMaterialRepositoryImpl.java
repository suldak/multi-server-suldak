package com.sulsul.suldaksuldak.repo.tag.material;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LiquorMaterialRepositoryImpl implements LiquorMaterialRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
}
