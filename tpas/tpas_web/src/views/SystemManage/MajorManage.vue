<template>
  <div class="app-container">
    <el-form ref="form" :model="searchForm">

      <el-input v-model="searchForm.majorName" placeholder="专业名称" clearable class="selector" style="width: 200px" />
      <el-button type="primary" size="small" class="button-find">查找</el-button>

      <el-button type="primary" size="small" class="button-add" @click="applyMajorDialogVisible = true">新增</el-button>
      <el-button type="primary" size="small" class="button-add">下载导入模板</el-button>
      <el-button type="primary" size="small" class="button-add">导入</el-button>
      <el-button type="primary" size="small" class="button-add">导出</el-button>

    </el-form>

    <el-table :data="majorInfo" stripe style="width: 100% " :border="true" fit>
      <el-table-column :resizable="false" prop="id" sortable label="序号" width="100px" />
      <el-table-column :resizable="false" prop="majorCode" sortable label="专业编码" />
      <el-table-column :resizable="false" prop="majorName" sortable label="专业名称" />
      <el-table-column :resizable="false" prop="remark" sortable label="备注" />
      <el-table-column :resizable="false" prop="createTime" sortable label="创建日期" />
      <el-table-column :resizable="false" label="操作">
        <template>
          <el-button type="text" size="small" @click="majorDialogVisible = true">修改</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog
      title="课程修改"
      :visible.sync="majorDialogVisible"
      top="5vh"
      :append-to-body="true"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
    >
      <el-form :model="majorForm">
        <el-form-item label="专业编码" :label-width="formLabelWidth">
          <el-input v-model="majorForm.majorCode" autocomplete="off" />
        </el-form-item>
        <el-form-item label="专业名称" :label-width="formLabelWidth">
          <el-input v-model="majorForm.majorName" autocomplete="off" />
        </el-form-item>
        <el-form-item label="备注" :label-width="formLabelWidth">
          <el-input v-model="majorForm.remark" autocomplete="off" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="majorEditEnsureOrCancel">确 定</el-button>
        <el-button @click="majorEditEnsureOrCancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog
      title="新增课程"
      :visible.sync="applyMajorDialogVisible"
      top="5vh"
      :append-to-body="true"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
    >
      <el-form :model="applyMajorForm">
        <el-form-item label="专业编码" :label-width="formLabelWidth">
          <el-input v-model="applyMajorForm.majorCode" autocomplete="off" />
        </el-form-item>
        <el-form-item label="专业名称" :label-width="formLabelWidth">
          <el-input v-model="applyMajorForm.majorName" autocomplete="off" />
        </el-form-item>
        <el-form-item label="备注" :label-width="formLabelWidth">
          <el-input v-model="applyMajorForm.remark" autocomplete="off" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="applyMajorEditEnsureOrCancel">确 定</el-button>
        <el-button @click="applyMajorEditEnsureOrCancel">取 消</el-button>
      </div>
    </el-dialog>

  </div>

</template>

<script>
export default {
  data() {
    return {
      searchForm: {
        majorName: ''
      },
      majorInfo: [],
      majorForm: {
      },
      applyMajorForm: {
      },
      majorDialogVisible: false,
      applyMajorDialogVisible: false,
      formLabelWidth: '120px'
    }
  },
  methods: {
    majorEditEnsureOrCancel() {

    },
    applyMajorEditEnsureOrCancel() {
      this.applyMajorDialogVisible = false
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

</style>
