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
        <el-select v-model="searchSelect[0].schoolYear" placeholder="年度" clearable class="selector-first">
          <el-option v-for="item in schoolYearOptions" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>

        <el-select v-model="searchSelect[0].semester" placeholder="学期" clearable class="selector">
          <el-option v-for="item in semesterOptions" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>

        <el-input
          v-if="permissionMap === undefined || permissionMap['courseQueryScoreList-teacherCode'] === undefined"
          v-model="searchSelect[0].teacherCode"
          placeholder="教师编码"
          clearable
          class="selector"
          style="width: 200px" />

        <el-button :v-loading="loadingVisible[1]" type="primary" size="small" class="button-find" @click="handleCurrentChange(1, 0)">查找</el-button>

        <div id="chartBox" class="chart-container">
          <div id="chartCmp" class="chart-cmp"/>
        </div>
      </el-tab-pane>

    </el-tabs>

  </div>

</template>

<script>
import { mapGetters } from 'vuex'
import { queryAssessList, assessDelete } from '@/api/score'
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
    // const that = this
    // window.addEventListener('resize', () => {
    //   that.chartCmp.resize()
    // })
  },
  mounted() {
    this.$nextTick(() => {
      this.drawChart()
      this.chartCmp.resize()
    })
    const that = this
    window.onresize = function() {
      // that.chartssize(document.getElementById('chartBox'),
      //   document.getElementById('chartCmp'))
      that.chartCmp.resize()
    }
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
      const echarts = require('echarts')
      this.chartCmp = echarts.init(document.getElementById('chartCmp'))
      const xData = (function() {
        const data = [
          '2017-2018-1',
          '2017-2018-2',
          '2018-2019-1',
          '2018-2019-1'
        ]
        return data
      }())
      this.chartCmp.setOption({
        backgroundColor: '#344b58',
        title: {
          text: 'admin2',
          x: '20',
          top: '20',
          textStyle: {
            color: '#fff',
            fontSize: '22'
          },
          subtextStyle: {
            color: '#90979c',
            fontSize: '16'
          }
        },
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
          data: ['授课', '实习', '论文', '总分']
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
          data: xData
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
            handleIcon: 'path://M306.1,413c0,2.2-1.8,4-4,4h-59.8c-2.2,0-4-1.8-4-4V200.8c0-2.2,1.8-4,4-4h59.8c2.2,0,4,1.8,4,4V413z',
            handleSize: '110%',
            handleStyle: {
              color: '#d3dee5'
            },
            textStyle: {
              color: '#fff' },
            borderColor: '#90979c'
          },
          {
            type: 'inside',
            show: true,
            height: 15,
            start: 1,
            end: 35
          }],
        series: [
          {
            name: '授课',
            type: 'bar',
            stack: 'total',
            barMaxWidth: 35,
            barGap: '10%',
            itemStyle: {
              normal: {
                color: 'rgba(255,144,128,1)',
                label: {
                  show: true,
                  textStyle: {
                    color: '#fff'
                  },
                  position: 'insideTop',
                  formatter(p) {
                    return p.value > 0 ? p.value : ''
                  }
                }
              }
            },
            data: [
              18.0,
              19.7,
              19.6,
              19.9
            ]
          },
          {
            name: '实习',
            type: 'bar',
            stack: 'total',
            itemStyle: {
              normal: {
                color: 'rgba(0,191,183,1)',
                barBorderRadius: 0,
                label: {
                  show: true,
                  position: 'top',
                  formatter(p) {
                    return p.value > 0 ? p.value : ''
                  }
                }
              }
            },
            data: [
              10.0,
              11.7,
              11.8,
              14.0
            ]
          },
          {
            name: '论文',
            type: 'bar',
            stack: 'total',
            itemStyle: {
              normal: {
                color: 'rgb(165,177,186)',
                barBorderRadius: 0,
                label: {
                  show: true,
                  position: 'top',
                  formatter(p) {
                    return p.value > 0 ? p.value : ''
                  }
                }
              }
            },
            data: [
              15.0,
              15.2,
              15.2,
              15.0
            ]
          },
          {
            name: '总分',
            type: 'line',
            stack: 'total',
            symbolSize: 10,
            symbol: 'circle',
            itemStyle: {
              normal: {
                color: 'rgba(252,230,48,1)',
                barBorderRadius: 0,
                label: {
                  show: true,
                  position: 'top',
                  formatter(p) {
                    return p.value > 0 ? p.value : ''
                  }
                }
              }
            },
            data: [
              43.0,
              46.6,
              46.6,
              48.9
            ]
          }
        ]
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
