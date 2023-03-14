package com.example.hurynovich.template_filler.integration;

import com.example.hurynovich.template_filler.dto.TemplateDto;
import com.example.hurynovich.template_filler.entity.PlaceholderKeyEntity;
import com.example.hurynovich.template_filler.repository.PlaceholderKeyRepository;
import com.example.hurynovich.template_filler.repository.TemplateRepository;
import com.example.hurynovich.template_filler.service.TemplateService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static java.util.List.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
class BaseTemplateServiceIT {

    private static final String TEMPLATE_NAME = "test template name";
    private static final String PLACEHOLDER_KEY_1 = "first_name";
    private static final String PLACEHOLDER_KEY_2 = "last_name";
    private static final String TEMPLATE_PAYLOAD = "Hello, {{" + PLACEHOLDER_KEY_1 + "}} {{" + PLACEHOLDER_KEY_2 + "}}";

    private static final Long TEMPLATE_ID_1 = 100L;
    private static final String TEMPLATE_NAME_1 = "test template name 1";
    private static final String TEMPLATE_PAYLOAD_1 = "test template payload 1";

    private static final Long TEMPLATE_ID_2 = 200L;
    private static final String TEMPLATE_NAME_2 = "test template name 2";
    private static final String TEMPLATE_PAYLOAD_2 = "test template payload 2";

    private static final Long TEMPLATE_ID_3 = 300L;
    private static final String TEMPLATE_NAME_3 = "test template name 3";
    private static final String TEMPLATE_PAYLOAD_3 = "test template payload 3";

    private static final String ID_FIELD_NAME = "id";

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private PlaceholderKeyRepository placeholderKeyRepository;

    @Autowired
    private TemplateService service;

    @Test
    @Sql(scripts = "/integration/db-scripts/common/clear.sql", executionPhase = AFTER_TEST_METHOD)
    void given_newTemplateDto_when_save_then_returnTemplateDto() {
        final var originalTemplateDto = new TemplateDto(null, TEMPLATE_NAME, TEMPLATE_PAYLOAD);
        final var actualTemplateDto = service.save(originalTemplateDto);

        assertThat(actualTemplateDto)
                .usingRecursiveComparison()
                .ignoringFields(ID_FIELD_NAME)
                .isEqualTo(originalTemplateDto);

        final var id = actualTemplateDto.id();
        assertNotNull(id);
        assertTrue(templateRepository.existsById(id));

        final var placeholderKeyEntities = placeholderKeyRepository.findAllByTemplateId(id);
        assertFalse(placeholderKeyEntities.isEmpty());
        assertEquals(2, placeholderKeyEntities.size());
        assertTrue(placeholderKeyEntities
                .stream()
                .map(PlaceholderKeyEntity::getPlaceholderKey)
                .toList()
                .containsAll(of(PLACEHOLDER_KEY_1, PLACEHOLDER_KEY_2)));
    }

    @Test
    @SqlGroup({@Sql(scripts = "/integration/db-scripts/templates/insert-template.sql",
            executionPhase = BEFORE_TEST_METHOD), @Sql(scripts = "/integration/db-scripts/common/clear.sql",
            executionPhase = AFTER_TEST_METHOD)})
    void given_templateExists_when_findById_then_returnTemplateDto() {
        final var expectedTemplateDto = new TemplateDto(TEMPLATE_ID_1, TEMPLATE_NAME_1, TEMPLATE_PAYLOAD_1);

        final var actualTemplateDto = service.findById(TEMPLATE_ID_1);

        assertEquals(expectedTemplateDto, actualTemplateDto);
    }

    @Test
    @SqlGroup({@Sql(scripts = "/integration/db-scripts/templates/insert-templates.sql",
            executionPhase = BEFORE_TEST_METHOD), @Sql(scripts = "/integration/db-scripts/common/clear.sql",
            executionPhase = AFTER_TEST_METHOD)})
    void given_templatesExist_when_findAll_then_returnTemplateDtoList() {
        final var expectedTemplateDtoList = of(new TemplateDto(TEMPLATE_ID_2, TEMPLATE_NAME_2, TEMPLATE_PAYLOAD_2),
                new TemplateDto(TEMPLATE_ID_3, TEMPLATE_NAME_3, TEMPLATE_PAYLOAD_3));

        final var actualTemplateDtoList = service.findAll();

        assertEquals(expectedTemplateDtoList, actualTemplateDtoList);
    }

    @Test
    @SqlGroup({@Sql(scripts = "/integration/db-scripts/templates/insert-template.sql",
            executionPhase = BEFORE_TEST_METHOD), @Sql(scripts = "/integration/db-scripts/common/clear.sql",
            executionPhase = AFTER_TEST_METHOD)})
    void given_templateExists_when_deleteById_then_delete() {
        service.deleteById(TEMPLATE_ID_1);

        assertFalse(templateRepository.existsById(TEMPLATE_ID_1));
        assertTrue(placeholderKeyRepository
                .findAllByTemplateId(TEMPLATE_ID_1)
                .isEmpty());
    }
}
