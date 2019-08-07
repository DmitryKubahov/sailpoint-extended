package com.sailpoint.extension.model.repository.custom;

import com.sailpoint.extension.model.entity.custom.CustomEntityChildJpa;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA repository for {@link CustomEntityChildJpa} entity
 */
public interface CustomEntityChildJpaRepository extends JpaRepository<CustomEntityChildJpa, String> {
}
