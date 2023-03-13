package com.example.hurynovich.template_filler.converter.impl;

import com.example.hurynovich.template_filler.dto.TemplateDto;
import com.example.hurynovich.template_filler.entity.TemplateEntity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BaseTemplateServiceConverterTest {

    private static final Long ID = 1426L;
    private static final String NAME = "test name";
    private static final String PAYLOAD = "test payload";

    private final BaseTemplateServiceConverter converter = new BaseTemplateServiceConverter();

    @Test
    void given_templateDto_when_convert_then_returnTemplateEntity() {
        final var templateDto = new TemplateDto(ID, NAME, PAYLOAD);
        final var expectedTemplateEntity = new TemplateEntity(ID, NAME, PAYLOAD);

        final var actualTemplateEntity = converter.convert(templateDto);

        assertThat(actualTemplateEntity)
                .usingRecursiveComparison()
                .isEqualTo(expectedTemplateEntity);
    }

    @Test
    void given_templateEntity_when_convert_then_returnTemplateDto() {
        final var templateEntity = new TemplateEntity(ID, NAME, PAYLOAD);
        final var expectedTemplateDto = new TemplateDto(ID, NAME, PAYLOAD);

        final var actualTemplateDto = converter.convert(templateEntity);

        assertThat(actualTemplateDto)
                .usingRecursiveComparison()
                .isEqualTo(expectedTemplateDto);
    }
}
