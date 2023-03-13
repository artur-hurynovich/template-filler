package com.example.hurynovich.template_filler.service.impl;

import com.example.hurynovich.template_filler.converter.PlaceholderKeyServiceConverter;
import com.example.hurynovich.template_filler.dto.PlaceholderKeyDto;
import com.example.hurynovich.template_filler.dto.TemplateDto;
import com.example.hurynovich.template_filler.repository.PlaceholderKeyRepository;
import com.example.hurynovich.template_filler.service.PlaceholderKeyExtractor;
import com.example.hurynovich.template_filler.service.PlaceholderKeyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BasePlaceholderKeyService implements PlaceholderKeyService {

    private final PlaceholderKeyServiceConverter converter;

    private final PlaceholderKeyRepository repository;

    private final PlaceholderKeyExtractor extractor;

    public BasePlaceholderKeyService(final PlaceholderKeyServiceConverter converter,
            final PlaceholderKeyRepository repository,
            final PlaceholderKeyExtractor extractor) {
        this.converter = converter;
        this.repository = repository;
        this.extractor = extractor;
    }

    @Override
    public void extractPlaceholderKeysAndSave(final TemplateDto templateDto) {
        final var placeholderKeyEntities = extractor
                .extract(templateDto.payload())
                .stream()
                .map(placeholderKey -> converter.convert(placeholderKey, templateDto.id()))
                .toList();

        repository.saveAll(placeholderKeyEntities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlaceholderKeyDto> findAllByTemplateId(final Long templateId) {
        return repository
                .findAllByTemplateId(templateId)
                .stream()
                .map(converter::convert)
                .toList();
    }

    @Override
    public void deleteAllByTemplateId(final Long templateId) {
        repository.deleteAllByTemplateId(templateId);
    }
}
