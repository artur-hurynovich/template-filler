package com.hurynovich.template_filler.integration;

import com.hurynovich.template_filler.dto.TemplateDto;
import com.hurynovich.template_filler.service.TemplateQueryService;
import com.hurynovich.template_filler.service.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
class BaseTemplateQueryServiceIT {

    private static final Long ID_1 = 100L;
    private static final String NAME_1 = "test name 1";
    private static final String PAYLOAD_1 = "test payload 1";

    private static final Long ID_2 = 200L;
    private static final String NAME_2 = "test name 2";
    private static final String PAYLOAD_2 = "test payload 2";

    private static final Long ID_3 = 300L;
    private static final String NAME_3 = "test name 3";
    private static final String PAYLOAD_3 = "test payload 3";

    private static final String NAME_PATTERN = "name 2";

    private static final String TEMPLATE_NOT_FOUND_EXCEPTION_MSG = "template with 'id'=[100] not found";

    @Autowired
    private TemplateQueryService service;

    @Test
    @SqlGroup({@Sql(scripts = "/integration/db-scripts/templates/insert-template.sql",
            executionPhase = BEFORE_TEST_METHOD), @Sql(scripts = "/integration/db-scripts/common/clear.sql",
            executionPhase = AFTER_TEST_METHOD)})
    void given_templateExists_when_findById_then_returnTemplateDto() {
        final var expectedTemplateDto = new TemplateDto(ID_1, NAME_1, PAYLOAD_1);

        final var actualTemplateDto = service.findById(ID_1);

        assertEquals(expectedTemplateDto, actualTemplateDto);
    }

    @Test
    void given_templateDoesNotExist_when_findById_then_throwNotFoundException() {
        final var actualNotFoundException = assertThrows(NotFoundException.class, () -> service.findById(ID_1));

        assertEquals(TEMPLATE_NOT_FOUND_EXCEPTION_MSG, actualNotFoundException.getMessage());
    }

    @Test
    @SqlGroup({@Sql(scripts = "/integration/db-scripts/templates/insert-templates.sql",
            executionPhase = BEFORE_TEST_METHOD), @Sql(scripts = "/integration/db-scripts/common/clear.sql",
            executionPhase = AFTER_TEST_METHOD)})
    void given_templatesExist_when_findAll_then_returnTemplateDtoList() {
        final var expectedTemplateDtoList = of(new TemplateDto(ID_2, NAME_2, PAYLOAD_2),
                new TemplateDto(ID_3, NAME_3, PAYLOAD_3));

        final var actualTemplateDtoList = service.findAll();

        assertEquals(expectedTemplateDtoList, actualTemplateDtoList);
    }

    @Test
    @SqlGroup({@Sql(scripts = "/integration/db-scripts/templates/insert-template.sql",
            executionPhase = BEFORE_TEST_METHOD), @Sql(scripts = "/integration/db-scripts/common/clear.sql",
            executionPhase = AFTER_TEST_METHOD)})
    void given_templateExists_when_existsByName_then_returnTrue() {
        assertTrue(service.existsByName(NAME_1));
    }

    @Test
    void given_templateDoesNotExist_when_existsByName_then_returnFalse() {
        assertFalse(service.existsByName(NAME_1));
    }

    @Test
    @SqlGroup({@Sql(scripts = "/integration/db-scripts/templates/insert-template.sql",
            executionPhase = BEFORE_TEST_METHOD), @Sql(scripts = "/integration/db-scripts/common/clear.sql",
            executionPhase = AFTER_TEST_METHOD)})
    void given_templateExists_when_existsByNameAndNotId_then_returnTrue() {
        assertTrue(service.existsByNameAndNotId(NAME_1, ID_2));
    }

    @Test
    @SqlGroup({@Sql(scripts = "/integration/db-scripts/templates/insert-template.sql",
            executionPhase = BEFORE_TEST_METHOD), @Sql(scripts = "/integration/db-scripts/common/clear.sql",
            executionPhase = AFTER_TEST_METHOD)})
    void given_templateExists_when_existsByNameAndNotId_then_returnFalse() {
        assertFalse(service.existsByNameAndNotId(NAME_1, ID_1));
    }

    @Test
    void given_templateDoesNotExist_when_existsByNameAndNotId_then_returnFalse() {
        assertFalse(service.existsByNameAndNotId(NAME_1, ID_1));
    }

    @Test
    @SqlGroup({@Sql(scripts = "/integration/db-scripts/templates/insert-templates.sql",
            executionPhase = BEFORE_TEST_METHOD), @Sql(scripts = "/integration/db-scripts/common/clear.sql",
            executionPhase = AFTER_TEST_METHOD)})
    void given_templatesExist_when_findAllByNamePattern_then_returnTemplateDtoList() {
        final var expectedTemplateDtoList = of(new TemplateDto(ID_2, NAME_2, PAYLOAD_2));

        final var actualTemplateDtoList = service.findAllByNamePattern(NAME_PATTERN);

        assertEquals(expectedTemplateDtoList, actualTemplateDtoList);
    }
}
