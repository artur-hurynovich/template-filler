package com.hurynovich.template_filler.converter.impl;

import com.hurynovich.template_filler.converter.PlaceholderKeyServiceConverter;
import com.hurynovich.template_filler.converter.TemplateServiceConverter;
import com.hurynovich.template_filler.dto.TemplateDto;
import com.hurynovich.template_filler.entity.TemplateEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseTemplateServiceConverter implements TemplateServiceConverter {

    private final PlaceholderKeyServiceConverter placeholderKeyServiceConverter;

    private BaseTemplateServiceConverter(final PlaceholderKeyServiceConverter placeholderKeyServiceConverter) {
        this.placeholderKeyServiceConverter = placeholderKeyServiceConverter;
    }

    @Override
    public TemplateEntity convert(final TemplateDto source, final List<String> placeholderKeys) {
        final var templateEntity = new TemplateEntity();
        templateEntity.setId(source.id());
        templateEntity.setName(source.name());
        templateEntity.setPayload(source.payload());
        placeholderKeys
                .stream()
                .map(placeholderKeyServiceConverter::convert)
                .forEach(templateEntity::addPlaceholderKey);

        return templateEntity;
    }

    @Override
    public TemplateDto convert(final TemplateEntity source) {
        return new TemplateDto(source.getId(), source.getName(), source.getPayload());
    }
}
