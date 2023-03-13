package com.example.hurynovich.template_filler.converter.impl;

import com.example.hurynovich.template_filler.converter.PlaceholderKeyServiceConverter;
import com.example.hurynovich.template_filler.dto.PlaceholderKeyDto;
import com.example.hurynovich.template_filler.entity.PlaceholderKeyEntity;
import org.springframework.stereotype.Service;

@Service
public class BasePlaceholderKeyServiceConverter implements PlaceholderKeyServiceConverter {

    @Override
    public PlaceholderKeyEntity convert(final String placeholderKey, final Long templateId) {
        return new PlaceholderKeyEntity(placeholderKey, templateId);
    }

    @Override
    public PlaceholderKeyDto convert(final PlaceholderKeyEntity source) {
        return new PlaceholderKeyDto(source.getId(), source.getPlaceholderKey(), source.getTemplateId());
    }
}
