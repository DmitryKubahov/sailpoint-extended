package com.sailpoint.vaadin.component.chartjs;

import com.vaadin.flow.templatemodel.TemplateModel;

/**
 * Represents chart's necessary data to be created jsonChart
 */
public interface ChartJsModel extends TemplateModel {
    /**
     * Setting valid json of chart values
     *
     * @param jsonChart - json string of valid data for chart
     */
    void setChartJs(String jsonChart);

    /**
     * Update current charts with new one
     *
     * @param jsonChart - json string of valid data for chart
     */
    void setNewChart(String jsonChart);
}
