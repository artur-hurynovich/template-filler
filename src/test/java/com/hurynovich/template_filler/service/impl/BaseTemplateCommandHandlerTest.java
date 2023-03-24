package com.hurynovich.template_filler.service.impl;

import com.hurynovich.template_filler.command.CreateTemplateCommand;
import com.hurynovich.template_filler.command.DeleteTemplateCommand;
import com.hurynovich.template_filler.command.UpdateTemplateCommand;
import com.hurynovich.template_filler.converter.TemplateCommandConverter;
import com.hurynovich.template_filler.converter.TemplateEventConverter;
import com.hurynovich.template_filler.dao.TemplateDao;
import com.hurynovich.template_filler.dao.TemplateEventDao;
import com.hurynovich.template_filler.dto.CreateTemplateEventDto;
import com.hurynovich.template_filler.dto.DeleteTemplateEventDto;
import com.hurynovich.template_filler.dto.UpdateTemplateEventDto;
import com.hurynovich.template_filler.entity.TemplateEventEntity;
import com.hurynovich.template_filler.entity.TemplateEventType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static com.hurynovich.template_filler.entity.TemplateEventType.CREATE;
import static com.hurynovich.template_filler.entity.TemplateEventType.DELETE;
import static com.hurynovich.template_filler.entity.TemplateEventType.UPDATE;
import static java.time.Clock.systemUTC;
import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BaseTemplateCommandHandlerTest {

    private static final Long ID = 2123L;

    private static final Long TEMPLATE_ID = 2128L;
    private static final String TEMPLATE_NAME = "test name";
    private static final String TEMPLATE_PAYLOAD = "test payload";

    @Mock
    private TemplateCommandConverter templateCommandConverter;

    @Mock
    private TemplateDao templateDao;

    @Mock
    private TemplateEventDao templateEventDao;

    @Mock
    private TemplateEventConverter templateEventConverter;

    @InjectMocks
    private BaseTemplateCommandHandler handler;

    @Test
    void given_createTemplateCommand_when_handle_then_returnCreateTemplateEventDto() {
        final var createTemplateCommand = new CreateTemplateCommand(TEMPLATE_NAME, TEMPLATE_PAYLOAD);
        final var templateEventEntity = generateTemplateEventEntity(null, CREATE);
        final var persistedTemplateEventEntity = generateTemplateEventEntity(ID, CREATE);
        final var createTemplateEventDto = new CreateTemplateEventDto(ID, TEMPLATE_ID, TEMPLATE_NAME, TEMPLATE_PAYLOAD);
        when(templateDao.getNextId()).thenReturn(TEMPLATE_ID);
        when(templateCommandConverter.convert(eq(TEMPLATE_ID), eq(createTemplateCommand), any(LocalDateTime.class))).thenReturn(templateEventEntity);
        when(templateEventDao.save(templateEventEntity)).thenReturn(persistedTemplateEventEntity);
        when(templateEventConverter.convertToCreateTemplateEvent(persistedTemplateEventEntity)).thenReturn(createTemplateEventDto);

        final var actualCreateTemplateEventDto = handler.handle(createTemplateCommand);

        assertEquals(createTemplateEventDto, actualCreateTemplateEventDto);
    }

    @Test
    void given_updateTemplateCommand_when_handle_then_returnUpdateTemplateEventDto() {
        final var updateTemplateCommand = new UpdateTemplateCommand(TEMPLATE_ID, TEMPLATE_NAME, TEMPLATE_PAYLOAD);
        final var templateEventEntity = generateTemplateEventEntity(null, UPDATE);
        final var persistedTemplateEventEntity = generateTemplateEventEntity(ID, UPDATE);
        final var updateTemplateEventDto = new UpdateTemplateEventDto(ID, TEMPLATE_ID, TEMPLATE_NAME, TEMPLATE_PAYLOAD);
        when(templateCommandConverter.convert(eq(updateTemplateCommand), any(LocalDateTime.class))).thenReturn(templateEventEntity);
        when(templateEventDao.save(templateEventEntity)).thenReturn(persistedTemplateEventEntity);
        when(templateEventConverter.convertToUpdateTemplateEvent(persistedTemplateEventEntity)).thenReturn(updateTemplateEventDto);

        final var actualUpdateTemplateEventDto = handler.handle(updateTemplateCommand);

        assertEquals(updateTemplateEventDto, actualUpdateTemplateEventDto);
    }

    @Test
    void given_deleteTemplateCommand_when_handle_then_returnDeleteTemplateEventDto() {
        final var deleteTemplateCommand = new DeleteTemplateCommand(TEMPLATE_ID);
        final var templateEventEntity = generateTemplateEventEntity(null, DELETE);
        final var persistedTemplateEventEntity = generateTemplateEventEntity(ID, DELETE);
        final var deleteTemplateEventDto = new DeleteTemplateEventDto(ID, TEMPLATE_ID);
        when(templateCommandConverter.convert(eq(deleteTemplateCommand), any(LocalDateTime.class))).thenReturn(templateEventEntity);
        when(templateEventDao.save(templateEventEntity)).thenReturn(persistedTemplateEventEntity);
        when(templateEventConverter.convertToDeleteTemplateEvent(persistedTemplateEventEntity)).thenReturn(deleteTemplateEventDto);

        final var actualDeleteTemplateEventDto = handler.handle(deleteTemplateCommand);

        assertEquals(deleteTemplateEventDto, actualDeleteTemplateEventDto);
    }

    private TemplateEventEntity generateTemplateEventEntity(final Long id, final TemplateEventType type) {
        final var templateEventEntity = new TemplateEventEntity();
        templateEventEntity.setId(id);
        templateEventEntity.setType(type);
        templateEventEntity.setTemplateId(TEMPLATE_ID);
        templateEventEntity.setTemplateName(TEMPLATE_NAME);
        templateEventEntity.setTemplatePayload(TEMPLATE_PAYLOAD);
        templateEventEntity.setDateTime(now(systemUTC()));

        return templateEventEntity;
    }
}
