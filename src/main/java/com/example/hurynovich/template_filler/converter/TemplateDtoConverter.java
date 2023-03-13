package com.example.hurynovich.template_filler.converter;

import com.example.hurynovich.template_filler.dto.TemplateDto;
import com.example.hurynovich.template_filler.entity.TemplateEntity;

public interface TemplateDtoConverter {

    TemplateEntity convert(TemplateDto source);

    TemplateDto convert(TemplateEntity source);
}
