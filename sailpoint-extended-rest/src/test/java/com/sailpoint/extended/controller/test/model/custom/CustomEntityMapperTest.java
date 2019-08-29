package com.sailpoint.extended.controller.test.model.custom;

import com.sailpoint.extended.controller.model.custom.entity.CustomEntity;
import com.sailpoint.extended.controller.model.custom.mapper.CustomEntityMapper;
import com.sailpoint.extended.model.jpa.entity.CustomEntityJpa;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Test for {@link CustomEntityMapper}
 */
class CustomEntityMapperTest {

    /**
     * Instance to test
     */
    private final CustomEntityMapper customEntityMapper = Mappers.getMapper(CustomEntityMapper.class);

    /**
     * Test mapping.
     * Input:
     * - valid custom entity jpa with all properties
     * Output:
     * - custom entity with the same properties as custom entity jpa
     */
    @Test
    void mappingTest() {
        CustomEntityJpa expected = new CustomEntityJpa();
        expected.setId(UUID.randomUUID().toString());
        expected.setName(UUID.randomUUID().toString());
        expected.setDateOfBirth(LocalDateTime.now());

        CustomEntity actual = customEntityMapper.mapToCustomEntity(expected);

        assertEquals(expected.getId(), actual.getId(), "Ids after mapping are not equals");
        assertEquals(expected.getName(), actual.getName(), "Names after mapping are not equals");
        assertEquals(expected.getDateOfBirth().format(DateTimeFormatter.ISO_LOCAL_DATE), actual.getDateOfBirth(),
                "Dates of birth after mapping are not equals");
    }

    /**
     * Test mapping.
     * Input:
     * - valid custom entity jpa with all properties = NULL
     * Output:
     * - custom entity with all properties == NULL
     */
    @Test
    void mappingCustomEntityJpaPropertiesNullTest() {
        CustomEntityJpa expected = new CustomEntityJpa();
        expected.setId(null);
        expected.setName(null);
        expected.setDateOfBirth(null);

        CustomEntity actual = customEntityMapper.mapToCustomEntity(expected);

        assertNull(actual.getId(), "Id must be null");
        assertNull(actual.getName(), "Name must be null");
        assertNull(actual.getDateOfBirth(), "Date of birth must be null");
    }

    /**
     * Test mapping.
     * Input:
     * - null as custom entity jpa
     * Output:
     * - null
     */
    @Test
    void mappingNullCustomEntityJpaTest() {
        assertNull(customEntityMapper.mapToCustomEntity(null));
    }
}
