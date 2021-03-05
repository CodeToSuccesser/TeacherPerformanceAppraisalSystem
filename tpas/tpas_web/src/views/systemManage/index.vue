<template>
  <div class="dashboard-container">
    <el-select v-model="value" placeholder="权值" class="selector-year">
      <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
    </el-select>

    <el-select v-model="value" placeholder="下标" class="selector-term">
      <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
    </el-select>

    <el-button type="primary" size="small" class="button-find">查找</el-button>

    <el-button type="primary" size="small" class="button-add" @click="paramEditVisible = true">新增</el-button>

    <el-table :data="params" stripe style="width: 100% " :border="true" fit>
      <el-table-column :resizable="false" prop="id" sortable label="序号" width="120px" />
      <el-table-column :resizable="false" prop="cNum" sortable label="权值类型" />
      <el-table-column :resizable="false" prop="cOption" sortable label="下标" />
      <el-table-column :resizable="false" prop="value" sortable label="参数权值" />
      <el-table-column :resizable="false" prop="remark" sortable label="备注" />
      <el-table-column :resizable="false" prop="createTime" sortable label="创建日期" />
      <el-table-column :resizable="false" label="操作">
        <template>
          <el-button type="text" size="small" @click="paramEdit">修改</el-button>
          <el-button type="text" size="small" @click="paramDelete">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="权值信息编辑" :visible.sync="paramEditVisible" top="5vh" :append-to-body="true" :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form :model="paramForm">
        <el-form-item label="权值类型" :label-width="formLabelWidth">
          <el-input v-model="paramForm.cNum" autocomplete="off" />
        </el-form-item>
        <el-form-item label="下标" :label-width="formLabelWidth">
          <el-input v-model="paramForm.cOption" autocomplete="off" />
        </el-form-item>
        <el-form-item label="参数权值" :label-width="formLabelWidth">
          <el-input v-model="paramForm.value" autocomplete="off" />
        </el-form-item>
        <el-form-item label="备注" :label-width="formLabelWidth">
          <el-input v-model="paramForm.remark" autocomplete="off" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="paramEditEnsureOrCancel">确 定</el-button>
        <el-button @click="paramEditEnsureOrCancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>

</template>

<script>
export default {
  name: 'Index',
  data() {
    return {
      paramEditVisible: false,
      params: [
        {
          id: ''
        }
      ],
      paramForm: {
        id: ''
      }
    }
  },
  methods: {
    paramEdit: function() {
      this.paramEditVisible = true
    },
    paramDelete: function() {

    },
    paramEditEnsureOrCancel: function() {
      this.paramEditVisible = false
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
