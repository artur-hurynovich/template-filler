package com.example.hurynovich.template_filler.service.impl;

import com.example.hurynovich.template_filler.converter.TemplateServiceConverter;
import com.example.hurynovich.template_filler.dto.TemplateDto;
import com.example.hurynovich.template_filler.repository.TemplateRepository;
import com.example.hurynovich.template_filler.service.PlaceholderKeyExtractor;
import com.example.hurynovich.template_filler.service.TemplateService;
import com.example.hurynovich.template_filler.service.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.String.format;

@Service
@Transactional
public class BaseTemplateService implements TemplateService {

    private static final String TEMPLATE_NOT_FOUND_EXCEPTION_MSG = "Template with id=[%d] not found";

    private final TemplateServiceConverter converter;

    private final TemplateRepository repository;

    private final PlaceholderKeyExtractor extractor;

    public BaseTemplateService(final TemplateServiceConverter converter, final TemplateRepository repository,
                               final PlaceholderKeyExtractor extractor) {
        this.converter = converter;
        this.repository = repository;
        this.extractor = extractor;
    }

    @Override
    public TemplateDto save(final TemplateDto templateDto) {
        final var placeholderKeys = extractor.extract(templateDto.payload());

        return converter.convert(repository.save(converter.convert(templateDto, placeholderKeys)));
    }

    @Override
    @Transactional(readOnly = true)
    public TemplateDto findById(final Long id) {
        return repository
                .findById(id)
                .map(converter::convert)
                .orElseThrow(() -> new NotFoundException(format(TEMPLATE_NOT_FOUND_EXCEPTION_MSG, id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TemplateDto> findAll() {
        return repository
                .findAll()
                .stream()
                .map(converter::convert)
                .toList();
    }

    @Override
    public void deleteById(final Long id) {
        repository.deleteById(id);
    }
}
