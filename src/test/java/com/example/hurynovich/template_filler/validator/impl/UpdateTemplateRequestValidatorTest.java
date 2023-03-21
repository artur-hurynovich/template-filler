package com.example.hurynovich.template_filler.validator.impl;

import com.example.hurynovich.template_filler.request.UpdateTemplateRequest;
import com.example.hurynovich.template_filler.validator.Validator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.example.hurynovich.template_filler.validator.model.ValidationResult.failure;
import static com.example.hurynovich.template_filler.validator.model.ValidationResult.success;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UpdateTemplateRequestValidatorTest {

    private static final Long ID = 1008L;
    private static final String NAME = "test name";
    private static final String PAYLOAD = "test payload";

    private static final String NON_VALID_ID_MSG = "'id' can't be null";
    private static final String NON_VALID_NAME_MSG = "'name' can't be null, empty or blank";
    private static final String NON_VALID_PAYLOAD_MSG = "'payload' can't be null, empty or blank";

    private final Validator<UpdateTemplateRequest> validator = new UpdateTemplateRequestValidator();

    @Test
    void given_validUpdateTemplateRequest_when_validate_then_returnSuccess() {
        final var request = new UpdateTemplateRequest(ID, NAME, PAYLOAD);
        final var expectedValidationResult = success();

        final var actualValidationResult = validator.validate(request);

        assertEquals(expectedValidationResult, actualValidationResult);
    }

    @Test
    void given_nullIdUpdateTemplateRequest_when_validate_then_returnSuccess() {
        final var request = new UpdateTemplateRequest(null, NAME, PAYLOAD);
        final var expectedValidationResult = failure(List.of(NON_VALID_ID_MSG));

        final var actualValidationResult = validator.validate(request);

        assertEquals(expectedValidationResult, actualValidationResult);
    }

    @Test
    void given_nullNameUpdateTemplateRequest_when_validate_then_returnSuccess() {
        final var request = new UpdateTemplateRequest(ID, null, PAYLOAD);
        final var expectedValidationResult = failure(List.of(NON_VALID_NAME_MSG));

        final var actualValidationResult = validator.validate(request);

        assertEquals(expectedValidationResult, actualValidationResult);
    }

    @Test
    void given_emptyNameUpdateTemplateRequest_when_validate_then_returnSuccess() {
        final var request = new UpdateTemplateRequest(ID, EMPTY, PAYLOAD);
        final var expectedValidationResult = failure(List.of(NON_VALID_NAME_MSG));

        final var actualValidationResult = validator.validate(request);

        assertEquals(expectedValidationResult, actualValidationResult);
    }

    @Test
    void given_blankNameUpdateTemplateRequest_when_validate_then_returnSuccess() {
        final var request = new UpdateTemplateRequest(ID, SPACE, PAYLOAD);
        final var expectedValidationResult = failure(List.of(NON_VALID_NAME_MSG));

        final var actualValidationResult = validator.validate(request);

        assertEquals(expectedValidationResult, actualValidationResult);
    }

    @Test
    void given_nullPayloadUpdateTemplateRequest_when_validate_then_returnSuccess() {
        final var request = new UpdateTemplateRequest(ID, NAME, null);
        final var expectedValidationResult = failure(List.of(NON_VALID_PAYLOAD_MSG));

        final var actualValidationResult = validator.validate(request);

        assertEquals(expectedValidationResult, actualValidationResult);
    }

    @Test
    void given_emptyPayloadUpdateTemplateRequest_when_validate_then_returnSuccess() {
        final var request = new UpdateTemplateRequest(ID, NAME, EMPTY);
        final var expectedValidationResult = failure(List.of(NON_VALID_PAYLOAD_MSG));

        final var actualValidationResult = validator.validate(request);

        assertEquals(expectedValidationResult, actualValidationResult);
    }

    @Test
    void given_blankPayloadUpdateTemplateRequest_when_validate_then_returnSuccess() {
        final var request = new UpdateTemplateRequest(ID, NAME, SPACE);
        final var expectedValidationResult = failure(List.of(NON_VALID_PAYLOAD_MSG));

        final var actualValidationResult = validator.validate(request);

        assertEquals(expectedValidationResult, actualValidationResult);
    }
}
