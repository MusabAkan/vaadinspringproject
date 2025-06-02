import { html, LitElement } from 'lit';
import Chart from 'chart.js/auto';

export class ChartJsPie extends LitElement {
    static properties = {
        data: { type: Object },
    };

    constructor() {
        super();
        this.data = { labels: [], data: [] };
        this.chart = null;
    }

    setData(dataMap) {
        const labels = Object.keys(dataMap);
        const data = Object.values(dataMap);

        if (this.chart) {
            this.chart.data.labels = labels;
            this.chart.data.datasets[0].data = data;
            this.chart.update();
        } else {
            // Grafik henüz hazır değilse veriyi sakla
            this.data = { labels, data };
        }
    }

    updated() {
        const canvas = this.renderRoot.querySelector('#myChart');
        if (canvas && !this.chart) {
            const ctx = canvas.getContext('2d');
            this.chart = new Chart(ctx, {
                type: 'pie',
                data: {
                    labels: this.data.labels,
                    datasets: [{
                        data: this.data.data,
                        backgroundColor: [
                            '#42A5F5', '#66BB6A', '#FFA726', '#EF5350',
                            '#AB47BC', '#FF7043', '#26A69A', '#D4E157'
                        ]
                    }]
                },
                options: {
                    responsive: true,
                    plugins: {
                        legend: {
                            position: 'bottom'
                        }
                    }
                }
            });
        }
    }

    render() {
        return html`
            <canvas id="myChart" width="300" height="300"></canvas>`;
    }
}

customElements.define('chart-js-pie', ChartJsPie);
