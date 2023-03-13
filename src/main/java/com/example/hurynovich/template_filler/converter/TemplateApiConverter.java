package com.example.hurynovich.template_filler.converter;

import com.example.hurynovich.template_filler.dto.TemplateDto;
import com.example.hurynovich.template_filler.request.CreateTemplateRequest;
import com.example.hurynovich.template_filler.request.UpdateTemplateRequest;
import com.example.hurynovich.template_filler.response.TemplateResponse;

public interface TemplateApiConverter {

    TemplateDto convert(CreateTemplateRequest source);

    TemplateDto convert(UpdateTemplateRequest source);

    TemplateResponse convert(TemplateDto source);
}
