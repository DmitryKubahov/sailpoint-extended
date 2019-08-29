package com.sailpoint.extended.controller.test.model.custom;

import com.sailpoint.extended.controller.model.custom.CustomEntityRestController;
import com.sailpoint.extended.controller.model.custom.entity.CustomEntity;
import com.sailpoint.extended.controller.model.custom.mapper.CustomEntityMapper;
import com.sailpoint.extended.model.jpa.entity.CustomEntityJpa;
import com.sailpoint.extended.model.jpa.repository.CustomEntityJpaRepository;
import com.sailpoint.extended.spring.SpringContextProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import sailpoint.tools.InvalidParameterException;
import sailpoint.tools.ObjectNotFoundException;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test for {@link CustomEntityRestController}
 */
class CustomEntityRestControllerTest {

    /**
     * Instance to test
     */
    private CustomEntityRestController customEntityRestController;
    /**
     * Custom entity mapper
     */
    private CustomEntityMapper customEntityMapper;
    /**
     * Custom entity jpa repository
     */
    private CustomEntityJpaRepository customEntityJpaRepository;
    /**
     * Spring application context
     */
    private ApplicationContext applicationContext;

    /**
     * Init all necessary instance before each tests
     */
    @BeforeEach
    void init() {
        this.customEntityMapper = mock(CustomEntityMapper.class);
        this.customEntityJpaRepository = mock(CustomEntityJpaRepository.class);
        this.applicationContext = mock(ApplicationContext.class);
        this.customEntityRestController = new CustomEntityRestController(this.customEntityMapper);

        SpringContextProvider springContextProvider = new SpringContextProvider();
        springContextProvider.setApplicationContext(this.applicationContext);
    }

    /**
     * Testing getting custom entity by id
     * Input:
     * - valid id
     * Output:
     * - custom entity
     * Expectation:
     * - calling getBean in application context for custom entity jps repository
     * - calling findOne in repository
     * - calling mapToCustomEntity in mapper
     */
    @Test
    @Disabled
    void gettingCustomEntityById() throws InvalidParameterException, ObjectNotFoundException {
        CustomEntity expected = buildCustomEntity();
        CustomEntityJpa customEntityJpa = buildCustomEntityJpa();

        when(this.applicationContext.getBean(eq(CustomEntityJpaRepository.class)))
                .thenReturn(this.customEntityJpaRepository);
        when(this.customEntityJpaRepository.findOne(eq(customEntityJpa.getId())))
                .thenReturn(customEntityJpa);
        when(this.customEntityMapper.mapToCustomEntity(eq(customEntityJpa))).thenReturn(expected);

        CustomEntity result = this.customEntityRestController.getCustomEntity(customEntityJpa.getId());
        assertEquals(expected, result, "Result is not match to expected value");

        verify(this.applicationContext).getBean(eq(CustomEntityJpaRepository.class));
        verify(this.customEntityJpaRepository).findOne(eq(customEntityJpa.getId()));
        verify(this.customEntityMapper).mapToCustomEntity(eq(customEntityJpa));
    }

    /**
     * Testing getting custom entity by invalid id
     * Input:
     * - null as id
     * Expectation:
     * - InvalidParameterException exception
     * - no calling getBean in application context for custom entity jps repository
     * - no calling findOne in repository
     * - no calling mapToCustomEntity in mapper
     */
    @Test
    @Disabled
    void gettingCustomEntityByNullId() {
        assertThrows(InvalidParameterException.class, () -> this.customEntityRestController.getCustomEntity(null),
                "Null id must cause InvalidParameterException");
        verify(this.applicationContext, never()).getBean((Class<Object>) any());
        verify(this.customEntityJpaRepository, never()).findOne(anyString());
        verify(this.customEntityMapper, never()).mapToCustomEntity(any());
    }

    /**
     * Testing getting custom entity by invalid id
     * Input:
     * - empty id
     * Expectation:
     * - InvalidParameterException exception
     * - no calling getBean in application context for custom entity jps repository
     * - no calling findOne in repository
     * - no calling mapToCustomEntity in mapper
     */
    @Test
    @Disabled
    void gettingCustomEntityByEmptyId() {
        assertThrows(InvalidParameterException.class, () -> this.customEntityRestController.getCustomEntity("       "),
                "Empty id must cause InvalidParameterException");
        verify(this.applicationContext, never()).getBean((Class<Object>) any());
        verify(this.customEntityJpaRepository, never()).findOne(anyString());
        verify(this.customEntityMapper, never()).mapToCustomEntity(any());
    }

    /**
     * Testing getting custom entity by invalid id
     * Input:
     * - unknown id
     * Expectation:
     * - ObjectNotFoundException exception
     * - calling getBean in application context for custom entity jps repository
     * - calling findOne in repository
     * - no calling mapToCustomEntity in mapper
     */
    @Test
    @Disabled
    void gettingCustomEntityByUnknownId() {
        String id = UUID.randomUUID().toString();

        when(this.applicationContext.getBean(eq(CustomEntityJpaRepository.class)))
                .thenReturn(this.customEntityJpaRepository);
        when(this.customEntityJpaRepository.findOne(eq(id))).thenReturn(null);

        assertThrows(ObjectNotFoundException.class, () -> this.customEntityRestController.getCustomEntity(id),
                "Unknown id must cause ObjectNotFoundException");
        verify(this.applicationContext).getBean(eq(CustomEntityJpaRepository.class));
        verify(this.customEntityJpaRepository).findOne(eq(id));
        verify(this.customEntityMapper, never()).mapToCustomEntity(any());
    }

    /**
     * Build custom entity with random data
     *
     * @return custom entity instance
     */
    private CustomEntity buildCustomEntity() {
        CustomEntity customEntity = new CustomEntity();
        customEntity.setId(UUID.randomUUID().toString());
        customEntity.setName(UUID.randomUUID().toString());
        customEntity.setDateOfBirth(UUID.randomUUID().toString());
        return customEntity;
    }

    /**
     * Build custom entity jap with random data
     *
     * @return custom entity jpa instance
     */
    private CustomEntityJpa buildCustomEntityJpa() {
        CustomEntityJpa customEntityJpa = new CustomEntityJpa();
        customEntityJpa.setId(UUID.randomUUID().toString());
        customEntityJpa.setName(UUID.randomUUID().toString());
        customEntityJpa.setDateOfBirth(LocalDateTime.now());
        return customEntityJpa;
    }
}
