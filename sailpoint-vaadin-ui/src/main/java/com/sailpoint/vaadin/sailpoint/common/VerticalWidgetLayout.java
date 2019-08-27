package com.sailpoint.vaadin.sailpoint.common;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 * Vertical layout for widget.
 * Sets:
 * height : {@link SailPointVaadinDictionary#WIDGET_HEIGHT}
 * width : {@link SailPointVaadinDictionary#FULL_WIDTH}
 */
public class VerticalWidgetLayout extends VerticalLayout {

    /**
     * Set width and height for standard widget layout size
     */
    public VerticalWidgetLayout() {
        setHeight(SailPointVaadinDictionary.WIDGET_HEIGHT);
        setWidth(SailPointVaadinDictionary.FULL_WIDTH);
    }
}
