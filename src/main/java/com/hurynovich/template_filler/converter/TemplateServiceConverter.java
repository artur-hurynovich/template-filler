package com.hurynovich.template_filler.converter;

import com.hurynovich.template_filler.dto.TemplateDto;
import com.hurynovich.template_filler.entity.TemplateEntity;

import java.util.List;

public interface TemplateServiceConverter {

    TemplateEntity convert(TemplateDto source, List<String> placeholderKeys);

    TemplateDto convert(TemplateEntity source);
}
