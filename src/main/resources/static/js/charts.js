let backgroundColor = 'rgb(147,190,234, 0.9)';
let borderColor = 'rgb(13,23,234)';
let hoverBackgroundColor = 'rgb(147,190,234, 0.7)';
let typeChart = 'line';
let booleanStacked = false;
let barChart;
let dailyTrend;

/*Toggle between charts*/
function changeChart(canvas, dates, label, listConfirmed, listRecovered, listDeaths, type) {
    barChart.destroy();

    if (label === 'Recovered') {
        backgroundColor = 'rgba(167,234,122,0.9)';
        borderColor = 'rgb(9,234,14)';
        hoverBackgroundColor = 'rgba(187,234,109,0.7)';
        barChartSelectedCountry(canvas, dates, label, listRecovered);
    } else if (label === 'Deaths') {
        backgroundColor = 'rgba(234,127,121,0.9)';
        borderColor = 'rgb(234,3,12)';
        hoverBackgroundColor = 'rgba(234,81,77,0.7)';
        barChartSelectedCountry(canvas, dates, label, listDeaths);
    } else if (label === 'Confirmed') {
        backgroundColor = 'rgb(147,190,234, 0.9)';
        borderColor = 'rgb(13,23,234)';
        hoverBackgroundColor = 'rgb(147,190,234, 0.7)';
        barChartSelectedCountry(canvas, dates, label, listConfirmed);
    } else if (type === 'bar') {
        typeChart = 'bar';
        booleanStacked = true;
        barLineChartAllValues(canvas, dates, label, listConfirmed, listRecovered, listDeaths);
    } else {
        typeChart = 'line';
        booleanStacked = false;
        barLineChartAllValues(canvas, dates, label, listConfirmed, listRecovered, listDeaths)
    }
}

/*Bar/line chart for selected country/world with all values (confirmed, recovered, deaths)*/
function barLineChartAllValues(canvas, dates, label, confirmed, recovered, deaths) {
    barChart = new Chart(canvas, {
        type: typeChart,
        data: {
            labels: dates,
            datasets: [{
                label: 'Confirmed',
                backgroundColor: 'rgb(147,190,234, 0.9)',
                borderColor: 'rgb(147,190,234, 0.9)',
                hoverBackgroundColor: 'rgb(147,190,234, 0.7)',
                fill: false,
                barThickness: 5,
                maxBarThickness: 8,
                data: confirmed
            }, {
                label: 'Recovered',
                backgroundColor: 'rgba(167,234,122,0.9)',
                borderColor: 'rgba(167,234,122,0.9)',
                hoverBackgroundColor: 'rgba(187,234,109,0.7)',
                fill: false,
                barThickness: 5,
                maxBarThickness: 8,
                data: recovered
            }, {
                label: 'Deaths',
                backgroundColor: 'rgba(234,127,121,0.9)',
                borderColor: 'rgba(234,127,121,0.9)',
                hoverBackgroundColor: 'rgba(234,81,77,0.7)',
                fill: false,
                barThickness: 5,
                maxBarThickness: 8,
                data: deaths
            }]
        },
        options: {
            responsive: true,
            responsiveAnimationDuration: 0,
            maintainAspectRatio: false,
            aspectRatio: 0.9,
            onResize: null,
            scales: {
                xAxes: [{
                    display: true,
                    stacked: booleanStacked,
                    gridLines: {
                        display: true
                    }/*,
                    ticks: {
                        autoSkip: true,
                        maxTicksLimit: 17
                    }*/
                }],
                yAxes: [{
                    display: true,
                    stacked: booleanStacked,
                    gridLines: {
                        display: true
                    },
                    ticks: {
                        userCallback: function (value, index, values) {
                            value = value.toString();
                            value = value.split(/(?=(?:...)*$)/);
                            value = value.join('.');
                            return value;
                        }
                    }
                }]
            },
            title: {
                display: true,
                fontSize: 20,
                lineHeight: 1,
                padding: 8,
                fontColor: '#69C8C8',
                text: label
            },
            legend: {
                display: true,
                position: 'bottom',
                align: 'center',
                labels: {
                    fontColor: 'rgb(000, 000, 000)',
                    fontSize: 12,
                    boxWidth: 35,
                    padding: 8
                }
            }
        }
    });
}

/*Bar chart for selected country and selected value*/
function barChartSelectedCountry(canvas, dates, label, listData) {
    barChart = new Chart(canvas, {
        type: 'bar',
        data: {
            labels: dates,
            datasets: [{
                label: label,
                backgroundColor: backgroundColor,
                borderColor: borderColor,
                hoverBackgroundColor: hoverBackgroundColor,
                barThickness: 5,
                maxBarThickness: 8,
                data: listData
            }]
        },
        options: {
            responsive: true,
            responsiveAnimationDuration: 0,
            maintainAspectRatio: false,
            aspectRatio: 0.9,
            onResize: null,
            scales: {
                xAxes: [{
                    display: true,
                    gridLines: {
                        display: true
                    }/*,
                    ticks: {
                        autoSkip: true,
                        maxTicksLimit: 17
                    }*/
                    /*callback: function (dataLabel) {
                        dateDeath.push(dataLabel);
                        if (dateDeath.filter(d => d === dataLabel).length >= 2) {
                            return '';
                        } else {
                            return dataLabel + '.2020';
                        }
                    }
                    //Hide the label of every 2nd dataset. return null to hide the grid line too
                    return index % 2 === 0 ? dataLabel : '';*/
                }],
                yAxes: [{
                    display: true,
                    gridLines: {
                        display: true
                    },
                    ticks: {
                        userCallback: function (value) {
                            value = value.toString();
                            value = value.split(/(?=(?:...)*$)/);
                            value = value.join('.');
                            return value;
                        }
                    }
                }]
            },
            title: {
                display: true,
                fontSize: 20,
                lineHeight: 1,
                padding: 8,
                fontColor: '#69C8C8',
                text: label
            },
            legend: {
                display: true,
                position: 'bottom',
                align: 'center',
                labels: {
                    fontColor: 'rgb(000, 000, 000)',
                    fontSize: 12,
                    boxWidth: 35,
                    padding: 8
                }
            }
        }
    });
}

/*Bar chart for per day values*/
function changedBarChartPerDay(selectedCanvas, datesOneDay, confirmedOneDay, recoveredOneDay, deathsOneDay, labelSelected) {
    dailyTrend.destroy();

    if (labelSelected === 'Confirmed') {
        backgroundColor = 'rgb(147,190,234, 0.9)';
        borderColor = 'rgb(13,23,234)';
        hoverBackgroundColor = 'rgb(147,190,234, 0.7)';
        barChartSelectedCountryPerDay(selectedCanvas, datesOneDay, 'Confirmed per day', confirmedOneDay);
    }
    if (labelSelected === 'Recovered') {
        backgroundColor = 'rgba(167,234,122,0.9)';
        borderColor = 'rgb(9,234,14)';
        hoverBackgroundColor = 'rgba(187,234,109,0.7)';
        barChartSelectedCountryPerDay(selectedCanvas, datesOneDay, 'Recovered per day', recoveredOneDay);
    }
    if (labelSelected === 'Deaths') {
        backgroundColor = 'rgba(234,127,121,0.9)';
        borderColor = 'rgb(234,3,12)';
        hoverBackgroundColor = 'rgba(234,81,77,0.7)';
        barChartSelectedCountryPerDay(selectedCanvas, datesOneDay, 'Deaths per day', deathsOneDay);
    }
}

function barChartSelectedCountryPerDay(canvasPerDay, datesPerDay, labelPerDay, listDataPerDay) {
    dailyTrend = new Chart(canvasPerDay, {
        type: 'bar',
        data: {
            labels: datesPerDay,
            datasets: [{
                label: labelPerDay,
                backgroundColor: backgroundColor,
                borderColor: borderColor,
                hoverBackgroundColor: hoverBackgroundColor,
                barThickness: 5,
                maxBarThickness: 8,
                data: listDataPerDay
            }]
        },
        options: {
            responsive: true,
            responsiveAnimationDuration: 0,
            maintainAspectRatio: false,
            aspectRatio: 0.9,
            onResize: null,
            scales: {
                xAxes: [{
                    display: true,
                    gridLines: {
                        display: true
                    }/*,
                    ticks: {
                        autoSkip: true,
                        maxTicksLimit: 17
                    }*/
                }],
                yAxes: [{
                    display: true,
                    gridLines: {
                        display: true
                    },
                    ticks: {
                        userCallback: function (value) {
                            value = value.toString();
                            value = value.split(/(?=(?:...)*$)/);
                            value = value.join('.');
                            return value;
                        }
                    }
                }]
            },
            title: {
                display: true,
                fontSize: 20,
                lineHeight: 1,
                padding: 8,
                fontColor: '#69C8C8',
                text: labelPerDay
            },
            legend: {
                display: true,
                position: 'bottom',
                align: 'center',
                labels: {
                    fontColor: 'rgb(000, 000, 000)',
                    fontSize: 12,
                    boxWidth: 35,
                    padding: 8
                }
            }
        }
    });
}