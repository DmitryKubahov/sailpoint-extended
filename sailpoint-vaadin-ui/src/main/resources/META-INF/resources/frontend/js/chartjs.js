import {html, PolymerElement} from '@polymer/polymer';
import Chart from 'chart.js'

/**
 * chart-height
 */
class Chartjs extends PolymerElement {
    static get properties() {
        return {
            chartJs: {
                type: String,
                observer: '_chartJsInit'
            },
            newChart: {
                type: String,
                observer: '_chartJsNewChart'
            }
        }
    }

    /**
     * Init chart by new value
     * @param newValue - value of chart
     */
    _chartJsInit(newValue) {
        this.initChart(newValue);
    }

    /**
     * Update datasets
     * @param newValue - new value of chart
     */
    _chartJsNewChart(newValue) {
        if (this.chart !== undefined && newValue != null) {
            let newChartJs = JSON.parse(newValue);
            this.chart.data.datasets = newChartJs.data.datasets;
            this.chart.options = newChartJs.options;
            this.chart.update();
        }
    }


    /**
     * Init chart by new value
     * @param newValue - value of chart
     */
    _chartJsChanged(newValue) {
        this.initChart(newValue);
    }

    static get is() {
        return 'chart-js'
    }

    static get template() {
        return html`
            <style>
                .chart-container {
                    position: relative;
                    height: var(--chart-height, 100%); 
                    width: var(--chart-width, 100%);
                }
            </style>
            <div class="chart-container"> <canvas id="chart" on-click="handleClick"></canvas></div>`;
    }

    handleClick(event) {
        var activePoints = this.chart.getElementAtEvent(event);
        var vaadinServer = this.$server;

        if (activePoints[0]) {
            var activePoint = activePoints[0];
            var data = activePoint._chart.data;
            var datasetIndex = activePoint._datasetIndex;
            var label = data.labels[activePoint._index];
            var datasetLabel = data.datasets[datasetIndex].label;
            var value = data.datasets[datasetIndex].data[activePoint._index];

            vaadinServer.handleClick(label, datasetLabel, value);
        }
    };

    ready() {
        super.ready();
        // WARNING: Chart.platform._style is part of the PRIVATE API, DO NOT RELY ON IT!!!!
        this.shadowRoot.appendChild(Chart.platform._style.cloneNode(true));
    }

    initChart(chartValue) {
        if (this.chart !== undefined) {
            this.chart.destroy();
        }
        let ctx = this.shadowRoot.querySelector('#chart').getContext('2d');
        this.chart = new Chart(ctx, JSON.parse(chartValue));
    }
}

customElements.define(Chartjs.is, Chartjs);
export {Chartjs};
