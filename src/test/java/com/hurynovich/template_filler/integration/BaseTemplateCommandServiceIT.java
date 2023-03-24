package com.hurynovich.template_filler.integration;

import com.hurynovich.template_filler.command.CreateTemplateCommand;
import com.hurynovich.template_filler.command.DeleteTemplateCommand;
import com.hurynovich.template_filler.command.UpdateTemplateCommand;
import com.hurynovich.template_filler.dto.TemplateDto;
import com.hurynovich.template_filler.entity.PlaceholderKeyEntity;
import com.hurynovich.template_filler.entity.TemplateEntity;
import com.hurynovich.template_filler.entity.TemplateEventEntity;
import com.hurynovich.template_filler.entity.TemplateEventType;
import com.hurynovich.template_filler.integration.test_repository.TestPlaceholderKeyRepository;
import com.hurynovich.template_filler.integration.test_repository.TestTemplateEventRepository;
import com.hurynovich.template_filler.integration.test_repository.TestTemplateRepository;
import com.hurynovich.template_filler.service.TemplateCommandService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.stream.Stream;

import static com.hurynovich.template_filler.entity.PlaceholderKeyEntity_.TEMPLATE;
import static com.hurynovich.template_filler.entity.TemplateEntity_.PLACEHOLDER_KEYS;
import static com.hurynovich.template_filler.entity.TemplateEventEntity_.DATE_TIME;
import static com.hurynovich.template_filler.entity.TemplateEventType.CREATE;
import static com.hurynovich.template_filler.entity.TemplateEventType.DELETE;
import static com.hurynovich.template_filler.entity.TemplateEventType.UPDATE;
import static java.time.temporal.ChronoUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
class BaseTemplateCommandServiceIT {

    private static final Long ID = 1L;
    private static final Long TEMPLATE_ID = 1L;
    private static final Long TEMPLATE_ID_EXISTING = 100L;
    private static final Long PLACEHOLDER_KEY_ID = 1L;
    private static final String TEMPLATE_NAME = "test template name";
    private static final String TEMPLATE_PAYLOAD = "test template payload {{test placeholder key}}";
    private static final String PLACEHOLDER_KEY = "test placeholder key";

    @Autowired
    private TestTemplateEventRepository templateEventRepository;

    @Autowired
    private TestTemplateRepository templateRepository;

    @Autowired
    private TestPlaceholderKeyRepository placeholderKeyRepository;

    @Autowired
    private TemplateCommandService service;

    @Test
    @Sql(scripts = "/integration/db-scripts/common/clear.sql", executionPhase = AFTER_TEST_METHOD)
    void given_createTemplateCommand_when_create_then_returnTemplateDto() {
        final var createTemplateCommand = new CreateTemplateCommand(TEMPLATE_NAME, TEMPLATE_PAYLOAD);
        final var expectedTemplateEventEntity = generateTemplateEventEntity(CREATE, TEMPLATE_ID);
        final var expectedTemplateDto = new TemplateDto(TEMPLATE_ID, TEMPLATE_NAME, TEMPLATE_PAYLOAD);
        final var expectedPlaceholderKeyEntity = generatePlaceholderKeyEntity();
        final var expectedTemplateEntity = generateTemplateEntity(TEMPLATE_ID, expectedPlaceholderKeyEntity);

        final var actualTemplateDto = service.create(createTemplateCommand);

        assertEquals(expectedTemplateDto, actualTemplateDto);
        assertTemplateEventEntity(expectedTemplateEventEntity);
        assertPlaceholderKeyEntities(TEMPLATE_ID, expectedPlaceholderKeyEntity);
        assertTemplateEntity(TEMPLATE_ID, expectedTemplateEntity);
    }

    @Test
    @SqlGroup({@Sql(scripts = "/integration/db-scripts/templates/insert-template.sql",
            executionPhase = BEFORE_TEST_METHOD), @Sql(scripts = "/integration/db-scripts/common/clear.sql",
            executionPhase = AFTER_TEST_METHOD)})
    void given_updateTemplateCommand_when_update_then_returnTemplateDto() {
        final var updateTemplateCommand = new UpdateTemplateCommand(TEMPLATE_ID_EXISTING, TEMPLATE_NAME, TEMPLATE_PAYLOAD);
        final var expectedTemplateEventEntity = generateTemplateEventEntity(UPDATE, TEMPLATE_ID_EXISTING);
        final var expectedTemplateDto = new TemplateDto(TEMPLATE_ID_EXISTING, TEMPLATE_NAME, TEMPLATE_PAYLOAD);
        final var expectedPlaceholderKeyEntity = generatePlaceholderKeyEntity();
        final var expectedTemplateEntity = generateTemplateEntity(TEMPLATE_ID_EXISTING, expectedPlaceholderKeyEntity);

        final var actualTemplateDto = service.update(updateTemplateCommand);

        assertEquals(expectedTemplateDto, actualTemplateDto);
        assertTemplateEventEntity(expectedTemplateEventEntity);
        assertPlaceholderKeyEntities(TEMPLATE_ID_EXISTING, expectedPlaceholderKeyEntity);
        assertTemplateEntity(TEMPLATE_ID_EXISTING, expectedTemplateEntity);
    }

    @Test
    @SqlGroup({@Sql(scripts = "/integration/db-scripts/templates/insert-template.sql",
            executionPhase = BEFORE_TEST_METHOD), @Sql(scripts = "/integration/db-scripts/common/clear.sql",
            executionPhase = AFTER_TEST_METHOD)})
    void given_deleteTemplateCommand_when_delete_then_delete() {
        final var deleteTemplateCommand = new DeleteTemplateCommand(TEMPLATE_ID_EXISTING);
        final var expectedTemplateEventEntity = new TemplateEventEntity();
        expectedTemplateEventEntity.setId(ID);
        expectedTemplateEventEntity.setType(DELETE);
        expectedTemplateEventEntity.setTemplateId(TEMPLATE_ID_EXISTING);

        service.delete(deleteTemplateCommand);

        assertTemplateEventEntity(expectedTemplateEventEntity);
        assertTrue(placeholderKeyRepository
                .findAllByTemplateId(TEMPLATE_ID_EXISTING)
                .isEmpty());
        assertTrue(templateRepository
                .findById(TEMPLATE_ID_EXISTING)
                .isEmpty());
    }

    private TemplateEventEntity generateTemplateEventEntity(final TemplateEventType type, final Long templateId) {
        final var templateEventEntity = new TemplateEventEntity();
        templateEventEntity.setId(ID);
        templateEventEntity.setType(type);
        templateEventEntity.setTemplateId(templateId);
        templateEventEntity.setTemplateName(TEMPLATE_NAME);
        templateEventEntity.setTemplatePayload(TEMPLATE_PAYLOAD);

        return templateEventEntity;
    }

    private PlaceholderKeyEntity generatePlaceholderKeyEntity() {
        final var placeholderKeyEntity = new PlaceholderKeyEntity();
        placeholderKeyEntity.setId(PLACEHOLDER_KEY_ID);
        placeholderKeyEntity.setPlaceholderKey(PLACEHOLDER_KEY);

        return placeholderKeyEntity;
    }

    private TemplateEntity generateTemplateEntity(final Long id, final PlaceholderKeyEntity... placeholderKeyEntities) {
        final var templateEntity = new TemplateEntity();
        templateEntity.setId(id);
        templateEntity.setName(TEMPLATE_NAME);
        templateEntity.setPayload(TEMPLATE_PAYLOAD);
        Stream
                .of(placeholderKeyEntities)
                .forEach(templateEntity::addPlaceholderKey);

        return templateEntity;
    }

    private void assertTemplateEventEntity(final TemplateEventEntity expectedTemplateEventEntity) {
        final var actualTemplateEventEntity = templateEventRepository
                .findById(ID)
                .orElseThrow();
        assertThat(actualTemplateEventEntity)
                .usingRecursiveComparison()
                .ignoringFields(DATE_TIME)
                .isEqualTo(expectedTemplateEventEntity);
        final var actualDateTime = actualTemplateEventEntity.getDateTime();
        assertNotNull(actualDateTime);
        assertThat(actualDateTime).isCloseToUtcNow(within(1, SECONDS));
    }

    private void assertPlaceholderKeyEntities(final Long templateId,
            final PlaceholderKeyEntity expectedPlaceholderKeyEntity) {
        final var actualPlaceholderKeyEntities = placeholderKeyRepository
                .findAllByTemplateId(templateId);
        assertFalse(actualPlaceholderKeyEntities.isEmpty());
        assertEquals(actualPlaceholderKeyEntities.size(), 1);
        assertThat(actualPlaceholderKeyEntities.get(0))
                .usingRecursiveComparison()
                .ignoringFields(TEMPLATE)
                .isEqualTo(expectedPlaceholderKeyEntity);
    }

    private void assertTemplateEntity(final Long templateId, final TemplateEntity expectedTemplateEntity) {
        final var actualTemplateEntity = templateRepository
                .findById(templateId)
                .orElseThrow();
        assertThat(actualTemplateEntity)
                .usingRecursiveComparison()
                .ignoringFields(PLACEHOLDER_KEYS)
                .isEqualTo(expectedTemplateEntity);
    }
}
