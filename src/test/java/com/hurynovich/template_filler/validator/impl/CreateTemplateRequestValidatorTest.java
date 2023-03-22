package com.hurynovich.template_filler.validator.impl;

import com.hurynovich.template_filler.request.CreateTemplateRequest;
import com.hurynovich.template_filler.service.TemplateService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.hurynovich.template_filler.validator.model.ValidationResult.failure;
import static com.hurynovich.template_filler.validator.model.ValidationResult.success;
import static java.util.List.of;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateTemplateRequestValidatorTest {

    private static final String NAME = "test name";
    private static final String PAYLOAD = "test payload";

    private static final String NON_VALID_NAME_MSG = "'name' can't be null, empty or blank";
    private static final String NON_VALID_PAYLOAD_MSG = "'payload' can't be null, empty or blank";
    private static final String NAME_DUPLICATE_MSG = "template with 'name'=[test name] already exists";

    @Mock
    private TemplateService service;

    @InjectMocks
    private CreateTemplateRequestValidator validator;

    @Test
    void given_validCreateTemplateRequest_when_validate_then_returnSuccess() {
        final var request = new CreateTemplateRequest(NAME, PAYLOAD);
        final var expectedValidationResult = success();
        when(service.existsByName(NAME)).thenReturn(false);

        final var actualValidationResult = validator.validate(request);

        assertEquals(expectedValidationResult, actualValidationResult);
    }

    @Test
    void given_nullNameCreateTemplateRequest_when_validate_then_returnSuccess() {
        final var request = new CreateTemplateRequest(null, PAYLOAD);
        final var expectedValidationResult = failure(of(NON_VALID_NAME_MSG));

        final var actualValidationResult = validator.validate(request);

        assertEquals(expectedValidationResult, actualValidationResult);
    }

    @Test
    void given_emptyNameCreateTemplateRequest_when_validate_then_returnSuccess() {
        final var request = new CreateTemplateRequest(EMPTY, PAYLOAD);
        final var expectedValidationResult = failure(of(NON_VALID_NAME_MSG));

        final var actualValidationResult = validator.validate(request);

        assertEquals(expectedValidationResult, actualValidationResult);
    }

    @Test
    void given_blankNameCreateTemplateRequest_when_validate_then_returnSuccess() {
        final var request = new CreateTemplateRequest(SPACE, PAYLOAD);
        final var expectedValidationResult = failure(of(NON_VALID_NAME_MSG));

        final var actualValidationResult = validator.validate(request);

        assertEquals(expectedValidationResult, actualValidationResult);
    }

    @Test
    void given_nullPayloadCreateTemplateRequest_when_validate_then_returnSuccess() {
        final var request = new CreateTemplateRequest(NAME, null);
        final var expectedValidationResult = failure(of(NON_VALID_PAYLOAD_MSG));
        when(service.existsByName(NAME)).thenReturn(false);

        final var actualValidationResult = validator.validate(request);

        assertEquals(expectedValidationResult, actualValidationResult);
    }

    @Test
    void given_emptyPayloadCreateTemplateRequest_when_validate_then_returnSuccess() {
        final var request = new CreateTemplateRequest(NAME, EMPTY);
        final var expectedValidationResult = failure(of(NON_VALID_PAYLOAD_MSG));
        when(service.existsByName(NAME)).thenReturn(false);

        final var actualValidationResult = validator.validate(request);

        assertEquals(expectedValidationResult, actualValidationResult);
    }

    @Test
    void given_blankPayloadCreateTemplateRequest_when_validate_then_returnSuccess() {
        final var request = new CreateTemplateRequest(NAME, SPACE);
        final var expectedValidationResult = failure(of(NON_VALID_PAYLOAD_MSG));
        when(service.existsByName(NAME)).thenReturn(false);

        final var actualValidationResult = validator.validate(request);

        assertEquals(expectedValidationResult, actualValidationResult);
    }

    @Test
    void given_validCreateTemplateRequest_when_validate_then_returnFailure() {
        final var request = new CreateTemplateRequest(NAME, PAYLOAD);
        final var expectedValidationResult = failure(of(NAME_DUPLICATE_MSG));
        when(service.existsByName(NAME)).thenReturn(true);

        final var actualValidationResult = validator.validate(request);

        assertEquals(expectedValidationResult, actualValidationResult);
    }
}
