package com.example.hurynovich.template_filler.controller;

import com.example.hurynovich.template_filler.converter.TemplateApiConverter;
import com.example.hurynovich.template_filler.request.CreateTemplateRequest;
import com.example.hurynovich.template_filler.request.UpdateTemplateRequest;
import com.example.hurynovich.template_filler.response.TemplateResponse;
import com.example.hurynovich.template_filler.service.TemplateService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/v1/templates")
public class TemplateController {

    private final TemplateApiConverter converter;

    private final TemplateService service;

    public TemplateController(final TemplateApiConverter converter, final TemplateService service) {
        this.converter = converter;
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public TemplateResponse create(@RequestBody final CreateTemplateRequest request) {
        return converter.convert(service.save(converter.convert(request)));
    }

    @GetMapping("/{id}")
    public TemplateResponse getById(@PathVariable final Long id) {
        return converter.convert(service.findById(id));
    }

    @GetMapping
    public List<TemplateResponse> getAll() {
        return service
                .findAll()
                .stream()
                .map(converter::convert)
                .toList();
    }

    @PutMapping("/{id}")
    public TemplateResponse update(@PathVariable final Long id, @RequestBody final UpdateTemplateRequest request) {
        return converter.convert(service.save(converter.convert(request)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteById(@PathVariable final Long id) {
        service.deleteById(id);
    }
}
