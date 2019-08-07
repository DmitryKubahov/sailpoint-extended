package com.sailpoint.extension.config.project;

import com.sailpoint.extension.config.jpa.CustomEntityJpaConfiguration;
import com.sailpoint.extension.config.legacy.SailpointConfiguration;
import com.sailpoint.extension.config.liquibase.LiquibaseConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * Main project configuration
 */
@Configuration
@Import({
        LiquibaseConfiguration.class,
        SailpointConfiguration.class,
        CustomEntityJpaConfiguration.class
})
@PropertySource("classpath:application.properties")
public class SailPointExtensionMainConfiguration {
}
