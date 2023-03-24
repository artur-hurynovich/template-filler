package com.hurynovich.template_filler.converter.impl;

import com.hurynovich.template_filler.command.CreateTemplateCommand;
import com.hurynovich.template_filler.command.DeleteTemplateCommand;
import com.hurynovich.template_filler.command.UpdateTemplateCommand;
import com.hurynovich.template_filler.converter.TemplateCommandConverter;
import com.hurynovich.template_filler.entity.TemplateEventEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.hurynovich.template_filler.entity.TemplateEventType.CREATE;
import static com.hurynovich.template_filler.entity.TemplateEventType.DELETE;
import static com.hurynovich.template_filler.entity.TemplateEventType.UPDATE;

@Service
public class BaseTemplateCommandConverter implements TemplateCommandConverter {

    @Override
    public TemplateEventEntity convert(final Long templateId, final CreateTemplateCommand command,
            final LocalDateTime dateTime) {
        final var templateEvent = new TemplateEventEntity();
        templateEvent.setType(CREATE);
        templateEvent.setTemplateId(templateId);
        templateEvent.setTemplateName(command.templateName());
        templateEvent.setTemplatePayload(command.templatePayload());
        templateEvent.setDateTime(dateTime);

        return templateEvent;
    }

    @Override
    public TemplateEventEntity convert(final UpdateTemplateCommand command, final LocalDateTime dateTime) {
        final var templateEvent = new TemplateEventEntity();
        templateEvent.setType(UPDATE);
        templateEvent.setTemplateId(command.templateId());
        templateEvent.setTemplateName(command.templateName());
        templateEvent.setTemplatePayload(command.templatePayload());
        templateEvent.setDateTime(dateTime);

        return templateEvent;
    }

    @Override
    public TemplateEventEntity convert(final DeleteTemplateCommand command, final LocalDateTime dateTime) {
        final var templateEvent = new TemplateEventEntity();
        templateEvent.setType(DELETE);
        templateEvent.setTemplateId(command.templateId());
        templateEvent.setDateTime(dateTime);

        return templateEvent;
    }
}
