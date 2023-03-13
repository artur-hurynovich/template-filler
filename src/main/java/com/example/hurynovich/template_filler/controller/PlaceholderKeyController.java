package com.example.hurynovich.template_filler.controller;

import com.example.hurynovich.template_filler.converter.PlaceholderKeyApiConverter;
import com.example.hurynovich.template_filler.response.PlaceholderKeyResponse;
import com.example.hurynovich.template_filler.service.PlaceholderKeyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/placeholder-keys")
public class PlaceholderKeyController {

    private final PlaceholderKeyService service;

    private final PlaceholderKeyApiConverter converter;

    public PlaceholderKeyController(final PlaceholderKeyService service,
            final PlaceholderKeyApiConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    @GetMapping
    public PlaceholderKeyResponse getByTemplateId(@RequestParam final Long templateId) {
        return converter.convert(service.findAllByTemplateId(templateId));
    }
}
