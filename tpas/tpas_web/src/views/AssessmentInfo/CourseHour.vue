<template>
  <div class="app-container">
    <el-form ref="form" :model="searchForm">
      <el-select v-model="searchForm.selectedSchoolYear" placeholder="学年" clearable class="selector-first">
        <el-option v-for="item in schoolYearOptions" :key="item.value" :label="item.label" :value="item.value" />
      </el-select>

      <el-select v-model="searchForm.selectedSemester" placeholder="学期" clearable class="selector">
        <el-option v-for="item in semesterOptions" :key="item.value" :label="item.label" :value="item.value" />
      </el-select>

      <el-input v-model="searchForm.selectedCourseName" placeholder="课程名称" clearable class="selector" style="width: 120px" />

      <el-input v-model="searchForm.selectedStudentInstitute" placeholder="学生学院" clearable class="selector" style="width: 120px" />

      <el-input
        v-if="this.permissionMap === undefined
          || this.permissionMap['getCourseHoursSearch-teacherCode'] === undefined
          || this.permissionMap['getCourseHoursSearch-teacherCode']['teacherCode'] === undefined"
        v-model="searchForm.selectedTeacherCode"
        placeholder="教师编码"
        clearable
        class="selector"
        style="width: 120px"
      />

      <el-button type="primary" size="small" class="button-find" @click="searchCourseHours">查找</el-button>

      <el-col>

        <el-button
          v-if="permissionMap && permissionMap['courseCalculateAssess-button']"
          type="primary"
          size="small"
          class="button-find"
          @click="calculateAssess(1)"
        >绩效考核</el-button>

        <el-button
          v-if="permissionMap && permissionMap['importCourseHour-Button']"
          type="primary"
          size="small"
          class="button-add"
          @click="importCourseHour"
        >导入</el-button>

        <el-button
          v-if="permissionMap && permissionMap['exportCourseHour-Button']"
          type="primary"
          size="small"
          class="button-add"
          @click="exportCourseHour"
        >导出</el-button>

        <el-button
          type="primary"
          size="small"
          class="button-add"
          @click="applyCourseHoursVisible = true"
        >新增</el-button>

        <el-button
          v-if="permissionMap && permissionMap['downloadCourseHourTemplate-Button']"
          type="primary"
          size="small"
          class="button-add"
          @click="downloadTemplate"
        >下载导入模板</el-button>
      </el-col>
    </el-form>

    <el-table :data="courseHourInfo" stripe style="width: 100% " :border="true" fit>
      <el-table-column :resizable="false" prop="id" sortable label="序号" align="center" width="60px">
        <template slot-scope="scope">
          <span>{{ (pageSize - 1) * (curPageNum - 1) + scope.$index + 1 }}</span>
        </template>
      </el-table-column>
      <el-table-column :resizable="false" prop="courseName" sortable label="课程名称" align="center" />
      <el-table-column :resizable="false" prop="selectedStudent" sortable label="已选学生人数" align="center" />
      <el-table-column :resizable="false" prop="studentsInstitute" sortable label="学生学院" align="center" />
      <el-table-column :resizable="false" prop="schoolYear" sortable label="学年" align="center" />
      <el-table-column :resizable="false" prop="semester" sortable label="学期" align="center">
        <template slot-scope="scope">
          {{ Number(courseHourInfo[scope.$index].semester) === 0 ? '第一学期' : '第二学期' }}
        </template>
      </el-table-column>
      <el-table-column :resizable="false" prop="primarySecondary" sortable label="是否主讲" align="center">
        <template slot-scope="scope">
          {{ Number(courseHourInfo[scope.$index].primarySecondary) === 0 ? '主讲' : '辅讲' }}
        </template>
      </el-table-column>
      <el-table-column :resizable="false" label="操作" align="center">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="showDetail(scope)">查看详情</el-button>
          <el-button type="text" size="small" @click="courseDetailEdit(scope)">修改</el-button>
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
      @prev-click="prePage"
      @next-click="nextPage"
      @current-change="handleCurrentChange"
    />

    <el-dialog title="课时详情" :visible.sync="courseDetailVisible" top="5vh" :append-to-body="true" :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form :model="courseDetailForm">
        <el-form-item label="教师编码" :label-width="formLabelWidth">
          <el-input v-model="courseDetailForm.teacherCode" autocomplete="off" :disabled="true" />
        </el-form-item>
        <el-form-item label="教师名称" :label-width="formLabelWidth">
          <el-input v-model="courseDetailForm.teacherName" autocomplete="off" :disabled="true" />
        </el-form-item>
        <el-form-item label="课程名称" :label-width="formLabelWidth">
          <el-input v-model="courseDetailForm.courseName" autocomplete="off" :disabled="true" />
        </el-form-item>
        <el-form-item label="课程容量" :label-width="formLabelWidth">
          <el-input v-model="courseDetailForm.totalCapacity" autocomplete="off" :disabled="true" />
        </el-form-item>
        <el-form-item label="已选学生人数" :label-width="formLabelWidth">
          <el-input v-model="courseDetailForm.selectedStudent" autocomplete="off" :disabled="courseDetailEditDisable" />
        </el-form-item>
        <el-form-item label="学生学院" :label-width="formLabelWidth">
          <el-input v-model="courseDetailForm.studentsInstitute" autocomplete="off" :disabled="true" />
        </el-form-item>
        <el-form-item label="教学班组成" :label-width="formLabelWidth">
          <el-input v-model="courseDetailForm.classed" autocomplete="off" :disabled="courseDetailEditDisable" />
        </el-form-item>
        <el-form-item label="是否主讲" :label-width="formLabelWidth">
          <el-select v-model="courseDetailForm.primarySecondary" placeholder="是否主讲" :disabled="true">
            <el-option label="是" value="0" />
            <el-option label="否" value="1" />
          </el-select>
        </el-form-item>
        <el-form-item label="周学时" :label-width="formLabelWidth">
          <el-input v-model="courseDetailForm.weekHours" autocomplete="off" :disabled="true" />
        </el-form-item>
        <el-form-item label="学期" :label-width="formLabelWidth">
          <el-select v-model="courseDetailForm.semester" placeholder="学期" :disabled="true">
            <el-option label="第一学期" value="0" />
            <el-option label="第二学期" value="1" />
          </el-select>
        </el-form-item>
        <el-form-item label="学年" :label-width="formLabelWidth">
          <el-input v-model="courseDetailForm.schoolYear" autocomplete="off" :disabled="true" />
        </el-form-item>
        <el-form-item label="起止周" :label-width="formLabelWidth">
          <el-input v-model="courseDetailForm.peroid" autocomplete="off" :disabled="courseDetailEditDisable" />
        </el-form-item>
        <el-form-item label="讲课学时" :label-width="formLabelWidth">
          <el-input v-model="courseDetailForm.teachingHours" autocomplete="off" :disabled="courseDetailEditDisable" />
        </el-form-item>
        <el-form-item label="上机学时" :label-width="formLabelWidth">
          <el-input v-model="courseDetailForm.computerHours" autocomplete="off" :disabled="courseDetailEditDisable" />
        </el-form-item>
        <el-form-item label="实验学时" :label-width="formLabelWidth">
          <el-input v-model="courseDetailForm.experimentHours" autocomplete="off" :disabled="courseDetailEditDisable" />
        </el-form-item>
        <el-form-item label="实验次数" :label-width="formLabelWidth">
          <el-input v-model="courseDetailForm.expNumber" autocomplete="off" :disabled="true" />
        </el-form-item>
        <el-form-item label="每次实验人数" :label-width="formLabelWidth">
          <el-input v-model="courseDetailForm.expPerNumber" autocomplete="off" :disabled="true" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button v-if="courseDetailEditDisable" type="primary" @click="courseDetailEditDisable = false">修 改</el-button>
        <el-button type="primary" @click="courseDetailEditEnsure">确 定</el-button>
        <el-button @click="courseDetailEditCancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog title="新增课时记录" :visible.sync="applyCourseHoursVisible" top="5vh" :append-to-body="true" :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form :model="applyCourseHourForm">
        <el-form-item label="课程名称" :label-width="formLabelWidth">
          <el-input v-model="applyCourseHourForm.courseName" autocomplete="off" />
        </el-form-item>
        <el-form-item label="课程编号" :label-width="formLabelWidth">
          <el-input v-model="applyCourseHourForm.courseCode" autocomplete="off" />
        </el-form-item>
        <el-form-item label="教师编号" :label-width="formLabelWidth">
          <el-input v-model="applyCourseHourForm.teacherCode" autocomplete="off" />
        </el-form-item>
        <el-form-item label="课程容量" :label-width="formLabelWidth">
          <el-input v-model="applyCourseHourForm.totalCapacity" autocomplete="off" />
        </el-form-item>
        <el-form-item label="已选学生人数" :label-width="formLabelWidth">
          <el-input v-model="applyCourseHourForm.selectedStudent" autocomplete="off" />
        </el-form-item>
        <el-form-item label="学生学院" :label-width="formLabelWidth">
          <el-input v-model="applyCourseHourForm.studentInstitute" autocomplete="off" />
        </el-form-item>
        <el-form-item label="是否主讲" :label-width="formLabelWidth">
          <el-select v-model="applyCourseHourForm.primarySecondary" placeholder="是否主讲">
            <el-option label="是" value="0" />
            <el-option label="否" value="1" />
          </el-select>
        </el-form-item>
        <el-form-item label="周学时" :label-width="formLabelWidth">
          <el-input v-model="applyCourseHourForm.weekHours" autocomplete="off" />
        </el-form-item>
        <el-form-item label="学期" :label-width="formLabelWidth">
          <el-select v-model="applyCourseHourForm.semester" placeholder="学期">
            <el-option label="第一学期" value="0" />
            <el-option label="第二学期" value="1" />
          </el-select>
        </el-form-item>
        <el-form-item label="学年" :label-width="formLabelWidth">
          <el-input v-model="applyCourseHourForm.schoolYear" autocomplete="off" />
        </el-form-item>
        <el-form-item label="讲课学时" :label-width="formLabelWidth">
          <el-input v-model="applyCourseHourForm.teachingHours" autocomplete="off" />
        </el-form-item>
        <el-form-item label="上机学时" :label-width="formLabelWidth">
          <el-input v-model="applyCourseHourForm.computerHours" autocomplete="off" />
        </el-form-item>
        <el-form-item label="实验学时" :label-width="formLabelWidth">
          <el-input v-model="applyCourseHourForm.experimentHours" autocomplete="off" />
        </el-form-item>
        <el-form-item label="实验次数" :label-width="formLabelWidth">
          <el-input v-model="applyCourseHourForm.expNumber" autocomplete="off" />
        </el-form-item>
        <el-form-item label="每次实验人数" :label-width="formLabelWidth">
          <el-input v-model="applyCourseHourForm.expPerNumber" autocomplete="off" />
        </el-form-item>
        <el-form-item label="教学班组成" :label-width="formLabelWidth">
          <el-input v-model="applyCourseHourForm.classed" autocomplete="off" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="applyCourseHours">申 请</el-button>
        <el-button @click="applyCourseHoursVisible = false">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog
      title="导入文件信息"
      :visible.sync="importDialogVisible"
      width="30%"
    >
      <el-form>
        <el-form-item label="选择上传文件：" style="margin-top: 10px">
          <el-upload
            action="#"
            :on-change="getFile"
            :on-remove="handleRemove"
            :multiple="false"
            :limit="1"
            :on-exceed="handleExceed"
            :file-list="fileList"
            accept=".xlsx,.xls"
            :auto-upload="false"
          >
            <el-button size="small" type="primary">选择文件</el-button>
          </el-upload>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelImportFile">取 消</el-button>
        <el-button type="primary" @click="importFile">确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
import {
  getCourseHours,
  downCourseHoursTemplate,
  exportCourseFile, importCourseHoursFile, modifyCourseHours, insertCourseHours
} from '@/api/course'
import { calculateAssess } from '@/api/score'

import { downloadExcel } from '@/utils/file'
import { showFullScreenLoading, hideFullScreenLoading } from '@/utils/loading'
import { mapGetters } from 'vuex'

export default {
  computed: {
    ...mapGetters([
      'semesterOptions',
      'schoolYearOptions'
    ])
  },
  data() {
    return {
      total: 0,
      pageSize: 25,
      curPageNum: 1,
      searchForm: {
        selectedSchoolYear: '',
        selectedSemester: '',
        selectedCourseName: '',
        selectedStudentInstitute: '',
        selectedTeacherCode: ''
      },
      courseHourInfo: [],
      importDialogVisible: false,
      dialogTableVisible: false,
      courseDetailVisible: false,
      courseDetailEditDisable: true,
      applyCourseHoursVisible: false,
      courseDetailForm: {},
      applyCourseHourForm: {
        courseName: '',
        selectedStudent: '',
        studentsInstitute: '',
        totalCapacity: '',
        weekHours: '',
        semester: '',
        teachingHours: '',
        computerHours: '',
        experimentHours: '',
        schoolYear: '',
        expNumber: '',
        expPerNumber: ''
      },
      formLabelWidth: '120px',
      fileList: [],
      permissionMap: {}
    }
  },
  created() {
    const param = {
      pageSize: this.pageSize,
      pageNum: this.curPageNum
    }
    // this.$store.getters.permissionMap[数据库system_permission表的value]，这里value对应system_menu表的value
    this.permissionMap = this.$store.getters.permissionMap['AssessmentInfo-CourseHour']
    this.getCourseHours(param)
  },
  methods: {
    courseDetailEditEnsure: function() {
      this.courseDetailVisible = false
      this.courseDetailEditDisable = true
      showFullScreenLoading('修改中')

      const data = {
        id: this.courseDetailForm.id,
        courseName: this.courseDetailForm.courseName,
        selectedStudent: this.courseDetailForm.selectedStudent,
        teachingHours: this.courseDetailForm.teachingHours,
        computerHours: this.courseDetailForm.computerHours,
        experimentHours: this.courseDetailForm.experimentHours,
        peroid: this.courseDetailForm.peroid,
        classed: this.courseDetailForm.classed,
        teacherCode: this.courseDetailForm.teacherCode,
        courseCode: this.courseDetailForm.courseCode,
        semester: this.courseDetailForm.semester,
        schoolYear: this.courseDetailForm.schoolYear
      }
      modifyCourseHours(data, this.courseDetailForm.id)
        .then(response => {
          hideFullScreenLoading()
          this.$message.success('修改成功')
        })
        .catch(error => {
          hideFullScreenLoading()
          console.log(error)
        })
    },
    courseDetailEditCancel: function() {
      this.courseDetailVisible = false
      this.courseDetailEditDisable = true
    },
    courseDetailEdit: function(scope) {
      this.courseDetailEditDisable = false
      this.courseDetailVisible = true
      this.courseDetailForm = JSON.parse(JSON.stringify(this.courseHourInfo[scope.$index]))
      this.courseDetailForm.primarySecondary = String(this.courseDetailForm.primarySecondary)
      this.courseDetailForm.semester = String(this.courseDetailForm.semester)
    },
    getCourseHours: function(body) {
      // 用法，数据库配置teacherId权限的用户，该接口参数必传teacherId
      // this.permissionMap[数据库system_permission表的permissionKey][数据库system_permission表的filed]
      if (this.permissionMap && this.permissionMap['getCourseHoursSearch-teacherCode'] && this.permissionMap['getCourseHoursSearch-teacherCode']['teacherCode']) {
        body.teacherCode = this.$store.getters.account === '' ? JSON.parse(sessionStorage.getItem('stateStore')).user.account : this.$store.getters.account
      }
      showFullScreenLoading('加载中')
      getCourseHours(body)
        .then(response => {
          const { data } = response
          this.courseHourInfo = data.list
          this.total = data.total
          hideFullScreenLoading()
        })
        .catch(error => {
          console.log(error)
          hideFullScreenLoading()
        })
    },
    importCourseHour: function() {
      this.importDialogVisible = true
    },
    exportCourseHour: function() {
      const param = {}
      if (this.searchForm.selectedCourseName && this.searchForm.selectedCourseName !== '') {
        param.courseName = this.searchForm.selectedCourseName
      }
      if (this.searchForm.selectedStudentInstitute && this.searchForm.selectedStudentInstitute !== '') {
        param.studentInstitute = this.searchForm.selectedStudentInstitute
      }
      if (this.searchForm.selectedSemester && this.searchForm.selectedSemester !== '') {
        param.semester = this.searchForm.selectedSemester
      }
      showFullScreenLoading('导出文件下载中')
      exportCourseFile(param)
        .then(response => {
          downloadExcel(response, '课时信息导出文件.xlsx')
          hideFullScreenLoading()
        }).catch(error => {
          console.log(error)
          hideFullScreenLoading()
        })
    },
    showDetail: function(scope) {
      // 数组元素深拷贝
      this.courseDetailForm = JSON.parse(JSON.stringify(this.courseHourInfo[scope.$index]))
      // 转换显示
      this.courseDetailForm.semester = String(this.courseDetailForm.semester)
      this.courseDetailForm.primarySecondary = Number(this.courseDetailForm.primarySecondary) === 0 ? '主讲' : '辅讲'
      this.courseDetailVisible = true
    },
    nextPage: function() {
      const param = {
        pageNum: this.curPageNum + 1,
        pageSize: this.pageSize
      }
      this.getCourseHours(param)
      this.curPageNum = this.curPageNum + 1
    },
    prePage: function() {
      const param = {
        pageNum: this.curPageNum - 1,
        pageSize: this.pageSize
      }
      this.getCourseHours(param)
      this.curPageNum = this.curPageNum - 1
    },
    handleCurrentChange: function(val) {
      const param = {
        pageNum: this.curPageNum,
        pageSize: this.pageSize
      }

      this.getCourseHours(param)
      this.curPageNum = val
    },
    searchCourseHours: function() {
      const param = {
        pageNum: this.curPageNum,
        pageSize: this.pageSize
      }
      if (this.searchForm.selectedCourseName !== undefined && this.searchForm.selectedCourseName !== '') {
        param.courseName = this.searchForm.selectedCourseName
      }
      if (this.searchForm.selectedStudentInstitute !== undefined && this.searchForm.selectedStudentInstitute !== '') {
        param.studentInstitute = this.searchForm.selectedStudentInstitute
      }
      if (this.searchForm.selectedSemester !== undefined && this.searchForm.selectedSemester !== '') {
        param.semester = this.searchForm.selectedSemester
      }
      if (this.searchForm.selectedSchoolYear !== undefined && this.searchForm.selectedSchoolYear !== '') {
        param.schoolYear = this.searchForm.selectedSchoolYear
      }
      if (this.searchForm.selectedTeacherCode !== undefined && this.searchForm.selectedTeacherCode !== '') {
        param.teacherCode = this.searchForm.selectedTeacherCode
      }
      this.getCourseHours(param)
    },
    downloadTemplate: function() {
      showFullScreenLoading('模板文件下载中')
      downCourseHoursTemplate()
        .then(response => {
          downloadExcel(response, '课时信息导入模板文件.xls')
          hideFullScreenLoading()
        })
        .catch(error => {
          console.log(error)
          hideFullScreenLoading()
        })
    },
    handleRemove() {
      this.fileList = []
    },
    handleExceed(files, fileList) {
      this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`)
    },
    getFile(file, fileList) {
      this.fileList = fileList
    },
    cancelImportFile() {
      this.fileList = []
      this.importDialogVisible = false
    },
    importFile() {
      if (!this.fileList || !this.fileList.length) {
        this.$notify.error({
          title: '错误',
          message: '请选择上传的文件',
          duration: 2000
        })
        return
      }
      const body = new FormData()
      body.append('file', this.fileList[0].raw)

      showFullScreenLoading('文件上传中')
      importCourseHoursFile(body)
        .then(response => {
          this.$notify.success({
            title: '导入成功',
            message: '课时信息导入成功' + response.data.successCount + '条，导入失败' + response.data.failCount + '条'
          })
          this.importDialogVisible = false
          hideFullScreenLoading()
        })
        .catch(error => {
          console.log(error)
          hideFullScreenLoading()
        })
    },
    applyCourseHours() {
      const data = {
        courseName: this.applyCourseHourForm.courseName,
        courseCode: this.applyCourseHourForm.courseCode,
        teacherCode: this.applyCourseHourForm.teacherCode,
        semester: this.applyCourseHourForm.semester,
        schoolYear: this.applyCourseHourForm.schoolYear,
        classed: this.applyCourseHourForm.classed,
        computerHours: this.applyCourseHourForm.computerHours,
        experimentHours: this.applyCourseHourForm.experimentHours,
        teachingHours: this.applyCourseHourForm.teachingHours,
        expPerNumber: this.applyCourseHourForm.expPerNumber,
        peroid: this.applyCourseHourForm.peroid,
        primarySecondary: this.applyCourseHourForm.primarySecondary,
        remark: this.applyCourseHourForm.remark,
        selectedStudent: this.applyCourseHourForm.selectedStudent,
        studentNumber: this.applyCourseHourForm.studentNumber,
        studentsInstitute: this.applyCourseHourForm.studentsInstitute,
        totalCapacity: this.applyCourseHourForm.totalCapacity,
        selectionNumber: this.applyCourseHourForm.courseCode
      }
      showFullScreenLoading()
      insertCourseHours(data)
        .then(response => {
          hideFullScreenLoading()
          this.applyCourseHoursVisible = false
          this.$message.success('新增成功')
          location.reload()
        })
        .catch(error => {
          console.log(error)
          hideFullScreenLoading()
        })
    },
    calculateAssess: function(cType) {
      const param = {
        schoolYear: this.searchForm.selectedSchoolYear,
        semester: this.searchForm.selectedSemester,
        cType: cType
      }
      calculateAssess(param).then(response => {
        this.$notify.success({
          title: '提交成功',
          message: '待统计' + response.data.successCount + '条，已统计' + response.data.failCount + '条'
        })
      }).catch(error => {
        console.log(error)
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

  .selector-first {
    margin-bottom: 20px;
    width: 120px;
  }

  .selector {
    margin-left: 10px;
    margin-bottom: 20px;
    width: 120px;
  }

  .button-find {
    margin-left: 30px;
  }

  .button-add {
    margin-left: 10px;
    margin-bottom: 10px;
  }

  .pagination {
    margin-top: 20px ;
    float: right ;
    margin-bottom: 20px
  }

</style>
