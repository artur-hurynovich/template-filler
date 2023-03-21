package com.hurynovich.template_filler.controller;

import com.hurynovich.template_filler.converter.TemplateApiConverter;
import com.hurynovich.template_filler.dto.TemplateDto;
import com.hurynovich.template_filler.request.CreateTemplateRequest;
import com.hurynovich.template_filler.request.UpdateTemplateRequest;
import com.hurynovich.template_filler.response.TemplateResponse;
import com.hurynovich.template_filler.service.TemplateService;
import com.hurynovich.template_filler.service.exception.NotFoundException;
import com.hurynovich.template_filler.validator.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static com.hurynovich.template_filler.validator.model.ValidationResult.failure;
import static com.hurynovich.template_filler.validator.model.ValidationResult.success;
import static java.util.List.of;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TemplateController.class)
class TemplateControllerTest {

    private static final String CREATE_PATH = "/v1/templates";
    private static final String GET_BY_ID_PATH = "/v1/templates/1707";
    private static final String GET_ALL_PATH = "/v1/templates";
    private static final String UPDATE_PATH = "/v1/templates/1707";
    private static final String DELETE_BY_ID_PATH = "/v1/templates/1707";

    private static final String CREATE_TEMPLATE_REQUEST_JSON = """
            {
              "name": "test template name 1",
              "payload": "test template payload 1"
            }
            """;
    private static final String UPDATE_TEMPLATE_REQUEST_JSON = """
            {
              "id": 1707,
              "name": "test template name 1",
              "payload": "test template payload 1"
            }
            """;
    private static final String TEMPLATE_RESPONSE_JSON = """
            {
              "id": 1707,
              "name": "test template name 1",
              "payload": "test template payload 1"
            }
            """;
    private static final String TEMPLATE_RESPONSES_JSON = """
            [
              {
                "id": 1707,
                "name": "test template name 1",
                "payload": "test template payload 1"
              },
              {
                "id": 1710,
                "name": "test template name 2",
                "payload": "test template payload 2"
              }
            ]
            """;

    private static final Long ID_1 = 1707L;
    private static final String NAME_1 = "test template name 1";
    private static final String PAYLOAD_1 = "test template payload 1";

    private static final Long ID_2 = 1710L;
    private static final String NAME_2 = "test template name 2";
    private static final String PAYLOAD_2 = "test template payload 2";

    private static final String VALIDATION_ERROR = "test validation error";
    private static final String NOT_FOUND_EXCEPTION_MSG = "test not found exception message";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Validator<CreateTemplateRequest> createTemplateRequestValidator;

    @MockBean
    private Validator<UpdateTemplateRequest> updateTemplateRequestValidator;

    @MockBean
    private TemplateApiConverter converter;

    @MockBean
    private TemplateService service;

    @Test
    void given_createTemplateRequest_when_create_then_returnTemplateResponse() throws Exception {
        final var createTemplateRequest = new CreateTemplateRequest(NAME_1, PAYLOAD_1);
        final var templateDto = new TemplateDto(ID_1, NAME_1, PAYLOAD_1);
        final var templateResponse = new TemplateResponse(ID_1, NAME_1, PAYLOAD_1);
        when(createTemplateRequestValidator.validate(refEq(createTemplateRequest))).thenReturn(success());
        when(converter.convert(refEq(createTemplateRequest))).thenReturn(templateDto);
        when(service.save(refEq(templateDto))).thenReturn(templateDto);
        when(converter.convert(refEq(templateDto))).thenReturn(templateResponse);

        mockMvc
                .perform(post(CREATE_PATH)
                        .contentType(APPLICATION_JSON)
                        .content(CREATE_TEMPLATE_REQUEST_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(TEMPLATE_RESPONSE_JSON));
    }

    @Test
    void given_nonValidCreateTemplateRequest_when_create_then_returnBadRequest() throws Exception {
        final var createTemplateRequest = new CreateTemplateRequest(NAME_1, PAYLOAD_1);
        when(createTemplateRequestValidator.validate(refEq(createTemplateRequest))).thenReturn(
                failure(of(VALIDATION_ERROR)));

        mockMvc
                .perform(post(CREATE_PATH)
                        .contentType(APPLICATION_JSON)
                        .content(CREATE_TEMPLATE_REQUEST_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(VALIDATION_ERROR));
    }

    @Test
    void given_id_when_getById_then_returnTemplateResponse() throws Exception {
        final var templateDto = new TemplateDto(ID_1, NAME_1, PAYLOAD_1);
        final var templateResponse = new TemplateResponse(ID_1, NAME_1, PAYLOAD_1);
        when(service.findById(ID_1)).thenReturn(templateDto);
        when(converter.convert(templateDto)).thenReturn(templateResponse);

        mockMvc
                .perform(get(GET_BY_ID_PATH))
                .andExpect(status().isOk())
                .andExpect(content().json(TEMPLATE_RESPONSE_JSON));
    }

    @Test
    void given_id_when_getById_then_returnNotFound() throws Exception {
        when(service.findById(ID_1)).thenThrow(new NotFoundException(NOT_FOUND_EXCEPTION_MSG));

        mockMvc
                .perform(get(GET_BY_ID_PATH))
                .andExpect(status().isNotFound())
                .andExpect(content().string(NOT_FOUND_EXCEPTION_MSG));
    }

    @Test
    void when_getAll_then_returnTemplateResponses() throws Exception {
        final var templateDto1 = new TemplateDto(ID_1, NAME_1, PAYLOAD_1);
        final var templateDto2 = new TemplateDto(ID_2, NAME_2, PAYLOAD_2);
        final var templateResponse1 = new TemplateResponse(ID_1, NAME_1, PAYLOAD_1);
        final var templateResponse2 = new TemplateResponse(ID_2, NAME_2, PAYLOAD_2);
        when(service.findAll()).thenReturn(of(templateDto1, templateDto2));
        when(converter.convert(templateDto1)).thenReturn(templateResponse1);
        when(converter.convert(templateDto2)).thenReturn(templateResponse2);

        mockMvc
                .perform(get(GET_ALL_PATH))
                .andExpect(status().isOk())
                .andExpect(content().json(TEMPLATE_RESPONSES_JSON));
    }

    @Test
    void given_updateTemplateRequest_when_update_then_returnTemplateResponse() throws Exception {
        final var updateTemplateRequest = new UpdateTemplateRequest(ID_1, NAME_1, PAYLOAD_1);
        final var templateDto = new TemplateDto(ID_1, NAME_1, PAYLOAD_1);
        final var templateResponse = new TemplateResponse(ID_1, NAME_1, PAYLOAD_1);
        when(updateTemplateRequestValidator.validate(refEq(updateTemplateRequest))).thenReturn(success());
        when(converter.convert(refEq(updateTemplateRequest))).thenReturn(templateDto);
        when(service.save(refEq(templateDto))).thenReturn(templateDto);
        when(converter.convert(refEq(templateDto))).thenReturn(templateResponse);

        mockMvc
                .perform(put(UPDATE_PATH)
                        .contentType(APPLICATION_JSON)
                        .content(UPDATE_TEMPLATE_REQUEST_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(TEMPLATE_RESPONSE_JSON));
    }

    @Test
    void given_nonValidUpdateTemplateRequest_when_update_then_returnBadRequest() throws Exception {
        final var updateTemplateRequest = new UpdateTemplateRequest(ID_1, NAME_1, PAYLOAD_1);
        when(updateTemplateRequestValidator.validate(refEq(updateTemplateRequest))).thenReturn(
                failure(of(VALIDATION_ERROR)));

        mockMvc
                .perform(put(UPDATE_PATH)
                        .contentType(APPLICATION_JSON)
                        .content(UPDATE_TEMPLATE_REQUEST_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(VALIDATION_ERROR));
    }

    @Test
    void given_id_when_deleteById_then_delete() throws Exception {
        doNothing()
                .when(service)
                .deleteById(ID_1);

        mockMvc
                .perform(delete(DELETE_BY_ID_PATH))
                .andExpect(status().isNoContent());
    }
}
