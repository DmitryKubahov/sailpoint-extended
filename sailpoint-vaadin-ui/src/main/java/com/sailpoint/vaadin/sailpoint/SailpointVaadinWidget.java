package com.sailpoint.vaadin.sailpoint;

import be.ceau.chart.BarChart;
import be.ceau.chart.Chart;
import be.ceau.chart.DoughnutChart;
import be.ceau.chart.color.Color;
import be.ceau.chart.data.BarData;
import be.ceau.chart.data.DoughnutData;
import be.ceau.chart.dataset.BarDataset;
import be.ceau.chart.dataset.DoughnutDataset;
import be.ceau.chart.options.BarOptions;
import be.ceau.chart.options.DoughnutOptions;
import be.ceau.chart.options.Legend;
import be.ceau.chart.options.Title;
import be.ceau.chart.options.animation.DoughnutAnimation;
import be.ceau.chart.options.scales.BarScale;
import be.ceau.chart.options.scales.XAxis;
import be.ceau.chart.options.scales.YAxis;
import be.ceau.chart.options.ticks.LinearTicks;
import com.sailpoint.extended.model.jpa.entity.CustomEntityJpa;
import com.sailpoint.extended.model.jpa.entity.CustomEntityJpa_;
import com.sailpoint.extended.model.jpa.repository.CustomEntityJpaRepository;
import com.sailpoint.extended.spring.SpringContextProvider;
import com.sailpoint.vaadin.component.carousel.Carousel;
import com.sailpoint.vaadin.component.carousel.Slide;
import com.sailpoint.vaadin.component.chartjs.ChartJs;
import com.sailpoint.vaadin.component.chartjs.ClickEvent;
import com.sailpoint.vaadin.sailpoint.common.SailPointVaadinDictionary;
import com.sailpoint.vaadin.sailpoint.common.VerticalWidgetLayout;
import com.sailpoint.vaadin.sailpoint.provider.SailPointObjectDataProvider;
import com.sailpoint.vaadin.sailpoint.provider.SpringDataJpaGridProvider;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import lombok.extern.slf4j.Slf4j;
import sailpoint.object.Identity;
import sailpoint.object.SailPointObject;
import sailpoint.object.Workflow;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Simple sailpoint vaadin widget vies
 */
@Slf4j
public class SailpointVaadinWidget extends VerticalWidgetLayout {

    /**
     * Bar chart component instance
     */
    private final ChartJs barChart;

    /**
     * Percent chart component instance
     */
    private final ChartJs percentChart;

    /**
     * Default constructor when build all components
     */
    public SailpointVaadinWidget() {
        log.debug("Build main component: carousel");
        Carousel carousel = new Carousel();
        carousel.setWidth(SailPointVaadinDictionary.FULL_WIDTH);
        carousel.setHeight(SailPointVaadinDictionary.WIDGET_CAROUSEL_HEIGHT);
        carousel.setSlideDuration(5);

        log.debug("Build component: bar chart");
        this.barChart = getStackedBarChart();
        barChart.setHeight(carousel.getHeight());

        log.debug("Build component: percent chart");
        this.percentChart = new ChartJs(getPercentChart(false));
        percentChart.setHeight(carousel.getHeight());

        log.debug("Add slides to carousel");
        carousel.setSlides(new Slide[]{
                new Slide(buildGridComponents(Workflow.class)),
                new Slide(buildGridComponents(Identity.class)),
                new Slide(buildCustomJpaEntityGrid()),
                new Slide(barChart),
                new Slide(percentChart),
                new Slide(new ButtomComponent()),
        });
        add(carousel);

        log.debug("Schedule updater for percent charts");
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                percentChart.updateCharts(getPercentChart(false));
                getUI().ifPresent(ui -> ui.access(ui::push));
            }
        }, 0, 1000);
    }

    /**
     * Build percent chart component
     *
     * @param aspectRation - aspect ration chart ration parameter value
     * @return percent chart component
     */
    static DoughnutChart getPercentChart(boolean aspectRation) {
        DoughnutOptions options = new DoughnutOptions();
        options.setRotation(BigDecimal.valueOf(1 * Math.PI)).setCircumference(BigDecimal.valueOf(1 * Math.PI))
                .setLegend(new Legend().setDisplay(false)).setResponsive(true).setMaintainAspectRatio(aspectRation)
                .setAnimation(new DoughnutAnimation().setDuration(0));
        DoughnutData doughnutData = new DoughnutData();
        int max = 100;
        int cpu = new Random().nextInt(100);
        options.setTitle(new Title().setText(String.format("CPU %d%%", cpu)).setDisplay(true));
        doughnutData.setLabels("CPU", "idle");
        doughnutData.addDataset(new DoughnutDataset().addData(cpu).addData(max - cpu)
                .setBackgroundColor(Arrays.asList(Color.GREEN, Color.GRAY)));
        return new DoughnutChart(doughnutData, options);
    }

    /**
     * Build grid for custom jpa entity
     *
     * @return grid for custom jpa entity
     */
    static Grid<CustomEntityJpa> buildCustomJpaEntityGrid() {
        Grid<CustomEntityJpa> customEntityJpaGrid = new Grid<>(CustomEntityJpa.class);
        customEntityJpaGrid.setColumns(CustomEntityJpa_.ID, CustomEntityJpa_.NAME);
        customEntityJpaGrid.setDataProvider(new SpringDataJpaGridProvider(
                SpringContextProvider.getApplicationContext().getBean(CustomEntityJpaRepository.class)));
        customEntityJpaGrid.addColumn(
                new LocalDateTimeRenderer<>(CustomEntityJpa::getDateOfBirth, DateTimeFormatter.ISO_LOCAL_DATE));
        customEntityJpaGrid.setSizeFull();
        return customEntityJpaGrid;
    }

    /**
     * Build grid for any sailpoint object
     *
     * @param spObjectType - type of sailpoint object
     * @param <T>          - type of object
     * @return grid for sailpoint object
     */
    private <T extends SailPointObject> Grid<T> buildGridComponents(Class<T> spObjectType) {
        Grid<T> identities = new Grid<>(spObjectType);
        identities.setColumns("id", "name", "created");
        identities.setDataProvider(new SailPointObjectDataProvider(spObjectType));
        identities.setSizeFull();
        return identities;
    }

    /**
     * Build bar chart component
     *
     * @return bar chart component
     */
    private ChartJs getStackedBarChart() {
        ChartJs chart = new ChartJs(generateBarChart());
        chart.addClickListener((ComponentEventListener<ClickEvent>) clickEvent -> {
            Notification
                    .show(String.format("%s : %s : %s", clickEvent.getLabel(), clickEvent.getDatasetLabel(),
                            clickEvent.getValue()),
                            3000, Notification.Position.TOP_CENTER);
            this.barChart.updateCharts(generateBarChart());
            this.barChart.getUI().ifPresent(ui -> ui.access(ui::push));
        });
        return chart;
    }

    /**
     * Generate data for chart
     *
     * @return chart data
     */
    private Chart generateBarChart() {
        BarScale scale = new BarScale()
                .addxAxes(new XAxis<LinearTicks>().setStacked(true))
                .addyAxes(new YAxis<LinearTicks>().setStacked(true));

        BarOptions options = new BarOptions();
        options.setResponsive(true).setMaintainAspectRatio(false).setScales(scale);

        BarData barData = new BarData();
        barData.setLabels("First", "Second", "Last");
        barData.addDataset(generateBarDataSet("SailPoint", Color.BLUE, barData.getLabels()));
        barData.addDataset(generateBarDataSet("IdentityIQ", Color.DARK_GREEN, barData.getLabels()));
        barData.addDataset(generateBarDataSet("Java", Color.AQUA_MARINE, barData.getLabels()));
        return new BarChart(barData, options).setHorizontal();
    }

    /**
     * Generate random bar dataset
     *
     * @param label - bar label
     * @param color - bar color
     * @param data  - labels of bars
     * @return bardataset instance
     */
    private BarDataset generateBarDataSet(String label, Color color, List<String> data) {
        Random random = new Random();
        BarDataset result = new BarDataset().addBackgroundColor(color).setLabel(label);
        data.forEach(barLabel -> result.addData(random.nextInt(1000)));
        return result;
    }
}
