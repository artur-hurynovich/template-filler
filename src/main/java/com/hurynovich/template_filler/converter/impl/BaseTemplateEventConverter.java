package com.hurynovich.template_filler.converter.impl;

import com.hurynovich.template_filler.converter.PlaceholderKeyServiceConverter;
import com.hurynovich.template_filler.converter.TemplateEventConverter;
import com.hurynovich.template_filler.dto.CreateTemplateEventDto;
import com.hurynovich.template_filler.dto.DeleteTemplateEventDto;
import com.hurynovich.template_filler.dto.UpdateTemplateEventDto;
import com.hurynovich.template_filler.entity.TemplateEntity;
import com.hurynovich.template_filler.entity.TemplateEventEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseTemplateEventConverter implements TemplateEventConverter {

    private final PlaceholderKeyServiceConverter placeholderKeyConverter;

    public BaseTemplateEventConverter(final PlaceholderKeyServiceConverter placeholderKeyConverter) {
        this.placeholderKeyConverter = placeholderKeyConverter;
    }

    @Override
    public CreateTemplateEventDto convertToCreateTemplateEvent(final TemplateEventEntity templateEvent) {
        return new CreateTemplateEventDto(templateEvent.getId(), templateEvent.getTemplateId(), templateEvent.getTemplateName(), templateEvent.getTemplatePayload());
    }

    @Override
    public UpdateTemplateEventDto convertToUpdateTemplateEvent(final TemplateEventEntity templateEvent) {
        return new UpdateTemplateEventDto(templateEvent.getId(), templateEvent.getTemplateId(), templateEvent.getTemplateName(), templateEvent.getTemplatePayload());
    }

    @Override
    public DeleteTemplateEventDto convertToDeleteTemplateEvent(final TemplateEventEntity templateEvent) {
        return new DeleteTemplateEventDto(templateEvent.getId(), templateEvent.getTemplateId());
    }

    @Override
    public TemplateEntity convert(final CreateTemplateEventDto templateEvent, final List<String> placeholderKeys) {
        final var template = new TemplateEntity();
        template.setId(templateEvent.templateId());
        template.setName(templateEvent.templateName());
        template.setPayload(templateEvent.templatePayload());
        placeholderKeys
                .stream()
                .map(placeholderKeyConverter::convert)
                .forEach(template::addPlaceholderKey);

        return template;
    }

    @Override
    public TemplateEntity convert(final UpdateTemplateEventDto templateEvent, final List<String> placeholderKeys) {
        final var template = new TemplateEntity();
        template.setId(templateEvent.templateId());
        template.setName(templateEvent.templateName());
        template.setPayload(templateEvent.templatePayload());
        placeholderKeys
                .stream()
                .map(placeholderKeyConverter::convert)
                .forEach(template::addPlaceholderKey);

        return template;
    }
}
