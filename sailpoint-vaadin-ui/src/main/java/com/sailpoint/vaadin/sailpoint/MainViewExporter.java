package com.sailpoint.vaadin.sailpoint;

/**
 * The main view contains a button and a click listener.
 */
public class MainViewExporter extends SailPointWidgetExporter<MainView> {

    /**
     * Default constructor for setting tag for this element
     */
    public MainViewExporter() {
        super("main-view");
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
    protected MainViewExporter(String tag) {
        super(tag);
    }
}
