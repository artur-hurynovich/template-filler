package com.example.hurynovich.template_filler.converter.impl;

import com.example.hurynovich.template_filler.converter.TemplateApiConverter;
import com.example.hurynovich.template_filler.dto.TemplateDto;
import com.example.hurynovich.template_filler.request.CreateTemplateRequest;
import com.example.hurynovich.template_filler.request.UpdateTemplateRequest;
import com.example.hurynovich.template_filler.response.TemplateResponse;
import org.springframework.stereotype.Service;

@Service
public class BaseTemplateApiConverter implements TemplateApiConverter {

    @Override
    public TemplateDto convert(final CreateTemplateRequest source) {
        return new TemplateDto(null, source.getName(), source.getPayload());
    }

    @Override
    public TemplateDto convert(final UpdateTemplateRequest source) {
        return new TemplateDto(source.getId(), source.getName(), source.getPayload());
    }

    @Override
    public TemplateResponse convert(final TemplateDto source) {
        return new TemplateResponse(source.id(), source.name(), source.payload());
    }
}
