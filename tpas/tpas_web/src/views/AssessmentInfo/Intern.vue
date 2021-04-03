<template>
  <div class="dashboard-container">
    <el-form ref="form" :model="searchForm">
      <el-select v-model="searchForm.selectedSchoolYear" placeholder="学年" clearable class="selector-first">
        <el-option v-for="item in schoolYearOptions" :key="item.value" :label="item.label" :value="item.value" />
      </el-select>

      <el-select v-model="searchForm.selectedSemester" placeholder="学期" clearable class="selector">
        <el-option v-for="item in semesterOptions" :key="item.value" :label="item.label" :value="item.value" />
      </el-select>

      <el-input
        v-if="this.permissionMap === undefined
        || this.permissionMap['getInternSearch-teacherCode'] === undefined
        || this.permissionMap['getInternSearch-teacherCode']['teacherCode'] === undefined"
        v-model="searchForm.selectedTeacherCode"
        placeholder="教师编码"
        clearable
        class="selector"
        style="width: 120px" />
      <el-button type="primary" size="small" class="button-find" @click="searchIntern">查找</el-button>

      <el-button
        v-if="permissionMap && permissionMap['internCalculateAssess-button']"
        type="primary"
        size="small"
        class="button-find"
        @click="calculateAssess(3)">绩效考核</el-button>

      <el-button type="primary" size="small" class="button-add" @click="applyInternDialogVisible = true">新增</el-button>
      <el-button v-if="permissionMap && permissionMap['downloadInternTemplate-Button']" type="primary" size="small" class="button-add" @click="downloadTemplate">下载导入模板</el-button>
      <el-button v-if="permissionMap && permissionMap['importIntern-Button']" type="primary" size="small" class="button-add" @click="importIntern">导入</el-button>
      <el-button v-if="permissionMap && permissionMap['exportIntern-Button']" type="primary" size="small" class="button-add" @click="exportIntern">导出</el-button>
    </el-form>

    <el-table :data="internInfo" stripe style="width: 100% " :border="true" fit>
      <el-table-column :resizable="false" prop="id" sortable label="序号" align="center">
        <template slot-scope="scope">
          <span>{{ (pageSize - 1) * (curPageNum - 1) + scope.$index + 1 }}</span>
        </template>
      </el-table-column>
      <el-table-column :resizable="false" prop="teacherName" sortable label="教师姓名" align="center" />
      <el-table-column :resizable="false" prop="teacherCode" sortable label="教师编码" align="center" />
      <el-table-column :resizable="false" prop="normalPractice" sortable label="师范实习带队人数" align="center" />
      <el-table-column :resizable="false" prop="nonNormalPractice" sortable label="非师范实习带队人数" align="center" />
      <el-table-column :resizable="false" prop="schoolPractice" sortable label="校内实习带队人数" align="center" />
      <el-table-column :resizable="false" prop="semester" sortable label="学期" align="center">
        <template slot-scope="scope">
          {{ Number(internInfo[scope.$index].semester) === 0 ? '第一学期' : '第二学期' }}
        </template>
      </el-table-column>
      <el-table-column :resizable="false" prop="schoolYear" sortable label="学年" align="center" />
      <el-table-column :resizable="false" prop="remark" sortable label="备注" align="center" />
      <el-table-column :resizable="false" prop="createTime" sortable label="创建日期" align="center" />
      <el-table-column :resizable="false" label="操作" align="center">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="internEdit(scope)">修改</el-button>
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

    <el-dialog title="实习带队信息修改" :visible.sync="internDialogVisible" top="5vh" :append-to-body="true" :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form :model="internForm">
        <el-form-item label="师范实习带队人数" :label-width="formLabelWidth">
          <el-input v-model="internForm.normalPractice" autocomplete="off" :disabled="internEditDisable" />
        </el-form-item>
        <el-form-item label="非师范实习带队人数" :label-width="formLabelWidth">
          <el-input v-model="internForm.nonNormalPractice" autocomplete="off" :disabled="internEditDisable" />
        </el-form-item>
        <el-form-item label="校内实习带队人数" :label-width="formLabelWidth">
          <el-input v-model="internForm.schoolPractice" autocomplete="off" :disabled="internEditDisable" />
        </el-form-item>
        <el-form-item label="学期" :label-width="formLabelWidth">
          <el-select v-model="internForm.semester" placeholder="学期" :disabled="internEditDisable">
            <el-option label="第一学期" value="0" />
            <el-option label="第二学期" value="1" />
          </el-select>
        </el-form-item>
        <el-form-item label="学年" :label-width="formLabelWidth">
          <el-input v-model="internForm.schoolYear" autocomplete="off" :disabled="internEditDisable" />
        </el-form-item>
        <el-form-item label="备注" :label-width="formLabelWidth">
          <el-input v-model="internForm.remark" autocomplete="off" :disabled="internEditDisable" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="internEditEnsure">确 定</el-button>
        <el-button @click="internEditCancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog title="新增实习带队信息" :visible.sync="applyInternDialogVisible" top="5vh" :append-to-body="true" :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form :model="applyInternForm">
        <el-form-item label="教师编号" :label-width="formLabelWidth">
          <el-input v-model="applyInternForm.teacherCode" autocomplete="off" />
        </el-form-item>
        <el-form-item label="师范实习带队人数" :label-width="formLabelWidth">
          <el-input v-model="applyInternForm.normalPractice" autocomplete="off" />
        </el-form-item>
        <el-form-item label="非师范实习带队人数" :label-width="formLabelWidth">
          <el-input v-model="applyInternForm.nonNormalPractice" autocomplete="off" />
        </el-form-item>
        <el-form-item label="校内实习带队人数" :label-width="formLabelWidth">
          <el-input v-model="applyInternForm.schoolPractice" autocomplete="off" />
        </el-form-item>
        <el-form-item label="学期" :label-width="formLabelWidth">
          <el-select v-model="applyInternForm.semester" placeholder="学期">
            <el-option label="第一学期" value="0" />
            <el-option label="第二学期" value="1" />
          </el-select>
        </el-form-item>
        <el-form-item label="学年" :label-width="formLabelWidth">
          <el-input v-model="applyInternForm.schoolYear" autocomplete="off" />
        </el-form-item>
        <el-form-item label="备注" :label-width="formLabelWidth">
          <el-input v-model="applyInternForm.remark" autocomplete="off" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="applyIntern">申 请</el-button>
        <el-button @click="applyInternDialogVisible = false">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog
      title="导入文件信息"
      :visible.sync="importDialogVisible"
      top="5vh"
      :append-to-body="true"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      width="40%"
    >

      <el-form :model="importInternForm" label-width="120px">
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
        <el-form-item label="学年：" style="margin-top: 10px">
          <el-input v-model="importInternForm.schoolYear" placeholder="学年" clearable />
        </el-form-item>
        <el-form-item label="学期：" style="margin-top: 5px">
          <el-input v-model="importInternForm.semester" placeholder="学期" clearable />
        </el-form-item>

      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelImportFile">取 消</el-button>
        <el-button type="primary" @click="importFile">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>

import { downInternInfoTemplate, getInternInfo, exportInternFile, importInternInfoFile, modifyInternInfo, insertInternInfo } from '@/api/intern'
import { downloadExcel } from '@/utils/file'
import { showFullScreenLoading, hideFullScreenLoading } from '@/utils/loading'
import { mapGetters } from 'vuex'
import { calculateAssess } from '@/api/score'

export default {
  computed: {
    ...mapGetters([
      'semesterOptions',
      'schoolYearOptions'
    ])
  },
  data() {
    return {
      importDialogVisible: false,
      pageSize: 25,
      curPageNum: 1,
      total: 0,
      importInternForm: {},
      searchForm: {
        selectedSchoolYear: '',
        selectedSemester: '',
        selectedTeacherCode: ''
      },
      internInfo: [],
      internDialogVisible: false,
      internEditDisable: true,
      applyInternDialogVisible: false,
      internForm: {
        normalPractice: '',
        nonNormalPractice: '',
        schoolPractice: '',
        semester: '',
        schoolYear: '',
        remark: ''
      },
      applyInternForm: {
        normalPractice: '',
        nonNormalPractice: '',
        schoolPractice: '',
        semester: '',
        schoolYear: '',
        remark: ''
      },
      formLabelWidth: '140px',
      fileList: [],
      permissionMap: {}
    }
  },
  created() {
    const param = {
      pageSize: this.pageSize,
      pageNum: this.curPageNum
    }
    this.permissionMap = this.$store.getters.permissionMap['AssessmentInfo-Intern']
    this.getInternInfo(param)
  },
  methods: {
    internEdit: function(scope) {
      this.internEditDisable = false
      this.internDialogVisible = true
      this.internForm = JSON.parse(JSON.stringify(this.internInfo[scope.$index]))
      // 转换显示
      this.internForm.semester = String(this.internForm.semester)
    },
    internEditEnsure: function() {
      this.internDialogVisible = false
      this.internEditDisable = true
      const data = {
        id: this.internForm.id,
        teacherCode: this.internForm.teacherCode,
        teacherId: this.internForm.teacherId,
        nonNormalPractice: this.internForm.nonNormalPractice,
        normalPractice: this.internForm.normalPractice,
        schoolPractice: this.internForm.schoolPractice,
        semester: this.internForm.semester,
        schoolYear: this.internForm.schoolYear
      }
      showFullScreenLoading()
      modifyInternInfo(data, this.internForm.id)
        .then(response => {
          this.$message.success('修改成功')
          hideFullScreenLoading()
        })
        .catch(error => {
          console.log(error)
          hideFullScreenLoading()
        })
    },
    internEditCancel: function() {
      this.internDialogVisible = false
      this.internEditDisable = true
    },
    importIntern: function() {
      this.importDialogVisible = true
    },
    exportIntern: function() {
      const param = {}
      if (this.searchForm.selectedSemester && this.searchForm.selectedSemester !== '') {
        param.semester = this.searchForm.selectedSemester
      }
      if (this.searchForm.selectedSchoolYear && this.searchForm.selectedSchoolYear !== '') {
        param.schoolYear = this.searchForm.selectedSchoolYear
      }
      exportInternFile(param)
        .then(response => {
          downloadExcel(response, '实习带队信息导出文件.xlsx')
        })
        .catch(error => {
          console.log(error)
        })
    },
    getInternInfo: function(body) {
      if (this.permissionMap && this.permissionMap['getInternSearch-teacherCode'] && this.permissionMap['getInternSearch-teacherCode']['teacherCode']) {
        body.teacherCode = this.$store.getters.account === '' ? JSON.parse(sessionStorage.getItem('stateStore')).user.account : this.$store.getters.account
      }
      getInternInfo(body).then(response => {
        const { data } = response
        this.internInfo = data.list
        this.total = data.total
      }).catch(error => {
        console.log(error)
      })
    },
    prePage: function() {
      const param = {
        pageSize: this.pageSize,
        pageNum: this.curPageNum - 1
      }
      this.getInternInfo(param)
      this.curPageNum = this.curPageNum - 1
    },
    nextPage: function() {
      const param = {
        pageSize: this.pageSize,
        pageNum: this.curPageNum + 1
      }
      this.getInternInfo(param)
      this.curPageNum = this.curPageNum + 1
    },
    handleCurrentChange: function(val) {
      const param = {
        pageSize: this.pageSize,
        pageNum: val
      }
      this.getInternInfo(param)
      this.curPageNum = val
    },
    downloadTemplate: function() {
      downInternInfoTemplate()
        .then(response => {
          downloadExcel(response, '实习带队信息模板.xls')
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
      if (!this.importInternForm.semester || !this.importInternForm.schoolYear) {
        this.$notify.error({
          title: '错误',
          message: '请输入导入实习带队信息所属的学期及学年信息',
          duration: 2000
        })
        return
      }

      const body = new FormData()
      body.append('file', this.fileList[0].raw)
      body.append('semester', this.importInternForm.semester)
      body.append('schoolYear', this.importInternForm.schoolYear)

      showFullScreenLoading('文件上传中')
      importInternInfoFile(body)
        .then(response => {
          this.$notify.success({
            title: '导入成功',
            message: '实习带队信息导入成功' + response.data.successCount + '条，导入失败' + response.data.failCount + '条'
          })
          this.importDialogVisible = false
          hideFullScreenLoading()
        })
        .catch(error => {
          console.log(error)
          hideFullScreenLoading()
        })
    },
    applyIntern() {
      const data = {
        nonNormalPractice: this.applyInternForm.nonNormalPractice,
        normalPractice: this.applyInternForm.normalPractice,
        remark: this.applyInternForm.remark,
        schoolPractice: this.applyInternForm.schoolPractice,
        schoolYear: this.applyInternForm.schoolYear,
        semester: this.applyInternForm.semester,
        teacherCode: this.applyInternForm.teacherCode
      }
      showFullScreenLoading()
      insertInternInfo(data)
        .then(response => {
          hideFullScreenLoading()
          this.$message.success('新增成功')
          location.reload()
        })
        .catch(error => {
          console.log(error)
          hideFullScreenLoading()
        })
    },
    searchIntern() {
      const param = {
        pageNum: this.curPageNum,
        pageSize: this.pageSize
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
      this.getInternInfo(param)
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
  }

  .pagination {
    margin-top: 20px ;
    float: right ;
    margin-bottom: 20px
  }

  .input{
    margin-top: 10px;
    margin-right: 10px;
  }

</style>
