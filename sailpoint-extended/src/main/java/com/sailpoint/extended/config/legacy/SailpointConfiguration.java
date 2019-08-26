package com.sailpoint.extended.config.legacy;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Standard java configuration for sailpoint
 */
@Configuration
@ImportResource({"classpath:iiqBeans.xml"})
public class SailpointConfiguration {

}
