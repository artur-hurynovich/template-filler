package com.example.hurynovich.template_filler.converter.impl;

import com.example.hurynovich.template_filler.converter.TemplateFillerResponseConverter;
import com.example.hurynovich.template_filler.response.TemplateFillerResponse;
import org.springframework.stereotype.Service;

@Service
public class BaseTemplateFillerResponseConverter implements TemplateFillerResponseConverter {

    @Override
    public TemplateFillerResponse convert(final String filledTemplate) {
        return new TemplateFillerResponse(filledTemplate);
    }
}
