package com.sailpoint.extended.config.project;

import com.sailpoint.extended.config.jpa.CustomEntityJpaConfiguration;
import com.sailpoint.extended.config.legacy.SailpointConfiguration;
import com.sailpoint.extended.spring.SpringContextProvider;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * Main project configuration, set in {@link iiqBeansExtended.xml}
 */
@Configuration
@Import({
        //LiquibaseConfiguration.class,
        SailpointConfiguration.class,
        CustomEntityJpaConfiguration.class
})
@ComponentScan(basePackageClasses = {
        SpringContextProvider.class
})
@PropertySource("classpath:application.properties")
public class SailPointSpringExtendedMainConfiguration {
}
