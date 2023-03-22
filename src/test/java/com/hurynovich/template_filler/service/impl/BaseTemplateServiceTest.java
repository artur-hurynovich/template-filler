package com.hurynovich.template_filler.service.impl;

import com.hurynovich.template_filler.converter.TemplateServiceConverter;
import com.hurynovich.template_filler.dto.TemplateDto;
import com.hurynovich.template_filler.entity.PlaceholderKeyEntity;
import com.hurynovich.template_filler.entity.TemplateEntity;
import com.hurynovich.template_filler.repository.TemplateRepository;
import com.hurynovich.template_filler.service.PlaceholderKeyExtractor;
import com.hurynovich.template_filler.service.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BaseTemplateServiceTest {

    private static final Long ID_1 = 1623L;
    private static final String NAME_1 = "test name 1";
    private static final String PAYLOAD_1 = "test payload 1";

    private static final Long ID_2 = 1624L;
    private static final String NAME_2 = "test name 2";
    private static final String PAYLOAD_2 = "test payload 2";

    private static final String PLACEHOLDER_KEY_1 = "test placeholder key 1";
    private static final String PLACEHOLDER_KEY_2 = "test placeholder key 2";

    private static final String TEMPLATE_NOT_FOUND_EXCEPTION_MSG = "template with 'id'=[1623] not found";

    @Mock
    private TemplateServiceConverter converter;

    @Mock
    private TemplateRepository repository;

    @Mock
    private PlaceholderKeyExtractor extractor;

    @InjectMocks
    private BaseTemplateService service;

    @Test
    void given_templateDto_when_save_then_returnTemplateDto() {
        final var placeholderKeys = List.of(PLACEHOLDER_KEY_1, PLACEHOLDER_KEY_2);
        final var templateDto = new TemplateDto(null, NAME_1, PAYLOAD_1);
        final var templateEntity = generateTemplateEntity1();
        final var persistedTemplateDto = new TemplateDto(ID_1, NAME_1, PAYLOAD_1);
        when(extractor.extract(PAYLOAD_1)).thenReturn(placeholderKeys);
        when(converter.convert(templateDto, placeholderKeys)).thenReturn(templateEntity);
        when(repository.save(templateEntity)).thenReturn(templateEntity);
        when(converter.convert(templateEntity)).thenReturn(persistedTemplateDto);

        final var actualTemplateDto = service.save(templateDto);

        assertEquals(persistedTemplateDto, actualTemplateDto);
    }

    @Test
    void given_id_when_findById_then_returnTemplateDto() {
        final var templateEntity = generateTemplateEntity1();
        final var templateDto = new TemplateDto(ID_1, NAME_1, PAYLOAD_1);
        when(repository.findById(ID_1)).thenReturn(Optional.of(templateEntity));
        when(converter.convert(templateEntity)).thenReturn(templateDto);

        final var actualTemplateDto = service.findById(ID_1);

        assertEquals(templateDto, actualTemplateDto);
    }

    @Test
    void given_id_when_findById_then_throwNotFoundException() {
        when(repository.findById(ID_1)).thenReturn(Optional.empty());

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
        when(repository.findAll()).thenReturn(List.of(templateEntity1, templateEntity2));
        when(converter.convert(templateEntity1)).thenReturn(templateDto1);
        when(converter.convert(templateEntity2)).thenReturn(templateDto2);

        final var actualTemplateDtoList = service.findAll();

        assertEquals(actualTemplateDtoList, expectedTemplateDtoList);
    }

    @Test
    void given_id_when_deleteById_then_delete() {
        doNothing()
                .when(repository)
                .deleteById(ID_1);

        service.deleteById(ID_1);
    }

    @Test
    void given_name_when_existsByName_then_returnTrue() {
        when(repository.existsByName(NAME_1)).thenReturn(true);

        final var actualExistsByName = service.existsByName(NAME_1);

        assertTrue(actualExistsByName);
    }

    @Test
    void given_nameAndId_when_existsByNameAndNotId_then_returnFalse() {
        when(repository.existsByNameAndIdNot(NAME_1, ID_1)).thenReturn(false);

        final var actualExistsByNameAndNotId = service.existsByNameAndNotId(NAME_1, ID_1);

        assertFalse(actualExistsByNameAndNotId);
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
