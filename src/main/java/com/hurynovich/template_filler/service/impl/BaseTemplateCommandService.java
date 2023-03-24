package com.hurynovich.template_filler.service.impl;

import com.hurynovich.template_filler.command.CreateTemplateCommand;
import com.hurynovich.template_filler.command.DeleteTemplateCommand;
import com.hurynovich.template_filler.command.UpdateTemplateCommand;
import com.hurynovich.template_filler.dto.TemplateDto;
import com.hurynovich.template_filler.service.TemplateCommandHandler;
import com.hurynovich.template_filler.service.TemplateCommandService;
import com.hurynovich.template_filler.service.TemplateEventHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BaseTemplateCommandService implements TemplateCommandService {

    private final TemplateCommandHandler templateCommandHandler;

    private final TemplateEventHandler templateEventHandler;

    public BaseTemplateCommandService(final TemplateCommandHandler templateCommandHandler,
            final TemplateEventHandler templateEventHandler) {
        this.templateCommandHandler = templateCommandHandler;
        this.templateEventHandler = templateEventHandler;
    }

    @Override
    public TemplateDto create(final CreateTemplateCommand command) {
        final var templateEvent = templateCommandHandler.handle(command);

        return templateEventHandler.handle(templateEvent);
    }

    @Override
    public TemplateDto update(final UpdateTemplateCommand command) {
        final var templateEvent = templateCommandHandler.handle(command);

        return templateEventHandler.handle(templateEvent);
    }

    @Override
    public void delete(final DeleteTemplateCommand command) {
        final var templateEvent = templateCommandHandler.handle(command);

        templateEventHandler.handle(templateEvent);
    }
}
