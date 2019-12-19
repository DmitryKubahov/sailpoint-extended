package com.sailpoint.extended.config.project;

import com.sailpoint.extended.config.legacy.SailpointConfiguration;
import com.sailpoint.extended.spring.SpringContextProvider;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Main project configuration, set in {@link iiqBeansExtended.xml}
 */
@Configuration
@Import({
        SailpointConfiguration.class,
})
@ComponentScan(basePackageClasses = {
        SpringContextProvider.class
})
public class SailPointSpringExtendedMainConfiguration {
}
