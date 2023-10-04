package com.sulsul.suldaksuldak.repo.file;

import com.sulsul.suldaksuldak.domain.file.FileBase;
import com.sulsul.suldaksuldak.domain.file.QFileBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface FileBaseRepository extends
        JpaRepository<FileBase, String>,
        FileBaseRepositoryCustom,
        QuerydslPredicateExecutor<FileBase>,
        QuerydslBinderCustomizer<QFileBase>
{
    @Override
    default void customize(QuerydslBindings bindings, QFileBase root) {
        bindings.excludeUnlistedProperties(true);
    }
}
