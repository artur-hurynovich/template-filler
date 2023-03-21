package com.example.hurynovich.template_filler.validator.impl;

import com.example.hurynovich.template_filler.request.AbstractTemplateRequest;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isBlank;

public abstract class AbstractTemplateRequestValidator extends AbstractValidator {

    private static final String NON_VALID_NAME_MSG = "'name' can't be null, empty or blank";
    private static final String NON_VALID_PAYLOAD_MSG = "'payload' can't be null, empty or blank";

    protected List<String> validate(final AbstractTemplateRequest request) {
        final var errors = new ArrayList<String>();
        if (isBlank(request.getName())) {
            errors.add(NON_VALID_NAME_MSG);
        }

        if (isBlank(request.getPayload())) {
            errors.add(NON_VALID_PAYLOAD_MSG);
        }

        return errors;
    }
}
