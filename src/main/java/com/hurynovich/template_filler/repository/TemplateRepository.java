package com.hurynovich.template_filler.repository;

import com.hurynovich.template_filler.entity.TemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemplateRepository extends JpaRepository<TemplateEntity, Long> {

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

    List<TemplateEntity> findAllByNameContaining(String name);
}
