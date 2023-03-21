package com.example.hurynovich.template_filler.validator.impl;

import com.example.hurynovich.template_filler.request.TemplateFillerRequest;
import com.example.hurynovich.template_filler.validator.Validator;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static com.example.hurynovich.template_filler.validator.model.ValidationResult.failure;
import static com.example.hurynovich.template_filler.validator.model.ValidationResult.success;
import static java.util.Collections.emptyMap;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TemplateFillerRequestValidatorTest {

    private static final Long TEMPLATE_ID = 956L;
    private static final Map<String, String> PLACEHOLDERS = Map.of("test placeholder key 1", "test placeholder value 1",
            "test placeholder key 2", "test placeholder value 2");

    private static final String NON_VALID_TEMPLATE_ID_MSG = "'templateId' can't be null";

    private final Validator<TemplateFillerRequest> validator = new TemplateFillerRequestValidator();

    @Test
    void given_validTemplateFillerRequest_when_validate_then_returnSuccess() {
        final var request = new TemplateFillerRequest(TEMPLATE_ID, PLACEHOLDERS);
        final var expectedValidationResult = success();

        final var actualValidationResult = validator.validate(request);

        assertEquals(expectedValidationResult, actualValidationResult);
    }

    @Test
    void given_emptyPlaceholdersTemplateFillerRequest_when_validate_then_returnSuccess() {
        final var request = new TemplateFillerRequest(TEMPLATE_ID, emptyMap());
        final var expectedValidationResult = success();

        final var actualValidationResult = validator.validate(request);

        assertEquals(expectedValidationResult, actualValidationResult);
    }

    @Test
    void given_nullTemplateIdTemplateFillerRequest_when_validate_then_returnFailure() {
        final var request = new TemplateFillerRequest(null, PLACEHOLDERS);
        final var expectedValidationResult = failure(List.of(NON_VALID_TEMPLATE_ID_MSG));

        final var actualValidationResult = validator.validate(request);

        assertEquals(expectedValidationResult, actualValidationResult);
    }
}
