package com.example.hurynovich.template_filler.service.impl;

import com.example.hurynovich.template_filler.converter.PlaceholderKeyServiceConverter;
import com.example.hurynovich.template_filler.dto.PlaceholderKeyDto;
import com.example.hurynovich.template_filler.dto.TemplateDto;
import com.example.hurynovich.template_filler.entity.PlaceholderKeyEntity;
import com.example.hurynovich.template_filler.repository.PlaceholderKeyRepository;
import com.example.hurynovich.template_filler.service.PlaceholderKeyExtractor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BasePlaceholderKeyServiceTest {

    private static final Long TEMPLATE_ID = 1603L;
    private static final String TEMPLATE_NAME = "test template name";
    private static final String TEMPLATE_PAYLOAD = "test template payload";

    private static final Long PLACEHOLDER_KEY_ID_1 = 1612L;
    private static final Long PLACEHOLDER_KEY_ID_2 = 1613L;
    private static final String PLACEHOLDER_KEY_1 = "test placeholder key 1";
    private static final String PLACEHOLDER_KEY_2 = "test placeholder key 2";

    @Mock
    private PlaceholderKeyServiceConverter converter;

    @Mock
    private PlaceholderKeyRepository repository;

    @Mock
    private PlaceholderKeyExtractor extractor;

    @InjectMocks
    private BasePlaceholderKeyService service;

    @Test
    void given_templateDto_when_extractPlaceholderKeysAndSave_then_savePlaceholderKeyEntities() {
        final var templateDto = new TemplateDto(TEMPLATE_ID, TEMPLATE_NAME, TEMPLATE_PAYLOAD);
        final var placeholderKeyEntity1 = new PlaceholderKeyEntity(PLACEHOLDER_KEY_1, TEMPLATE_ID);
        final var placeholderKeyEntity2 = new PlaceholderKeyEntity(PLACEHOLDER_KEY_2, TEMPLATE_ID);
        final var placeholderKeyEntities = of(placeholderKeyEntity1, placeholderKeyEntity2);
        when(extractor.extract(TEMPLATE_PAYLOAD)).thenReturn(of(PLACEHOLDER_KEY_1, PLACEHOLDER_KEY_2));
        when(converter.convert(PLACEHOLDER_KEY_1, TEMPLATE_ID)).thenReturn(placeholderKeyEntity1);
        when(converter.convert(PLACEHOLDER_KEY_2, TEMPLATE_ID)).thenReturn(placeholderKeyEntity2);
        when(repository.saveAll(placeholderKeyEntities)).thenReturn(placeholderKeyEntities);

        service.extractPlaceholderKeysAndSave(templateDto);
    }

    @Test
    void given_templateId_when_findAllByTemplateId_then_returnPlaceholderKeyDtoList() {
        final var placeholderKeyEntity1 = new PlaceholderKeyEntity(PLACEHOLDER_KEY_1, TEMPLATE_ID);
        final var placeholderKeyEntity2 = new PlaceholderKeyEntity(PLACEHOLDER_KEY_2, TEMPLATE_ID);
        final var placeholderKeyEntities = of(placeholderKeyEntity1, placeholderKeyEntity2);
        final var placeholderKeyDto1 = new PlaceholderKeyDto(PLACEHOLDER_KEY_ID_1, PLACEHOLDER_KEY_1, TEMPLATE_ID);
        final var placeholderKeyDto2 = new PlaceholderKeyDto(PLACEHOLDER_KEY_ID_2, PLACEHOLDER_KEY_2, TEMPLATE_ID);
        final var expectedPlaceholderKeyDtoList = of(placeholderKeyDto1, placeholderKeyDto2);
        when(repository.findAllByTemplateId(TEMPLATE_ID)).thenReturn(placeholderKeyEntities);
        when(converter.convert(placeholderKeyEntity1)).thenReturn(placeholderKeyDto1);
        when(converter.convert(placeholderKeyEntity2)).thenReturn(placeholderKeyDto2);

        final var actualPlaceholderKeyDtoList = service.findAllByTemplateId(TEMPLATE_ID);

        assertEquals(expectedPlaceholderKeyDtoList, actualPlaceholderKeyDtoList);
    }

    @Test
    void given_templateId_when_deleteAllByTemplateId_then_delete() {
        doNothing()
                .when(repository)
                .deleteAllByTemplateId(TEMPLATE_ID);

        service.deleteAllByTemplateId(TEMPLATE_ID);
    }
}
