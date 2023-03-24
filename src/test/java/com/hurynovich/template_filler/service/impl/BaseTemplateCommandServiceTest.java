package com.hurynovich.template_filler.service.impl;

import com.hurynovich.template_filler.command.CreateTemplateCommand;
import com.hurynovich.template_filler.command.DeleteTemplateCommand;
import com.hurynovich.template_filler.command.UpdateTemplateCommand;
import com.hurynovich.template_filler.dto.CreateTemplateEventDto;
import com.hurynovich.template_filler.dto.DeleteTemplateEventDto;
import com.hurynovich.template_filler.dto.TemplateDto;
import com.hurynovich.template_filler.dto.UpdateTemplateEventDto;
import com.hurynovich.template_filler.service.TemplateCommandHandler;
import com.hurynovich.template_filler.service.TemplateEventHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BaseTemplateCommandServiceTest {

    private static final Long TEMPLATE_ID = 2108L;
    private static final String TEMPLATE_NAME = "test name";
    private static final String TEMPLATE_PAYLOAD = "test payload";

    @Mock
    private TemplateCommandHandler templateCommandHandler;

    @Mock
    private TemplateEventHandler templateEventHandler;

    @InjectMocks
    private BaseTemplateCommandService service;

    @Test
    void given_createTemplateCommand_when_create_then_returnTemplateDto() {
        final var createTemplateCommand = new CreateTemplateCommand(TEMPLATE_NAME, TEMPLATE_PAYLOAD);
        final var createTemplateEventDto = new CreateTemplateEventDto(null, TEMPLATE_ID, TEMPLATE_NAME, TEMPLATE_PAYLOAD);
        final var templateDto = new TemplateDto(TEMPLATE_ID, TEMPLATE_NAME, TEMPLATE_PAYLOAD);
        when(templateCommandHandler.handle(createTemplateCommand)).thenReturn(createTemplateEventDto);
        when(templateEventHandler.handle(createTemplateEventDto)).thenReturn(templateDto);

        final var actualTemplateDto = service.create(createTemplateCommand);

        assertEquals(templateDto, actualTemplateDto);
    }

    @Test
    void given_updateTemplateCommand_when_update_then_returnTemplateDto() {
        final var updateTemplateCommand = new UpdateTemplateCommand(TEMPLATE_ID, TEMPLATE_NAME, TEMPLATE_PAYLOAD);
        final var updateTemplateEventDto = new UpdateTemplateEventDto(null, TEMPLATE_ID, TEMPLATE_NAME, TEMPLATE_PAYLOAD);
        final var templateDto = new TemplateDto(TEMPLATE_ID, TEMPLATE_NAME, TEMPLATE_PAYLOAD);
        when(templateCommandHandler.handle(updateTemplateCommand)).thenReturn(updateTemplateEventDto);
        when(templateEventHandler.handle(updateTemplateEventDto)).thenReturn(templateDto);

        final var actualTemplateDto = service.update(updateTemplateCommand);

        assertEquals(templateDto, actualTemplateDto);
    }

    @Test
    void given_deleteTemplateCommand_when_delete_then_delete() {
        final var deleteTemplateCommand = new DeleteTemplateCommand(TEMPLATE_ID);
        final var deleteTemplateEventDto = new DeleteTemplateEventDto(null, TEMPLATE_ID);
        when(templateCommandHandler.handle(deleteTemplateCommand)).thenReturn(deleteTemplateEventDto);
        doNothing()
                .when(templateEventHandler)
                .handle(deleteTemplateEventDto);

        service.delete(deleteTemplateCommand);
    }
}
