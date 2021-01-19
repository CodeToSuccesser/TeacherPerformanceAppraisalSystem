<template>
  <div class="app-container">
    <el-select v-model="value" placeholder="年度" class="selector-year">
      <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
    </el-select>

    <el-select v-model="value" placeholder="学期" class="selector-term">
      <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
    </el-select>

    <el-button type="primary" size="small" class="button-find">查找</el-button>

    <el-button type="primary" size="small" class="button-add" @click="applyPaperDialogVisible = true">申请新增</el-button>

    <el-table :data="paperInfo" stripe style="width: 100% " :border="true" fit>
      <el-table-column :resizable="false" prop="id" sortable label="序号" />
      <el-table-column :resizable="false" prop="major" sortable label="专业名称" />
      <el-table-column :resizable="false" prop="studentNumber" sortable label="学生人数" />
      <el-table-column :resizable="false" prop="schoolYear" sortable label="学年" />
      <el-table-column :resizable="false" prop="semester" sortable label="学期" />
      <el-table-column :resizable="false" prop="remark" sortable label="备注" />
      <el-table-column :resizable="false" prop="createTime" sortable label="创建日期" />
      <el-table-column :resizable="false" label="操作">
        <template>
          <el-button type="text" size="small" @click="paperEdit">修改</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="论文指导信息修改" :visible.sync="paperDialogVisible" top="5vh" :append-to-body="true" :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form :model="paperForm">
        <el-form-item label="专业名称" :label-width="formLabelWidth">
          <el-input v-model="paperForm.major" autocomplete="off" :disabled="paperEditDisable" />
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
          <el-input v-model="applyPaperForm.major" autocomplete="off" />
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

export default {
  name: 'Paper',
  data() {
    return {
      paperInfo: [{
        date: ''
      }],
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
        major: '',
        studentNumber: '',
        semester: '',
        schoolYear: '',
        remark: ''
      },
      formLabelWidth: '120px'
    }
  },
  methods: {
    paperEdit: function() {
      this.paperEditDisable = false
      this.paperDialogVisible = true
    },
    paperEditEnsureOrCancel: function() {
      this.paperDialogVisible = false
      this.paperEditDisable = true
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

  .courseHourDialog {
    width: 70%;
    height: 80%;
    top: 5vh;
  }
</style>
