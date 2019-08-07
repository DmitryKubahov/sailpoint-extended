package com.sailpoint.extension.config.liquibase;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * Config properties for liquibase bean
 */
@Data
public class LiquibaseConfig {

    /**
     * Driver class name property value
     */
    @Value("${liquibase.datasource.driverClassName}")
    private String driverClassName;

    /**
     * Url to data base property value
     */
    @Value("${liquibase.datasource.url}")
    private String url;

    /**
     * User name for connection
     */
    @Value("${liquibase.datasource.user}")
    private String user;

    /**
     * Password of user for connection
     */
    @Value("${liquibase.datasource.password}")
    private String password;

}
