package com.sailpoint.extension.model.entity.custom;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Test class for test_custom_entity_child_table table
 */
@Data
@Entity
@Table(name = "test_custom_entity_child_table")
public class CustomEntityChildJpa {
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
    @ManyToOne
    @JoinColumn(name = "custom_entity_id")
    private CustomEntityJpa parent;

}
