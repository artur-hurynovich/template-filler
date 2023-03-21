package com.example.hurynovich.template_filler.validator.model;

import java.util.List;

import static com.example.hurynovich.template_filler.validator.model.ValidationResultType.FAILURE;
import static com.example.hurynovich.template_filler.validator.model.ValidationResultType.SUCCESS;
import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;

public record ValidationResult(ValidationResultType type, List<String> errors) {

    public static ValidationResult success() {
        return new ValidationResult(SUCCESS, emptyList());
    }

    public static ValidationResult failure(final List<String> errors) {
        return new ValidationResult(FAILURE, unmodifiableList(errors));
    }
}
