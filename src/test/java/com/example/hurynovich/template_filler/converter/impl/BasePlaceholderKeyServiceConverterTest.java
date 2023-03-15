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

    private final PlaceholderKeyServiceConverter converter = new BasePlaceholderKeyServiceConverter();

    @Test
    void given_placeholderKey_when_convert_then_returnPlaceholderKeyEntity() {
        final var expectedPlaceholderKeyEntity = new PlaceholderKeyEntity();
        expectedPlaceholderKeyEntity.setPlaceholderKey(PLACEHOLDER_KEY);

        final var actualPlaceholderKeyEntity = converter.convert(PLACEHOLDER_KEY);

        assertThat(actualPlaceholderKeyEntity)
                .usingRecursiveComparison()
                .isEqualTo(expectedPlaceholderKeyEntity);
    }

    @Test
    void given_placeholderKeyEntity_when_convert_then_returnPlaceholderKeyDto() {
        final var placeholderKeyEntity = new PlaceholderKeyEntity();
        placeholderKeyEntity.setId(ID);
        placeholderKeyEntity.setPlaceholderKey(PLACEHOLDER_KEY);
        final var expectedPlaceholderKeyDto = new PlaceholderKeyDto(ID, PLACEHOLDER_KEY);

        final var actualPlaceholderKeyDto = converter.convert(placeholderKeyEntity);

        assertEquals(expectedPlaceholderKeyDto, actualPlaceholderKeyDto);
    }
}
