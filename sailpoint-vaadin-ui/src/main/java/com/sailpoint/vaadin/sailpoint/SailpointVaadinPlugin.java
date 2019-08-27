package com.sailpoint.vaadin.sailpoint;

import be.ceau.chart.DoughnutChart;
import com.sailpoint.vaadin.component.chartjs.ChartJs;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.sailpoint.vaadin.sailpoint.SailpointVaadinWidget.buildCustomJpaEntityGrid;
import static com.sailpoint.vaadin.sailpoint.SailpointVaadinWidget.getPercentChart;

/**
 * Simple sailpoint vaadin plugin view
 */
@Slf4j
public class SailpointVaadinPlugin extends VerticalLayout {

    /**
     * Charts to update
     */
    private final List<ChartJs> charts = new ArrayList<>();

    /**
     * Default constructor when build all components
     */
    public SailpointVaadinPlugin() {
        setSizeFull();
        setHeight("85vh");
        log.debug("Build component: simple button");
        Button button = new Button("Plugin button: click me", event -> Notification.show("Plugin button clicked!"));
        add(button);
        log.debug("Build component: percent charts");

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidthFull();
        horizontalLayout.setHeight("200px");
        int countCharts = 5;
        while (countCharts-- > 0) {
            ChartJs chartJs = new ChartJs(getPercentChart(true));
            chartJs.setWidth("270px");
            this.charts.add(chartJs);
        }
        horizontalLayout.addAndExpand(this.charts.toArray(new Component[0]));

        log.debug("Add charts");
        add(horizontalLayout);
        log.debug("Add custom jpa grid");
        add(buildCustomJpaEntityGrid());

        log.debug("Schedule updater for percent charts");
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                charts.forEach(chart -> chart.updateCharts(getPercentChart(true)));
                getUI().ifPresent(ui -> ui.access(ui::push));
            }
        }, 0, 2000);
    }
}
