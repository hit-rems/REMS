<template>
  <div ref="chart" style="width: 600px; height: 400px;"></div>
</template>

<script>
import { ref, onMounted, watch } from 'vue';
import * as echarts from 'echarts';

export default {
  props: {
    chartData: {
      type: Array,
      required: true
    }
  },
  setup(props) {
    const chart = ref(null);
    let myChart = null;

    const setOption = () => {
      const option = {
        title: {
          text: '分类名称和数量信息'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'right'
        },
        series: [
          {
            name: '分类数量',
            type: 'pie',
            radius: '50%',
            data: props.chartData,
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      };
      if (myChart) {
        myChart.setOption(option);
      }
    };

    onMounted(() => {
      myChart = echarts.init(chart.value);
      setOption();
    });

    watch(
      () => props.chartData,
      () => {
        setOption();
      }
    );

    return {
      chart
    };
  }
};
</script>
