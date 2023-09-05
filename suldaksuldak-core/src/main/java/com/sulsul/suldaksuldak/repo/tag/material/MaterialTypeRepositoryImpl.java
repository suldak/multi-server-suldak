package com.sulsul.suldaksuldak.repo.tag.material;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MaterialTypeRepositoryImpl implements MaterialTypeRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
}
