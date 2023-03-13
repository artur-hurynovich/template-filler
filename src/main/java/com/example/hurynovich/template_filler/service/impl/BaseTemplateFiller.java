package com.example.hurynovich.template_filler.service.impl;

import com.example.hurynovich.template_filler.service.TemplateFiller;
import com.example.hurynovich.template_filler.service.TemplateService;
import org.springframework.stereotype.Service;

import java.util.Map;

import static org.apache.commons.text.StringSubstitutor.replace;

@Service
public class BaseTemplateFiller implements TemplateFiller {

    private static final String PLACEHOLDER_PREFIX = "{{";
    private static final String PLACEHOLDER_POSTFIX = "}}";

    private final TemplateService templateService;

    public BaseTemplateFiller(final TemplateService templateService) {
        this.templateService = templateService;
    }

    @Override
    public String fill(final Long templateId, final Map<String, String> placeholders) {
        final var templateDto = templateService.findById(templateId);

        return replace(templateDto.payload(), placeholders, PLACEHOLDER_PREFIX, PLACEHOLDER_POSTFIX);
    }
}
