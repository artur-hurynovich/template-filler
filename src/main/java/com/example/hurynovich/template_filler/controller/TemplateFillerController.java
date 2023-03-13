package com.example.hurynovich.template_filler.controller;

import com.example.hurynovich.template_filler.converter.TemplateFillerApiConverter;
import com.example.hurynovich.template_filler.request.TemplateFillerRequest;
import com.example.hurynovich.template_filler.response.TemplateFillerResponse;
import com.example.hurynovich.template_filler.service.TemplateFiller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/template-filler")
public class TemplateFillerController {

    private final TemplateFiller filler;

    private final TemplateFillerApiConverter converter;

    public TemplateFillerController(final TemplateFiller filler,
            final TemplateFillerApiConverter converter) {
        this.filler = filler;
        this.converter = converter;
    }

    @PostMapping
    public TemplateFillerResponse fillTemplate(@RequestBody final TemplateFillerRequest request) {
        return converter.convert(filler.fill(request.templateId(), request.placeholders()));
    }
}
