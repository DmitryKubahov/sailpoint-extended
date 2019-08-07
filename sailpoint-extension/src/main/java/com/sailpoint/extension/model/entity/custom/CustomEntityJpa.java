package com.sailpoint.extension.model.entity.custom;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

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
     * Collection of child elements
     */
    @OneToMany(mappedBy = "parent")
    private List<CustomEntityChildJpa> child;
}
