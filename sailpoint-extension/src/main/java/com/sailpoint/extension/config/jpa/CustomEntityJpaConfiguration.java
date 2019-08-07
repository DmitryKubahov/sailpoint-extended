package com.sailpoint.extension.config.jpa;

import com.sailpoint.extension.config.liquibase.LiquibaseConfig;
import com.sailpoint.extension.model.entity.custom.CustomEntityJpa;
import com.sailpoint.extension.model.repository.custom.CustomEntityJpaRepository;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Test custom jpa configuration
 */
@Configuration
@EnableJpaRepositories(
        basePackageClasses = CustomEntityJpaRepository.class,
        transactionManagerRef = CustomEntityJpaConfiguration.CUSTOM_JPA_ENTITY_TRANSACTION_MANAGER_NAME,
        entityManagerFactoryRef = CustomEntityJpaConfiguration.CUSTOM_JPA_ENTITY_MANAGER_FACTORY_NAME
)
@EnableTransactionManagement
public class CustomEntityJpaConfiguration {

    /**
     * Custom jpa entity transaction manager name
     */
    public static final String CUSTOM_JPA_ENTITY_TRANSACTION_MANAGER_NAME = "customJpaEntityTransactionManager";
    /**
     * Custom jpa entity manager factory bean name
     */
    public static final String CUSTOM_JPA_ENTITY_MANAGER_FACTORY_NAME = "customJpaEntityManagerFactory";

    /**
     * Creates bean with all configs parameters for custom object
     *
     * @return configs bean for custom jpa
     */
    @Bean
    public CustomEntityJpaConfig customEntityJpaConfig() {
        return new CustomEntityJpaConfig();
    }

    /**
     * Creates jpa entity manager factory for custom jpa entities
     *
     * @return entity manager factory instance
     */
    @Bean(CUSTOM_JPA_ENTITY_MANAGER_FACTORY_NAME)
    public LocalContainerEntityManagerFactoryBean customJpaEntityManager(CustomEntityJpaConfig customEntityJpaConfig) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(customJpaEntityDataSource(customEntityJpaConfig));

        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(jpaVendorAdapter);
        em.setPackagesToScan(CustomEntityJpa.class.getPackage().getName());
        em.setJpaProperties(getCustomJpaProperties(customEntityJpaConfig));

        return em;
    }

    /**
     * Creates transaction manager for custom jpa entities
     *
     * @param customJpaEntityManager - jpa entity manager factory for custom jpa entities
     * @return transaction manager for custom jpa entities factory
     */
    @Bean(CUSTOM_JPA_ENTITY_TRANSACTION_MANAGER_NAME)
    public PlatformTransactionManager customJpaEntityTransactionManager(
            @Qualifier(CUSTOM_JPA_ENTITY_MANAGER_FACTORY_NAME) EntityManagerFactory customJpaEntityManager) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(customJpaEntityManager);
        return transactionManager;
    }

    /**
     * Creates dataSource custom jpa entity factory
     *
     * @return dataSource custom jpa entity factory bean
     */
    @Bean
    public DataSource customJpaEntityDataSource(CustomEntityJpaConfig customEntityJpaConfig) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(customEntityJpaConfig.getDriverClassName());
        dataSource.setUrl(customEntityJpaConfig.getUrl());
        dataSource.setUsername(customEntityJpaConfig.getUser());
        dataSource.setPassword(customEntityJpaConfig.getPassword());
        return dataSource;
    }

    /**
     * Defines jpa properties for custom jpa entities
     *
     * @param customEntityJpaConfig - current configs for custom jpa entities
     * @return properties jpa
     */
    private Properties getCustomJpaProperties(CustomEntityJpaConfig customEntityJpaConfig) {
        Properties customJpaProperties = new Properties();
        customJpaProperties.setProperty(AvailableSettings.HBM2DDL_AUTO, customEntityJpaConfig.getDdlAuto());
        customJpaProperties.setProperty(AvailableSettings.DIALECT, customEntityJpaConfig.getDialect());
        return customJpaProperties;
    }
}
