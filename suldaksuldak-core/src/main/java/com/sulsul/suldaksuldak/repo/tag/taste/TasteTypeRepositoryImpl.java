package com.sulsul.suldaksuldak.repo.tag.taste;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TasteTypeRepositoryImpl implements TasteTypeRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
}
