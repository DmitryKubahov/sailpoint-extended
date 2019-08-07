package com.sailpoint.extension.config.legacy;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({
        "classpath:customIiqBeans.xml"
})
public class SailpointConfiguration {

}
