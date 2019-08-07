package com.sailpoint.extension.model.repository.custom;

import com.sailpoint.extension.model.entity.custom.CustomEntityJpa;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA repository for {@link CustomEntityJpa} entity
 */
public interface CustomEntityJpaRepository extends JpaRepository<CustomEntityJpa, String> {
}
