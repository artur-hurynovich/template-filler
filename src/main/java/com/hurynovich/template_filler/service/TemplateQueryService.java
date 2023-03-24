package com.hurynovich.template_filler.service;

import com.hurynovich.template_filler.dto.TemplateDto;

import java.util.List;

public interface TemplateQueryService {

    TemplateDto findById(Long id);

    List<TemplateDto> findAll();

    boolean existsByName(String name);

    boolean existsByNameAndNotId(String name, Long id);

    List<TemplateDto> findAllByNamePattern(String namePattern);
}
