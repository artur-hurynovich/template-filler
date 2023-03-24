package com.hurynovich.template_filler.converter.impl;

import com.hurynovich.template_filler.converter.TemplateApiConverter;
import com.hurynovich.template_filler.dto.TemplateDto;
import com.hurynovich.template_filler.response.TemplateResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BaseTemplateApiConverterTest {

    private static final Long ID = 1414L;
    private static final String NAME = "test name";
    private static final String PAYLOAD = "test payload";

    private final TemplateApiConverter converter = new BaseTemplateApiConverter();

    @Test
    void given_templateDto_when_convert_then_returnTemplateResponse() {
        final var templateDto = new TemplateDto(ID, NAME, PAYLOAD);
        final var expectedTemplateResponse = new TemplateResponse(ID, NAME, PAYLOAD);

        final var actualTemplateResponse = converter.convert(templateDto);

        assertEquals(expectedTemplateResponse, actualTemplateResponse);
    }
}
