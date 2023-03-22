package com.hurynovich.template_filler.validator.impl;

import com.hurynovich.template_filler.request.CreateTemplateRequest;
import com.hurynovich.template_filler.service.TemplateService;
import com.hurynovich.template_filler.validator.Validator;
import com.hurynovich.template_filler.validator.model.ValidationResult;
import org.springframework.stereotype.Service;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
public class CreateTemplateRequestValidator extends AbstractTemplateRequestValidator implements Validator<CreateTemplateRequest> {

    private static final String NAME_DUPLICATE_MSG = "template with 'name'=[%s] already exists";

    public CreateTemplateRequestValidator(final TemplateService service) {
        super(service);
    }

    @Override
    public ValidationResult validate(final CreateTemplateRequest request) {
        final var errors = super.validate(request);
        final var name = request.getName();
        if (isNotBlank(name) && service.existsByName(name)) {
            errors.add(format(NAME_DUPLICATE_MSG, name));
        }

        return buildValidationResult(errors);
    }
}
