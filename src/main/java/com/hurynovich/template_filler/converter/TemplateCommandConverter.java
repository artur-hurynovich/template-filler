package com.hurynovich.template_filler.converter;

import com.hurynovich.template_filler.command.CreateTemplateCommand;
import com.hurynovich.template_filler.command.DeleteTemplateCommand;
import com.hurynovich.template_filler.command.UpdateTemplateCommand;
import com.hurynovich.template_filler.entity.TemplateEventEntity;

import java.time.LocalDateTime;

public interface TemplateCommandConverter {

    TemplateEventEntity convert(Long templateId, CreateTemplateCommand command, LocalDateTime dateTime);

    TemplateEventEntity convert(UpdateTemplateCommand command, LocalDateTime dateTime);

    TemplateEventEntity convert(DeleteTemplateCommand command, LocalDateTime dateTime);
}
