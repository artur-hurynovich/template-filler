package com.example.hurynovich.template_filler.converter.impl;

import com.example.hurynovich.template_filler.converter.TemplateServiceConverter;
import com.example.hurynovich.template_filler.dto.TemplateDto;
import com.example.hurynovich.template_filler.entity.TemplateEntity;
import org.springframework.stereotype.Service;

@Service
public class BaseTemplateServiceConverter implements TemplateServiceConverter {

    @Override
    public TemplateEntity convert(final TemplateDto source) {
        return new TemplateEntity(source.id(), source.name(), source.payload());
    }

    @Override
    public TemplateDto convert(final TemplateEntity source) {
        return new TemplateDto(source.getId(), source.getName(), source.getPayload());
    }
}
