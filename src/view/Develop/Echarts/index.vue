<template>
  <div class="app-container echarts-container">
    <el-tabs type="border-card">
      <el-tab-pane label="折线图">
        <base-echarts
          id="line-echarts"
          class-name="line-echarts"
          width="800px"
          height="500px"
          :option="lineOption"
        />
      </el-tab-pane>
      <el-tab-pane label="柱状图">
        <base-echarts
          id="bar-echarts"
          class-name="bar-echarts"
          width="800px"
          height="500px"
          :option="barOption"
        />
      </el-tab-pane>
      <el-tab-pane label="饼图">
        <base-echarts
          id="pie-echarts"
          class-name="pie-echarts"
          width="800px"
          height="500px"
          :option="pieOption"
        />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import BaseEcharts from '@/component/BaseEcharts/index.vue';

export default {
  name: 'EchartsDemo',
  components: {
    BaseEcharts
  },
  data() {
    return {
      lineOption: {
        xAxis: {
          type: 'category',
          data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: [150, 230, 224, 218, 135, 147, 260],
            type: 'line'
          }
        ]
      },
      barOption: {
        xAxis: {
          type: 'category',
          data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: [120, 200, 150, 80, 70, 110, 130],
            type: 'bar',
            showBackground: true,
            backgroundStyle: {
              color: 'rgba(180, 180, 180, 0.2)'
            }
          }
        ]
      },
      pieOption: {
        tooltip: {
          trigger: 'item'
        },
        legend: {
          top: '5%',
          left: 'center'
        },
        series: [
          {
            name: 'Access From',
            type: 'pie',
            radius: ['40%', '70%'],
            avoidLabelOverlap: false,
            itemStyle: {
              borderRadius: 10,
              borderColor: '#fff',
              borderWidth: 2
            },
            label: {
              show: false,
              position: 'center'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: 40,
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: false
            },
            data: [
              {value: 1048, name: 'Search Engine'},
              {value: 735, name: 'Direct'},
              {value: 580, name: 'Email'},
              {value: 484, name: 'Union Ads'},
              {value: 300, name: 'Video Ads'}
            ]
          }
        ]
      }
    };
  }
};
</script>
<style scoped>
</style>
