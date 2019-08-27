package com.sailpoint.vaadin.sailpoint.embadded;

import com.sailpoint.vaadin.sailpoint.SailpointVaadinPlugin;

/**
 * Vaadin plugin component exporter to use it as embedded
 */
public class SailpointVaadinPluginExporter extends SailPointExporter<SailpointVaadinPlugin> {

    /**
     * Default constructor for setting tag for this element
     */
    public SailpointVaadinPluginExporter() {
        super("sp-vaadin-plugin");
    }

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
    protected SailpointVaadinPluginExporter(String tag) {
        super(tag);
    }
}
