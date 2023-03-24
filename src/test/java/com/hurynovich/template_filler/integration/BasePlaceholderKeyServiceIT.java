package com.hurynovich.template_filler.integration;

import com.hurynovich.template_filler.dto.PlaceholderKeyDto;
import com.hurynovich.template_filler.service.PlaceholderKeyService;
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
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
class BasePlaceholderKeyServiceIT {

    private static final Long TEMPLATE_ID = 100L;

    private static final Long ID_1 = 111L;
    private static final String PLACEHOLDER_KEY_1 = "test placeholder key 1";
    private static final Long ID_2 = 222L;
    private static final String PLACEHOLDER_KEY_2 = "test placeholder key 2";

    @Autowired
    private PlaceholderKeyService service;

    @Test
    @SqlGroup({@Sql(scripts = "/integration/db-scripts/templates/insert-template.sql",
            executionPhase = BEFORE_TEST_METHOD), @Sql(scripts = "/integration/db-scripts/common/clear.sql",
            executionPhase = AFTER_TEST_METHOD)})
    void given_placeholderKeysExist_when_findAllByTemplateId_then_returnPlaceholderKeyDtoList() {
        final var expectedPlaceholderKeyDto1 = new PlaceholderKeyDto(ID_1, PLACEHOLDER_KEY_1);
        final var expectedPlaceholderKeyDto2 = new PlaceholderKeyDto(ID_2, PLACEHOLDER_KEY_2);

        final var actualPlaceholderKeyDtoList = service.findAllByTemplateId(TEMPLATE_ID);

        assertEquals(of(expectedPlaceholderKeyDto1, expectedPlaceholderKeyDto2), actualPlaceholderKeyDtoList);
    }
}
