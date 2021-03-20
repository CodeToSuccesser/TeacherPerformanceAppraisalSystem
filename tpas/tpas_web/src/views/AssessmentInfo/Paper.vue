<template>
  <div class="app-container">
    <el-form ref="form" :model="searchForm">
      <el-select v-model="searchForm.selectedSchoolYear" placeholder="学年" class="selector-first">
        <el-option v-for="item in schoolYearOptions" :key="item.value" :label="item.label" :value="item.value" />
      </el-select>

      <el-select v-model="searchForm.selectedSemester" placeholder="学期" class="selector">
        <el-option v-for="item in semesterOptions" :key="item.key" :label="item.key" :value="item.value" />
      </el-select>

      <el-input v-model="searchForm.selectedMajorName" placeholder="专业名称" clearable class="selector" style="width: 120px" />

      <el-button type="primary" size="small" class="button-find">查找</el-button>

      <el-button type="primary" size="small" class="button-add" @click="applyPaperDialogVisible = true">申请新增</el-button>
      <el-button v-if="isAdmin" type="primary" size="small" class="button-add" @click="downloadTemplate">下载导入模板</el-button>
      <el-button v-if="isAdmin" type="primary" size="small" class="button-add" @click="importPaper">导入</el-button>
      <el-button v-if="isAdmin" type="primary" size="small" class="button-add" @click="exportPaper">导出</el-button>
    </el-form>

    <el-table :data="paperInfo" stripe style="width: 100% " :border="true" fit>
      <el-table-column :resizable="false" prop="id" sortable label="序号" align="center" width="60px">
        <template slot-scope="scope">
          <span>{{ (pageSize - 1) * (curPageNum - 1) + scope.$index + 1 }}</span>
        </template>
      </el-table-column>
      <el-table-column :resizable="false" prop="majorName" sortable label="专业名称" align="center" />
      <el-table-column :resizable="false" prop="studentNumber" sortable label="学生人数" align="center" />
      <el-table-column :resizable="false" prop="schoolYear" sortable label="学年" align="center" />
      <el-table-column :resizable="false" prop="semester" sortable label="学期" align="center" />
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
          <el-input v-model="paperForm.semester" autocomplete="off" :disabled="paperEditDisable" />
        </el-form-item>
        <el-form-item label="学年" :label-width="formLabelWidth">
          <el-input v-model="paperForm.schoolYear" autocomplete="off" :disabled="paperEditDisable" />
        </el-form-item>
        <el-form-item label="备注" :label-width="formLabelWidth">
          <el-input v-model="paperForm.remark" autocomplete="off" :disabled="paperEditDisable" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="paperEditEnsureOrCancel">确 定</el-button>
        <el-button @click="paperEditEnsureOrCancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog title="论文指导信息申请" :visible.sync="applyPaperDialogVisible" top="5vh" :append-to-body="true" :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form :model="applyPaperForm">
        <el-form-item label="专业名称" :label-width="formLabelWidth">
          <el-input v-model="applyPaperForm.majorName" autocomplete="off" />
        </el-form-item>
        <el-form-item label="学生人数" :label-width="formLabelWidth">
          <el-input v-model="applyPaperForm.studentNumber" autocomplete="off" />
        </el-form-item>
        <el-form-item label="学期" :label-width="formLabelWidth">
          <el-input v-model="applyPaperForm.semester" autocomplete="off" />
        </el-form-item>
        <el-form-item label="学年" :label-width="formLabelWidth">
          <el-input v-model="applyPaperForm.schoolYear" autocomplete="off" />
        </el-form-item>
        <el-form-item label="备注" :label-width="formLabelWidth">
          <el-input v-model="applyPaperForm.remark" autocomplete="off" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary">申 请</el-button>
        <el-button @click="applyPaperDialogVisible = false">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>

import { downloadPaperTemplate, getPaperInfo, exportPaperInfo } from '@/api/paper'
import { downloadExcel } from '@/utils/file'

export default {
  name: 'Paper',
  data() {
    return {
      pageSize: 25,
      curPageNum: 1,
      total: 0,
      paperInfo: [],
      searchForm: {
        selectedSemester: '',
        selectedSchoolYear: '',
        selectedMajorName: ''
      },
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
      isAdmin: this.$store.getters.userType === '' ? sessionStorage.getItem('userType') : this.$store.getters.userType
    }
  },
  created() {
    const param = {
      pageSize: this.pageSize,
      pageNum: this.curPageNum
    }
    this.getPaperInfo(param)
  },
  methods: {
    paperEdit: function(scope) {
      this.paperEditDisable = false
      this.paperDialogVisible = true
      this.paperForm = this.paperInfo[scope.$index]
    },
    paperEditEnsureOrCancel: function() {
      this.paperDialogVisible = false
      this.paperEditDisable = true
    },
    getPaperInfo: function(body) {
      const userType = this.$store.getters.userType === '' ? sessionStorage.getItem('userType') : this.$store.getters.userType
      if (Number(userType) !== 1) {
        body.teacherId = Number(this.$store.getters.id === '' ? sessionStorage.getItem('id') : this.$store.getters.id)
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

</style>
