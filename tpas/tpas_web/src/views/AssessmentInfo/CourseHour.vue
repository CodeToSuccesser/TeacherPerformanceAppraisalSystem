<template>
  <div class="app-container">
    <el-form ref="form" :model="searchForm">
      <el-select v-model="searchForm.selectedSchoolYear" placeholder="学年" clearable class="selector-first">
        <el-option v-for="item in schoolYearOptions" :key="item.value" :label="item.key" :value="item.value" />
      </el-select>

      <el-select v-model="searchForm.selectedSemester" placeholder="学期" clearable class="selector">
        <el-option v-for="item in semesterOptions" :key="item.key" :label="item.key" :value="item.value" />
      </el-select>

      <el-input v-model="searchForm.selectedCourseName" placeholder="课程名称" clearable class="selector" style="width: 120px" />

      <el-input v-model="searchForm.selectedStudentInstitute" placeholder="学生学院" clearable class="selector" style="width: 120px" />

      <el-button type="primary" size="small" class="button-find" @click="searchCourseHours">查找</el-button>

      <el-button v-if="isAdmin" type="primary" size="small" class="button-add" @click="importCourseHour">导入</el-button>
      <el-button v-if="isAdmin" type="primary" size="small" class="button-add" @click="exportCourseHour">导出</el-button>
      <el-button type="primary" size="small" class="button-add" @click="applyCourseHoursVisible = true">申请新增</el-button>
      <el-button v-if="isAdmin" type="primary" size="small" class="button-add" @click="downloadTemplate">下载导入模板</el-button>

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
        {{ courseHourInfo.semester === 0 ? 1 : 2 }}
      </el-table-column>
      <el-table-column :resizable="false" prop="primarySecondary" sortable label="是否主讲" align="center">
        {{ courseHourInfo.primarySecondary === 0 ? '主讲' : '辅讲' }}
      </el-table-column>
      <el-table-column :resizable="false" label="操作" align="center">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="showDetail(scope)">查看详情</el-button>
          <el-button type="text" size="small" @click="courseDetailEdit">修改</el-button>
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
        <el-form-item label="课程名称" :label-width="formLabelWidth">
          <el-input v-model="courseDetailForm.courseName" autocomplete="off" :disabled="courseDetailEditDisable" />
        </el-form-item>
        <el-form-item label="课程容量" :label-width="formLabelWidth">
          <el-input v-model="courseDetailForm.totalCapacity" autocomplete="off" :disabled="courseDetailEditDisable" />
        </el-form-item>
        <el-form-item label="已选学生人数" :label-width="formLabelWidth">
          <el-input v-model="courseDetailForm.selectedStudent" autocomplete="off" :disabled="courseDetailEditDisable" />
        </el-form-item>
        <el-form-item label="学生学院" :label-width="formLabelWidth">
          <el-input v-model="courseDetailForm.studentsInstitute" autocomplete="off" :disabled="courseDetailEditDisable" />
        </el-form-item>
        <el-form-item label="是否主讲" :label-width="formLabelWidth">
          <el-input v-model="courseDetailForm.primarySecondary" autocomplete="off" :disabled="courseDetailEditDisable" />
        </el-form-item>
        <el-form-item label="周学时" :label-width="formLabelWidth">
          <el-input v-model="courseDetailForm.weekHours" autocomplete="off" :disabled="courseDetailEditDisable" />
        </el-form-item>
        <el-form-item label="学期" :label-width="formLabelWidth">
          <el-input v-model="courseDetailForm.semester" autocomplete="off" :disabled="courseDetailEditDisable" />
        </el-form-item>
        <el-form-item label="学年" :label-width="formLabelWidth">
          <el-input v-model="courseDetailForm.schoolYear" autocomplete="off" :disabled="courseDetailEditDisable" />
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
          <el-input v-model="courseDetailForm.expNumber" autocomplete="off" :disabled="courseDetailEditDisable" />
        </el-form-item>
        <el-form-item label="每次实验人数" :label-width="formLabelWidth">
          <el-input v-model="courseDetailForm.expPerNumber" autocomplete="off" :disabled="courseDetailEditDisable" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="courseDetailEditDisable = false">修 改</el-button>
        <el-button type="primary" @click="courseDetailEditEnsureOrCancel">确 定</el-button>
        <el-button @click="courseDetailEditEnsureOrCancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog title="申请新增课时记录" :visible.sync="applyCourseHoursVisible" top="5vh" :append-to-body="true" :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form :model="applyCourseHourForm">
        <el-form-item label="课程名称" :label-width="formLabelWidth">
          <el-input v-model="applyCourseHourForm.courseName" autocomplete="off" />
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
          <el-input v-model="applyCourseHourForm.studentInstitute" autocomplete="off" />
        </el-form-item>
        <el-form-item label="周学时" :label-width="formLabelWidth">
          <el-input v-model="applyCourseHourForm.weekHours" autocomplete="off" />
        </el-form-item>
        <el-form-item label="学期" :label-width="formLabelWidth">
          <el-input v-model="applyCourseHourForm.semester" autocomplete="off" />
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
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary">申 请</el-button>
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
  exportCourseFile, importCourseHoursFile
} from '@/api/course'

import { downloadExcel } from '@/utils/file'
import { showFullScreenLoading, hideFullScreenLoading } from '@/utils/loading'

export default {
  data() {
    return {
      total: 0,
      pageSize: 25,
      curPageNum: 1,
      searchForm: {
        selectedSchoolYear: '',
        selectedSemester: '',
        selectedCourseName: '',
        selectedStudentInstitute: ''
      },
      courseHourInfo: [],
      schoolYearOptions: {},
      semesterOptions: [
        {
          key: '第一学期',
          value: 0
        },
        {
          key: '第二学期',
          value: 1
        }
      ],
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
      isAdmin: false,
      fileList: []
    }
  },
  created() {
    const param = {
      pageSize: this.pageSize,
      pageNum: this.curPageNum
    }
    this.getCourseHours(param)
    var roleName = this.$store.getters.rolesName === '' ? sessionStorage.getItem('rolesName') : this.$store.getters.rolesName
    this.isAdmin = roleName === '管理员角色'
  },
  methods: {
    courseDetailEditEnsureOrCancel: function() {
      this.courseDetailEditDisable = true
      this.courseDetailVisible = false
    },
    courseDetailEdit: function() {
      this.courseDetailEditDisable = false
      this.courseDetailVisible = true
    },
    getCourseHours: function(body) {
      const userType = this.$store.getters.userType === '' ? sessionStorage.getItem('userType') : this.$store.getters.userType
      if (Number(userType) !== 1) {
        body.teacherId = Number(this.$store.getters.id === '' ? sessionStorage.getItem('id') : this.$store.getters.id)
      }
      getCourseHours(body)
        .then(response => {
          const { data } = response
          this.courseHourInfo = data.list
          this.total = data.total
        })
        .catch(error => {
          console.log(error)
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
      exportCourseFile(param)
        .then(response => {
          downloadExcel(response, '课时信息导出文件.xlsx')
        }).catch(error => {
          console.log(error)
        })
    },
    showDetail: function(scope) {
      this.courseDetailForm = this.courseHourInfo[scope.$index]
      // 转换显示
      this.courseDetailForm.semester = this.courseDetailForm.semester === 0 ? 1 : 2
      this.courseDetailForm.primarySecondary = this.courseDetailForm.primarySecondary === 0 ? '主讲' : '辅讲'
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
      if (this.searchForm.selectedCourseName && this.searchForm.selectedCourseName !== '') {
        param.courseName = this.searchForm.selectedCourseName
      }
      if (this.searchForm.selectedStudentInstitute && this.searchForm.selectedStudentInstitute !== '') {
        param.studentInstitute = this.searchForm.selectedStudentInstitute
      }
      if (this.searchForm.selectedSemester && this.searchForm.selectedSemester !== '') {
        param.semester = this.searchForm.selectedSemester
      }
      this.getCourseHours(param)
    },
    downloadTemplate: function() {
      downCourseHoursTemplate()
        .then(response => {
          downloadExcel(response, '课时信息导入模板文件.xls')
        })
        .catch(error => {
          console.log(error)
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
  }

  .selector {
    margin-left: 10px;
    margin-bottom: 20px;
  }

  .button-find {
    margin-left: 30px;
  }

  .button-add {
    float: right;
    margin-left: 10px;
  }

  .pagination {
    margin-top: 20px ;
    float: right ;
    margin-bottom: 20px
  }

</style>
