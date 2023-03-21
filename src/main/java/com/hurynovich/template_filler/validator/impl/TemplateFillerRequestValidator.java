package com.hurynovich.template_filler.validator.impl;

import com.hurynovich.template_filler.request.TemplateFillerRequest;
import com.hurynovich.template_filler.validator.Validator;
import com.hurynovich.template_filler.validator.model.ValidationResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TemplateFillerRequestValidator extends AbstractValidator implements Validator<TemplateFillerRequest> {

    private static final String NON_VALID_TEMPLATE_ID_MSG = "'templateId' can't be null";

    @Override
    public ValidationResult validate(final TemplateFillerRequest request) {
        final var errors = new ArrayList<String>();
        if (request.templateId() == null) {
            errors.add(NON_VALID_TEMPLATE_ID_MSG);
        }

        return buildValidationResult(errors);
    }
}
