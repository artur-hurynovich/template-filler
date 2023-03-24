package com.hurynovich.template_filler.service;

import com.hurynovich.template_filler.command.CreateTemplateCommand;
import com.hurynovich.template_filler.command.DeleteTemplateCommand;
import com.hurynovich.template_filler.command.UpdateTemplateCommand;
import com.hurynovich.template_filler.dto.CreateTemplateEventDto;
import com.hurynovich.template_filler.dto.DeleteTemplateEventDto;
import com.hurynovich.template_filler.dto.UpdateTemplateEventDto;

public interface TemplateCommandHandler {

    CreateTemplateEventDto handle(CreateTemplateCommand command);

    UpdateTemplateEventDto handle(UpdateTemplateCommand command);

    DeleteTemplateEventDto handle(DeleteTemplateCommand command);
}
