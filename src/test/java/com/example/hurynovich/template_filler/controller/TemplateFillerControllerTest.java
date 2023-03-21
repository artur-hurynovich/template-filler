package com.example.hurynovich.template_filler.controller;

import com.example.hurynovich.template_filler.converter.TemplateFillerApiConverter;
import com.example.hurynovich.template_filler.request.TemplateFillerRequest;
import com.example.hurynovich.template_filler.response.TemplateFillerResponse;
import com.example.hurynovich.template_filler.service.TemplateFiller;
import com.example.hurynovich.template_filler.validator.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static com.example.hurynovich.template_filler.validator.model.ValidationResult.failure;
import static com.example.hurynovich.template_filler.validator.model.ValidationResult.success;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TemplateFillerController.class)
class TemplateFillerControllerTest {

    private static final String TEMPLATE_FILLER_PATH = "/v1/template-filler";

    private static final String FILL_TEMPLATE_REQUEST_JSON = """
            {
              "templateId": 1813,
              "placeholders": {
                "first_name": "John",
                "last_name": "Doe"
              }
            }
            """;
    private static final String FILL_TEMPLATE_RESPONSE_JSON = """
            {
              "filledTemplate": "Hello, John Doe"
            }
            """;

    private static final Long TEMPLATE_ID = 1813L;
    private static final Map<String, String> PLACEHOLDERS = Map.of("first_name", "John", "last_name", "Doe");

    private static final String FILLED_TEMPLATE = "Hello, John Doe";

    private static final String VALIDATION_ERROR = "test validation error";

    private final TemplateFillerRequest request = new TemplateFillerRequest(TEMPLATE_ID, PLACEHOLDERS);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Validator<TemplateFillerRequest> validator;

    @MockBean
    private TemplateFiller filler;

    @MockBean
    private TemplateFillerApiConverter converter;

    @Test
    void given_templateFillerRequest_when_fillTemplate_then_returnFilledTemplateResponse() throws Exception {
        final var templateFillerResponse = new TemplateFillerResponse(FILLED_TEMPLATE);
        when(validator.validate(request)).thenReturn(success());
        when(filler.fill(TEMPLATE_ID, PLACEHOLDERS)).thenReturn(FILLED_TEMPLATE);
        when(converter.convert(FILLED_TEMPLATE)).thenReturn(templateFillerResponse);

        mockMvc
                .perform(post(TEMPLATE_FILLER_PATH)
                        .contentType(APPLICATION_JSON)
                        .content(FILL_TEMPLATE_REQUEST_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(FILL_TEMPLATE_RESPONSE_JSON));
    }

    @Test
    void given_nonValidTemplateFillerRequest_when_fillTemplate_then_returnBadRequest() throws Exception {
        when(validator.validate(request)).thenReturn(failure(List.of(VALIDATION_ERROR)));

        mockMvc
                .perform(post(TEMPLATE_FILLER_PATH)
                        .contentType(APPLICATION_JSON)
                        .content(FILL_TEMPLATE_REQUEST_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(VALIDATION_ERROR));
    }
}
