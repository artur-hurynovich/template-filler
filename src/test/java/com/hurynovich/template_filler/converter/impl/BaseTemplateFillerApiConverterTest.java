package com.hurynovich.template_filler.converter.impl;

import com.hurynovich.template_filler.converter.TemplateFillerApiConverter;
import com.hurynovich.template_filler.response.TemplateFillerResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BaseTemplateFillerApiConverterTest {

    private static final String FILLED_TEMPLATE = "test filled template";

    private final TemplateFillerApiConverter converter = new BaseTemplateFillerApiConverter();

    @Test
    void given_filledTemplate_when_convert_then_returnTemplateFillerResponse() {
        final var expectedTemplateFillerResponse = new TemplateFillerResponse(FILLED_TEMPLATE);

        final var actualTemplateFillerResponse = converter.convert(FILLED_TEMPLATE);

        assertEquals(expectedTemplateFillerResponse, actualTemplateFillerResponse);
    }
}
