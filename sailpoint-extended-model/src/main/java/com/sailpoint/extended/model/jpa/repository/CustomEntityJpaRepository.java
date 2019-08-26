package com.sailpoint.extended.model.jpa.repository;

import com.sailpoint.extended.model.jpa.entity.CustomEntityJpa;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA repository for {@link CustomEntityJpa} entity
 */
public interface CustomEntityJpaRepository extends JpaRepository<CustomEntityJpa, String> {
}
