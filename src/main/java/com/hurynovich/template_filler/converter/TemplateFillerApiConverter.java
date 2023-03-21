package com.hurynovich.template_filler.converter;

import com.hurynovich.template_filler.response.TemplateFillerResponse;

public interface TemplateFillerApiConverter {

    TemplateFillerResponse convert(String filledTemplate);
}
