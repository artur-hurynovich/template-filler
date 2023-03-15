package com.example.hurynovich.template_filler.converter.impl;

import com.example.hurynovich.template_filler.converter.PlaceholderKeyServiceConverter;
import com.example.hurynovich.template_filler.dto.TemplateDto;
import com.example.hurynovich.template_filler.entity.PlaceholderKeyEntity;
import com.example.hurynovich.template_filler.entity.TemplateEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BaseTemplateServiceConverterTest {

    private static final Long ID = 1426L;
    private static final String NAME = "test name";
    private static final String PAYLOAD = "test payload";

    private static final String PLACEHOLDER_KEY_1 = "test placeholder key 1";
    private static final String PLACEHOLDER_KEY_2 = "test placeholder key 2";

    @Mock
    private PlaceholderKeyServiceConverter placeholderKeyServiceConverter;

    @InjectMocks
    private BaseTemplateServiceConverter converter;

    @Test
    void given_templateDtoAndPlaceholderKeys_when_convert_then_returnTemplateEntity() {
        final var templateDto = new TemplateDto(ID, NAME, PAYLOAD);
        final var placeholderKeyEntity1 = new PlaceholderKeyEntity();
        placeholderKeyEntity1.setPlaceholderKey(PLACEHOLDER_KEY_1);
        final var placeholderKeyEntity2 = new PlaceholderKeyEntity();
        placeholderKeyEntity2.setPlaceholderKey(PLACEHOLDER_KEY_2);
        final var expectedTemplateEntity = generateTemplateEntity(placeholderKeyEntity1, placeholderKeyEntity2);
        when(placeholderKeyServiceConverter.convert(PLACEHOLDER_KEY_1)).thenReturn(placeholderKeyEntity1);
        when(placeholderKeyServiceConverter.convert(PLACEHOLDER_KEY_2)).thenReturn(placeholderKeyEntity2);

        final var actualTemplateEntity = converter.convert(templateDto, List.of(PLACEHOLDER_KEY_1, PLACEHOLDER_KEY_2));

        assertThat(actualTemplateEntity)
                .usingRecursiveComparison()
                .isEqualTo(expectedTemplateEntity);
    }

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
        Stream
                .of(placeholderKeyEntities)
                .forEach(templateEntity::addPlaceholderKey);

        return templateEntity;
    }
}
