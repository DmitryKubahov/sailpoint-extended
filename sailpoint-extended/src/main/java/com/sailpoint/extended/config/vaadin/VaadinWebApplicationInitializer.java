package com.sailpoint.extended.config.vaadin;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

/**
 * Vaadin web application initializer
 */
@Slf4j
public class VaadinWebApplicationInitializer implements WebApplicationInitializer {

    /**
     * All filter names for vaadin servlet from sailpoint servlet
     */
    private static final String[] FILTER_NAMES = {
            SailPointVaadinServlet.SP_CONTEXT_FILTER_NAME,
            SailPointVaadinServlet.SP_PAGE_AUTHENTICATION_FILTER_NAME
    };

    /**
     * Initialize vaadin web servlet
     *
     * @param servletContext - current servlet context
     */
    public void onStartup(ServletContext servletContext) {
        log.debug("Init spring context for vaadin servlet");
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        servletContext.addListener(new ContextLoaderListener(context));

        ServletRegistration.Dynamic registration = servletContext
                .addServlet("dispatcher", new SailPointVaadinServlet(context, true));
        registration.setLoadOnStartup(-1);
        registration.addMapping(SailPointVaadinServlet.SERVLET_PATH);
        addFilters(servletContext);
    }

    /**
     * Add main filters for vaadin servlet:
     * - context filter
     * - authentication filter
     * If there is no filter in servlet context -> throw UnsupportedOperationException {@see ServletContext#getFilterRegistration}
     *
     * @param servletContext - current servlet context
     */
    private void addFilters(@NonNull ServletContext servletContext) {
        for (String filterName : FILTER_NAMES) {
            log.debug("Try to find filter by name:[{}] in context", filterName);
            FilterRegistration filter = servletContext.getFilterRegistration(filterName);
            log.debug("Filter by name:[{}] was found in context, add mapping url for sailpoint vaadin servlet:[{}]",
                    filterName, SailPointVaadinServlet.SERVLET_PATH);
            filter.addMappingForUrlPatterns(null, true, SailPointVaadinServlet.SERVLET_PATH);
        }
    }
}
