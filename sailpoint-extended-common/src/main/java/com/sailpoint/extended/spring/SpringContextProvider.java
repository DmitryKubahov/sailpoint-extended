package com.sailpoint.extended.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Spring context static provider
 */
@Component
public class SpringContextProvider implements ApplicationContextAware {

    /**
     * Spring application context instance
     */
    private static ApplicationContext ctx;

    /**
     * Get current spring context instance
     *
     * @return spring context instance
     */
    public static ApplicationContext getApplicationContext() {
        return ctx;
    }

    /**
     * Set context via initialization
     *
     * @param context - current spring context
     * @throws BeansException exception during setting context
     */
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        ctx = context;
    }
}
