package com.example.hurynovich.template_filler.validator.impl;

import com.example.hurynovich.template_filler.request.UpdateTemplateRequest;
import com.example.hurynovich.template_filler.validator.Validator;
import com.example.hurynovich.template_filler.validator.model.ValidationResult;
import org.springframework.stereotype.Service;

@Service
public class UpdateTemplateRequestValidator extends AbstractTemplateRequestValidator implements Validator<UpdateTemplateRequest> {

    private static final String NON_VALID_ID_MSG = "'id' can't be null";

    @Override
    public ValidationResult validate(final UpdateTemplateRequest request) {
        final var errors = super.validate(request);
        if (request.getId() == null) {
            errors.add(NON_VALID_ID_MSG);
        }

        return buildValidationResult(errors);
    }
}
