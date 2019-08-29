package com.sailpoint.extended.controller.model.custom;

import com.sailpoint.extended.controller.model.custom.entity.CustomEntity;
import com.sailpoint.extended.controller.model.custom.mapper.CustomEntityMapper;
import com.sailpoint.extended.model.jpa.entity.CustomEntityJpa;
import com.sailpoint.extended.model.jpa.repository.CustomEntityJpaRepository;
import com.sailpoint.extended.spring.SpringContextProvider;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import sailpoint.rest.BaseResource;
import sailpoint.tools.InvalidParameterException;
import sailpoint.tools.ObjectNotFoundException;
import sailpoint.tools.Util;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * Controller for custom entity
 */
@Slf4j
@Path("custom")
public class CustomEntityRestController extends BaseResource {

    /**
     * Mapper for custom entity
     */
    private final CustomEntityMapper customEntityMapper;

    /**
     * Instantiates instance without parent and with default custom entity mapper
     */
    public CustomEntityRestController() {
        this(Mappers.getMapper(CustomEntityMapper.class));
    }

    /**
     * Instantiates instance without parent and passed custom entity mapper instance
     *
     * @param customEntityMapper - instance of custom entity mapper
     */
    public CustomEntityRestController(CustomEntityMapper customEntityMapper) {
        this.customEntityMapper = customEntityMapper;
    }

    /**
     * Instantiates instance with parent and default custom entity mapper
     *
     * @param parent - parent instance for current controller
     */
    public CustomEntityRestController(BaseResource parent) {
        this(parent, Mappers.getMapper(CustomEntityMapper.class));
    }

    /**
     * Instantiates instance with passed parent and custom entity mapper
     *
     * @param parent             - parent instance for current controller
     * @param customEntityMapper - instance of custom entity mapper
     */
    public CustomEntityRestController(BaseResource parent, CustomEntityMapper customEntityMapper) {
        super(parent);
        this.customEntityMapper = customEntityMapper;
    }

    /**
     * Get custom entity by id, If not found -> throw error {@link ObjectNotFoundException}
     */
    @GET
    @Path("entity/{id}")
    public CustomEntity getCustomEntity(@PathParam("id") String id)
            throws InvalidParameterException, ObjectNotFoundException {
        log.debug("Try to find custom entity by id:[{}]", id);
        if (Util.isNullOrEmpty(id)) {
            log.debug("Passed id is empty");
            throw new InvalidParameterException();
        }
        log.debug("Getting repository bean from spring");
        CustomEntityJpaRepository repository = SpringContextProvider.getApplicationContext()
                .getBean(CustomEntityJpaRepository.class);
        CustomEntityJpa customEntityJpa = repository.findOne(id);
        if (customEntityJpa == null) {
            log.debug("Could not find custom entity jpa by id:[{}]", id);
            throw new ObjectNotFoundException(CustomEntityJpa.class, id);
        }
        log.debug("Map custom entity jpa to custom entity");
        return customEntityMapper.mapToCustomEntity(customEntityJpa);
    }
}
