package com.hurynovich.template_filler.controller;

import com.hurynovich.template_filler.controller.exception.ControllerValidationException;
import com.hurynovich.template_filler.converter.TemplateFillerApiConverter;
import com.hurynovich.template_filler.request.TemplateFillerRequest;
import com.hurynovich.template_filler.response.TemplateFillerResponse;
import com.hurynovich.template_filler.service.TemplateFiller;
import com.hurynovich.template_filler.validator.Validator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/template-filler")
public class TemplateFillerController {

    private final Validator<TemplateFillerRequest> validator;

    private final TemplateFiller filler;

    private final TemplateFillerApiConverter converter;

    public TemplateFillerController(final Validator<TemplateFillerRequest> validator, final TemplateFiller filler,
                                    final TemplateFillerApiConverter converter) {
        this.validator = validator;
        this.filler = filler;
        this.converter = converter;
    }

    @PostMapping
    public TemplateFillerResponse fillTemplate(@RequestBody final TemplateFillerRequest request) {
        final var validationResult = validator.validate(request);
        if (validationResult.isSuccess()) {
            return converter.convert(filler.fill(request.templateId(), request.placeholders()));
        } else {
            throw new ControllerValidationException(validationResult.formatErrors());
        }
    }
}
