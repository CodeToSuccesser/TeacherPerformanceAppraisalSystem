<template>
  <div class="app-container">
    <el-form ref="form" :model="searchForm">
      <el-select v-model="searchForm.selectedSchoolYear" placeholder="学年" clearable class="selector-first">
        <el-option v-for="item in schoolYearOptions" :key="item.value" :label="item.label" :value="item.value" />
      </el-select>

      <el-select v-model="searchForm.selectedSemester" placeholder="学期" clearable class="selector">
        <el-option v-for="item in semesterOptions" :key="item.value" :label="item.label" :value="item.value" />
      </el-select>

      <el-input v-model="searchForm.selectedMajorName" placeholder="专业名称" clearable class="selector" style="width: 120px" />

      <el-input
        v-if="this.permissionMap === undefined
          || this.permissionMap['getPaperSearch-teacherCode'] === undefined
          || this.permissionMap['getPaperSearch-teacherCode']['teacherCode'] === undefined"
        v-model="searchForm.selectedTeacherCode"
        placeholder="教师编码"
        clearable
        class="selector"
        style="width: 120px"
      />

      <el-button type="primary" size="small" class="button-find" @click="searchPaper">查找</el-button>

      <el-col>
        <el-button
          v-if="permissionMap && permissionMap['paperCalculateAssess-button']"
          type="primary"
          size="small"
          class="button-find"
          @click="calculateAssess(2)"
        >绩效考核</el-button>

        <el-button type="primary" size="small" class="button-add" @click="applyPaperDialogVisible = true">新增</el-button>
        <el-button
          v-if="permissionMap && permissionMap['downloadPaperTemplate-Button']"
          type="primary"
          size="small"
          class="button-add"
          @click="downloadTemplate"
        >下载导入模板</el-button>

        <el-button
          v-if="permissionMap && permissionMap['importPaper-Button']"
          type="primary"
          size="small"
          class="button-add"
          @click="importPaper"
        >导入</el-button>

        <el-button
          v-if="permissionMap && permissionMap['exportPaper-Button']"
          type="primary"
          size="small"
          class="button-add"
          @click="exportPaper"
        >导出</el-button>
      </el-col>
    </el-form>

    <el-table :data="paperInfo" stripe style="width: 100% " :border="true" fit>
      <el-table-column :resizable="false" prop="id" sortable label="序号" align="center" width="60px">
        <template slot-scope="scope">
          <span>{{ (pageSize - 1) * (curPageNum - 1) + scope.$index + 1 }}</span>
        </template>
      </el-table-column>
      <el-table-column :resizable="false" prop="teacherName" sortable label="教师姓名" align="center" />
      <el-table-column :resizable="false" prop="teacherCode" sortable label="教师编码" align="center" />
      <el-table-column :resizable="false" prop="majorName" sortable label="专业名称" align="center" />
      <el-table-column :resizable="false" prop="studentNumber" sortable label="学生人数" align="center" />
      <el-table-column :resizable="false" prop="schoolYear" sortable label="学年" align="center" />
      <el-table-column :resizable="false" prop="semester" sortable label="学期" align="center">
        <template slot-scope="scope">
          {{ Number(paperInfo[scope.$index].semester) === 0 ? '第一学期' : '第二学期' }}
        </template>
      </el-table-column>
      <el-table-column :resizable="false" prop="remark" sortable label="备注" align="center" />
      <el-table-column :resizable="false" prop="createTime" sortable label="创建日期" align="center" />
      <el-table-column :resizable="false" label="操作" align="center">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="paperEdit(scope)">修改</el-button>
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

    <el-dialog title="论文指导信息修改" :visible.sync="paperDialogVisible" top="5vh" :append-to-body="true" :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form :model="paperForm">
        <el-form-item label="专业名称" :label-width="formLabelWidth">
          <el-input v-model="paperForm.majorName" autocomplete="off" :disabled="paperEditDisable" />
        </el-form-item>
        <el-form-item label="学生人数" :label-width="formLabelWidth">
          <el-input v-model="paperForm.studentNumber" autocomplete="off" :disabled="paperEditDisable" />
        </el-form-item>
        <el-form-item label="学期" :label-width="formLabelWidth">
          <el-select v-model="paperForm.semester" placeholder="学期" :disabled="paperEditDisable">
            <el-option label="第一学期" value="0" />
            <el-option label="第二学期" value="1" />
          </el-select>
        </el-form-item>
        <el-form-item label="学年" :label-width="formLabelWidth">
          <el-input v-model="paperForm.schoolYear" autocomplete="off" :disabled="paperEditDisable" />
        </el-form-item>
        <el-form-item label="备注" :label-width="formLabelWidth">
          <el-input v-model="paperForm.remark" autocomplete="off" :disabled="paperEditDisable" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="paperEditEnsure">确 定</el-button>
        <el-button @click="paperEditCancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog title="论文指导信息申请" :visible.sync="applyPaperDialogVisible" top="5vh" :append-to-body="true" :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form :model="applyPaperForm">
        <el-form-item label="教师编号" :label-width="formLabelWidth">
          <el-input v-model="applyPaperForm.teacherCode" autocomplete="off" />
        </el-form-item>
        <el-form-item label="专业编码" :label-width="formLabelWidth">
          <el-input v-model="applyPaperForm.majorCode" autocomplete="off" />
        </el-form-item>
        <el-form-item label="专业名称" :label-width="formLabelWidth">
          <el-input v-model="applyPaperForm.majorName" autocomplete="off" />
        </el-form-item>
        <el-form-item label="学生人数" :label-width="formLabelWidth">
          <el-input v-model="applyPaperForm.studentNumber" autocomplete="off" />
        </el-form-item>
        <el-form-item label="学期" :label-width="formLabelWidth">
          <el-select v-model="applyPaperForm.semester" placeholder="学期">
            <el-option label="第一学期" value="0" />
            <el-option label="第二学期" value="1" />
          </el-select>        </el-form-item>
        <el-form-item label="学年" :label-width="formLabelWidth">
          <el-input v-model="applyPaperForm.schoolYear" autocomplete="off" />
        </el-form-item>
        <el-form-item label="备注" :label-width="formLabelWidth">
          <el-input v-model="applyPaperForm.remark" autocomplete="off" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="applyPaper">申 请</el-button>
        <el-button @click="applyPaperDialogVisible = false">取 消</el-button>
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

      <el-form :model="importPaperForm" label-width="120px">
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
          <el-input v-model="importPaperForm.schoolYear" placeholder="学年" clearable />
        </el-form-item>
        <el-form-item label="学期：" style="margin-top: 5px">
          <el-input v-model="importPaperForm.semester" placeholder="学期" clearable />
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

import { downloadPaperTemplate, getPaperInfo, exportPaperInfo, importPaperInfo, modifyPaperInfo, insertPaperInfo } from '@/api/paper'
import { downloadExcel } from '@/utils/file'
import { showFullScreenLoading, hideFullScreenLoading } from '@/utils/loading'
import { calculateAssess } from '@/api/score'
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
      pageSize: 25,
      curPageNum: 1,
      total: 0,
      paperInfo: [],
      searchForm: {
        selectedSemester: '',
        selectedSchoolYear: '',
        selectedMajorName: '',
        selectedTeacherCode: ''
      },
      importPaperForm: {
        semester: '',
        schoolYear: ''
      },
      importDialogVisible: false,
      paperDialogVisible: false,
      paperEditDisable: true,
      applyPaperDialogVisible: false,
      paperForm: {
        major: '',
        studentNumber: '',
        semester: '',
        schoolYear: '',
        remark: ''
      },
      applyPaperForm: {
        majorName: '',
        studentNumber: '',
        semester: '',
        schoolYear: '',
        remark: ''
      },
      formLabelWidth: '120px',
      isAdmin: false,
      fileList: [],
      permissionMap: {}
    }
  },
  created() {
    const param = {
      pageSize: this.pageSize,
      pageNum: this.curPageNum
    }
    this.permissionMap = this.$store.getters.permissionMap['AssessmentInfo-Paper']
    this.getPaperInfo(param)
  },
  methods: {
    paperEdit: function(scope) {
      this.paperEditDisable = false
      this.paperDialogVisible = true
      this.paperForm = JSON.parse(JSON.stringify(this.paperInfo[scope.$index]))
      this.paperForm.semester = String(this.paperForm.semester)
    },
    paperEditCancel: function() {
      this.paperDialogVisible = false
      this.paperEditDisable = true
    },
    paperEditEnsure: function() {
      this.paperDialogVisible = false
      this.paperEditDisable = true

      const data = {
        id: this.paperForm.id,
        majorId: this.paperForm.majorId,
        majorCode: this.paperForm.majorCode,
        teacherCode: this.paperForm.teacherCode,
        studentNumber: this.paperForm.studentNumber,
        semester: this.paperForm.semester,
        schoolYear: this.paperForm.schoolYear,
        teacherId: this.paperForm.teacherId
      }
      showFullScreenLoading()
      modifyPaperInfo(data, this.paperForm.id)
        .then(response => {
          this.$message.success('修改成功')
          hideFullScreenLoading()
        })
        .catch(error => {
          console.log(error)
          hideFullScreenLoading()
        })
    },
    getPaperInfo: function(body) {
      if (this.permissionMap && this.permissionMap['getPaperSearch-teacherCode'] && this.permissionMap['getPaperSearch-teacherCode']['teacherCode']) {
        body.teacherCode = this.$store.getters.account === '' ? JSON.parse(sessionStorage.getItem('stateStore')).user.account : this.$store.getters.account
      }
      getPaperInfo(body).then(response => {
        const { data } = response
        this.paperInfo = data.list
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
      this.getPaperInfo(param)
      this.curPageNum = this.curPageNum - 1
    },
    nextPage: function() {
      const param = {
        pageSize: this.pageSize,
        pageNum: this.curPageNum + 1
      }
      this.getPaperInfo(param)
      this.curPageNum = this.curPageNum + 1
    },
    handleCurrentChange: function(val) {
      const param = {
        pageSize: this.pageSize,
        pageNum: val
      }
      this.getPaperInfo(param)
      this.curPageNum = val
    },
    importPaper: function() {
      this.importDialogVisible = true
    },
    exportPaper: function() {
      const param = {}
      if (this.searchForm.selectedSemester && this.searchForm.selectedSemester !== '') {
        param.semester = this.searchForm.selectedSemester
      }
      if (this.searchForm.selectedSchoolYear && this.searchForm.selectedSchoolYear !== '') {
        param.schoolYear = this.searchForm.selectedSchoolYear
      }
      if (this.searchForm.selectedMajorName && this.searchForm.selectedMajorName !== '') {
        param.majorName = this.searchForm.selectedMajorName
      }
      exportPaperInfo(param)
        .then(response => {
          downloadExcel(response, '论文指导信息导出文件.xlsx')
        })
        .catch(error => {
          console.log(error)
        })
    },
    downloadTemplate: function() {
      downloadPaperTemplate()
        .then(response => {
          downloadExcel(response, '论文指导信息模板.xls')
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
      body.append('semester', this.importPaperForm.semester)
      body.append('schoolYear', this.importDialogVisible.schoolYear)

      showFullScreenLoading('文件上传中')
      importPaperInfo(body)
        .then(response => {
          this.$notify.success({
            title: '导入成功',
            message: '论文指导信息导入成功' + response.data.successCount + '条，导入失败' + response.data.failCount + '条'
          })
          this.importDialogVisible = false
          hideFullScreenLoading()
        })
        .catch(error => {
          console.log(error)
          hideFullScreenLoading()
        })
    },
    applyPaper() {
      const data = {
        majorCode: this.applyPaperForm.majorCode,
        majorName: this.applyPaperForm.majorName,
        remark: this.applyPaperForm.remark,
        schoolYear: this.applyPaperForm.schoolYear,
        semester: this.applyPaperForm.semester,
        studentNumber: this.applyPaperForm.studentNumber,
        teacherCode: this.applyPaperForm.teacherCode
      }
      showFullScreenLoading()
      insertPaperInfo(data)
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
    searchPaper() {
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
      if (this.searchForm.selectedMajorName !== undefined && this.searchForm.selectedMajorName !== '') {
        param.majorName = this.searchForm.selectedMajorName
      }
      this.getPaperInfo(param)
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
    margin-left: 10px;
    margin-bottom: 10px;
  }

  .pagination {
    margin-top: 20px ;
    float: right ;
    margin-bottom: 20px
  }

</style>
