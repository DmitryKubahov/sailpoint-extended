package com.sailpoint.extended.config.liquibase;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * Configuration for liquibase
 */
@Configuration("liquibaseConfiguration")
public class LiquibaseConfiguration {

    /**
     * Name of liquibase bean name for sailpoint DB
     */
    public static final String LIQUIBASE_BEAN_NAME = "sailpointDBLiquibase";

    /**
     * Creates config bean for liquibase
     *
     * @return liquibase config instance
     */
    @Bean
    public LiquibaseConfig liquibaseConfig() {
        return new LiquibaseConfig();
    }

    /**
     * Create liquibase spring integration bean. Need to create BEFORE
     *
     * @return liquibase bean instance
     */
    @Bean(LiquibaseConfiguration.LIQUIBASE_BEAN_NAME)
    public SpringLiquibase liquibase(LiquibaseConfig liquibaseConfig) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:liquibase/db.changelog-master.xml");
        liquibase.setDataSource(liquibaseDataSource(liquibaseConfig));
        return liquibase;
    }

    /**
     * Creates dataSource bean for liquibase
     *
     * @return liquibase datasource instance
     */
    @Bean
    public DataSource liquibaseDataSource(LiquibaseConfig liquibaseConfig) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(liquibaseConfig.getDriverClassName());
        dataSource.setUrl(liquibaseConfig.getUrl());
        dataSource.setUsername(liquibaseConfig.getUser());
        dataSource.setPassword(liquibaseConfig.getPassword());
        return dataSource;
    }
}
