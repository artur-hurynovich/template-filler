package com.hurynovich.template_filler.converter.impl;

import com.hurynovich.template_filler.converter.PlaceholderKeyServiceConverter;
import com.hurynovich.template_filler.dto.CreateTemplateEventDto;
import com.hurynovich.template_filler.dto.DeleteTemplateEventDto;
import com.hurynovich.template_filler.dto.UpdateTemplateEventDto;
import com.hurynovich.template_filler.entity.PlaceholderKeyEntity;
import com.hurynovich.template_filler.entity.TemplateEntity;
import com.hurynovich.template_filler.entity.TemplateEventEntity;
import com.hurynovich.template_filler.entity.TemplateEventType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static com.hurynovich.template_filler.entity.TemplateEventType.CREATE;
import static com.hurynovich.template_filler.entity.TemplateEventType.DELETE;
import static com.hurynovich.template_filler.entity.TemplateEventType.UPDATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BaseTemplateEventConverterTest {

    private static final Long ID = 941L;
    private static final Long TEMPLATE_ID = 2009L;
    private static final String TEMPLATE_NAME = "test template name";
    private static final String TEMPLATE_PAYLOAD = "test template payload";
    private static final LocalDateTime DATE_TIME = LocalDateTime.of(2023, 3, 12, 20, 9, 10);

    private static final String PLACEHOLDER_KEY_1 = "test placeholder key 1";
    private static final String PLACEHOLDER_KEY_2 = "test placeholder key 2";

    @Mock
    private PlaceholderKeyServiceConverter placeholderKeyConverter;

    @InjectMocks
    private BaseTemplateEventConverter converter;

    @Test
    void given_templateEventEntity_when_convertToCreateTemplateEvent_then_returnCreateTemplateEventDto() {
        final var templateEventEntity = generateTemplateEventEntity(CREATE);
        final var expectedCreateTemplateEventDto = new CreateTemplateEventDto(ID, TEMPLATE_ID, TEMPLATE_NAME, TEMPLATE_PAYLOAD);

        final var actualCreateTemplateEventDto = converter.convertToCreateTemplateEvent(templateEventEntity);

        assertEquals(expectedCreateTemplateEventDto, actualCreateTemplateEventDto);
    }

    @Test
    void given_templateEventEntity_when_convertToUpdateTemplateEvent_then_returnUpdateTemplateEventDto() {
        final var templateEventEntity = generateTemplateEventEntity(UPDATE);
        final var expectedUpdateTemplateEventDto = new UpdateTemplateEventDto(ID, TEMPLATE_ID, TEMPLATE_NAME, TEMPLATE_PAYLOAD);

        final var actualUpdateTemplateEventDto = converter.convertToUpdateTemplateEvent(templateEventEntity);

        assertEquals(expectedUpdateTemplateEventDto, actualUpdateTemplateEventDto);
    }

    @Test
    void given_templateEventEntity_when_convertToDeleteTemplateEvent_then_returnDeleteTemplateEventDto() {
        final var templateEventEntity = generateTemplateEventEntity(DELETE);
        final var expectedDeleteTemplateEventDto = new DeleteTemplateEventDto(ID, TEMPLATE_ID);

        final var actualDeleteTemplateEventDto = converter.convertToDeleteTemplateEvent(templateEventEntity);

        assertEquals(expectedDeleteTemplateEventDto, actualDeleteTemplateEventDto);
    }

    @Test
    void given_createTemplateEventDtoAndPlaceholderKeys_when_convert_then_returnTemplateEntity() {
        final var createTemplateEventDto = new CreateTemplateEventDto(ID, TEMPLATE_ID, TEMPLATE_NAME, TEMPLATE_PAYLOAD);
        final var placeholderKeyEntity1 = new PlaceholderKeyEntity();
        placeholderKeyEntity1.setPlaceholderKey(PLACEHOLDER_KEY_1);
        final var placeholderKeyEntity2 = new PlaceholderKeyEntity();
        placeholderKeyEntity2.setPlaceholderKey(PLACEHOLDER_KEY_2);
        final var expectedTemplateEntity = new TemplateEntity();
        expectedTemplateEntity.setId(TEMPLATE_ID);
        expectedTemplateEntity.setName(TEMPLATE_NAME);
        expectedTemplateEntity.setPayload(TEMPLATE_PAYLOAD);
        expectedTemplateEntity.addPlaceholderKey(placeholderKeyEntity1);
        expectedTemplateEntity.addPlaceholderKey(placeholderKeyEntity2);
        when(placeholderKeyConverter.convert(PLACEHOLDER_KEY_1)).thenReturn(placeholderKeyEntity1);
        when(placeholderKeyConverter.convert(PLACEHOLDER_KEY_2)).thenReturn(placeholderKeyEntity2);

        final var actualTemplateEntity = converter.convert(createTemplateEventDto, List.of(PLACEHOLDER_KEY_1, PLACEHOLDER_KEY_2));

        assertThat(actualTemplateEntity)
                .usingRecursiveComparison()
                .isEqualTo(expectedTemplateEntity);
    }

    @Test
    void given_updateTemplateEventDtoAndPlaceholderKeys_when_convert_then_returnTemplateEntity() {
        final var updateTemplateEventDto = new UpdateTemplateEventDto(ID, TEMPLATE_ID, TEMPLATE_NAME, TEMPLATE_PAYLOAD);
        final var placeholderKeyEntity1 = new PlaceholderKeyEntity();
        placeholderKeyEntity1.setPlaceholderKey(PLACEHOLDER_KEY_1);
        final var placeholderKeyEntity2 = new PlaceholderKeyEntity();
        placeholderKeyEntity2.setPlaceholderKey(PLACEHOLDER_KEY_2);
        final var expectedTemplateEntity = new TemplateEntity();
        expectedTemplateEntity.setId(TEMPLATE_ID);
        expectedTemplateEntity.setName(TEMPLATE_NAME);
        expectedTemplateEntity.setPayload(TEMPLATE_PAYLOAD);
        expectedTemplateEntity.addPlaceholderKey(placeholderKeyEntity1);
        expectedTemplateEntity.addPlaceholderKey(placeholderKeyEntity2);
        when(placeholderKeyConverter.convert(PLACEHOLDER_KEY_1)).thenReturn(placeholderKeyEntity1);
        when(placeholderKeyConverter.convert(PLACEHOLDER_KEY_2)).thenReturn(placeholderKeyEntity2);

        final var actualTemplateEntity = converter.convert(updateTemplateEventDto, List.of(PLACEHOLDER_KEY_1, PLACEHOLDER_KEY_2));

        assertThat(actualTemplateEntity)
                .usingRecursiveComparison()
                .isEqualTo(expectedTemplateEntity);
    }

    private TemplateEventEntity generateTemplateEventEntity(final TemplateEventType type) {
        final var templateEventEntity = new TemplateEventEntity();
        templateEventEntity.setId(ID);
        templateEventEntity.setType(type);
        templateEventEntity.setTemplateId(TEMPLATE_ID);
        templateEventEntity.setTemplateName(TEMPLATE_NAME);
        templateEventEntity.setTemplatePayload(TEMPLATE_PAYLOAD);
        templateEventEntity.setDateTime(DATE_TIME);

        return templateEventEntity;
    }
}
