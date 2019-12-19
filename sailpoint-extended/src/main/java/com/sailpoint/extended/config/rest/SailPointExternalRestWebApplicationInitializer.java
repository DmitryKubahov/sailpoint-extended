package com.sailpoint.extended.config.rest;

import com.sailpoint.extended.controller.external.ExternalRestController;
import com.sailpoint.extended.controller.internal.InternalRestController;
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
     * External rest api servlet name for identityiq. All calls via '/external/rest/*' path
     */
    private static final String EXTERNAL_REST_SERVLET_NAME = "JAX-RS External REST Servlet";
    /**
     * Rest api servlet name. All calls via '/rest/*' path
     */
    private static final String INTERNAL_REST_SERVLET_NAME = "JAX-RS REST Servlet";

    /**
     * Try to get servlet registration for EXTERNAL REST API servlet and replace {@link ServletProperties#JAXRS_APPLICATION_CLASS}
     *
     * @param servletContext - current servlet context
     */
    @Override
    public void onStartup(ServletContext servletContext) {
        addPackageToServlet(servletContext, INTERNAL_REST_SERVLET_NAME,
                InternalRestController.class.getPackage().getName());
        addPackageToServlet(servletContext, EXTERNAL_REST_SERVLET_NAME,
                ExternalRestController.class.getPackage().getName());
    }

    /**
     * Try to get servlet by passed name and add property {@link ServerProperties#PROVIDER_PACKAGES} to it
     *
     * @param servletContext - current servlet context instance
     * @param servletName    - servlet name to search and set package
     * @param packageName    - package name to set
     */
    private void addPackageToServlet(ServletContext servletContext, String servletName, String packageName) {
        log.debug("Try to get external servlet by name:[{}]", servletName);
        ServletRegistration registration = servletContext.getServletRegistration(servletName);
        if (registration == null) {
            log.error("Could not find external servlet registration. External api will not work!!!!");
            return;
        }
        registration.setInitParameter(ServerProperties.PROVIDER_PACKAGES, packageName);
    }
}
