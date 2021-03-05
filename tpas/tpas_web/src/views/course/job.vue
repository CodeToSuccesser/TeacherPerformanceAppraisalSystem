<template>
  <div class="dashboard-container">
    <el-select v-model="value" placeholder="年度" class="selector-year">
      <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
    </el-select>

    <el-select v-model="value" placeholder="学期" class="selector-term">
      <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
    </el-select>

    <el-button type="primary" size="small" class="button-find">查找</el-button>

    <el-button type="primary" size="small" class="button-add" @click="applyJobDialogVisible = true">申请新增</el-button>
    <el-button v-if="isAdmin" type="primary" size="small" class="button-add" @click="importJob">导入</el-button>
    <el-button v-if="isAdmin" type="primary" size="small" class="button-add" @click="exportJob">导出</el-button>

    <el-table :data="jobInfo" stripe style="width: 100% " :border="true" fit>
      <el-table-column :resizable="false" prop="id" sortable label="序号" width="120px" />
      <el-table-column :resizable="false" prop="teacherName" sortable label="教师姓名" />
      <el-table-column :resizable="false" prop="normalPractice" sortable label="师范实习带队人数" />
      <el-table-column :resizable="false" prop="nonNormalPractice" sortable label="非师范实习带队人数" />
      <el-table-column :resizable="false" prop="schoolPractice" sortable label="校内实习带队人数" />
      <el-table-column :resizable="false" prop="semester" sortable label="学期" />
      <el-table-column :resizable="false" prop="schoolYear" sortable label="学年" />
      <el-table-column :resizable="false" prop="remark" sortable label="备注" />
      <el-table-column :resizable="false" prop="createTime" sortable label="创建日期" />
      <el-table-column :resizable="false" label="操作">
        <template>
          <el-button type="text" size="small" @click="jobEdit">修改</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="实习带队信息修改" :visible.sync="jobDialogVisible" top="5vh" :append-to-body="true" :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form :model="jobForm">
        <el-form-item label="师范实习带队人数" :label-width="formLabelWidth">
          <el-input v-model="jobForm.normalPractice" autocomplete="off" :disabled="jobEditDisable" />
        </el-form-item>
        <el-form-item label="非师范实习带队人数" :label-width="formLabelWidth">
          <el-input v-model="jobForm.nonNormalPractice" autocomplete="off" :disabled="jobEditDisable" />
        </el-form-item>
        <el-form-item label="校内实习带队人数" :label-width="formLabelWidth">
          <el-input v-model="jobForm.schoolPractice" autocomplete="off" :disabled="jobEditDisable" />
        </el-form-item>
        <el-form-item label="学期" :label-width="formLabelWidth">
          <el-input v-model="jobForm.semester" autocomplete="off" :disabled="jobEditDisable" />
        </el-form-item>
        <el-form-item label="学年" :label-width="formLabelWidth">
          <el-input v-model="jobForm.schoolYear" autocomplete="off" :disabled="jobEditDisable" />
        </el-form-item>
        <el-form-item label="备注" :label-width="formLabelWidth">
          <el-input v-model="jobForm.remark" autocomplete="off" :disabled="jobEditDisable" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="jobEditEnsureOrCancel">确 定</el-button>
        <el-button @click="jobEditEnsureOrCancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog title="实习带队信息申请" :visible.sync="applyJobDialogVisible" top="5vh" :append-to-body="true" :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form :model="applyJobForm">
        <el-form-item label="师范实习带队人数" :label-width="formLabelWidth">
          <el-input v-model="applyJobForm.normalPractice" autocomplete="off" />
        </el-form-item>
        <el-form-item label="非师范实习带队人数" :label-width="formLabelWidth">
          <el-input v-model="applyJobForm.nonNormalPractice" autocomplete="off" />
        </el-form-item>
        <el-form-item label="校内实习带队人数" :label-width="formLabelWidth">
          <el-input v-model="applyJobForm.schoolPractice" autocomplete="off" />
        </el-form-item>
        <el-form-item label="学期" :label-width="formLabelWidth">
          <el-input v-model="applyJobForm.semester" autocomplete="off" />
        </el-form-item>
        <el-form-item label="学年" :label-width="formLabelWidth">
          <el-input v-model="applyJobForm.schoolYear" autocomplete="off" />
        </el-form-item>
        <el-form-item label="备注" :label-width="formLabelWidth">
          <el-input v-model="applyJobForm.remark" autocomplete="off" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary">申 请</el-button>
        <el-button @click="applyJobDialogVisible = false">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>

export default {
  name: 'Job',
  data() {
    return {
      jobInfo: [{
        date: ''
      }],
      jobDialogVisible: false,
      jobEditDisable: true,
      applyJobDialogVisible: false,
      jobForm: {
        normalPractice: '',
        nonNormalPractice: '',
        schoolPractice: '',
        semester: '',
        schoolYear: '',
        remark: ''
      },
      applyJobForm: {
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
  methods: {
    jobEdit: function() {
      this.jobEditDisable = false
      this.jobDialogVisible = true
    },
    jobEditEnsureOrCancel: function() {
      this.jobDialogVisible = false
      this.jobEditDisable = true
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

</style>
