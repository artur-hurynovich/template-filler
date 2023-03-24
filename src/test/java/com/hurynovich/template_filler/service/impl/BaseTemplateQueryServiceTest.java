package com.hurynovich.template_filler.service.impl;

import com.hurynovich.template_filler.converter.TemplateServiceConverter;
import com.hurynovich.template_filler.dao.TemplateDao;
import com.hurynovich.template_filler.dto.TemplateDto;
import com.hurynovich.template_filler.entity.PlaceholderKeyEntity;
import com.hurynovich.template_filler.entity.TemplateEntity;
import com.hurynovich.template_filler.service.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BaseTemplateQueryServiceTest {

    private static final Long ID_1 = 1623L;
    private static final String NAME_1 = "test name 1";
    private static final String PAYLOAD_1 = "test payload 1";

    private static final Long ID_2 = 1624L;
    private static final String NAME_2 = "test name 2";
    private static final String PAYLOAD_2 = "test payload 2";

    private static final String PLACEHOLDER_KEY_1 = "test placeholder key 1";
    private static final String PLACEHOLDER_KEY_2 = "test placeholder key 2";

    private static final String NAME_PATTERN = "test name";

    private static final String TEMPLATE_NOT_FOUND_EXCEPTION_MSG = "template with 'id'=[1623] not found";

    @Mock
    private TemplateServiceConverter converter;

    @Mock
    private TemplateDao dao;

    @InjectMocks
    private BaseTemplateQueryService service;

    @Test
    void given_id_when_findById_then_returnTemplateDto() {
        final var templateEntity = generateTemplateEntity1();
        final var templateDto = new TemplateDto(ID_1, NAME_1, PAYLOAD_1);
        when(dao.findById(ID_1)).thenReturn(Optional.of(templateEntity));
        when(converter.convert(templateEntity)).thenReturn(templateDto);

        final var actualTemplateDto = service.findById(ID_1);

        assertEquals(templateDto, actualTemplateDto);
    }

    @Test
    void given_id_when_findById_then_throwNotFoundException() {
        when(dao.findById(ID_1)).thenReturn(empty());

        final var actualException = assertThrows(NotFoundException.class, () -> service.findById(ID_1));

        assertEquals(TEMPLATE_NOT_FOUND_EXCEPTION_MSG, actualException.getMessage());
    }

    @Test
    void when_findAll_then_returnTemplateDtoList() {
        final var templateEntity1 = generateTemplateEntity1();
        final var templateEntity2 = generateTemplateEntity2();
        final var templateDto1 = new TemplateDto(ID_1, NAME_1, PAYLOAD_1);
        final var templateDto2 = new TemplateDto(ID_2, NAME_2, PAYLOAD_2);
        final var expectedTemplateDtoList = List.of(templateDto1, templateDto2);
        when(dao.findAll()).thenReturn(List.of(templateEntity1, templateEntity2));
        when(converter.convert(templateEntity1)).thenReturn(templateDto1);
        when(converter.convert(templateEntity2)).thenReturn(templateDto2);

        final var actualTemplateDtoList = service.findAll();

        assertEquals(actualTemplateDtoList, expectedTemplateDtoList);
    }

    @Test
    void given_name_when_existsByName_then_returnTrue() {
        when(dao.existsByName(NAME_1)).thenReturn(true);

        final var actualExistsByName = service.existsByName(NAME_1);

        assertTrue(actualExistsByName);
    }

    @Test
    void given_nameAndId_when_existsByNameAndNotId_then_returnFalse() {
        when(dao.existsByNameAndNotId(NAME_1, ID_1)).thenReturn(false);

        final var actualExistsByNameAndNotId = service.existsByNameAndNotId(NAME_1, ID_1);

        assertFalse(actualExistsByNameAndNotId);
    }

    @Test
    void when_findAllByNamePattern_then_returnTemplateDtoList() {
        final var templateEntity1 = generateTemplateEntity1();
        final var templateEntity2 = generateTemplateEntity2();
        final var templateDto1 = new TemplateDto(ID_1, NAME_1, PAYLOAD_1);
        final var templateDto2 = new TemplateDto(ID_2, NAME_2, PAYLOAD_2);
        final var expectedTemplateDtoList = List.of(templateDto1, templateDto2);
        when(dao.findAllByNameContaining(NAME_PATTERN)).thenReturn(List.of(templateEntity1, templateEntity2));
        when(converter.convert(templateEntity1)).thenReturn(templateDto1);
        when(converter.convert(templateEntity2)).thenReturn(templateDto2);

        final var actualTemplateDtoList = service.findAllByNamePattern(NAME_PATTERN);

        assertEquals(actualTemplateDtoList, expectedTemplateDtoList);
    }

    private TemplateEntity generateTemplateEntity1() {
        final var placeholderKeyEntity1 = new PlaceholderKeyEntity();
        placeholderKeyEntity1.setPlaceholderKey(PLACEHOLDER_KEY_1);
        final var placeholderKeyEntity2 = new PlaceholderKeyEntity();
        placeholderKeyEntity2.setPlaceholderKey(PLACEHOLDER_KEY_2);

        final var templateEntity1 = new TemplateEntity();
        templateEntity1.setId(ID_1);
        templateEntity1.setName(NAME_1);
        templateEntity1.setPayload(PAYLOAD_1);
        templateEntity1.addPlaceholderKey(placeholderKeyEntity1);
        templateEntity1.addPlaceholderKey(placeholderKeyEntity2);

        return templateEntity1;
    }

    private TemplateEntity generateTemplateEntity2() {
        final var placeholderKeyEntity1 = new PlaceholderKeyEntity();
        placeholderKeyEntity1.setPlaceholderKey(PLACEHOLDER_KEY_1);
        final var placeholderKeyEntity2 = new PlaceholderKeyEntity();
        placeholderKeyEntity2.setPlaceholderKey(PLACEHOLDER_KEY_2);

        final var templateEntity1 = new TemplateEntity();
        templateEntity1.setId(ID_1);
        templateEntity1.setName(NAME_1);
        templateEntity1.setPayload(PAYLOAD_1);
        templateEntity1.addPlaceholderKey(placeholderKeyEntity1);
        templateEntity1.addPlaceholderKey(placeholderKeyEntity2);

        return templateEntity1;
    }
}
