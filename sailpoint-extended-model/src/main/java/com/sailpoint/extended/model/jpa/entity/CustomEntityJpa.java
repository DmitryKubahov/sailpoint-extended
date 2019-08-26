package com.sailpoint.extended.model.jpa.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * Test class for test_custom_entity_table table
 */
@Data
@Entity
@Table(name = "test_custom_entity_table")
public class CustomEntityJpa {

    /**
     * Identifier entity property
     */
    @Id
    @GeneratedValue
    @Column(name = "id")
    private String id;

    /**
     * Name property
     */
    @Column(name = "name")
    private String name;

    /**
     * Date of birth property
     */
    @Column(name = "date_of_birth")
    private LocalDateTime dateOfBirth;
}
