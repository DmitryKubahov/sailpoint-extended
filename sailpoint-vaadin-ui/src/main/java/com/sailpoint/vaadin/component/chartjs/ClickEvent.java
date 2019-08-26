package com.sailpoint.vaadin.component.chartjs;

import com.vaadin.flow.component.ComponentEvent;
import lombok.Data;

/**
 * Represents dataset click event.
 * Holds information which dataset values was clicked.
 */
@Data
public class ClickEvent extends ComponentEvent<ChartJs> {
    /**
     * Click label value
     */
    private final String label;
    /**
     * Click value of chart
     */
    private final String value;
    /**
     * Current click dataset label
     */
    private final String datasetLabel;

    /**
     * Constructor with all parameters
     *
     * @param source       - chartjs instance source
     * @param fromClient   - is from client
     * @param label        - current click event label
     * @param datasetLabel - data set label click
     * @param value        - click value
     */
    public ClickEvent(ChartJs source, boolean fromClient, String label, String datasetLabel, String value) {
        super(source, fromClient);
        this.label = label;
        this.value = value;
        this.datasetLabel = datasetLabel;
    }
}
