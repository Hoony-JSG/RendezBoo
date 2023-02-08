import React, { Component } from 'react'
import ApexCharts from 'react-apexcharts'
import { BiHappyHeartEyes } from 'react-icons/bi'

class RocketRadar extends Component {
  constructor(props) {
    super(props)

    this.state = {
      series: [
        {
          name: 'Series 1',
          data: [20, 100, 40, 30, 50, 80],
        },
      ],
      options: {
        chart: {
          height: 350,
          type: 'radar',
        },
        dataLabels: {
          enabled: true,
        },
        plotOptions: {
          radar: {
            size: 140,
            polygons: {
              strokeColors: '#e9e9e9',
              fill: {
                colors: ['#f8f8f8', '#fff'],
              },
            },
          },
        },
        title: {
          text: '다이어그램',
        },
        colors: ['#FF4560'],
        markers: {
          size: 4,
          colors: ['#fff'],
          strokeColor: '#FF4560',
          strokeWidth: 2,
        },
        tooltip: {
          y: {
            formatter: function (val) {
              return val
            },
          },
        },
        xaxis: {
          categories: [
            <BiHappyHeartEyes />,
            '당황',
            '놀람',
            '슬픔',
            '분노',
            '역겨움',
          ],
        },
        yaxis: {
          tickAmount: 7,
          labels: {
            formatter: function (val, i) {
              if (i % 2 === 0) {
                return val
              } else {
                return ''
              }
            },
          },
        },
      },
    }
  }

  render() {
    return (
      <div id="chart">
        <ApexCharts
          options={this.state.options}
          series={this.state.series}
          type="radar"
          height={350}
        />
      </div>
    )
  }
}

export default RocketRadar
