package com.sailpoint.vaadin.component.chartjs;

import be.ceau.chart.Chart;
import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.shared.Registration;
import lombok.NonNull;

/**
 * This class is a java wrapper for chart.js web component.
 */
@Tag("chart-js")
@NpmPackage(value = "moment", version = "2.24.0")
@NpmPackage(value = "chart.js", version = "2.8.0")
@JsModule("./js/chartjs.js")
public class ChartJs extends PolymerTemplate<ChartJsModel> implements HasSize {

    /**
     * Chart height style property name
     */
    private static final String CHART_HEIGHT = "--chart-height";
    /**
     * Chart width style property name
     */
    private static final String CHART_WIDTH = "--chart-width";

    /**
     * Create chartjs with passed data
     *
     * @param chart - chart data.
     */
    public ChartJs(@NonNull Chart chart) {
        getModel().setChartJs(chart.toJson());
    }

    /**
     * Update current chartjs by new data
     *
     * @param chart - chart data.
     */
    public void updateCharts(@NonNull Chart chart) {
        getModel().setNewChart(chart.toJson());
    }

    /**
     * Enables to add a listener on frontend click event.
     * This is invoked only when a dataset is clicked.
     *
     * @param listener - listener instance
     */
    public Registration addClickListener(
            ComponentEventListener<ClickEvent> listener) {
        return addListener(ClickEvent.class, listener);
    }

    /**
     * This is a RPC callback invoked by client on dataset click event.
     * It raise ClickEvent.
     *
     * @param label        - chart label when having vertical chart than it is x value.
     * @param datasetLabel - dataset label usually is seen in legend.
     * @param value        - value of dataset corresponds to y value in vertical chart.
     */
    @ClientCallable
    private void handleClick(String label, String datasetLabel, String value) {
        fireEvent(new ClickEvent(this, true, label, datasetLabel, value));
    }

    /**
     * Get height from style property {@link ChartJs#CHART_HEIGHT}
     *
     * @return current height of element from style
     */
    @Override
    public String getHeight() {
        return this.getElement().getStyle().get(CHART_HEIGHT);
    }

    /**
     * Set height to style property {@link ChartJs#CHART_HEIGHT}
     *
     * @param height - height value to set
     */
    @Override
    public void setHeight(String height) {
        this.getElement().getStyle().set(CHART_HEIGHT, height);
    }

    /**
     * Get width from style property {@link ChartJs#CHART_WIDTH}
     *
     * @return current width of element from style
     */
    @Override
    public String getWidth() {
        return this.getElement().getStyle().get(CHART_WIDTH);
    }

    /**
     * Set width to style property {@link ChartJs#CHART_WIDTH}
     *
     * @param width - width value to set
     */
    @Override
    public void setWidth(String width) {
        this.getElement().getStyle().set(CHART_WIDTH, width);
    }
}
