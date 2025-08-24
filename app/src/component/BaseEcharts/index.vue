<template>
  <div
    :id="id"
    :class="className"
    :style="{ height: height, width: width }"
  />
</template>

<script>
import * as echarts from 'echarts';
import Resize from '@/component/BaseEcharts/mixin/Resize';

export default {
  name: 'BaseEcharts',
  mixins: [Resize],
  props: {
    className: {
      type: String,
      default: 'base-echarts'
    },
    id: {
      type: String,
      default: 'base-echarts'
    },
    width: {
      type: String,
      default: '600px'
    },
    height: {
      type: String,
      default: '300px'
    },
    option: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      chart: null
    };
  },
  watch: {
    option() {
      this.initChart();
    }
  },
  mounted() {
    this.initChart();
  },
  beforeDestroy() {
    if (!this.chart) {
      return;
    }
    this.chart.dispose();
    this.chart = null;
  },
  methods: {
    initChart() {
      this.chart = echarts.init(document.getElementById(this.id));
      this.chart.setOption(this.option);
    }
  }
};
</script>
<style scoped>

</style>
