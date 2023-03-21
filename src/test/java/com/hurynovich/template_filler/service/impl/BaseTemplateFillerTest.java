package com.hurynovich.template_filler.service.impl;

import com.hurynovich.template_filler.dto.TemplateDto;
import com.hurynovich.template_filler.service.TemplateService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static java.util.Map.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BaseTemplateFillerTest {

    private static final Long TEMPLATE_ID = 1617L;
    private static final String TEMPLATE_NAME = "test template name";
    private static final String TEMPLATE_PAYLOAD = "Hello, {{first_name}} {{last_name}}";
    private static final Map<String, String> PLACEHOLDERS = of("first_name", "John", "last_name", "Doe");
    private static final String FILLED_TEMPLATE = "Hello, John Doe";

    @Mock
    private TemplateService service;

    @InjectMocks
    private BaseTemplateFiller filler;

    @Test
    void given_templateIdAndPlaceholders_when_fill_then_returnFilledTemplate() {
        final var templateDto = new TemplateDto(TEMPLATE_ID, TEMPLATE_NAME, TEMPLATE_PAYLOAD);
        when(service.findById(TEMPLATE_ID)).thenReturn(templateDto);

        final var actualFilledTemplate = filler.fill(TEMPLATE_ID, PLACEHOLDERS);

        assertEquals(FILLED_TEMPLATE, actualFilledTemplate);
    }
}
