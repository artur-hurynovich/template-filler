package com.hurynovich.template_filler.validator.impl;

import com.hurynovich.template_filler.request.UpdateTemplateRequest;
import com.hurynovich.template_filler.service.TemplateService;
import com.hurynovich.template_filler.validator.Validator;
import com.hurynovich.template_filler.validator.model.ValidationResult;
import org.springframework.stereotype.Service;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
public class UpdateTemplateRequestValidator extends AbstractTemplateRequestValidator implements Validator<UpdateTemplateRequest> {

    private static final String NON_VALID_ID_MSG = "'id' can't be null";
    private static final String NAME_DUPLICATE_MSG = "template with 'name'=[%s] already exists";

    public UpdateTemplateRequestValidator(final TemplateService service) {
        super(service);
    }

    @Override
    public ValidationResult validate(final UpdateTemplateRequest request) {
        final var errors = super.validate(request);
        final var id = request.getId();
        if (id == null) {
            errors.add(NON_VALID_ID_MSG);
        } else {
            final var name = request.getName();
            if (isNotBlank(name) && service.existsByNameAndNotId(name, id)) {
                errors.add(format(NAME_DUPLICATE_MSG, name));
            }
        }

        return buildValidationResult(errors);
    }
}
