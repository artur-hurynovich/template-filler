package com.hurynovich.template_filler.service.impl;

import com.hurynovich.template_filler.converter.PlaceholderKeyServiceConverter;
import com.hurynovich.template_filler.dto.PlaceholderKeyDto;
import com.hurynovich.template_filler.repository.PlaceholderKeyRepository;
import com.hurynovich.template_filler.service.PlaceholderKeyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BasePlaceholderKeyService implements PlaceholderKeyService {

    private final PlaceholderKeyServiceConverter converter;

    private final PlaceholderKeyRepository repository;

    public BasePlaceholderKeyService(final PlaceholderKeyServiceConverter converter,
                                     final PlaceholderKeyRepository repository) {
        this.converter = converter;
        this.repository = repository;
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
}
