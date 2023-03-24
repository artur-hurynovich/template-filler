package com.hurynovich.template_filler.service.impl;

import com.hurynovich.template_filler.converter.PlaceholderKeyServiceConverter;
import com.hurynovich.template_filler.dao.PlaceholderKeyDao;
import com.hurynovich.template_filler.dto.PlaceholderKeyDto;
import com.hurynovich.template_filler.service.PlaceholderKeyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BasePlaceholderKeyService implements PlaceholderKeyService {

    private final PlaceholderKeyServiceConverter converter;

    private final PlaceholderKeyDao dao;

    public BasePlaceholderKeyService(final PlaceholderKeyServiceConverter converter,
            final PlaceholderKeyDao dao) {
        this.converter = converter;
        this.dao = dao;
    }

    @Override
    public List<PlaceholderKeyDto> findAllByTemplateId(final Long templateId) {
        return dao
                .findAllByTemplateId(templateId)
                .stream()
                .map(converter::convert)
                .toList();
    }
}
