package com.hurynovich.template_filler.service.impl;

import com.hurynovich.template_filler.converter.TemplateEventConverter;
import com.hurynovich.template_filler.converter.TemplateServiceConverter;
import com.hurynovich.template_filler.dao.TemplateDao;
import com.hurynovich.template_filler.dto.CreateTemplateEventDto;
import com.hurynovich.template_filler.dto.DeleteTemplateEventDto;
import com.hurynovich.template_filler.dto.TemplateDto;
import com.hurynovich.template_filler.dto.UpdateTemplateEventDto;
import com.hurynovich.template_filler.entity.PlaceholderKeyEntity;
import com.hurynovich.template_filler.entity.TemplateEntity;
import com.hurynovich.template_filler.service.PlaceholderKeyExtractor;
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
class BaseTemplateEventHandlerTest {

    private static final Long TEMPLATE_ID = 1623L;
    private static final String TEMPLATE_NAME = "test name";
    private static final String TEMPLATE_PAYLOAD = "test payload";

    private static final String PLACEHOLDER_KEY_1 = "test placeholder key 1";
    private static final String PLACEHOLDER_KEY_2 = "test placeholder key 2";

    @Mock
    private PlaceholderKeyExtractor placeholderKeyExtractor;

    @Mock
    private TemplateEventConverter templateEventConverter;

    @Mock
    private TemplateDao templateDao;

    @Mock
    private TemplateServiceConverter templateConverter;

    @InjectMocks
    private BaseTemplateEventHandler handler;

    @Test
    void given_createTemplateEventDto_when_handle_then_returnTemplateDto() {
        final var createTemplateEventDto = new CreateTemplateEventDto(null, TEMPLATE_ID, TEMPLATE_NAME, TEMPLATE_PAYLOAD);
        final var placeholderKeys = of(PLACEHOLDER_KEY_1, PLACEHOLDER_KEY_2);
        final var templateEntity = generateTemplateEntity(null);
        final var persistedTemplateEntity = generateTemplateEntity(TEMPLATE_ID);
        final var persistedTemplateDto = new TemplateDto(TEMPLATE_ID, TEMPLATE_NAME, TEMPLATE_PAYLOAD);
        when(placeholderKeyExtractor.extract(TEMPLATE_PAYLOAD)).thenReturn(placeholderKeys);
        when(templateEventConverter.convert(createTemplateEventDto, placeholderKeys)).thenReturn(templateEntity);
        when(templateDao.save(templateEntity)).thenReturn(persistedTemplateEntity);
        when(templateConverter.convert(persistedTemplateEntity)).thenReturn(persistedTemplateDto);

        final var actualTemplateDto = handler.handle(createTemplateEventDto);

        assertEquals(persistedTemplateDto, actualTemplateDto);
    }

    @Test
    void given_updateTemplateEventDto_when_handle_then_returnTemplateDto() {
        final var updateTemplateEventDto = new UpdateTemplateEventDto(null, TEMPLATE_ID, TEMPLATE_NAME, TEMPLATE_PAYLOAD);
        final var placeholderKeys = of(PLACEHOLDER_KEY_1, PLACEHOLDER_KEY_2);
        final var templateEntity = generateTemplateEntity(TEMPLATE_ID);
        final var persistedTemplateEntity = generateTemplateEntity(TEMPLATE_ID);
        final var persistedTemplateDto = new TemplateDto(TEMPLATE_ID, TEMPLATE_NAME, TEMPLATE_PAYLOAD);
        when(placeholderKeyExtractor.extract(TEMPLATE_PAYLOAD)).thenReturn(placeholderKeys);
        when(templateEventConverter.convert(updateTemplateEventDto, placeholderKeys)).thenReturn(templateEntity);
        when(templateDao.save(templateEntity)).thenReturn(persistedTemplateEntity);
        when(templateConverter.convert(persistedTemplateEntity)).thenReturn(persistedTemplateDto);

        final var actualTemplateDto = handler.handle(updateTemplateEventDto);

        assertEquals(persistedTemplateDto, actualTemplateDto);
    }

    @Test
    void given_deleteTemplateEventDto_when_handle_then_deleteById() {
        final var deleteTemplateEventDto = new DeleteTemplateEventDto(null, TEMPLATE_ID);
        doNothing()
                .when(templateDao)
                .deleteById(TEMPLATE_ID);

        handler.handle(deleteTemplateEventDto);
    }

    private TemplateEntity generateTemplateEntity(final Long id) {
        final var placeholderKeyEntity1 = new PlaceholderKeyEntity();
        placeholderKeyEntity1.setPlaceholderKey(PLACEHOLDER_KEY_1);
        final var placeholderKeyEntity2 = new PlaceholderKeyEntity();
        placeholderKeyEntity2.setPlaceholderKey(PLACEHOLDER_KEY_2);

        final var templateEntity1 = new TemplateEntity();
        templateEntity1.setId(id);
        templateEntity1.setName(TEMPLATE_NAME);
        templateEntity1.setPayload(TEMPLATE_PAYLOAD);
        templateEntity1.addPlaceholderKey(placeholderKeyEntity1);
        templateEntity1.addPlaceholderKey(placeholderKeyEntity2);

        return templateEntity1;
    }
}
