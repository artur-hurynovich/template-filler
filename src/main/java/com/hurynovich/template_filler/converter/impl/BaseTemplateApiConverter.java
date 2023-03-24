package com.hurynovich.template_filler.converter.impl;

import com.hurynovich.template_filler.converter.TemplateApiConverter;
import com.hurynovich.template_filler.dto.TemplateDto;
import com.hurynovich.template_filler.response.TemplateResponse;
import org.springframework.stereotype.Service;

@Service
public class BaseTemplateApiConverter implements TemplateApiConverter {

    @Override
    public TemplateResponse convert(final TemplateDto source) {
        return new TemplateResponse(source.id(), source.name(), source.payload());
    }
}
