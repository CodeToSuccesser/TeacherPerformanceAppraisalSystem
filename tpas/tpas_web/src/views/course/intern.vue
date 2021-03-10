<template>
  <div class="dashboard-container">
    <el-form ref="form" :model="searchForm">
      <el-select v-model="searchForm.selectedSchoolYear" placeholder="年度" class="selector-first">
        <el-option v-for="item in schoolYearOptions" :key="item.key" :label="item.key" :value="item.value" />
      </el-select>

      <el-select v-model="searchForm.selectedSemester" placeholder="学期" class="selector">
        <el-option v-for="item in semesterOptions" :key="item.key" :label="item.key" :value="item.value" />
      </el-select>

      <el-button type="primary" size="small" class="button-find">查找</el-button>

      <el-button type="primary" size="small" class="button-add" @click="applyInternDialogVisible = true">申请新增</el-button>
      <el-button v-if="isAdmin" type="primary" size="small" class="button-add" @click="importIntern">导入</el-button>
      <el-button v-if="isAdmin" type="primary" size="small" class="button-add" @click="exportIntern">导出</el-button>
    </el-form>

    <el-table :data="internInfo" stripe style="width: 100% " :border="true" fit>
      <el-table-column :resizable="false" prop="id" sortable label="序号" align="center">
        <template slot-scope="scope">
          <span>{{ (pageSize - 1) * (curPageNum - 1) + scope.$index + 1 }}</span>
        </template>
      </el-table-column>
      <el-table-column :resizable="false" prop="teacherName" sortable label="教师姓名" align="center" />
      <el-table-column :resizable="false" prop="normalPractice" sortable label="师范实习带队人数" align="center" />
      <el-table-column :resizable="false" prop="nonNormalPractice" sortable label="非师范实习带队人数" align="center" />
      <el-table-column :resizable="false" prop="schoolPractice" sortable label="校内实习带队人数" align="center" />
      <el-table-column :resizable="false" prop="semester" sortable label="学期" align="center" />
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
          <el-input v-model="internForm.semester" autocomplete="off" :disabled="internEditDisable" />
        </el-form-item>
        <el-form-item label="学年" :label-width="formLabelWidth">
          <el-input v-model="internForm.schoolYear" autocomplete="off" :disabled="internEditDisable" />
        </el-form-item>
        <el-form-item label="备注" :label-width="formLabelWidth">
          <el-input v-model="internForm.remark" autocomplete="off" :disabled="internEditDisable" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="internEditEnsureOrCancel">确 定</el-button>
        <el-button @click="internEditEnsureOrCancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog title="实习带队信息申请" :visible.sync="applyInternDialogVisible" top="5vh" :append-to-body="true" :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form :model="applyInternForm">
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
          <el-input v-model="applyInternForm.semester" autocomplete="off" />
        </el-form-item>
        <el-form-item label="学年" :label-width="formLabelWidth">
          <el-input v-model="applyInternForm.schoolYear" autocomplete="off" />
        </el-form-item>
        <el-form-item label="备注" :label-width="formLabelWidth">
          <el-input v-model="applyInternForm.remark" autocomplete="off" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary">申 请</el-button>
        <el-button @click="applyInternDialogVisible = false">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>

import { getInternInfo } from '@/api/intern'

export default {
  name: 'intern',
  data() {
    return {
      pageSize: 25,
      curPageNum: 1,
      total: 0,
      searchForm: {
        selectedSchoolYear: '',
        selectedSemester: ''
      },
      schoolYearOptions: '',
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
      internInfo: [{
        date: ''
      }],
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
      isAdmin: this.$store.getters.userType === '' ? sessionStorage.getItem('userType') : this.$store.getters.userType
    }
  },
  created() {
    const param = {
      pageSize: this.pageSize,
      pageNum: this.curPageNum
    }
    if (this.$store.getters.userType !== 0) {
      param.teacherId = Number(this.$store.getters.id === '' ? sessionStorage.getItem('id') : this.$store.getters.id)
    }
    this.getInternInfo(param)
  },
  methods: {
    internEdit: function(scope) {
      this.internEditDisable = false
      this.internDialogVisible = true
      this.internForm = this.internInfo[scope.$index]
    },
    internEditEnsureOrCancel: function() {
      this.internDialogVisible = false
      this.internEditDisable = true
    },
    importIntern: function() {

    },
    exportIntern: function() {

    },
    getInternInfo: function(body) {
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
      if (this.$store.getters.userType !== 0) {
        param.teacherId = Number(this.$store.getters.id === '' ? sessionStorage.getItem('id') : this.$store.getters.id)
      }
      this.getInternInfo(param)
      this.curPageNum = this.curPageNum - 1
    },
    nextPage: function() {
      const param = {
        pageSize: this.pageSize,
        pageNum: this.curPageNum + 1
      }
      if (this.$store.getters.userType !== 0) {
        param.teacherId = Number(this.$store.getters.id === '' ? sessionStorage.getItem('id') : this.$store.getters.id)
      }
      this.getInternInfo(param)
      this.curPageNum = this.curPageNum + 1
    },
    handleCurrentChange: function(val) {
      const param = {
        pageSize: this.pageSize,
        pageNum: val
      }
      if (this.$store.getters.userType !== 0) {
        param.teacherId = Number(this.$store.getters.id === '' ? sessionStorage.getItem('id') : this.$store.getters.id)
      }
      this.getInternInfo(param)
      this.curPageNum = val
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
