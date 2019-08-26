package com.sailpoint.vaadin.sailpoint;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 * Just button view
 */
public class ButtomComponent extends VerticalLayout {

    /**
     * Just add button
     */
    public ButtomComponent() {
        Button button = new Button("Click me", event -> Notification.show("Clicked!"));
        add(button);
    }
}
