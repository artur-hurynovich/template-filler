package com.hurynovich.template_filler.service;

import com.hurynovich.template_filler.dto.CreateTemplateEventDto;
import com.hurynovich.template_filler.dto.DeleteTemplateEventDto;
import com.hurynovich.template_filler.dto.TemplateDto;
import com.hurynovich.template_filler.dto.UpdateTemplateEventDto;

public interface TemplateEventHandler {

    TemplateDto handle(CreateTemplateEventDto templateEvent);

    TemplateDto handle(UpdateTemplateEventDto templateEvent);

    void handle(DeleteTemplateEventDto templateEvent);
}
