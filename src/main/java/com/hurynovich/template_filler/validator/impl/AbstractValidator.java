package com.hurynovich.template_filler.validator.impl;

import com.hurynovich.template_filler.validator.model.ValidationResult;

import java.util.List;

public abstract class AbstractValidator {

    protected ValidationResult buildValidationResult(final List<String> errors) {
        final ValidationResult validationResult;
        if (errors.isEmpty()) {
            validationResult = ValidationResult.success();
        } else {
            validationResult = ValidationResult.failure(errors);
        }

        return validationResult;
    }
}
