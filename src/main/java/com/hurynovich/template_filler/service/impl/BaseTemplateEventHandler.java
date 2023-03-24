package com.hurynovich.template_filler.service.impl;

import com.hurynovich.template_filler.converter.TemplateEventConverter;
import com.hurynovich.template_filler.converter.TemplateServiceConverter;
import com.hurynovich.template_filler.dao.TemplateDao;
import com.hurynovich.template_filler.dto.CreateTemplateEventDto;
import com.hurynovich.template_filler.dto.DeleteTemplateEventDto;
import com.hurynovich.template_filler.dto.TemplateDto;
import com.hurynovich.template_filler.dto.UpdateTemplateEventDto;
import com.hurynovich.template_filler.service.PlaceholderKeyExtractor;
import com.hurynovich.template_filler.service.TemplateEventHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BaseTemplateEventHandler implements TemplateEventHandler {

    private final PlaceholderKeyExtractor placeholderKeyExtractor;

    private final TemplateEventConverter templateEventConverter;

    private final TemplateDao templateDao;

    private final TemplateServiceConverter templateConverter;

    public BaseTemplateEventHandler(final PlaceholderKeyExtractor placeholderKeyExtractor,
            final TemplateEventConverter templateEventConverter, final TemplateDao templateDao,
            final TemplateServiceConverter templateConverter) {
        this.placeholderKeyExtractor = placeholderKeyExtractor;
        this.templateEventConverter = templateEventConverter;
        this.templateDao = templateDao;
        this.templateConverter = templateConverter;
    }

    @Override
    public TemplateDto handle(final CreateTemplateEventDto templateEvent) {
        final var placeholderKeys = placeholderKeyExtractor.extract(templateEvent.templatePayload());

        final var template = templateEventConverter.convert(templateEvent, placeholderKeys);

        final var persistedTemplate = templateDao.save(template);

        return templateConverter.convert(persistedTemplate);
    }

    @Override
    public TemplateDto handle(final UpdateTemplateEventDto templateEvent) {
        final var placeholderKeys = placeholderKeyExtractor.extract(templateEvent.templatePayload());

        final var template = templateEventConverter.convert(templateEvent, placeholderKeys);

        final var persistedTemplate = templateDao.save(template);

        return templateConverter.convert(persistedTemplate);
    }

    @Override
    public void handle(final DeleteTemplateEventDto templateEvent) {
        templateDao.deleteById(templateEvent.templateId());
    }
}
