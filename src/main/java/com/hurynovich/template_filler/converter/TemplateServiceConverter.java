package com.hurynovich.template_filler.converter;

import com.hurynovich.template_filler.dto.TemplateDto;
import com.hurynovich.template_filler.entity.TemplateEntity;

public interface TemplateServiceConverter {

    TemplateDto convert(TemplateEntity source);
}
