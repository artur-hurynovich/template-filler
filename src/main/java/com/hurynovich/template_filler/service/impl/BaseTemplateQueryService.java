package com.hurynovich.template_filler.service.impl;

import com.hurynovich.template_filler.converter.TemplateServiceConverter;
import com.hurynovich.template_filler.dao.TemplateDao;
import com.hurynovich.template_filler.dto.TemplateDto;
import com.hurynovich.template_filler.service.TemplateQueryService;
import com.hurynovich.template_filler.service.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.String.format;

@Service
@Transactional(readOnly = true)
public class BaseTemplateQueryService implements TemplateQueryService {

    private static final String TEMPLATE_NOT_FOUND_EXCEPTION_MSG = "template with 'id'=[%d] not found";

    private final TemplateServiceConverter converter;

    private final TemplateDao dao;

    public BaseTemplateQueryService(final TemplateServiceConverter converter,
            final TemplateDao dao) {
        this.converter = converter;
        this.dao = dao;
    }

    @Override
    public TemplateDto findById(final Long id) {
        return dao
                .findById(id)
                .map(converter::convert)
                .orElseThrow(() -> new NotFoundException(format(TEMPLATE_NOT_FOUND_EXCEPTION_MSG, id)));
    }

    @Override
    public List<TemplateDto> findAll() {
        return dao
                .findAll()
                .stream()
                .map(converter::convert)
                .toList();
    }

    @Override
    public boolean existsByName(final String name) {
        return dao.existsByName(name);
    }

    @Override
    public boolean existsByNameAndNotId(final String name, final Long id) {
        return dao.existsByNameAndNotId(name, id);
    }

    @Override
    public List<TemplateDto> findAllByNamePattern(final String namePattern) {
        return dao
                .findAllByNameContaining(namePattern)
                .stream()
                .map(converter::convert)
                .toList();
    }
}
