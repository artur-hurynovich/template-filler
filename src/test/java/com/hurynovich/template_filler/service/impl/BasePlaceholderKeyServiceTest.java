package com.hurynovich.template_filler.service.impl;

import com.hurynovich.template_filler.converter.PlaceholderKeyServiceConverter;
import com.hurynovich.template_filler.dao.PlaceholderKeyDao;
import com.hurynovich.template_filler.dto.PlaceholderKeyDto;
import com.hurynovich.template_filler.entity.PlaceholderKeyEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BasePlaceholderKeyServiceTest {

    private static final Long TEMPLATE_ID = 1603L;

    private static final Long PLACEHOLDER_KEY_ID_1 = 1612L;
    private static final Long PLACEHOLDER_KEY_ID_2 = 1613L;
    private static final String PLACEHOLDER_KEY_1 = "test placeholder key 1";
    private static final String PLACEHOLDER_KEY_2 = "test placeholder key 2";

    @Mock
    private PlaceholderKeyServiceConverter converter;

    @Mock
    private PlaceholderKeyDao dao;

    @InjectMocks
    private BasePlaceholderKeyService service;

    @Test
    void given_templateId_when_findAllByTemplateId_then_returnPlaceholderKeyDtoList() {
        final var placeholderKeyEntity1 = new PlaceholderKeyEntity();
        placeholderKeyEntity1.setId(PLACEHOLDER_KEY_ID_1);
        placeholderKeyEntity1.setPlaceholderKey(PLACEHOLDER_KEY_1);
        final var placeholderKeyEntity2 = new PlaceholderKeyEntity();
        placeholderKeyEntity2.setId(PLACEHOLDER_KEY_ID_2);
        placeholderKeyEntity2.setPlaceholderKey(PLACEHOLDER_KEY_2);
        final var placeholderKeyEntities = of(placeholderKeyEntity1, placeholderKeyEntity2);
        final var placeholderKeyDto1 = new PlaceholderKeyDto(PLACEHOLDER_KEY_ID_1, PLACEHOLDER_KEY_1);
        final var placeholderKeyDto2 = new PlaceholderKeyDto(PLACEHOLDER_KEY_ID_2, PLACEHOLDER_KEY_2);
        final var expectedPlaceholderKeyDtoList = of(placeholderKeyDto1, placeholderKeyDto2);
        when(dao.findAllByTemplateId(TEMPLATE_ID)).thenReturn(placeholderKeyEntities);
        when(converter.convert(placeholderKeyEntity1)).thenReturn(placeholderKeyDto1);
        when(converter.convert(placeholderKeyEntity2)).thenReturn(placeholderKeyDto2);

        final var actualPlaceholderKeyDtoList = service.findAllByTemplateId(TEMPLATE_ID);

        assertEquals(expectedPlaceholderKeyDtoList, actualPlaceholderKeyDtoList);
    }
}
