package com.hurynovich.template_filler.converter;

import com.hurynovich.template_filler.dto.TemplateDto;
import com.hurynovich.template_filler.response.TemplateResponse;

public interface TemplateApiConverter {

    TemplateResponse convert(TemplateDto source);
}
