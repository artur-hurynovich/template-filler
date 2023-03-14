package com.example.hurynovich.template_filler.service.impl;

import com.example.hurynovich.template_filler.converter.TemplateServiceConverter;
import com.example.hurynovich.template_filler.dto.TemplateDto;
import com.example.hurynovich.template_filler.repository.TemplateRepository;
import com.example.hurynovich.template_filler.service.PlaceholderKeyService;
import com.example.hurynovich.template_filler.service.TemplateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BaseTemplateService implements TemplateService {

    private final TemplateServiceConverter converter;

    private final TemplateRepository repository;

    private final PlaceholderKeyService placeholderKeyService;

    public BaseTemplateService(final TemplateServiceConverter converter,
            final TemplateRepository repository,
            final PlaceholderKeyService placeholderKeyService) {
        this.converter = converter;
        this.repository = repository;
        this.placeholderKeyService = placeholderKeyService;
    }

    @Override
    public TemplateDto save(final TemplateDto templateDto) {
        final Long id = templateDto.id();
        if (id != null) {
            placeholderKeyService.deleteAllByTemplateId(id);
        }

        final var persistedTemplateDto = converter.convert(repository.save(converter.convert(templateDto)));

        placeholderKeyService.extractPlaceholderKeysAndSave(persistedTemplateDto);

        return persistedTemplateDto;
    }

    @Override
    @Transactional(readOnly = true)
    public TemplateDto findById(final Long id) {
        return repository
                .findById(id)
                .map(converter::convert)
                .orElseThrow();
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
        placeholderKeyService.deleteAllByTemplateId(id);

        repository.deleteById(id);
    }
}
