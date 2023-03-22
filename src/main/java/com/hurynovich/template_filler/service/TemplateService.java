package com.hurynovich.template_filler.service;

import com.hurynovich.template_filler.dto.TemplateDto;

import java.util.List;

public interface TemplateService {

    TemplateDto save(TemplateDto templateDto);

    TemplateDto findById(Long id);

    List<TemplateDto> findAll();

    void deleteById(Long id);

    boolean existsByName(String name);

    boolean existsByNameAndNotId(String name, Long id);
}
