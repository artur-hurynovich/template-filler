package com.example.hurynovich.template_filler.validator.impl;

import com.example.hurynovich.template_filler.request.CreateTemplateRequest;
import com.example.hurynovich.template_filler.validator.Validator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.example.hurynovich.template_filler.validator.model.ValidationResult.failure;
import static com.example.hurynovich.template_filler.validator.model.ValidationResult.success;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateTemplateRequestValidatorTest {

    private static final String NAME = "test name";
    private static final String PAYLOAD = "test payload";

    private static final String NON_VALID_NAME_MSG = "'name' can't be null, empty or blank";
    private static final String NON_VALID_PAYLOAD_MSG = "'payload' can't be null, empty or blank";

    private final Validator<CreateTemplateRequest> validator = new CreateTemplateRequestValidator();

    @Test
    void given_validCreateTemplateRequest_when_validate_then_returnSuccess() {
        final var request = new CreateTemplateRequest(NAME, PAYLOAD);
        final var expectedValidationResult = success();

        final var actualValidationResult = validator.validate(request);

        assertEquals(expectedValidationResult, actualValidationResult);
    }

    @Test
    void given_nullNameCreateTemplateRequest_when_validate_then_returnSuccess() {
        final var request = new CreateTemplateRequest(null, PAYLOAD);
        final var expectedValidationResult = failure(List.of(NON_VALID_NAME_MSG));

        final var actualValidationResult = validator.validate(request);

        assertEquals(expectedValidationResult, actualValidationResult);
    }

    @Test
    void given_emptyNameCreateTemplateRequest_when_validate_then_returnSuccess() {
        final var request = new CreateTemplateRequest(EMPTY, PAYLOAD);
        final var expectedValidationResult = failure(List.of(NON_VALID_NAME_MSG));

        final var actualValidationResult = validator.validate(request);

        assertEquals(expectedValidationResult, actualValidationResult);
    }

    @Test
    void given_blankNameCreateTemplateRequest_when_validate_then_returnSuccess() {
        final var request = new CreateTemplateRequest(SPACE, PAYLOAD);
        final var expectedValidationResult = failure(List.of(NON_VALID_NAME_MSG));

        final var actualValidationResult = validator.validate(request);

        assertEquals(expectedValidationResult, actualValidationResult);
    }

    @Test
    void given_nullPayloadCreateTemplateRequest_when_validate_then_returnSuccess() {
        final var request = new CreateTemplateRequest(NAME, null);
        final var expectedValidationResult = failure(List.of(NON_VALID_PAYLOAD_MSG));

        final var actualValidationResult = validator.validate(request);

        assertEquals(expectedValidationResult, actualValidationResult);
    }

    @Test
    void given_emptyPayloadCreateTemplateRequest_when_validate_then_returnSuccess() {
        final var request = new CreateTemplateRequest(NAME, EMPTY);
        final var expectedValidationResult = failure(List.of(NON_VALID_PAYLOAD_MSG));

        final var actualValidationResult = validator.validate(request);

        assertEquals(expectedValidationResult, actualValidationResult);
    }

    @Test
    void given_blankPayloadCreateTemplateRequest_when_validate_then_returnSuccess() {
        final var request = new CreateTemplateRequest(NAME, SPACE);
        final var expectedValidationResult = failure(List.of(NON_VALID_PAYLOAD_MSG));

        final var actualValidationResult = validator.validate(request);

        assertEquals(expectedValidationResult, actualValidationResult);
    }
}
