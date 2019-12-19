package com.sailpoint.extended.controller.external;

import io.micrometer.core.instrument.Tag;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import sailpoint.api.SailPointFactory;
import sailpoint.environmentMonitoring.MonitoringUtil;
import sailpoint.object.Filter;
import sailpoint.object.QueryOptions;
import sailpoint.object.Server;
import sailpoint.object.TaskResult;
import sailpoint.rest.BaseResource;
import sailpoint.tools.GeneralException;
import sailpoint.tools.Util;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.Collections;
import java.util.Date;

/**
 * External identity iq controller for prometheus metrics
 */
@Slf4j
@Path("metrics")
public class ExternalMetricPrometheusRestController extends BaseResource {

    /**
     * Name for metrics of total time for getting all statistics
     */
    private static final String GETTING_METRICS_TIMER_NAME = "get_metrics_timer_millis";

    /**
     * Host name tag value
     */
    private static final String TAG_HOST_NAME = "host_name";

    /**
     * Active task metric name
     */
    private static final String ACTIVE_TASK_METRIC_NAME = "active_tasks";
    /**
     * Active task name tag value
     */
    private static final String TAG_ACTIVE_TASK_NAME = "active_task_name";

    /**
     * Get all metrics for prometheus database. There are 3 steps for getting metrics:
     * 1 - clear all current meters for prometheus registry
     * 2 - getting current host service statistic
     * 3 - pass all attributes from statistics to prometheus registry
     */
    @GET
    @Produces("text/plain;version=0.0.4;charset=utf-8")
    @Path("prometheus")
    public String getPrometheusMetrics() throws GeneralException {
        log.debug("Create metrics and start timer for getting metrics");
        StopWatch gettingMetrics = new StopWatch();
        gettingMetrics.start();
        PrometheusMeterRegistry prometheusMeterRegistry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
        try {
            saveSystemMetrics(prometheusMeterRegistry);
            saveTasksMetrics(prometheusMeterRegistry);
        } finally {
            gettingMetrics.stop();
            log.debug("Total time of getting metrics:[{}] mills", gettingMetrics.getTotalTimeMillis());
            saveHostGougeMetric(GETTING_METRICS_TIMER_NAME, gettingMetrics.getTotalTimeMillis(),
                    prometheusMeterRegistry);
        }
        return prometheusMeterRegistry.scrape();
    }

    /**
     * Save statistics for task: execution time
     *
     * @param prometheusMeterRegistry - micrometer register for prometheus
     * @throws GeneralException error getting current sailpoint context
     */
    private void saveTasksMetrics(PrometheusMeterRegistry prometheusMeterRegistry) throws GeneralException {
        log.debug("Save tasks metrics");
        log.debug("Build query options only for active tasks");
        QueryOptions queryOptions = new QueryOptions();
        queryOptions.add(Filter.not(Filter.notnull("completed")));
        queryOptions.addOrdering("name", true);
        for (TaskResult taskResult : SailPointFactory.getCurrentContext().getObjects(TaskResult.class, queryOptions)) {
            prometheusMeterRegistry.gauge(
                    ACTIVE_TASK_METRIC_NAME,
                    Collections.singletonList(Tag.of(TAG_ACTIVE_TASK_NAME, taskResult.getName())),
                    Util.computeDifferenceMilli(taskResult.getLaunched(), new Date()) / Util.MILLI_IN_SECOND);
        }
    }

    /**
     * Save only system metrics from {@link MonitoringUtil}
     * - cpu usage (percentage)
     * - memory usage (Mb)
     * - memory usage (percentage)
     * - request threads count
     * - task threads count
     * - data base response time (millis)
     *
     * @param prometheusMeterRegistry - micrometer register for prometheus
     * @throws GeneralException error getting current sailpoint context
     */
    private void saveSystemMetrics(PrometheusMeterRegistry prometheusMeterRegistry) throws GeneralException {
        log.debug("Save system metrics");
        saveHostGougeMetric(Server.ATT_CPU_USAGE, MonitoringUtil.getCpuUsage().floatValue(), prometheusMeterRegistry);
        saveHostGougeMetric(Server.ATT_MEMORY_USAGE, MonitoringUtil.getUsedMemory(), prometheusMeterRegistry);
        saveHostGougeMetric(Server.ATT_MEMORY_USAGE_PERCENTAGE, MonitoringUtil.getMemoryUsagePercentage().floatValue(),
                prometheusMeterRegistry);
        saveHostGougeMetric(Server.ARG_MAX_REQUEST_THREADS, MonitoringUtil.getRequestProcessorThreads(),
                prometheusMeterRegistry);
        saveHostGougeMetric(Server.ATT_TASK_THREADS, MonitoringUtil.getQuartzThreads(), prometheusMeterRegistry);
        saveHostGougeMetric(Server.ATT_DATABASE_RESPONSE_TIME,
                MonitoringUtil.getDbResponseTime(SailPointFactory.getCurrentContext()), prometheusMeterRegistry);
        saveHostGougeMetric(Server.ATT_DATABASE_RESPONSE_TIME,
                MonitoringUtil.getDbResponseTime(SailPointFactory.getCurrentContext()), prometheusMeterRegistry);
    }

    /**
     * Save passed value as gauge to metric registry with hostName tags
     *
     * @param metricName              - metric name to check and save
     * @param metricValue             - metric value for passed name
     * @param prometheusMeterRegistry - micrometer register for prometheus
     */
    private void saveHostGougeMetric(String metricName, Number metricValue,
                                     PrometheusMeterRegistry prometheusMeterRegistry) {
        log.debug("Save metric name:[{}], value:[{}]", metricName, metricValue);
        if (metricValue != null) {
            prometheusMeterRegistry
                    .gauge(
                            metricName,
                            Collections.singletonList(Tag.of(TAG_HOST_NAME, Util.getHostName())),
                            metricValue);
        }
    }
}
