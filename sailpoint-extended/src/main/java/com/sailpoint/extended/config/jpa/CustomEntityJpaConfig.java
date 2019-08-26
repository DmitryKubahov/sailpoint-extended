package com.sailpoint.extended.config.jpa;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * Custom jpa configs
 */
@Data
public class CustomEntityJpaConfig {

    /**
     * Driver class name property value
     */
    @Value("${customjpa.datasource.driverClassName}")
    private String driverClassName;

    /**
     * Url to data base property value
     */
    @Value("${customjpa.datasource.url}")
    private String url;

    /**
     * User name for connection
     */
    @Value("${customjpa.datasource.user}")
    private String user;

    /**
     * Password of user for connection
     */
    @Value("${customjpa.datasource.password}")
    private String password;

    /**
     * DDL auto value property. By default - none
     */
    @Value("${customjpa.hibernate.ddl.auto:none}")
    private String ddlAuto;

    /**
     * Hibernate dialect value
     */
    @Value("${customjpa.hibernate.dialect}")
    private String dialect;
}
