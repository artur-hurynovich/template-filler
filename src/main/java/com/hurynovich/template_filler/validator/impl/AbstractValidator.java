package com.hurynovich.template_filler.validator.impl;

import com.hurynovich.template_filler.validator.model.ValidationResult;

import java.util.List;

import static com.hurynovich.template_filler.validator.model.ValidationResult.failure;
import static com.hurynovich.template_filler.validator.model.ValidationResult.success;

public abstract class AbstractValidator {

    protected ValidationResult buildValidationResult(final List<String> errors) {
        final ValidationResult validationResult;
        if (errors.isEmpty()) {
            validationResult = success();
        } else {
            validationResult = failure(errors);
        }

        return validationResult;
    }
}
