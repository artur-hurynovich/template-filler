package com.hurynovich.template_filler.controller;

import com.hurynovich.template_filler.command.CreateTemplateCommand;
import com.hurynovich.template_filler.command.DeleteTemplateCommand;
import com.hurynovich.template_filler.command.UpdateTemplateCommand;
import com.hurynovich.template_filler.controller.exception.ControllerValidationException;
import com.hurynovich.template_filler.converter.TemplateApiConverter;
import com.hurynovich.template_filler.response.TemplateResponse;
import com.hurynovich.template_filler.service.TemplateCommandService;
import com.hurynovich.template_filler.service.TemplateQueryService;
import com.hurynovich.template_filler.validator.Validator;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/v1/templates")
public class TemplateController {

    private static final String ID_PATH_VARIABLE_MISMATCH_MSG = "path variable 'id'=[%d] should be equal to 'templateId'=[%d]";

    private final Validator<CreateTemplateCommand> createTemplateCommandValidator;

    private final Validator<UpdateTemplateCommand> updateTemplateCommandValidator;

    private final TemplateApiConverter converter;

    private final TemplateCommandService templateCommandService;

    private final TemplateQueryService templateQueryService;

    public TemplateController(final Validator<CreateTemplateCommand> createTemplateCommandValidator,
            final Validator<UpdateTemplateCommand> updateTemplateCommandValidator, final TemplateApiConverter converter,
            final TemplateCommandService templateCommandService, final TemplateQueryService templateQueryService) {
        this.createTemplateCommandValidator = createTemplateCommandValidator;
        this.updateTemplateCommandValidator = updateTemplateCommandValidator;
        this.converter = converter;
        this.templateCommandService = templateCommandService;
        this.templateQueryService = templateQueryService;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public TemplateResponse create(@RequestBody final CreateTemplateCommand command) {
        final var validationResult = createTemplateCommandValidator.validate(command);
        if (validationResult.isSuccess()) {
            final var template = templateCommandService.create(command);

            return converter.convert(template);
        } else {
            throw new ControllerValidationException(validationResult.formatErrors());
        }
    }

    @GetMapping("/{id}")
    public TemplateResponse getById(@PathVariable final Long id) {
        return converter.convert(templateQueryService.findById(id));
    }

    @GetMapping
    public List<TemplateResponse> getAll(@RequestParam(name = "name", required = false) final String namePattern) {
        final List<TemplateResponse> templateResponses;
        if (isNotBlank(namePattern)) {
            templateResponses = templateQueryService
                    .findAllByNamePattern(namePattern)
                    .stream()
                    .map(converter::convert)
                    .toList();
        } else {
            templateResponses = templateQueryService
                    .findAll()
                    .stream()
                    .map(converter::convert)
                    .toList();
        }

        return templateResponses;
    }

    @PutMapping("/{id}")
    public TemplateResponse update(@PathVariable final Long id, @RequestBody final UpdateTemplateCommand command) {
        final var validationResult = updateTemplateCommandValidator.validate(command);
        if (validationResult.isSuccess()) {
            final Long templateId = command.templateId();
            if (id.equals(templateId)) {
                return converter.convert(templateCommandService.update(command));
            } else {
                throw new ControllerValidationException(format(ID_PATH_VARIABLE_MISMATCH_MSG, id, templateId));
            }
        } else {
            throw new ControllerValidationException(validationResult.formatErrors());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteById(@PathVariable final Long id) {
        templateCommandService.delete(new DeleteTemplateCommand(id));
    }
}
