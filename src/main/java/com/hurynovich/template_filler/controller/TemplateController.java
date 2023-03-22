package com.hurynovich.template_filler.controller;

import com.hurynovich.template_filler.controller.exception.ControllerValidationException;
import com.hurynovich.template_filler.converter.TemplateApiConverter;
import com.hurynovich.template_filler.request.CreateTemplateRequest;
import com.hurynovich.template_filler.request.UpdateTemplateRequest;
import com.hurynovich.template_filler.response.TemplateResponse;
import com.hurynovich.template_filler.service.TemplateService;
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

    private static final String ID_PATH_VARIABLE_MISMATCH_MSG = "path variable 'id'=[%d] should be equal to 'request.id'=[%d]";

    private final Validator<CreateTemplateRequest> createTemplateRequestValidator;

    private final Validator<UpdateTemplateRequest> updateTemplateRequestValidator;

    private final TemplateApiConverter converter;

    private final TemplateService service;

    public TemplateController(final Validator<CreateTemplateRequest> createTemplateRequestValidator,
                              final Validator<UpdateTemplateRequest> updateTemplateRequestValidator,
                              final TemplateApiConverter converter, final TemplateService service) {
        this.createTemplateRequestValidator = createTemplateRequestValidator;
        this.updateTemplateRequestValidator = updateTemplateRequestValidator;
        this.converter = converter;
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public TemplateResponse create(@RequestBody final CreateTemplateRequest request) {
        final var validationResult = createTemplateRequestValidator.validate(request);
        if (validationResult.isSuccess()) {
            return converter.convert(service.save(converter.convert(request)));
        } else {
            throw new ControllerValidationException(validationResult.formatErrors());
        }
    }

    @GetMapping("/{id}")
    public TemplateResponse getById(@PathVariable final Long id) {
        return converter.convert(service.findById(id));
    }

    @GetMapping
    public List<TemplateResponse> getAll(@RequestParam(name = "name", required = false) final String namePattern) {
        final List<TemplateResponse> templateResponses;
        if (isNotBlank(namePattern)) {
            templateResponses = service
                    .findAllByNamePattern(namePattern)
                    .stream()
                    .map(converter::convert)
                    .toList();
        } else {
            templateResponses = service
                    .findAll()
                    .stream()
                    .map(converter::convert)
                    .toList();
        }

        return templateResponses;
    }

    @PutMapping("/{id}")
    public TemplateResponse update(@PathVariable final Long id, @RequestBody final UpdateTemplateRequest request) {
        final var validationResult = updateTemplateRequestValidator.validate(request);
        if (validationResult.isSuccess()) {
            final Long requestId = request.getId();
            if (id.equals(requestId)) {
                return converter.convert(service.save(converter.convert(request)));
            } else {
                throw new ControllerValidationException(format(ID_PATH_VARIABLE_MISMATCH_MSG, id, requestId));
            }
        } else {
            throw new ControllerValidationException(validationResult.formatErrors());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteById(@PathVariable final Long id) {
        service.deleteById(id);
    }
}
