package com.hurynovich.template_filler.dao;

import com.hurynovich.template_filler.entity.TemplateEntity;

import java.util.List;
import java.util.Optional;

public interface TemplateDao {

    Long getNextId();

    TemplateEntity save(TemplateEntity template);

    void deleteById(Long id);

    Optional<TemplateEntity> findById(Long id);

    List<TemplateEntity> findAll();

    boolean existsByName(String name);

    boolean existsByNameAndNotId(String name, Long id);

    List<TemplateEntity> findAllByNameContaining(String namePattern);
}
