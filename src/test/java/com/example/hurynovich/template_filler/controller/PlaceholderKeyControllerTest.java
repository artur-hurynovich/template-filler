package com.example.hurynovich.template_filler.controller;

import com.example.hurynovich.template_filler.converter.PlaceholderKeyApiConverter;
import com.example.hurynovich.template_filler.dto.PlaceholderKeyDto;
import com.example.hurynovich.template_filler.response.PlaceholderKeyResponse;
import com.example.hurynovich.template_filler.service.PlaceholderKeyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static java.util.List.of;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PlaceholderKeyController.class)
class PlaceholderKeyControllerTest {

    private static final String GET_BY_TEMPLATE_ID_PATH = "/v1/placeholder-keys?templateId=1639";
    private static final String GET_BY_TEMPLATE_ID_RESPONSE_JSON = """
            {
              "placeholderKeys": [
                "test placeholder key 1",
                "test placeholder key 2"
              ]
            }
            """;

    private static final Long TEMPLATE_ID = 1639L;

    private static final Long PLACEHOLDER_KEY_ID_1 = 1645L;
    private static final String PLACEHOLDER_KEY_1 = "test placeholder key 1";

    private static final Long PLACEHOLDER_KEY_ID_2 = 1646L;
    private static final String PLACEHOLDER_KEY_2 = "test placeholder key 2";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlaceholderKeyService service;

    @MockBean
    private PlaceholderKeyApiConverter converter;

    @Test
    void given_templateId_when_getByTemplateId_then_returnPlaceholderKeyResponse() throws Exception {
        final var placeholderKeyDto1 = new PlaceholderKeyDto(PLACEHOLDER_KEY_ID_1, PLACEHOLDER_KEY_1);
        final var placeholderKeyDto2 = new PlaceholderKeyDto(PLACEHOLDER_KEY_ID_2, PLACEHOLDER_KEY_2);
        final var placeholderKeyDtoList = of(placeholderKeyDto1, placeholderKeyDto2);
        final var placeholderKeyResponse = new PlaceholderKeyResponse(of(PLACEHOLDER_KEY_1, PLACEHOLDER_KEY_2));
        when(service.findAllByTemplateId(TEMPLATE_ID)).thenReturn(placeholderKeyDtoList);
        when(converter.convert(placeholderKeyDtoList)).thenReturn(placeholderKeyResponse);

        mockMvc
                .perform(get(GET_BY_TEMPLATE_ID_PATH))
                .andExpect(status().isOk())
                .andExpect(content().json(GET_BY_TEMPLATE_ID_RESPONSE_JSON));
    }
}
