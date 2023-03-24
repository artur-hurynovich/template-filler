package com.hurynovich.template_filler.validator.impl;

import com.hurynovich.template_filler.command.CreateTemplateCommand;
import com.hurynovich.template_filler.service.TemplateQueryService;
import com.hurynovich.template_filler.validator.Validator;
import com.hurynovich.template_filler.validator.model.ValidationResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class CreateTemplateCommandValidator extends AbstractValidator implements Validator<CreateTemplateCommand> {

    private static final String NON_VALID_NAME_MSG = "'name' can't be null, empty or blank";
    private static final String NON_VALID_PAYLOAD_MSG = "'payload' can't be null, empty or blank";
    private static final String NAME_DUPLICATE_MSG = "template with 'name'=[%s] already exists";

    private final TemplateQueryService service;

    public CreateTemplateCommandValidator(final TemplateQueryService service) {
        this.service = service;
    }

    @Override
    public ValidationResult validate(final CreateTemplateCommand command) {
        final var errors = new ArrayList<String>();
        final var templateName = command.templateName();
        if (isBlank(templateName)) {
            errors.add(NON_VALID_NAME_MSG);
        } else if (service.existsByName(templateName)) {
            errors.add(format(NAME_DUPLICATE_MSG, templateName));
        }

        if (isBlank(command.templatePayload())) {
            errors.add(NON_VALID_PAYLOAD_MSG);
        }

        return buildValidationResult(errors);
    }
}
