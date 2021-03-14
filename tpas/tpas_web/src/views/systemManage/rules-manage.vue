<template>
  <div class="dashboard-container">

    <el-tabs v-model="activeName">
      <el-tab-pane label="权值参数" name="paramSetting">
        <el-select v-model="value" placeholder="权值类型" class="selector-year">
          <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>

        <el-select v-model="value" placeholder="权值元素" class="selector-term">
          <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>

        <el-button type="primary" size="small" class="button-find">查找</el-button>

        <el-button type="primary" size="small" class="button-add" @click="paramEditVisible = true">新增</el-button>

        <el-table :data="params" stripe style="width: 100% " :border="true" fit>
          <el-table-column :resizable="false" prop="id" sortable label="序号" width="120px" />
          <el-table-column :resizable="false" prop="cType" sortable label="权值类型" />
          <el-table-column :resizable="false" prop="cNum" sortable label="权值元素" />
          <el-table-column :resizable="false" prop="cOption" sortable label="下标" />
          <el-table-column :resizable="false" prop="rulesSettingIds" sortable label="限制规则Id" />
          <el-table-column :resizable="false" prop="valueType" sortable label="取值方式" />
          <el-table-column :resizable="false" prop="paramValue" sortable label="参数权值" />
          <el-table-column :resizable="false" prop="columnName" sortable label="字段名称" />
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
            <el-select v-model="paramForm.cType" placeholder="权值类型" class="selector-year">
              <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
            </el-select>
            <el-form-item label="权值元素" :label-width="formLabelWidth">
              <el-input v-model="paramForm.cNum" autocomplete="off" />
            </el-form-item>
            <el-form-item label="下标" :label-width="formLabelWidth">
              <el-input v-model="paramForm.cOption" autocomplete="off" />
            </el-form-item>
            <el-form-item label="限制规则" label-width="">
              <el-form-item v-model="paramForm.rulesSettingIds" autocomplete="off" />
            </el-form-item>
            <el-select v-model="paramForm.valueType" placeholder="取值类型" class="selector-year">
              <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
            </el-select>
            <el-form-item label="参数权值/字段名称" :label-width="formLabelWidth">
              <el-input v-if="paramForm.valueType===1" v-model="paramForm.paramValue" autocomplete="off" />
              <el-input v-else v-model="paramForm.columnName" autocomplete="off" />
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
      </el-tab-pane>

      <el-tab-pane label="参数规则" name="ruleSetting">
        <el-select v-model="value" placeholder="权值类型" class="selector-year">
          <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>

        <el-button type="primary" size="small" class="button-find">查找</el-button>

        <el-button type="primary" size="small" class="button-add" @click="ruleEditVisible = true">新增</el-button>

        <el-table :data="rules" stripe style="width: 100% " :border="true" fit>
          <el-table-column :resizable="false" prop="id" sortable label="序号" width="120px" />
          <el-table-column :resizable="false" prop="cType" sortable label="权值类型" />
          <el-table-column :resizable="false" prop="valueName" sortable label="限制字段" />
          <el-table-column :resizable="false" prop="ruleType" sortable label="规则类型" />
          <el-table-column :resizable="false" prop="leftValue" sortable label="区间左值/定值" />
          <el-table-column :resizable="false" prop="rightValue" sortable label="区间右值" />
          <el-table-column :resizable="false" prop="remark" sortable label="备注" />
          <el-table-column :resizable="false" prop="createTime" sortable label="创建日期" />
          <el-table-column :resizable="false" label="操作">
            <template>
              <el-button type="text" size="small" @click="ruleEdit">修改</el-button>
              <el-button type="text" size="small" @click="paramDelete">删除</el-button>
            </template>
          </el-table-column>
        </el-table>

        <el-dialog title="权值信息编辑" :visible.sync="ruleEditVisible" top="5vh" :append-to-body="true" :close-on-click-modal="false" :close-on-press-escape="false">
          <el-form :model="ruleForm">
            <el-select v-model="ruleForm.cType" placeholder="权值类型" class="selector-year">
              <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
            </el-select>
            <el-form-item label="限制字段" :label-width="formLabelWidth">
              <el-input v-model="ruleForm.valueName" autocomplete="off" />
            </el-form-item>
            <el-select v-model="ruleForm.ruleType" placeholder="规则类型" class="selector-year">
              <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
            </el-select>
            <el-form-item label="区间左值/定值" :label-width="formLabelWidth">
              <el-input v-model="ruleForm.leftValue" autocomplete="off" />
            </el-form-item>
            <el-form-item label="区间右值" :label-width="formLabelWidth">
              <el-input v-model="ruleForm.leftValue" autocomplete="off" />
            </el-form-item>
            <el-form-item label="备注" :label-width="formLabelWidth">
              <el-input v-model="ruleForm.remark" autocomplete="off" />
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="ruleEditEnsureOrCancel">确 定</el-button>
            <el-button @click="ruleEditEnsureOrCancel">取 消</el-button>
          </div>
        </el-dialog>
      </el-tab-pane>

      <el-tab-pane label="绩效规则" name="assessRule">
        <el-select v-model="value" placeholder="权值类型" class="selector-year">
          <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>

        <el-button type="primary" size="small" class="button-find">查找</el-button>

        <el-button type="primary" size="small" class="button-add" @click="assessEditVisible = true">新增</el-button>

        <el-table :data="assessRule" stripe style="width: 100% " :border="true" fit>
          <el-table-column :resizable="false" prop="id" sortable label="序号" width="120px" />
          <el-table-column :resizable="false" prop="cType" sortable label="权值类型" />
          <el-table-column :resizable="false" prop="assessDetail" sortable label="绩效计算公式" />
          <el-table-column :resizable="false" prop="ruleSettingIds" sortable label="限制规则id" />
          <el-table-column :resizable="false" prop="remark" sortable label="备注" />
          <el-table-column :resizable="false" prop="createTime" sortable label="创建日期" />
          <el-table-column :resizable="false" label="操作">
            <template>
              <el-button type="text" size="small" @click="assessEdit">修改</el-button>
              <el-button type="text" size="small" @click="paramDelete">删除</el-button>
            </template>
          </el-table-column>
        </el-table>

        <el-dialog title="权值信息编辑" :visible.sync="assessEditVisible" top="5vh" :append-to-body="true" :close-on-click-modal="false" :close-on-press-escape="false">
          <el-form :model="assessForm">
            <el-form-item label="权值类型" :label-width="formLabelWidth">
              <el-input v-model="assessForm.cType" autocomplete="off" />
            </el-form-item>
            <el-form-item label="绩效计算公式" :label-width="formLabelWidth">
              <el-input v-model="assessForm.assessDetail" autocomplete="off" />
            </el-form-item>
            <el-form-item label="限制规则id" :label-width="formLabelWidth">
              <el-input v-model="assessForm.ruleSettingIds" autocomplete="off" />
            </el-form-item>
            <el-form-item label="备注" :label-width="formLabelWidth">
              <el-input v-model="paramForm.remark" autocomplete="off" />
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="assessEditEnsureOrCancel">确 定</el-button>
            <el-button @click="assessEditEnsureOrCancel">取 消</el-button>
          </div>
        </el-dialog>
      </el-tab-pane>
    </el-tabs>

  </div>


</template>

<script>
export default {
  name: 'Index',
  data() {
    return {
      paramEditVisible: false,
      ruleEditVisible: false,
      assessEditVisible: false,
      params: [
        {
          id: 1,
          cType: '授课',
          cNum: 1,
          cOption: 1,
          rulesSettingIds: '1,2',
          valueType: '字段值',
          paramValue: 0,
          columnName: 'teachingHours',
          remark: '理论课C1, 理论课学时',
          createTime:'2021-02-23 20:58:51.0'
        }, {
          id: 2,
          cType: '授课',
          cNum: 1,
          cOption: 2,
          rulesSettingIds: '3',
          valueType: '字段值',
          paramValue: 0,
          columnName: 'experimentHours',
          remark: '课程实验C1, 实验学时',
          createTime:'2021-02-23 20:58:51.0'
        }, {
          id: 3,
          cType: '授课',
          cNum: 1,
          cOption: 3,
          rulesSettingIds: '4',
          valueType: '字段值',
          paramValue: 0,
          columnName: 'experimentHours',
          remark: '实验课程C1, 实验学时',
          createTime:'2021-02-23 20:58:51.0'
        }, {
          id: 4,
          cType: '授课',
          cNum: 2,
          cOption: 1,
          rulesSettingIds: '5',
          valueType: '参数定值',
          paramValue: 0,
          columnName: '',
          remark: '全日制本、专科授课C2',
          createTime:'2021-02-23 20:58:51.0'
        }, {
          id: 5,
          cType: '授课',
          cNum: 2,
          cOption: 2,
          rulesSettingIds: '6',
          valueType: '参数定值',
          paramValue: 0.2,
          columnName: '',
          remark: '全日制研究生授课C2',
          createTime:'2021-02-23 20:58:51.0'
        }, {
          id: 6,
          cType: '授课',
          cNum: 2,
          cOption: 3,
          rulesSettingIds: '7',
          valueType: '参数定值',
          paramValue: 0.3,
          columnName: '',
          remark: '研究生课程班授课C2',
          createTime:'2021-02-23 20:58:51.0'
        }
      ],
      paramForm: {
        id: 1,
        cType: '授课',
        cNum: 1,
        cOption: 1,
        rulesSettingIds: '1,2',
        valueType: 1,
        paramValue: 0,
        columnName: 'teachingHours',
        remark: '理论课C1, 理论课学时',
        createTime:'2021-02-23 20:58:51.0'
      },
      rules: [
        {
          id: 1,
          cType: '授课',
          valueName: 'computerHours',
          ruleType: '值区分',
          leftValue: 0,
          rightValue: 0,
          remark: '理论课-上机学时0',
          createTime: '2021-02-23 20:56:28.0'
        },{
          id: 2,
          cType: '授课',
          valueName: 'experimentHours',
          ruleType: '值区分',
          leftValue: 0,
          rightValue: 0,
          remark: '理论课-实验学时0',
          createTime: '2021-02-23 20:56:28.0'
        }, {
          id: 2,
          cType: '授课',
          valueName: 'experimentHours',
          ruleType: '区间区分',
          leftValue: 0.1,
          rightValue: '',
          remark: '课程实验',
          createTime: '2021-02-23 20:56:28.0'
      }],
      ruleForm: {
        id: 1,
        cType: '授课',
        valueName: 'computerHours',
        ruleType: '值区分',
        leftValue: 0,
        rightValue: 0,
        remark: '理论课-上机学时0',
        createTime: '2021-02-23 20:56:28.0'
      },
      assessRule: [
        {
          id: 1,
          cType: '授课',
          assessDetail: '{1}*{8}*(1+{2}+{3}+{4})',
          remark: '研究生、本、专科理论课',
          createTime: '2021-02-23 20:59:00.0',
          ruleSettingIds: '1,2'
        }, {
          id: 1,
          cType: '论文',
          assessDetail: '{13}*{12}',
          remark: '指导全日制本科生论文',
          createTime: '2021-02-23 20:59:00.0',
          ruleSettingIds: ''
        }, {
          id: 1,
          cType: '实习',
          assessDetail: '{14}*24',
          remark: '师范实习',
          createTime: '2021-02-23 20:59:00.0',
          ruleSettingIds: ''
        }
      ],
      assessForm: {
        id: 1,
        cType: '授课',
        assessDetail: '{1}*{8}*(1+{2}+{3}+{4})',
        remark: '研究生、本、专科理论课',
        createTime: '2021-02-23 20:59:00.0',
        ruleSettingIds: '1,2'
      },
      activeName: 'paramSetting'
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
    },
    ruleEditEnsureOrCancel: function () {
      this.ruleEditVisible = false
    },
    ruleEdit: function() {
      this.ruleEditVisible = true
    },
    assessEditEnsureOrCancel: function () {
      this.assessEditVisible = false
    },
    assessEdit: function() {
      this.assessEditVisible = true
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
