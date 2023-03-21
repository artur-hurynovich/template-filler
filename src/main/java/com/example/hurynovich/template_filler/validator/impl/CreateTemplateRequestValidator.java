package com.example.hurynovich.template_filler.validator.impl;

import com.example.hurynovich.template_filler.request.CreateTemplateRequest;
import com.example.hurynovich.template_filler.validator.Validator;
import com.example.hurynovich.template_filler.validator.model.ValidationResult;
import org.springframework.stereotype.Service;

@Service
public class CreateTemplateRequestValidator extends AbstractTemplateRequestValidator implements Validator<CreateTemplateRequest> {

    @Override
    public ValidationResult validate(final CreateTemplateRequest request) {
        final var errors = super.validate(request);

        return buildValidationResult(errors);
    }
}
