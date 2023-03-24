package com.hurynovich.template_filler.service.impl;

import com.hurynovich.template_filler.service.TemplateFiller;
import com.hurynovich.template_filler.service.TemplateQueryService;
import org.springframework.stereotype.Service;

import java.util.Map;

import static org.apache.commons.text.StringSubstitutor.replace;

@Service
public class BaseTemplateFiller implements TemplateFiller {

    private static final String PLACEHOLDER_PREFIX = "{{";
    private static final String PLACEHOLDER_POSTFIX = "}}";

    private final TemplateQueryService templateQueryService;

    public BaseTemplateFiller(final TemplateQueryService templateQueryService) {
        this.templateQueryService = templateQueryService;
    }

    @Override
    public String fill(final Long templateId, final Map<String, String> placeholders) {
        final var templateDto = templateQueryService.findById(templateId);

        return replace(templateDto.payload(), placeholders, PLACEHOLDER_PREFIX, PLACEHOLDER_POSTFIX);
    }
}
