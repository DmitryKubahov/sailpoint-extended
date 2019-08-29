package com.sailpoint.extended.controller.model.custom.mapper;

import com.sailpoint.extended.controller.model.custom.entity.CustomEntity;
import com.sailpoint.extended.mapper.CommonMapper;
import com.sailpoint.extended.model.jpa.entity.CustomEntityJpa;
import com.sailpoint.extended.model.jpa.entity.CustomEntityJpa_;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Custom entity mapper
 */
@Mapper(uses = {
        CommonMapper.class
})
public interface CustomEntityMapper {

    /**
     * Map {@link CustomEntityJpa} to {@link CustomEntity}
     *
     * @param customEntityJpa - instance of custom entity jpa
     * @return instance of custom entity from custom entity jps
     */
    @Mapping(target = CustomEntityJpa_.ID, source = CustomEntityJpa_.ID)
    @Mapping(target = CustomEntityJpa_.NAME, source = CustomEntityJpa_.NAME)
    @Mapping(target = CustomEntityJpa_.DATE_OF_BIRTH, source = CustomEntityJpa_.DATE_OF_BIRTH,
            qualifiedByName = CommonMapper.LOCAL_DATE_TIME_TO_ISO_LOCAL_DATE_QUALIFIER_NAME)
    CustomEntity mapToCustomEntity(CustomEntityJpa customEntityJpa);
}
