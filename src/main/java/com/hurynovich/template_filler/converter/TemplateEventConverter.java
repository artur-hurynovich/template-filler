package com.hurynovich.template_filler.converter;

import com.hurynovich.template_filler.dto.CreateTemplateEventDto;
import com.hurynovich.template_filler.dto.DeleteTemplateEventDto;
import com.hurynovich.template_filler.dto.UpdateTemplateEventDto;
import com.hurynovich.template_filler.entity.TemplateEntity;
import com.hurynovich.template_filler.entity.TemplateEventEntity;

import java.util.List;

public interface TemplateEventConverter {

    CreateTemplateEventDto convertToCreateTemplateEvent(TemplateEventEntity templateEvent);

    UpdateTemplateEventDto convertToUpdateTemplateEvent(TemplateEventEntity templateEvent);

    DeleteTemplateEventDto convertToDeleteTemplateEvent(TemplateEventEntity templateEvent);

    TemplateEntity convert(CreateTemplateEventDto templateEvent, List<String> placeholderKeys);

    TemplateEntity convert(UpdateTemplateEventDto templateEvent, List<String> placeholderKeys);
}
