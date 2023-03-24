package com.hurynovich.template_filler.service;

import com.hurynovich.template_filler.command.CreateTemplateCommand;
import com.hurynovich.template_filler.command.DeleteTemplateCommand;
import com.hurynovich.template_filler.command.UpdateTemplateCommand;
import com.hurynovich.template_filler.dto.TemplateDto;

public interface TemplateCommandService {

    TemplateDto create(CreateTemplateCommand command);

    TemplateDto update(UpdateTemplateCommand command);

    void delete(DeleteTemplateCommand command);
}
