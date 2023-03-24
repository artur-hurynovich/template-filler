package com.hurynovich.template_filler.validator.impl;

import com.hurynovich.template_filler.command.UpdateTemplateCommand;
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
class UpdateTemplateCommandValidatorTest {

    private static final Long ID = 1008L;
    private static final String NAME = "test name";
    private static final String PAYLOAD = "test payload";

    private static final String NON_VALID_ID_MSG = "'id' can't be null";
    private static final String NON_VALID_NAME_MSG = "'name' can't be null, empty or blank";
    private static final String NON_VALID_PAYLOAD_MSG = "'payload' can't be null, empty or blank";
    private static final String NAME_DUPLICATE_MSG = "template with 'name'=[test name] already exists";

    @Mock
    private TemplateQueryService service;

    @InjectMocks
    private UpdateTemplateCommandValidator validator;

    @Test
    void given_validUpdateTemplateCommand_when_validate_then_returnSuccess() {
        final var request = new UpdateTemplateCommand(ID, NAME, PAYLOAD);
        final var expectedValidationResult = success();
        when(service.existsByNameAndNotId(NAME, ID)).thenReturn(false);

        final var actualValidationResult = validator.validate(request);

        assertEquals(expectedValidationResult, actualValidationResult);
    }

    @Test
    void given_nullIdUpdateTemplateCommand_when_validate_then_returnSuccess() {
        final var request = new UpdateTemplateCommand(null, NAME, PAYLOAD);
        final var expectedValidationResult = failure(of(NON_VALID_ID_MSG));

        final var actualValidationResult = validator.validate(request);

        assertEquals(expectedValidationResult, actualValidationResult);
    }

    @Test
    void given_nullNameUpdateTemplateCommand_when_validate_then_returnSuccess() {
        final var request = new UpdateTemplateCommand(ID, null, PAYLOAD);
        final var expectedValidationResult = failure(of(NON_VALID_NAME_MSG));

        final var actualValidationResult = validator.validate(request);

        assertEquals(expectedValidationResult, actualValidationResult);
    }

    @Test
    void given_emptyNameUpdateTemplateCommand_when_validate_then_returnSuccess() {
        final var request = new UpdateTemplateCommand(ID, EMPTY, PAYLOAD);
        final var expectedValidationResult = failure(of(NON_VALID_NAME_MSG));

        final var actualValidationResult = validator.validate(request);

        assertEquals(expectedValidationResult, actualValidationResult);
    }

    @Test
    void given_blankNameUpdateTemplateCommand_when_validate_then_returnSuccess() {
        final var request = new UpdateTemplateCommand(ID, SPACE, PAYLOAD);
        final var expectedValidationResult = failure(of(NON_VALID_NAME_MSG));

        final var actualValidationResult = validator.validate(request);

        assertEquals(expectedValidationResult, actualValidationResult);
    }

    @Test
    void given_nullPayloadUpdateTemplateCommand_when_validate_then_returnSuccess() {
        final var request = new UpdateTemplateCommand(ID, NAME, null);
        final var expectedValidationResult = failure(of(NON_VALID_PAYLOAD_MSG));
        when(service.existsByNameAndNotId(NAME, ID)).thenReturn(false);

        final var actualValidationResult = validator.validate(request);

        assertEquals(expectedValidationResult, actualValidationResult);
    }

    @Test
    void given_emptyPayloadUpdateTemplateCommand_when_validate_then_returnSuccess() {
        final var request = new UpdateTemplateCommand(ID, NAME, EMPTY);
        final var expectedValidationResult = failure(of(NON_VALID_PAYLOAD_MSG));
        when(service.existsByNameAndNotId(NAME, ID)).thenReturn(false);

        final var actualValidationResult = validator.validate(request);

        assertEquals(expectedValidationResult, actualValidationResult);
    }

    @Test
    void given_blankPayloadUpdateTemplateCommand_when_validate_then_returnSuccess() {
        final var request = new UpdateTemplateCommand(ID, NAME, SPACE);
        final var expectedValidationResult = failure(of(NON_VALID_PAYLOAD_MSG));
        when(service.existsByNameAndNotId(NAME, ID)).thenReturn(false);

        final var actualValidationResult = validator.validate(request);

        assertEquals(expectedValidationResult, actualValidationResult);
    }

    @Test
    void given_validUpdateTemplateCommand_when_validate_then_returnFailure() {
        final var request = new UpdateTemplateCommand(ID, NAME, PAYLOAD);
        final var expectedValidationResult = failure(of(NAME_DUPLICATE_MSG));
        when(service.existsByNameAndNotId(NAME, ID)).thenReturn(true);

        final var actualValidationResult = validator.validate(request);

        assertEquals(expectedValidationResult, actualValidationResult);
    }
}
