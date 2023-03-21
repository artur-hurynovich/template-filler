package com.hurynovich.template_filler.validator;

import com.hurynovich.template_filler.validator.model.ValidationResult;

public interface Validator<T> {

    ValidationResult validate(T t);
}
