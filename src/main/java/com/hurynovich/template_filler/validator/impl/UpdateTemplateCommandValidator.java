package com.hurynovich.template_filler.validator.impl;

import com.hurynovich.template_filler.command.UpdateTemplateCommand;
import com.hurynovich.template_filler.service.TemplateQueryService;
import com.hurynovich.template_filler.validator.Validator;
import com.hurynovich.template_filler.validator.model.ValidationResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
public class UpdateTemplateCommandValidator extends AbstractValidator implements Validator<UpdateTemplateCommand> {

    private static final String NON_VALID_ID_MSG = "'id' can't be null";
    private static final String NON_VALID_NAME_MSG = "'name' can't be null, empty or blank";
    private static final String NON_VALID_PAYLOAD_MSG = "'payload' can't be null, empty or blank";
    private static final String NAME_DUPLICATE_MSG = "template with 'name'=[%s] already exists";

    private final TemplateQueryService service;

    public UpdateTemplateCommandValidator(final TemplateQueryService service) {
        this.service = service;
    }

    @Override
    public ValidationResult validate(final UpdateTemplateCommand command) {
        final var errors = new ArrayList<String>();
        final var templateName = command.templateName();
        if (isBlank(templateName)) {
            errors.add(NON_VALID_NAME_MSG);
        }

        if (isBlank(command.templatePayload())) {
            errors.add(NON_VALID_PAYLOAD_MSG);
        }

        final var id = command.templateId();
        if (id == null) {
            errors.add(NON_VALID_ID_MSG);
        } else {
            if (isNotBlank(templateName) && service.existsByNameAndNotId(templateName, id)) {
                errors.add(format(NAME_DUPLICATE_MSG, templateName));
            }
        }

        return buildValidationResult(errors);
    }
}
