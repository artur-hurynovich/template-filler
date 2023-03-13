package com.example.hurynovich.template_filler.converter.impl;

import com.example.hurynovich.template_filler.converter.PlaceholderKeyServiceConverter;
import com.example.hurynovich.template_filler.dto.PlaceholderKeyDto;
import com.example.hurynovich.template_filler.entity.PlaceholderKeyEntity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BasePlaceholderKeyServiceConverterTest {

    private static final Long ID = 1405L;
    private static final String PLACEHOLDER_KEY = "test placeholder key";
    private static final Long TEMPLATE_ID = 1406L;

    private final PlaceholderKeyServiceConverter converter = new BasePlaceholderKeyServiceConverter();

    @Test
    void given_placeholderKeyAndTemplateId_when_convert_then_returnPlaceholderKeyEntity() {
        final var expectedPlaceholderKeyEntity = new PlaceholderKeyEntity(PLACEHOLDER_KEY, TEMPLATE_ID);

        final var actualPlaceholderKeyEntity = converter.convert(PLACEHOLDER_KEY, TEMPLATE_ID);

        assertThat(actualPlaceholderKeyEntity)
                .usingRecursiveComparison()
                .isEqualTo(expectedPlaceholderKeyEntity);
    }

    @Test
    void given_placeholderKeyEntity_when_convert_then_returnPlaceholderKeyDto() {
        final var placeholderKeyEntity = new PlaceholderKeyEntity(PLACEHOLDER_KEY, TEMPLATE_ID);
        placeholderKeyEntity.setId(ID);
        final var expectedPlaceholderKeyDto = new PlaceholderKeyDto(ID, PLACEHOLDER_KEY, TEMPLATE_ID);

        final var actualPlaceholderKeyDto = converter.convert(placeholderKeyEntity);

        assertEquals(expectedPlaceholderKeyDto, actualPlaceholderKeyDto);
    }
}
