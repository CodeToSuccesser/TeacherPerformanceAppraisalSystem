<template>
  <div class="app-container">

    <el-tabs v-model="activeName">
      <el-tab-pane label="总分" name="table">
        <el-select v-model="searchSelect[0].schoolYear" placeholder="年度" clearable class="selector-first">
          <el-option v-for="item in schoolYearOptions" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>

        <el-select v-model="searchSelect[0].semester" placeholder="学期" clearable class="selector">
          <el-option v-for="item in semesterOptions" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>

        <el-input
          v-if="permissionMap === undefined || permissionMap['queryAssessList-teacherCode'] === undefined"
          v-model="searchSelect[0].teacherCode"
          placeholder="教师编码"
          clearable
          class="selector"
          style="width: 200px" />

        <el-button :v-loading="loadingVisible[0]" type="primary" size="small" class="button-find" @click="handleCurrentChange(0)">查找</el-button>

        <el-table :data="assessList" stripe style="width: 100% " :border="true" fit>
          <el-table-column :resizable="false" prop="id" sortable label="序号" width="60px" align="center" />
          <el-table-column label="学年" prop="schoolYear" align="center" />
          <el-table-column label="学期" prop="semester" align="center">
            <template slot-scope="scope">
              {{ semesterOptions.filter(it => it.value === scope.row.semester) && semesterOptions.filter(it => it.value === scope.row.semester)[0]?
              semesterOptions.filter(it => it.value === scope.row.semester)[0].label:'-'}}
            </template>
          </el-table-column>
          <el-table-column :resizable="false" prop="teacherCode" sortable label="教师编码" align="center"/>
          <el-table-column :resizable="false" prop="teacherName" sortable label="教师姓名" align="center"/>
          <el-table-column :resizable="false" prop="courseQuality" sortable label="授课绩效" align="center"/>
          <el-table-column :resizable="false" prop="paperQuality" sortable label="论文指导绩效" align="center"/>
          <el-table-column :resizable="false" prop="internQuality" sortable label="实习指导绩效" align="center"/>
          <el-table-column :resizable="false" prop="otherQuality" sortable label="其他绩效" align="center"/>
          <el-table-column :resizable="false" prop="totalQuality" sortable label="总分" align="center"/>
          <el-table-column :resizable="false" prop="remark" sortable label="备注" />
          <el-table-column :resizable="false" prop="createTime" sortable label="创建日期" />
          <el-table-column :resizable="false" label="操作"  align="center">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="assessDelete(scope)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>

        <el-pagination
          background
          layout="prev, pager, next"
          :total="total"
          :page-size="pageSize"
          :current-page="curPageNum"
          class="pagination"
          @prev-click="skipPage(-1)"
          @next-click="skipPage(1)"
          @current-change="handleCurrentChange"
        />

      </el-tab-pane>

      <el-tab-pane label="统计" name="chart">
        <el-select v-model="searchSelect[1].schoolYear" placeholder="年度" clearable class="selector-first">
          <el-option v-for="item in schoolYearOptions" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>

        <el-select v-model="searchSelect[1].semester" placeholder="学期" clearable class="selector">
          <el-option v-for="item in semesterOptions" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>

        <el-input
          v-if="permissionMap === undefined || permissionMap['courseQueryScoreList-teacherCode'] === undefined"
          v-model="searchSelect[1].teacherCode"
          placeholder="教师编码"
          clearable
          class="selector"
          style="width: 200px" />

        <el-button :v-loading="loadingVisible[1]" type="primary" size="small" class="button-find" @click="drawChart">查找</el-button>

        <div id="chartBox" class="chart-container">
          <div id="chartCmp" class="chart-cmp"/>
        </div>
      </el-tab-pane>

    </el-tabs>

  </div>

</template>

<script>
import { mapGetters } from 'vuex'
import { queryAssessList, assessDelete, getScoreAnalysis } from '@/api/score'
import resize from '@/components/Charts/mixins/resize'

export default {
  name: 'AnalysisVue',
  mixins: [resize],
  data() {
    return {
      formLabelWidth: '120px',
      activeName: 'table',
      permissionMap: {},
      total: 0,
      pageSize: 25,
      curPageNum: 1,
      editComponentVisible: false,
      loadingVisible: [
        false,
        false
      ],
      searchSelect: [
        {
          schoolYear: undefined,
          semester: undefined,
          teacherCode: undefined
        },
        {
          schoolYear: undefined,
          semester: undefined,
          teacherCode: undefined
        }],
      assessList: []
    }
  },
  computed: {
    ...mapGetters([
      'schoolYearOptions',
      'semesterOptions'
    ])
  },
  created() {
    this.permissionMap = this.$store.getters.permissionMap['Assess-Analysis']
    this.skipPage(0)
  },
  mounted() {
    this.$nextTick(() => {
      this.drawChart()
    })
  },
  methods: {
    skipPage: function(addPage) {
      const param = {
        pageNum: this.curPageNum + addPage,
        pageSize: this.pageSize
      }
      this.searchList(param)
      this.curPageNum = this.curPageNum + addPage
    },
    handleCurrentChange: function(val) {
      const param = {
        pageSize: this.pageSize,
        pageNum: val
      }
      this.searchList(param)
      this.curPageNum = val
    },
    /**
     * 查询列表
     * @param param
     */
    searchList: function(param) {
      this.loadingVisible[0] = true
      const searchModel = param
      if (this.searchSelect[0].schoolYear && this.searchSelect[0].schoolYear !== '') {
        searchModel.schoolYear = this.searchSelect[0].schoolYear
      }
      if (this.searchSelect[0].semester && this.searchSelect[0].semester !== '') {
        searchModel.semester = this.searchSelect[0].semester
      }
      if (this.permissionMap && this.permissionMap['queryAssessList-teacherCode'] && this.permissionMap['queryAssessList-teacherCode']['teacherCode']) {
        searchModel.teacherCode = this.$store.getters.account === '' ? JSON.parse(sessionStorage.getItem('stateStore')).user.name : this.$store.getters.account
      }
      queryAssessList(searchModel).then(response => {
        const { data } = response
        this.assessList = data.list
        this.total = data.total
        this.loadingVisible[0] = false
      }).catch(error => {
        console.log(error)
        this.loadingVisible[0] = false
      })
    },
    assessDelete: function(data) {
      this.$confirm('确定删除？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.loadingVisible[0] = true
        const param = [data.row.id]
        assessDelete(param).then(() => {
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
          this.loadingVisible[0] = false
          this.handleCurrentChange(0)
        }).catch(error => {
          console.log(error)
          this.loadingVisible[0] = false
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    },
    drawChart() {
      this.loadingVisible[1] = true
      const searchModel = {}
      if (this.searchSelect[1].schoolYear && this.searchSelect[1].schoolYear !== '') {
        searchModel.schoolYear = this.searchSelect[1].schoolYear
      }
      if (this.searchSelect[1].semester !== undefined) {
        searchModel.semester = this.searchSelect[1].semester
      }
      if (this.permissionMap && this.permissionMap['queryAssessList-teacherCode'] && this.permissionMap['queryAssessList-teacherCode']['teacherCode']) {
        searchModel.teacherCode = this.$store.getters.account === '' ? JSON.parse(sessionStorage.getItem('stateStore')).user.name : this.$store.getters.account
      } else {
        if (this.searchSelect[1].teacherCode && this.searchSelect[1].teacherCode !== '') {
          searchModel.teacherCode = this.searchSelect[1].teacherCode
        }
      }
      const option = {
        backgroundColor: '#fff',
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            textStyle: {
              color: '#fff'
            }
          }
        },
        grid: {
          left: '5%',
          right: '5%',
          borderWidth: 0,
          top: 150,
          bottom: 95,
          textStyle: {
            color: '#fff'
          }
        },
        legend: {
          x: '5%',
          top: '10%',
          textStyle: {
            color: '#90979c'
          },
          data: []
        },
        calculable: true,
        xAxis: [{
          type: 'category',
          axisLine: {
            lineStyle: {
              color: '#90979c'
            }
          },
          splitLine: {
            show: false
          },
          axisTick: {
            show: false
          },
          splitArea: {
            show: false
          },
          axisLabel: {
            interval: 0
          },
          data: (function() {
            const data = [
            ]
            return data
          }())
        }],
        yAxis: [{
          type: 'value',
          splitLine: {
            show: false
          },
          axisLine: {
            lineStyle: {
              color: '#90979c'
            }
          },
          axisTick: {
            show: false
          },
          axisLabel: {
            interval: 0
          },
          splitArea: {
            show: false
          }
        }],
        dataZoom: [
          {
            show: true,
            height: 30,
            xAxisIndex: [
              0
            ],
            bottom: 30,
            start: 10,
            end: 80,
            handleSize: '110%',
            handleStyle: {
              color: '#d3dee5'
            },
            textStyle: {
              color: '#000' },
            borderColor: '#90979c'
          },
          {
            type: 'inside',
            show: true,
            height: 15,
            start: 1,
            end: 35
          }]
      }
      const that = this
      getScoreAnalysis(searchModel).then(response => {
        const { data } = response
        option.title = {
          text: data.title,
          x: '20',
          top: '20',
          textStyle: {
            color: '#17273a',
            fontSize: '22'
          },
          subtextStyle: {
            color: '#323436',
            fontSize: '16'
          }
        }
        option.legend.data = data.legendData
        option.xAxis[0].data = (function() {
          return data.xData
        }())
        option.series = []
        if (data.dataZoomSeries) {
          data.dataZoomSeries.forEach(it => {
            const serie = {
              name: it.name,
              type: it.type,
              stack: it.stack,
              barMaxWidth: it.barMaxWidth,
              barGap: it.barGap,
              symbolSize: it.symbolSize,
              symbol: it.symbol,
              itemStyle: {
                normal: {
                  color: it.color,
                  barBorderRadius: it.barBorderRadius,
                  label: {
                    show: true,
                    textStyle: {
                      color: it.textColor
                    },
                    position: it.position,
                    formatter(p) {
                      return p.value > 0 ? p.value : ''
                    }
                  }
                }
              },
              data: it.data
            }
            option.series.push(serie)
          })
        }
        const echarts = require('echarts')
        that.chartCmp = echarts.init(document.getElementById('chartCmp'))
        that.chartCmp.setOption(option)
      }).catch(error => {
        console.log(error)
        this.loadingVisible[1] = false
      })
    }
  }
}

</script>

<style lang="scss" scoped>
  .dashboard {
    &-container {
     margin: 30px;
    }

    &-text {
     font-size: 30px;
     line-height: 46px;
   }
  }

  .button-find {
    margin-left: 30px;
  }

  .selector-first {
    margin-bottom: 20px;
  }

  .selector {
    margin-left: 10px;
    margin-bottom: 20px;
  }

  .pagination {
    margin-top: 20px ;
    float: right ;
    margin-bottom: 20px
  }
  .chart-container{
    width: 100%;
    height: calc(100vh - 240px);
  }
  .chart-cmp{
    width: 1500px;
    height: calc(100vh - 240px);
  }
</style>
