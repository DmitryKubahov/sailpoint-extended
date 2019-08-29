package com.sailpoint.extended.config.rest;

import com.sailpoint.extended.controller.CustomRestController;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

/**
 * Uses to extend parameter of external rest api to include custom controllers
 */
@Slf4j
public class SailPointExternalRestWebApplicationInitializer implements WebApplicationInitializer {

    /**
     * External rest api servlet name
     */
    private static final String EXTERNAL_REST_SERVLET_NAME = "JAX-RS External REST Servlet";
    /**
     * Rest api servlet name
     */
    private static final String REST_SERVLET_NAME = "JAX-RS REST Servlet";

    /**
     * Try to get servlet registration for EXTERNAL REST API servlet and replace {@link ServletProperties#JAXRS_APPLICATION_CLASS}
     *
     * @param servletContext - current servlet context
     */
    @Override
    public void onStartup(ServletContext servletContext) {
        log.debug("Try to get external servlet by name:[{}]", REST_SERVLET_NAME);
        ServletRegistration registration = servletContext.getServletRegistration(REST_SERVLET_NAME);
        if (registration == null) {
            log.error("Could not find external servlet registration. External api will not work!!!!");
            return;
        }
        registration.setInitParameter(ServerProperties.PROVIDER_PACKAGES,
                CustomRestController.class.getPackage().getName());
    }
}
