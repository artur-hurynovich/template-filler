package com.example.hurynovich.template_filler.converter.impl;

import com.example.hurynovich.template_filler.converter.PlaceholderKeyApiConverter;
import com.example.hurynovich.template_filler.dto.PlaceholderKeyDto;
import com.example.hurynovich.template_filler.response.PlaceholderKeyResponse;
import org.junit.jupiter.api.Test;

import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BasePlaceholderKeyApiConverterTest {

    private static final Long ID_1 = 1356L;
    private static final Long ID_2 = 1357L;

    private static final String PLACEHOLDER_KEY_1 = "test placeholder key 1";
    private static final String PLACEHOLDER_KEY_2 = "test placeholder key 2";

    private final PlaceholderKeyApiConverter converter = new BasePlaceholderKeyApiConverter();

    @Test
    void given_placeholderKeyDtoList_when_convert_then_returnPlaceholderKeyResponse() {
        final var placeholderKeyDto1 = new PlaceholderKeyDto(ID_1, PLACEHOLDER_KEY_1);
        final var placeholderKeyDto2 = new PlaceholderKeyDto(ID_2, PLACEHOLDER_KEY_2);
        final var expectedPlaceholderKeyResponse = new PlaceholderKeyResponse(of(PLACEHOLDER_KEY_1, PLACEHOLDER_KEY_2));

        final var actualPlaceholderKeyResponse = converter.convert(of(placeholderKeyDto1, placeholderKeyDto2));

        assertEquals(expectedPlaceholderKeyResponse, actualPlaceholderKeyResponse);
    }
}
