package com.sailpoint.extended.config.vaadin;

import com.sailpoint.extended.spring.SpringContextProvider;
import com.vaadin.flow.function.DeploymentConfiguration;
import com.vaadin.flow.server.Constants;
import com.vaadin.flow.spring.SpringServlet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.web.WebApplicationInitializer;
import sailpoint.api.SailPointContext;
import sailpoint.api.SailPointFactory;
import sailpoint.web.PageAuthenticationFilter;
import sailpoint.web.SailPointContextRequestFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

/**
 * Sailpoint vaadin servlet. Extends for security: working only if there is a sailpoint context existed
 */
@Slf4j
public class SailPointVaadinServlet extends SpringServlet {

    /**
     * Root path for vaadin servlet
     */
    public static final String SERVLET_PATH = "/sp-vaadin/*";

    /**
     * Name of {@link SailPointContextRequestFilter} filter. Placed in web.xml
     */
    public static final String SP_CONTEXT_FILTER_NAME = "SailPoint Context Filter";

    /**
     * Default user name of {@link SailPointContextRequestFilter} filter
     */
    public static final String SP_CONTEXT_FILTER_USER_NAME = "SailPointContextRequestFilter";

    /**
     * Name of {@link PageAuthenticationFilter} filter. Placed in web.xml
     */
    public static final String SP_PAGE_AUTHENTICATION_FILTER_NAME = "pageAuthenticationFilter";

    /**
     * Creates a new Vaadin servlet instance with the application
     * {@code context} provided.
     *
     * @param context            the Spring application context
     * @param forwardingEnforced - forwarding enforced flag
     */
    public SailPointVaadinServlet(ApplicationContext context, boolean forwardingEnforced) {
        super(context, forwardingEnforced);
    }

    /**
     * Before service check current sailpoint context
     *
     * @param request  - current http request
     * @param response - http response
     * @throws ServletException error while service requests
     * @throws IOException      - writing response error
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (isSailpointContextValid()) {
            super.service(request, response);
        }
    }

    /**
     * Try to get current sailpoint context. If got error or context == null -> context is not valid
     *
     * @return is sailpoint context valid flag
     */
    private boolean isSailpointContextValid() {
        log.debug("Check current sailpoint context");
        try {
            SailPointContext sailPointContext = SailPointFactory.getCurrentContext();
            if (sailPointContext == null) {
                log.trace("Current sailpoint context is null");
                return false;
            }
            if (SP_CONTEXT_FILTER_USER_NAME.equals(sailPointContext.getUserName())) {
                log.trace("Not authenticated context");
                return false;
            }
            return true;
        } catch (Exception ex) {
            log.debug("Current sailpoint context is not valid");
        }
        return false;
    }

    /**
     * As it is a mix of web.xml and {@link WebApplicationInitializer} then need to fill properties from real spring
     * application context
     *
     * @param initParameters - current init parameters
     * @return deployment configuration instance
     */
    @Override
    protected DeploymentConfiguration createDeploymentConfiguration(
            Properties initParameters) {
        return super.createDeploymentConfiguration(
                Optional.ofNullable(SpringContextProvider.getApplicationContext()).map(applicationContext -> {
                    Properties properties = new Properties(initParameters);
                    Environment env = applicationContext.getEnvironment();
                    if (env != null) {
                        for (String propertyName : SpringServlet.PROPERTY_NAMES) {
                            setProperty(Constants.VAADIN_PREFIX.concat(propertyName), propertyName, properties, env);
                        }
                    }
                    return properties;
                }).orElse(initParameters));
    }

    /**
     * Set property value to properties from environment
     *
     * @param envProperty - name of property in environment
     * @param initParam   - real property name form vaadin servlet
     * @param properties  - current vaadin servlet properties
     * @param env         - spring environment
     */
    private void setProperty(String envProperty, String initParam, Properties properties, Environment env) {
        if (env.containsProperty(envProperty)) {
            String propertyValue = env.getProperty(envProperty);
            if (propertyValue != null) {
                properties.put(initParam, propertyValue);
            }
        }
    }
}
