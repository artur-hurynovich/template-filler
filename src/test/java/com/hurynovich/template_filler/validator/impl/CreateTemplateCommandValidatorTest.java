package com.hurynovich.template_filler.validator.impl;

import com.hurynovich.template_filler.command.CreateTemplateCommand;
import com.hurynovich.template_filler.service.TemplateQueryService;
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
class CreateTemplateCommandValidatorTest {

    private static final String NAME = "test name";
    private static final String PAYLOAD = "test payload";

    private static final String NON_VALID_NAME_MSG = "'name' can't be null, empty or blank";
    private static final String NON_VALID_PAYLOAD_MSG = "'payload' can't be null, empty or blank";
    private static final String NAME_DUPLICATE_MSG = "template with 'name'=[test name] already exists";

    @Mock
    private TemplateQueryService service;

    @InjectMocks
    private CreateTemplateCommandValidator validator;

    @Test
    void given_validCreateTemplateCommand_when_validate_then_returnSuccess() {
        final var request = new CreateTemplateCommand(NAME, PAYLOAD);
        final var expectedValidationResult = success();
        when(service.existsByName(NAME)).thenReturn(false);

        final var actualValidationResult = validator.validate(request);

        assertEquals(expectedValidationResult, actualValidationResult);
    }

    @Test
    void given_nullNameCreateTemplateCommand_when_validate_then_returnSuccess() {
        final var request = new CreateTemplateCommand(null, PAYLOAD);
        final var expectedValidationResult = failure(of(NON_VALID_NAME_MSG));

        final var actualValidationResult = validator.validate(request);

        assertEquals(expectedValidationResult, actualValidationResult);
    }

    @Test
    void given_emptyNameCreateTemplateCommand_when_validate_then_returnSuccess() {
        final var request = new CreateTemplateCommand(EMPTY, PAYLOAD);
        final var expectedValidationResult = failure(of(NON_VALID_NAME_MSG));

        final var actualValidationResult = validator.validate(request);

        assertEquals(expectedValidationResult, actualValidationResult);
    }

    @Test
    void given_blankNameCreateTemplateCommand_when_validate_then_returnSuccess() {
        final var request = new CreateTemplateCommand(SPACE, PAYLOAD);
        final var expectedValidationResult = failure(of(NON_VALID_NAME_MSG));

        final var actualValidationResult = validator.validate(request);

        assertEquals(expectedValidationResult, actualValidationResult);
    }

    @Test
    void given_nullPayloadCreateTemplateCommand_when_validate_then_returnSuccess() {
        final var request = new CreateTemplateCommand(NAME, null);
        final var expectedValidationResult = failure(of(NON_VALID_PAYLOAD_MSG));
        when(service.existsByName(NAME)).thenReturn(false);

        final var actualValidationResult = validator.validate(request);

        assertEquals(expectedValidationResult, actualValidationResult);
    }

    @Test
    void given_emptyPayloadCreateTemplateCommand_when_validate_then_returnSuccess() {
        final var request = new CreateTemplateCommand(NAME, EMPTY);
        final var expectedValidationResult = failure(of(NON_VALID_PAYLOAD_MSG));
        when(service.existsByName(NAME)).thenReturn(false);

        final var actualValidationResult = validator.validate(request);

        assertEquals(expectedValidationResult, actualValidationResult);
    }

    @Test
    void given_blankPayloadCreateTemplateCommand_when_validate_then_returnSuccess() {
        final var request = new CreateTemplateCommand(NAME, SPACE);
        final var expectedValidationResult = failure(of(NON_VALID_PAYLOAD_MSG));
        when(service.existsByName(NAME)).thenReturn(false);

        final var actualValidationResult = validator.validate(request);

        assertEquals(expectedValidationResult, actualValidationResult);
    }

    @Test
    void given_validCreateTemplateCommand_when_validate_then_returnFailure() {
        final var request = new CreateTemplateCommand(NAME, PAYLOAD);
        final var expectedValidationResult = failure(of(NAME_DUPLICATE_MSG));
        when(service.existsByName(NAME)).thenReturn(true);

        final var actualValidationResult = validator.validate(request);

        assertEquals(expectedValidationResult, actualValidationResult);
    }
}
