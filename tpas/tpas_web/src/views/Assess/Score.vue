<template>
  <div class="app-container">

    <el-tabs v-model="activeName">
      <el-tab-pane label="授课学时" name="courseHour">
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

        <el-button :v-loading="loadingVisible[0]" type="primary" size="small" class="button-find" @click="handleCurrentChange(0, 0)">查找</el-button>

        <el-button type="primary" size="small" class="button-add" @click="exportScoreFile(0)">导出结果</el-button>

        <el-table :data="courseScoreInfo" stripe style="width: 100% " :border="true" fit class="score-table">
          <el-table-column :resizable="false" prop="id" sortable label="序号" width="60px" align="center"/>
          <el-table-column :resizable="false" prop="schoolYear" sortable label="学年" align="center"/>
          <el-table-column :resizable="false" prop="semester" sortable label="学期" align="center" >
            <template slot-scope="scope">
              {{ semesterOptions.filter(item => item.value === scope.row.semester) && semesterOptions.filter(item => item.value === scope.row.semester)[0]?
              semesterOptions.filter(item => item.value === scope.row.semester)[0].label:'?' }}
            </template>
          </el-table-column>
          <el-table-column :resizable="false" prop="teacherCode" sortable label="教师工号" align="center"/>
          <el-table-column :resizable="false" prop="teacherName" sortable label="教师姓名" align="center"/>
          <el-table-column :resizable="false" prop="courseCode" sortable label="课程编码" align="center"/>
          <el-table-column :resizable="false" prop="courseName" sortable label="课程名称" />
          <el-table-column :resizable="false" prop="assessDetail" sortable label="绩效规则" align="center"/>
          <el-table-column :resizable="false" prop="assessFormat" sortable label="绩效公式" align="center"/>
          <el-table-column :resizable="false" prop="total" sortable label="得分" align="center"/>
          <el-table-column :resizable="false" prop="createTime" sortable label="创建日期" align="center"/>
        </el-table>
        <el-pagination
          background
          layout="prev, pager, next"
          :total="pageInfo[0].total"
          :page-size="pageInfo[0].pageSize"
          :current-page="pageInfo[0].curPageNum"
          class="pagination"
          @prev-click="skipPage(0, -1)"
          @next-click="skipPage(0, 1)"
          @current-change="(val) => handleCurrentChange(0, val)"
        />
      </el-tab-pane>
      <el-tab-pane label="论文指导" name="paper">
        <el-select v-model="searchSelect[1].schoolYear" placeholder="年度" clearable class="selector-first">
          <el-option v-for="item in schoolYearOptions" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>

        <el-select v-model="searchSelect[1].semester" placeholder="学期" clearable class="selector">
          <el-option v-for="item in semesterOptions" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>

        <el-input
          v-if="permissionMap === undefined || permissionMap['paperQueryScoreList-teacherCode'] === undefined"
          v-model="searchSelect[1].teacherCode"
          placeholder="教师编码"
          clearable
          class="selector"
          style="width: 200px" />

        <el-button :v-loading="loadingVisible[1]" type="primary" size="small" class="button-find" @click="handleCurrentChange(1, 0)">查找</el-button>
        <el-button type="primary" size="small" class="button-add" @click="exportScoreFile(1)">导出结果</el-button>

        <el-table :data="paperScoreInfo" stripe style="width: 100% " :border="true" fit class="score-table">
          <el-table-column :resizable="false" prop="id" sortable label="序号" width="60px" align="center"/>
          <el-table-column :resizable="false" prop="schoolYear" sortable label="学年" align="center"/>
          <el-table-column :resizable="false" prop="semester" sortable label="学期" align="center">
            <template slot-scope="scope">
              {{ semesterOptions.filter(item => item.value === scope.row.semester) && semesterOptions.filter(item => item.value === scope.row.semester)[0]?
              semesterOptions.filter(item => item.value === scope.row.semester)[0].label:'?' }}
            </template>
          </el-table-column>
          <el-table-column :resizable="false" prop="teacherCode" sortable label="教师工号" align="center"/>
          <el-table-column :resizable="false" prop="teacherName" sortable label="教师姓名" align="center"/>
          <el-table-column :resizable="false" prop="studentNumber" sortable label="指导人数" align="center"/>
          <el-table-column :resizable="false" prop="assessDetail" sortable label="绩效规则" align="center"/>
          <el-table-column :resizable="false" prop="assessFormat" sortable label="绩效公式" align="center"/>
          <el-table-column :resizable="false" prop="total" sortable label="得分"  width="60px" align="center"/>
          <el-table-column :resizable="false" prop="createTime" sortable label="创建日期" align="center"/>
        </el-table>
        <el-pagination
          background
          layout="prev, pager, next"
          :total="pageInfo[1].total"
          :page-size="pageInfo[1].pageSize"
          :current-page="pageInfo[1].curPageNum"
          class="pagination"
          @prev-click="skipPage(1, -1)"
          @next-click="skipPage(1, 1)"
          @current-change="(val) => handleCurrentChange(1, val)"
        />

      </el-tab-pane>
      <el-tab-pane label="实习带队" name="intern">
        <el-select v-model="searchSelect[2].schoolYear" placeholder="年度" clearable class="selector-first">
          <el-option v-for="item in schoolYearOptions" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>

        <el-select v-model="searchSelect[2].semester" placeholder="学期" clearable class="selector">
          <el-option v-for="item in semesterOptions" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>

        <el-input
          v-if="permissionMap === undefined || permissionMap['internQueryScoreList-teacherCode'] === undefined"
          v-model="searchSelect[2].teacherCode"
          placeholder="教师编码"
          clearable
          class="selector"
          style="width: 200px" />

        <el-button :v-loading="loadingVisible[1]" type="primary" size="small" class="button-find" @click="handleCurrentChange(2, 0)">查找</el-button>
        <el-button type="primary" size="small" class="button-add" @click="exportScoreFile(2)">导出结果</el-button>

        <el-table :data="internScoreInfo" stripe style="width: 100% " :border="true" fit class="score-table">
          <el-table-column :resizable="false" prop="id" sortable label="序号" width="60px" align="center"/>
          <el-table-column :resizable="false" prop="schoolYear" sortable label="学年" align="center"/>
          <el-table-column :resizable="false" prop="semester" sortable label="学期" align="center">
            <template slot-scope="scope">
              {{ semesterOptions.filter(item => item.value === scope.row.semester) && semesterOptions.filter(item => item.value === scope.row.semester)[0]?
              semesterOptions.filter(item => item.value === scope.row.semester)[0].label:'?' }}
            </template>
          </el-table-column>
          <el-table-column :resizable="false" prop="teacherCode" sortable label="教师工号" align="center"/>
          <el-table-column :resizable="false" prop="teacherName" sortable label="教师姓名" align="center"/>
          <el-table-column :resizable="false" prop="normalPractice" sortable label="师范带队人数" align="center"/>
          <el-table-column :resizable="false" prop="schoolPractice" sortable label="校内实习带队人数" align="center"/>
          <el-table-column :resizable="false" prop="assessDetail" sortable label="绩效规则" align="center"/>
          <el-table-column :resizable="false" prop="assessFormat" sortable label="绩效公式" align="center"/>
          <el-table-column :resizable="false" prop="total" sortable label="得分" width="60px" align="center"/>
          <el-table-column :resizable="false" prop="createTime" sortable label="创建日期" align="center"/>
        </el-table>
        <el-pagination
          background
          layout="prev, pager, next"
          :total="pageInfo[2].total"
          :page-size="pageInfo[2].pageSize"
          :current-page="pageInfo[2].curPageNum"
          class="pagination"
          @prev-click="skipPage(2, -1)"
          @next-click="skipPage(2, 1)"
          @current-change="(val) => handleCurrentChange(2, val)"
        />
      </el-tab-pane>
    </el-tabs>

  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { queryScoreList, exportScoreFile } from '@/api/score'
import { downloadExcel } from '@/utils/file'
import { hideFullScreenLoading } from '@/utils/loading'

export default {
  name: 'Assess',
  computed: {
    ...mapGetters([
      'schoolYearOptions',
      'semesterOptions'
    ])
  },
  data() {
    return {
      formLabelWidth: '120px',
      loadingVisible: [
        false,
        false,
        false
      ],
      pageInfo: [
        {
          total: 0,
          pageSize: 25,
          curPageNum: 1
        }, {
          total: 0,
          pageSize: 25,
          curPageNum: 1
        }, {
          total: 0,
          pageSize: 25,
          curPageNum: 1
        }
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
        },
        {
          schoolYear: undefined,
          semester: undefined,
          teacherCode: undefined
        }
      ],
      permissionMap: {},
      courseScoreInfo: [],
      paperScoreInfo: [],
      internScoreInfo: [],
      activeName: 'courseHour'
    }
  },
  created() {
    this.permissionMap = this.$store.getters.permissionMap['Assess-Score']
    this.skipPage(0, 0)
    this.skipPage(1, 0)
    this.skipPage(2, 0)
  },
  methods: {
    skipPage: function(index, addPage) {
      const param = {
        pageNum: this.pageInfo[index].curPageNum + addPage,
        pageSize: this.pageInfo[index].pageSize
      }
      this.searchList(index, param)
      this.pageInfo[index].curPageNum = this.pageInfo[index].curPageNum + addPage
    },
    handleCurrentChange: function(index, val) {
      const param = {
        pageSize: this.pageInfo[0].pageSize,
        pageNum: val
      }
      this.searchList(index, param)
      this.pageInfo[index].curPageNum = val
    },
    searchList: function(index, param) {
      this.loadingVisible[index] = true
      const { path, searchModel } = this.getSearchData(index, param)
      queryScoreList(path, searchModel).then((response) => {
        const { data } = response
        switch (index) {
          case 0: {
            this.courseScoreInfo = data.list
            break
          }
          case 1: {
            this.paperScoreInfo = data.list
            break
          }
          case 2: {
            this.internScoreInfo = data.list
            break
          }
        }
        this.pageInfo[index].total = data.total
        this.loadingVisible[index] = false
      }).catch(() => {
        this.loadingVisible[index] = false
      })
    },
    exportScoreFile: function(index) {
      const { path, searchModel } = this.getSearchData(index, {})
      exportScoreFile(path, searchModel).then(response => {
        downloadExcel(response, '绩效考核明细.xlsx')
        hideFullScreenLoading()
      }).catch(error => {
        console.log(error)
        hideFullScreenLoading()
      })
    },
    getSearchData: function(index, param) {
      const searchModel = param
      var path = ''
      if (this.searchSelect[index].schoolYear && this.searchSelect[index].schoolYear !== '') {
        searchModel.schoolYear = this.searchSelect[index].schoolYear
      }
      if (this.searchSelect[index].semester && this.searchSelect[index].semester !== '') {
        searchModel.semester = this.searchSelect[index].semester
      }
      switch (index) {
        case 0: {
          if (this.permissionMap && this.permissionMap['courseQueryScoreList-teacherCode'] && this.permissionMap['courseQueryScoreList-teacherCode']['teacherCode']) {
            searchModel.teacherCode = this.$store.getters.account
          } else {
            searchModel.teacherCode = this.searchSelect[index].teacherCode
          }
          path = 'course'
          break
        }
        case 1: {
          if (this.permissionMap && this.permissionMap['paperQueryScoreList-teacherCode'] && this.permissionMap['paperQueryScoreList-teacherCode']['teacherCode']) {
            searchModel.teacherCode = this.$store.getters.account
          } else {
            searchModel.teacherCode = this.searchSelect[index].teacherCode
          }
          path = 'paper'
          break
        }
        case 2: {
          if (this.permissionMap && this.permissionMap['internQueryScoreList-teacherCode'] && this.permissionMap['internQueryScoreList-teacherCode']['teacherCode']) {
            searchModel.teacherCode = this.$store.getters.account
          } else {
            searchModel.teacherCode = this.searchSelect[index].teacherCode
          }
          path = 'intern'
          break
        }
      }
      return { path, searchModel }
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

  .selector-year {
    margin-bottom: 20px;
  }

  .selector-term {
    margin-left: 10px;
    margin-bottom: 20px;
  }

  .button-find {
    margin-left: 30px;
  }

  .button-add {
    float: right;
  }

  .score-table{
    margin-top: 15px;
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

</style>
