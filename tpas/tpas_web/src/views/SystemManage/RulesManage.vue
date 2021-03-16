<template>
  <div class="dashboard-container">

    <el-tabs v-model="activeName">
      <el-tab-pane label="权值参数" name="paramSetting">
        <el-select v-model="searchSelect[0].paramType" placeholder="权值类型" class="selector-year">
          <el-option v-for="item in paramType.keys()" :key="item" :label="paramType.get(item)" :value="item" />
        </el-select>

        <el-select v-model="searchSelect[0].paramCNum" placeholder="权值元素" class="selector-term">
          <el-option v-for="item in paramCNumList.keys()" :key="item" :label="paramCNumList.get(item)" :value="item" />
        </el-select>

        <el-button :v-loading="loadingVisible[0]" type="primary" size="small" class="button-find" @click="skipPage(0, 0)">查找</el-button>

        <el-button type="primary" size="small" class="button-add" @click="editComponentVisible[0] = true">新增</el-button>

        <el-table :data="paramList" stripe style="width: 100% " :border="true" fit>
          <el-table-column :resizable="false" prop="id" sortable label="序号" width="120px" >
            <template slot-scope="scope">
              <span>{{ (pageInfo[0].pageSize - 1) * (pageInfo[0].curPageNum - 1) + scope.$index + 1 }}</span>
            </template>
          </el-table-column>
          <el-table-column :resizable="false" prop="cType" sortable label="权值类型" >
            {{ paramType[paramList.cType] }}
          </el-table-column>
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

        <el-pagination
          background
          layout="prev, pager, next"
          :total="pageInfo[0].total"
          :page-size="pageInfo[0].pageSize"
          :current-page="pageInfo[0].curPageNum"
          class="pagination"
          @prev-click="skipPage(0, -1)"
          @next-click="skipPage(0, 1)"
          @current-change="(val) => handleCurrentChange(0, val)"
        />

        <el-dialog title="权值信息编辑" :visible.sync="editComponentVisible[0]" top="5vh" :append-to-body="true" :close-on-click-modal="false" :close-on-press-escape="false">
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
import { queryParamRules } from '@/api/sysParam'
import { mapGetters } from 'vuex'

export default {
  computed: {
    ...mapGetters([
      'paramCNumList',
      'paramType',
      'paramValueType',
      'paramColumnName'
    ])
  },
  data() {
    return {
      formLabelWidth: '120px',
      pageInfo: [
        {
          total: 0,
          pageSize: 25,
          curPageNum: 1
        }, {
          total: 0,
          pageSize: 25,
          curPageNum: 1
        }, {
          total: 0,
          pageSize: 25,
          curPageNum: 1
        }
      ],
      editComponentVisible: [
        false,
        false,
        false
      ],
      loadingVisible: [
        false,
        false,
        false
      ],
      searchSelect: [
        {
          paramType: '',
          paramCNum: ''
        }, {
          paramType: '',
          paramCNum: ''
        }, {
          paramType: '',
          paramCNum: ''
        }
      ],
      paramList: [],
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
        createTime: '2021-02-23 20:58:51.0'
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
        }, {
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
        }
      ],
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
  created() {
    console.log(this.$store.getters.paramCNumList.size)
    if (this.$store.getters.paramCNumList.size <= 1) {
      this.$store.dispatch('sysParam/queryCNumIndex')
    }
    console.log(this.$store.getters.paramCNumList)
    this.skipPage(0, 0)
    this.skipPage(1, 0)
    this.skipPage(2, 0)
  },
  methods: {
    skipPage: function(index, addPage) {
      const param = {
        pageNum: this.pageInfo[index].curPageNum + addPage,
        pageSize: this.pageInfo[index].pageSize
      }
      this.searchList(index, param)
      this.pageInfo[index].curPageNum = this.pageInfo[index].curPageNum + addPage
    },
    handleCurrentChange: function(index, val) {
      const param = {
        pageSize: this.pageInfo[0].pageSize,
        pageNum: val
      }
      switch (index) {
        case 0: {
          this.searchList(index, param)
          break
        }
        case 1: {
          this.searchList(index, param)
          break
        }
        case 2: {
          this.searchList(index, param)
          break
        }
        default: {
          return
        }
      }
      console.log('val: ', val)
      this.pageInfo[index].curPageNum = val
    },
    searchList: function(index, param) {
      const searchModel = param
      if (this.searchSelect[index].paramType && this.searchSelect[index].paramType !== '') {
        searchModel.cType = this.searchSelect[index].paramType
      }
      if (this.searchSelect[index].paramCNum && this.searchSelect[index].paramCNum !== '') {
        searchModel.cNum = this.searchSelect[index].paramCNum
      }
      switch (index) {
        case 0: {
          this.queryParams(searchModel)
          break
        }
        case 1: {
          this.queryRules(searchModel)
          break
        }
        case 2: {
          this.queryAssess(searchModel)
          break
        }
        default: {
          return
        }
      }
    },
    queryParams: function(reqData) {
      this.loadingVisible[0] = true
      queryParamRules(reqData).then((response) => {
        const { data } = response
        this.paramList = data.list
        this.pageInfo[0].total = data.total
        this.loadingVisible[0] = false
      }).catch(() => {
        this.loadingVisible[0] = false
      })
    },
    queryRules: function(data) {
    },
    queryAssess: function(data) {
    },
    paramEdit: function() {
      this.paramEditVisible = true
    },
    paramDelete: function() {

    },
    paramEditEnsureOrCancel: function() {
      this.paramEditVisible = false
    },
    ruleEditEnsureOrCancel: function() {
      this.ruleEditVisible = false
    },
    ruleEdit: function() {
      this.ruleEditVisible = true
    },
    assessEditEnsureOrCancel: function() {
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
