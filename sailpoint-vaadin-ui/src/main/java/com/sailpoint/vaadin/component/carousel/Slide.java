package com.sailpoint.vaadin.component.carousel;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.Tag;

/**
 * Slide component for carousel
 */
@Tag("paper-slide")
public class Slide extends Component implements HasComponents {

    /**
     * Constructor with passed components
     *
     * @param components - collection of components for carousel
     */
    public Slide(Component... components) {
        this.add(components);
    }

}