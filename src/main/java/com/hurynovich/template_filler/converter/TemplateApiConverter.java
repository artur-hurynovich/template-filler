package com.hurynovich.template_filler.converter;

import com.hurynovich.template_filler.dto.TemplateDto;
import com.hurynovich.template_filler.request.CreateTemplateRequest;
import com.hurynovich.template_filler.request.UpdateTemplateRequest;
import com.hurynovich.template_filler.response.TemplateResponse;

public interface TemplateApiConverter {

    TemplateDto convert(CreateTemplateRequest source);

    TemplateDto convert(UpdateTemplateRequest source);

    TemplateResponse convert(TemplateDto source);
}
