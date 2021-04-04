<template>
  <div :id="id" :class="className" :style="{height:height,width:width}" />
</template>

<script>
import resize from './mixins/resize'

export default {
  mixins: [resize],
  props: {
    className: {
      type: String,
      default: 'chart'
    },
    id: {
      type: String,
      default: 'chart'
    },
    width: {
      type: String,
      default: '200px'
    },
    height: {
      type: String,
      default: '200px'
    },
    xAxisData: {
      type: Array,
      default: () => []
    },
    titleText: {
      type: String,
      default: ''
    },
    legendData: {
      type: Array,
      default: () => []
    },
    dataZoomSeries: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {
      chart: null
    }
  },
  mounted() {
    // this.initChart()
  },
  beforeDestroy() {
    if (!this.chart) {
      return
    }
    this.chart.dispose()
    this.chart = null
  },
  methods: {
  }
}
</script>
