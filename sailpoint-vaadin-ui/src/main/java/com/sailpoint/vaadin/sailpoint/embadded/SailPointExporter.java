package com.sailpoint.vaadin.sailpoint.embadded;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.WebComponentExporter;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.webcomponent.WebComponent;

/**
 * Common vaadin sailpoint vaadin component exporter with necessary styles
 */
@CssImport("./styles/sailpoint-vaadin-style.css")
public abstract class SailPointExporter<C extends Component> extends WebComponentExporter<C> {

    /**
     * Creates a new {@code WebComponentExporter} instance and configures the
     * tag name of the web component created based on this exporter.
     * <p>
     * This constructor is not meant to be overridden unless the {@code
     * exporter} can be extended. Rather, create a non-args constructor and call
     * this constructor from it.
     *
     * @param tag tag name of the web component created by the exporter, cannot
     *            be {@code null}
     */
    protected SailPointExporter(String tag) {
        super(tag);
    }

    /**
     * No need any extra configuration wile creating component
     *
     * @param webComponent - current web component
     * @param component    - current vaadin component
     */
    @Override
    protected void configureInstance(WebComponent<C> webComponent, C component) {
    }
}
