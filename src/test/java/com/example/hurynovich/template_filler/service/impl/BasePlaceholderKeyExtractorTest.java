package com.example.hurynovich.template_filler.service.impl;

import com.example.hurynovich.template_filler.service.PlaceholderKeyExtractor;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BasePlaceholderKeyExtractorTest {

    private final PlaceholderKeyExtractor extractor = new BasePlaceholderKeyExtractor();

    private static Stream<Arguments> argumentsFactory() {
        return Stream.of(Arguments.of("zero{{one}} two {{three}}four", List.of("one", "three")),
                Arguments.of("zero{{one {{two {{three}}four", List.of("one {{two {{three")),
                Arguments.of("zero{{one_two three}}four", List.of("one_two three")),
                Arguments.of("zero{{one two {{three{{four", emptyList()));
    }

    @ParameterizedTest
    @MethodSource("argumentsFactory")
    void given_templatePayload_when_extract_then_returnPlaceholderKeys(final String templatePayload,
                                                                       final List<String> expectedPlaceholderKeys) {
        final var actualPlaceholderKeys = extractor.extract(templatePayload);

        assertEquals(expectedPlaceholderKeys, actualPlaceholderKeys);
    }
}
