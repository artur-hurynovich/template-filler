package com.hurynovich.template_filler.converter.impl;

import com.hurynovich.template_filler.command.CreateTemplateCommand;
import com.hurynovich.template_filler.command.DeleteTemplateCommand;
import com.hurynovich.template_filler.command.UpdateTemplateCommand;
import com.hurynovich.template_filler.converter.TemplateCommandConverter;
import com.hurynovich.template_filler.entity.TemplateEventEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.hurynovich.template_filler.entity.TemplateEventType.CREATE;
import static com.hurynovich.template_filler.entity.TemplateEventType.DELETE;
import static com.hurynovich.template_filler.entity.TemplateEventType.UPDATE;
import static java.time.LocalDateTime.of;
import static org.assertj.core.api.Assertions.assertThat;

class BaseTemplateCommandConverterTest {

    private static final Long TEMPLATE_ID = 2000L;
    private static final String TEMPLATE_NAME = "test template name";
    private static final String TEMPLATE_PAYLOAD = "test template payload";
    private static final LocalDateTime DATE_TIME = of(2023, 3, 12, 20, 1, 2);

    private final TemplateCommandConverter converter = new BaseTemplateCommandConverter();

    @Test
    void given_createTemplateCommandAndDateTime_when_convert_then_returnTemplateEventEntity() {
        final var createTemplateCommand = new CreateTemplateCommand(TEMPLATE_NAME, TEMPLATE_PAYLOAD);
        final var expectedTemplateEventEntity = new TemplateEventEntity();
        expectedTemplateEventEntity.setType(CREATE);
        expectedTemplateEventEntity.setTemplateId(TEMPLATE_ID);
        expectedTemplateEventEntity.setTemplateName(TEMPLATE_NAME);
        expectedTemplateEventEntity.setTemplatePayload(TEMPLATE_PAYLOAD);
        expectedTemplateEventEntity.setDateTime(DATE_TIME);

        final var actualTemplateEventEntity = converter.convert(TEMPLATE_ID, createTemplateCommand, DATE_TIME);

        assertThat(actualTemplateEventEntity)
                .usingRecursiveComparison()
                .isEqualTo(expectedTemplateEventEntity);
    }

    @Test
    void given_updateTemplateCommandAndDateTime_when_convert_then_returnTemplateEventEntity() {
        final var updateTemplateCommand = new UpdateTemplateCommand(TEMPLATE_ID, TEMPLATE_NAME, TEMPLATE_PAYLOAD);
        final var expectedTemplateEventEntity = new TemplateEventEntity();
        expectedTemplateEventEntity.setType(UPDATE);
        expectedTemplateEventEntity.setTemplateId(TEMPLATE_ID);
        expectedTemplateEventEntity.setTemplateName(TEMPLATE_NAME);
        expectedTemplateEventEntity.setTemplatePayload(TEMPLATE_PAYLOAD);
        expectedTemplateEventEntity.setDateTime(DATE_TIME);

        final var actualTemplateEventEntity = converter.convert(updateTemplateCommand, DATE_TIME);

        assertThat(actualTemplateEventEntity)
                .usingRecursiveComparison()
                .isEqualTo(expectedTemplateEventEntity);
    }

    @Test
    void given_deleteTemplateCommandAndDateTime_when_convert_then_returnTemplateEventEntity() {
        final var deleteTemplateCommand = new DeleteTemplateCommand(TEMPLATE_ID);
        final var expectedTemplateEventEntity = new TemplateEventEntity();
        expectedTemplateEventEntity.setType(DELETE);
        expectedTemplateEventEntity.setTemplateId(TEMPLATE_ID);
        expectedTemplateEventEntity.setDateTime(DATE_TIME);

        final var actualTemplateEventEntity = converter.convert(deleteTemplateCommand, DATE_TIME);

        assertThat(actualTemplateEventEntity)
                .usingRecursiveComparison()
                .isEqualTo(expectedTemplateEventEntity);
    }
}
