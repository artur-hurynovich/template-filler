package com.example.hurynovich.template_filler.service.impl;

import com.example.hurynovich.template_filler.converter.TemplateServiceConverter;
import com.example.hurynovich.template_filler.dto.TemplateDto;
import com.example.hurynovich.template_filler.entity.TemplateEntity;
import com.example.hurynovich.template_filler.repository.TemplateRepository;
import com.example.hurynovich.template_filler.service.PlaceholderKeyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BaseTemplateServiceTest {

    private static final Long ID_1 = 1623L;
    private static final String TEMPLATE_NAME_1 = "test template name 1";
    private static final String TEMPLATE_PAYLOAD_1 = "test template payload 1";

    private static final Long ID_2 = 1624L;
    private static final String TEMPLATE_NAME_2 = "test template name 2";
    private static final String TEMPLATE_PAYLOAD_2 = "test template payload 2";

    @Mock
    private TemplateServiceConverter converter;

    @Mock
    private TemplateRepository repository;

    @Mock
    private PlaceholderKeyService placeholderKeyService;

    @InjectMocks
    private BaseTemplateService service;

    @Test
    void given_templateDto_when_save_then_returnTemplateDto() {
        final var templateDto = new TemplateDto(ID_1, TEMPLATE_NAME_1, TEMPLATE_PAYLOAD_1);
        final var templateEntity = new TemplateEntity(ID_1, TEMPLATE_NAME_1, TEMPLATE_PAYLOAD_1);
        when(converter.convert(templateDto)).thenReturn(templateEntity);
        when(repository.save(templateEntity)).thenReturn(templateEntity);
        when(converter.convert(templateEntity)).thenReturn(templateDto);
        doNothing()
                .when(placeholderKeyService)
                .extractPlaceholderKeysAndSave(templateDto);

        final var actualTemplateDto = service.save(templateDto);

        assertEquals(templateDto, actualTemplateDto);
    }

    @Test
    void given_id_when_findById_then_returnTemplateDto() {
        final var templateEntity = new TemplateEntity(ID_1, TEMPLATE_NAME_1, TEMPLATE_PAYLOAD_1);
        final var templateDto = new TemplateDto(ID_1, TEMPLATE_NAME_1, TEMPLATE_PAYLOAD_1);
        when(repository.findById(ID_1)).thenReturn(Optional.of(templateEntity));
        when(converter.convert(templateEntity)).thenReturn(templateDto);

        final var actualTemplateDto = service.findById(ID_1);

        assertEquals(templateDto, actualTemplateDto);
    }

    @Test
    void when_findAll_then_returnTemplateDtoList() {
        final var templateEntity1 = new TemplateEntity(ID_1, TEMPLATE_NAME_1, TEMPLATE_PAYLOAD_1);
        final var templateEntity2 = new TemplateEntity(ID_2, TEMPLATE_NAME_2, TEMPLATE_PAYLOAD_2);
        final var templateDto1 = new TemplateDto(ID_1, TEMPLATE_NAME_1, TEMPLATE_PAYLOAD_1);
        final var templateDto2 = new TemplateDto(ID_2, TEMPLATE_NAME_2, TEMPLATE_PAYLOAD_2);
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
        doNothing()
                .when(placeholderKeyService)
                .deleteAllByTemplateId(ID_1);

        service.deleteById(ID_1);
    }
}
