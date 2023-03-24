package com.hurynovich.template_filler.converter.impl;

import com.hurynovich.template_filler.converter.TemplateServiceConverter;
import com.hurynovich.template_filler.dto.TemplateDto;
import com.hurynovich.template_filler.entity.PlaceholderKeyEntity;
import com.hurynovich.template_filler.entity.TemplateEntity;
import org.junit.jupiter.api.Test;

import static java.util.stream.Stream.of;
import static org.assertj.core.api.Assertions.assertThat;

class BaseTemplateServiceConverterTest {

    private static final Long ID = 1426L;
    private static final String NAME = "test name";
    private static final String PAYLOAD = "test payload";

    private static final String PLACEHOLDER_KEY_1 = "test placeholder key 1";
    private static final String PLACEHOLDER_KEY_2 = "test placeholder key 2";

    private final TemplateServiceConverter converter = new BaseTemplateServiceConverter();

    @Test
    void given_templateEntity_when_convert_then_returnTemplateDto() {
        final var placeholderKeyEntity1 = new PlaceholderKeyEntity();
        placeholderKeyEntity1.setPlaceholderKey(PLACEHOLDER_KEY_1);
        final var placeholderKeyEntity2 = new PlaceholderKeyEntity();
        placeholderKeyEntity2.setPlaceholderKey(PLACEHOLDER_KEY_2);
        final var templateEntity = generateTemplateEntity(placeholderKeyEntity1, placeholderKeyEntity2);
        final var expectedTemplateDto = new TemplateDto(ID, NAME, PAYLOAD);

        final var actualTemplateDto = converter.convert(templateEntity);

        assertThat(actualTemplateDto)
                .usingRecursiveComparison()
                .isEqualTo(expectedTemplateDto);
    }

    private TemplateEntity generateTemplateEntity(final PlaceholderKeyEntity... placeholderKeyEntities) {
        final var templateEntity = new TemplateEntity();
        templateEntity.setId(ID);
        templateEntity.setName(NAME);
        templateEntity.setPayload(PAYLOAD);
        of(placeholderKeyEntities).forEach(templateEntity::addPlaceholderKey);

        return templateEntity;
    }
}
