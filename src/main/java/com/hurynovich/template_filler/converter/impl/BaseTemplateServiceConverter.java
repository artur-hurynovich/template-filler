package com.hurynovich.template_filler.converter.impl;

import com.hurynovich.template_filler.converter.TemplateServiceConverter;
import com.hurynovich.template_filler.dto.TemplateDto;
import com.hurynovich.template_filler.entity.TemplateEntity;
import org.springframework.stereotype.Service;

@Service
public class BaseTemplateServiceConverter implements TemplateServiceConverter {

    @Override
    public TemplateDto convert(final TemplateEntity source) {
        return new TemplateDto(source.getId(), source.getName(), source.getPayload());
    }
}
