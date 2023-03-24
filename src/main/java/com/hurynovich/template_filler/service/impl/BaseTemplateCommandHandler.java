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
import com.hurynovich.template_filler.service.TemplateCommandHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static java.time.Clock.systemUTC;
import static java.time.LocalDateTime.now;

@Service
@Transactional
public class BaseTemplateCommandHandler implements TemplateCommandHandler {

    private final TemplateCommandConverter templateCommandConverter;

    private final TemplateDao templateDao;

    private final TemplateEventDao templateEventDao;

    private final TemplateEventConverter templateEventConverter;

    public BaseTemplateCommandHandler(final TemplateCommandConverter templateCommandConverter,
            final TemplateDao templateDao, final TemplateEventDao templateEventDao,
            final TemplateEventConverter templateEventConverter) {
        this.templateCommandConverter = templateCommandConverter;
        this.templateDao = templateDao;
        this.templateEventDao = templateEventDao;
        this.templateEventConverter = templateEventConverter;
    }

    @Override
    public CreateTemplateEventDto handle(final CreateTemplateCommand command) {
        final var templateId = templateDao.getNextId();
        final var templateEvent = templateCommandConverter.convert(templateId, command, currentDateTime());

        final var persistedTemplateEvent = templateEventDao.save(templateEvent);

        return templateEventConverter.convertToCreateTemplateEvent(persistedTemplateEvent);
    }

    private LocalDateTime currentDateTime() {
        return now(systemUTC());
    }

    @Override
    public UpdateTemplateEventDto handle(final UpdateTemplateCommand command) {
        final var templateEvent = templateCommandConverter.convert(command, currentDateTime());

        final var persistedTemplateEvent = templateEventDao.save(templateEvent);

        return templateEventConverter.convertToUpdateTemplateEvent(persistedTemplateEvent);
    }

    @Override
    public DeleteTemplateEventDto handle(final DeleteTemplateCommand command) {
        final var templateEvent = templateCommandConverter.convert(command, currentDateTime());

        final var persistedTemplateEvent = templateEventDao.save(templateEvent);

        return templateEventConverter.convertToDeleteTemplateEvent(persistedTemplateEvent);
    }
}
