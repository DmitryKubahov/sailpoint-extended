package com.sailpoint.extended.controller.model.custom.entity;

import lombok.Data;

/**
 * Custom jpa entity class for JSON mapping
 */
@Data
public class CustomEntity {

    /**
     * Identifier entity property
     */
    private String id;

    /**
     * Name property
     */
    private String name;

    /**
     * Date of birth string representation
     */
    private String dateOfBirth;
}
