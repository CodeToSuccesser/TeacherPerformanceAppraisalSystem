<template>
  <div class="dashboard-container">

    <el-tabs v-model="activeName">
      <el-tab-pane label="权值参数" name="paramSetting">
        <el-select v-model="searchSelect[0].paramType" placeholder="权值类型" class="selector-year">
          <el-option v-for="item in paramType" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>

        <el-select v-model="searchSelect[0].paramCNum" placeholder="权值元素" class="selector-term">
          <el-option v-for="item in paramCNumList" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>

        <el-button :v-loading="loadingVisible[0]" type="primary" size="small" class="button-find" @click="handleCurrentChange(0, 0)">查找</el-button>

        <el-button type="primary" size="small" class="button-add" @click="paramEdit(null)">新增</el-button>

        <el-table :data="paramList" stripe style="width: 100% " :border="true" fit>
          <el-table-column :resizable="false" prop="id" sortable label="序号" width="60px" align="center" />
          <el-table-column :resizable="false" prop="cType" sortable label="权值类型"  align="center" >
            <template slot-scope="scope">
              {{ paramType.filter(item => item.value === scope.row.cType)[0].label }}
            </template>
          </el-table-column>
          <el-table-column :resizable="false" prop="cNum" sortable label="权值元素" width="60px" align="center" />
          <el-table-column :resizable="false" prop="cOption" sortable label="下标" width="60px" align="center" />
          <el-table-column :resizable="false" prop="rulesSettingIds" sortable label="限制规则" width="180px">
            <template slot-scope="scope">
              <div v-for="item in getRuleIds(scope.row.rulesSettingIds)" :key="item">
                {{ ruleBaseInfo.filter(it => it.id === item).length>0?ruleBaseInfo.filter(it => it.id === item)[0].ruleName:'?' }}
              </div>
            </template>
          </el-table-column>
          <el-table-column :resizable="false" prop="valueType" sortable label="取值方式" align="center">
            <template slot-scope="scope">
              {{ paramValueType.filter(item => item.value === scope.row.valueType)[0].label }}
            </template>
          </el-table-column>
          <el-table-column :resizable="false" sortable label="取值"  align="center">
            <template slot-scope="scope">
              <span v-if="scope.row.valueType === 1">
              {{ paramColumnName.filter(item => item.paramType === scope.row.cType && item.columnName === scope.row.columnName).length>0?
              paramColumnName.filter(item => item.paramType === scope.row.cType && item.columnName === scope.row.columnName)[0].text:scope.row.columnName}}
              </span>
              <span v-else>
                {{ scope.row.paramValue }}
              </span>
            </template>
          </el-table-column>
          <el-table-column :resizable="false" prop="remark" sortable label="备注" />
          <el-table-column :resizable="false" prop="createTime" sortable label="创建日期" />
          <el-table-column :resizable="false" label="操作"  align="center">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="paramEdit(scope)">修改</el-button>
              <el-button type="text" size="small" @click="paramDelete(scope)">删除</el-button>
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
            <el-form-item v-if="paramForm.id" label="编码" :label-width="formLabelWidth">
              <el-input v-model="paramForm.id" :disabled="true" autocomplete="off" />
            </el-form-item>
            <el-form-item label="权值类型" :label-width="formLabelWidth">
              <el-select v-model="paramForm.cType" :label-width="formLabelWidth">
                <el-option
                  v-for="item in paramType.filter(it => it.value !== '')"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value" />
              </el-select>
            </el-form-item>
            <el-form-item label="权值元素" :label-width="formLabelWidth">
              <el-input v-model="paramForm.cNum" autocomplete="off" />
            </el-form-item>
            <el-form-item label="下标" :label-width="formLabelWidth">
              <el-input v-model="paramForm.cOption" autocomplete="off" />
            </el-form-item>
            <el-form-item label="限制规则" :label-width="formLabelWidth">
              <el-checkbox-group v-model="checkRuleIdList" @change="handleCheckedParamRuleIds(0)">
                <el-checkbox
                  v-for="item in ruleBaseInfo.filter(it => it.cType === paramForm.cType)"
                  :key="item.id"
                  :label="item.id"
                  border
                >{{ item.ruleName }}</el-checkbox>
              </el-checkbox-group>
            </el-form-item>
            <el-form-item label="取值类型" :label-width="formLabelWidth">
              <el-select v-model="paramForm.valueType" class="selector-year">
                <el-option v-for="item in paramValueType" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
            <el-form-item v-if="paramForm.valueType===1" label="字段名称" :label-width="formLabelWidth">
              <el-select v-model="paramForm.columnName" :label-width="formLabelWidth">
                <el-option
                  v-for="item in paramColumnName"
                  :key="JSON.stringify(item)"
                  :label="item.text"
                  :value="item.columnName" />
              </el-select>
            </el-form-item>
            <el-form-item v-else label="参数权值" :label-width="formLabelWidth">
              <el-input v-model="paramForm.paramValue" autocomplete="off" />
            </el-form-item>
            <el-form-item label="备注" :label-width="formLabelWidth">
              <el-input v-model="paramForm.remark" autocomplete="off" />
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="editEnsureOrCancel(0, false)">确 定</el-button>
            <el-button @click="editEnsureOrCancel(0, true)">取 消</el-button>
          </div>
        </el-dialog>
      </el-tab-pane>

      <el-tab-pane label="参数规则" name="ruleSetting">
        <el-select v-model="searchSelect[1].paramType" placeholder="权值类型" class="selector-year">
          <el-option v-for="item in paramType" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>

        <el-button :v-loading="loadingVisible[1]" type="primary" size="small" class="button-find" @click="handleCurrentChange(1, 0)">查找</el-button>

        <el-button type="primary" size="small" class="button-add" @click="ruleEdit(null)">新增</el-button>

        <el-table :data="ruleList" stripe style="width: 100% " :border="true" fit>
          <el-table-column :resizable="false" prop="id" sortable label="序号" width="60px" align="center"/>
          <el-table-column :resizable="false" prop="ruleName" label="规则名称" />
          <el-table-column :resizable="false" prop="cType" sortable label="权值类型" align="center">
            <template slot-scope="scope">
              {{ paramType.filter(item => item.value === scope.row.cType)[0].label }}
            </template>
          </el-table-column>
          <el-table-column :resizable="false" prop="valueName" sortable label="限制字段" align="center">
            <template slot-scope="scope">
              {{ paramColumnName.filter(item => item.paramType === scope.row.cType && item.columnName === scope.row.valueName).length>0?
              paramColumnName.filter(item => item.paramType === scope.row.cType && item.columnName === scope.row.valueName)[0].text:scope.row.valueName}}
            </template>
          </el-table-column>
          <el-table-column :resizable="false" prop="ruleType" sortable label="比较类型" align="center">
            <template slot-scope="scope">
              {{ ruleCompareType.filter(item => item.value === scope.row.ruleType)[0].label }}
            </template>
          </el-table-column>
          <el-table-column :resizable="false" label="比较值" align="center">
            <template slot-scope="scope">
              <span v-if="scope.row.ruleType === 1">
                {{ scope.row.leftValue }}
              </span>
              <span v-else>
                [{{ scope.row.leftValue }}, {{ scope.row.rightValue?scope.row.rightValue:'-' }}]
              </span>
            </template>
          </el-table-column>
          <el-table-column :resizable="false" prop="remark" sortable label="备注" />
          <el-table-column :resizable="false" prop="createTime" sortable label="创建日期" />
          <el-table-column :resizable="false" label="操作" align="center">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="ruleEdit(scope)">修改</el-button>
              <el-button type="text" size="small" @click="ruleDelete(scope)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>

        <el-pagination
          background
          layout="prev, pager, next"
          :total="pageInfo[1].total"
          :page-size="pageInfo[1].pageSize"
          :current-page="pageInfo[1].curPageNum"
          class="pagination"
          @prev-click="skipPage(1, -1)"
          @next-click="skipPage(1, 1)"
          @current-change="(val) => handleCurrentChange(1, val)"
        />

        <el-dialog title="权值信息编辑" :visible.sync="editComponentVisible[1]" top="5vh" :append-to-body="true" :close-on-click-modal="false" :close-on-press-escape="false">
          <el-form :model="ruleForm">
            <el-form-item v-if="ruleForm.id" label="编码" :label-width="formLabelWidth">
              <el-input v-model="ruleForm.id" :disabled="true" autocomplete="off" />
            </el-form-item>
            <el-form-item label="规则名称" :label-width="formLabelWidth">
              <el-input v-model="ruleForm.ruleName" autocomplete="off" />
            </el-form-item>
            <el-form-item label="权值类型" :label-width="formLabelWidth">
              <el-select v-model="ruleForm.cType" :label-width="formLabelWidth">
                <el-option
                  v-for="item in paramType.filter(it => it.value !== '')"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value" />
              </el-select>
            </el-form-item>
            <el-form-item label="限制字段" :label-width="formLabelWidth">
              <el-select v-model="ruleForm.valueName" :label-width="formLabelWidth">
                <el-option
                  v-for="item in paramColumnName.filter(it => it.paramType === ruleForm.cType)"
                  :key="JSON.stringify(item)"
                  :label="item.text"
                  :value="item.columnName" />
              </el-select>
            </el-form-item>
            <el-form-item label="规则类型" :label-width="formLabelWidth">
              <el-select v-model="ruleForm.ruleType" class="selector-year">
                <el-option v-for="item in ruleCompareType" :key="JSON.stringify(item)" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
            <el-form-item v-if="ruleForm.ruleType === 1" label="比较值" :label-width="formLabelWidth">
              <el-input v-model="ruleForm.leftValue" autocomplete="off" />
            </el-form-item>
            <el-form-item v-if="ruleForm.ruleType === 2" label="比较区间左值" :label-width="formLabelWidth">
              <el-input v-model="ruleForm.leftValue" autocomplete="off" />
            </el-form-item>
            <el-form-item v-if="ruleForm.ruleType === 2" label="比较区间右值" :label-width="formLabelWidth">
              <el-input v-model="ruleForm.rightValue" autocomplete="off" />
            </el-form-item>
            <el-form-item label="备注" :label-width="formLabelWidth">
              <el-input v-model="ruleForm.remark" autocomplete="off" />
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="editEnsureOrCancel(1, false)">确 定</el-button>
            <el-button @click="editEnsureOrCancel(1, true)">取 消</el-button>
          </div>
        </el-dialog>
      </el-tab-pane>

      <el-tab-pane label="绩效规则" name="assessRule">
        <el-select v-model="searchSelect[2].paramType" placeholder="权值类型" class="selector-year">
          <el-option v-for="item in paramType" :key="item.label" :label="item.label" :value="item.value" />
        </el-select>

        <el-button :v-loading="loadingVisible[2]" type="primary" size="small" class="button-find" @click="handleCurrentChange(2, 0)">查找</el-button>

        <el-button type="primary" size="small" class="button-add" @click="assessEdit(null)">新增</el-button>

        <el-table :data="assessList" stripe style="width: 100% " :border="true" fit>
          <el-table-column :resizable="false" prop="id" sortable label="序号" width="60px" align="center"/>
          <el-table-column :resizable="false" prop="cType" sortable label="权值类型" align="center">
            <template slot-scope="scope">
              {{ paramType.filter(item => item.value === scope.row.cType)[0].label }}
            </template>
          </el-table-column>
          <el-table-column :resizable="false" prop="assessDetail" sortable label="绩效计算公式" align="center"/>
          <el-table-column :resizable="false" prop="ruleSettingIds" sortable label="限制规则id"  width="180px">
            <template slot-scope="scope">
              <div v-for="item in getRuleIds(scope.row.ruleSettingIds)" :key="item">
                {{ ruleBaseInfo.filter(it => it.id === item).length>0?ruleBaseInfo.filter(it => it.id === item)[0].ruleName:'?' }}
              </div>
            </template>
          </el-table-column>
          <el-table-column :resizable="false" prop="remark" sortable label="备注" />
          <el-table-column :resizable="false" prop="createTime" sortable label="创建日期" />
          <el-table-column :resizable="false" label="操作" align="center">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="assessEdit(scope)">修改</el-button>
              <el-button type="text" size="small" @click="assessDelete(scope)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>

        <el-pagination
          background
          layout="prev, pager, next"
          :total="pageInfo[2].total"
          :page-size="pageInfo[2].pageSize"
          :current-page="pageInfo[2].curPageNum"
          class="pagination"
          @prev-click="skipPage(2, -1)"
          @next-click="skipPage(2, 1)"
          @current-change="(val) => handleCurrentChange(2, val)"
        />

        <el-dialog title="权值信息编辑" :visible.sync="editComponentVisible[2]" top="5vh" :append-to-body="true" :close-on-click-modal="false" :close-on-press-escape="false">
          <el-form :model="assessForm">
            <el-form-item v-if="assessForm.id" label="编码" :label-width="formLabelWidth">
              <el-input v-model="assessForm.id" :disabled="true" autocomplete="off" />
            </el-form-item>
            <el-form-item label="权值类型" :label-width="formLabelWidth">
              <el-select v-model="assessForm.cType" placeholder="权值类型" :label-width="formLabelWidth">
                <el-option
                  v-for="item in paramType.filter(it => it.value !== '')"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value" />
              </el-select>
            </el-form-item>
            <el-form-item label="绩效计算公式" :label-width="formLabelWidth">
              <el-input v-model="assessForm.assessDetail" autocomplete="off" />
            </el-form-item>
            <el-form-item label="限制规则" :label-width="formLabelWidth">
              <el-checkbox-group v-model="checkRuleIdList" @change="handleCheckedParamRuleIds(2)">
                <el-checkbox
                  v-for="item in ruleBaseInfo.filter(it => it.cType === assessForm.cType)"
                  :key="item.id"
                  :label="item.id"
                  border
                >{{ item.ruleName }}</el-checkbox>
              </el-checkbox-group>
            </el-form-item>
            <el-form-item label="备注" :label-width="formLabelWidth">
              <el-input v-model="paramForm.remark" autocomplete="off" />
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="editEnsureOrCancel(2, false)">确 定</el-button>
            <el-button @click="editEnsureOrCancel(2, true)">取 消</el-button>
          </div>
        </el-dialog>
      </el-tab-pane>
    </el-tabs>

  </div>

</template>

<script>
import {
  queryParamRules,
  queryRuleList,
  queryAssessList,
  getRuleList,
  editParamRules,
  editRuleSetting,
  editAssessRule,
  deleteParamRules,
  deleteRuleSetting,
  deleteAssessRule
} from '@/api/sysParam'
import { mapGetters } from 'vuex'
import { string2List } from '@/utils'

export default {
  computed: {
    ...mapGetters([
      'paramCNumList',
      'paramType',
      'paramValueType',
      'paramColumnName',
      'ruleCompareType'
    ])
  },
  data() {
    return {
      formLabelWidth: '120px',
      checkRuleIdList: [],
      ruleBaseInfo: [],
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
          paramType: undefined,
          paramCNum: undefined
        }, {
          paramType: undefined,
          paramCNum: undefined
        }, {
          paramType: undefined,
          paramCNum: undefined
        }
      ],
      paramList: [],
      paramForm: {},
      ruleList: [],
      ruleForm: {},
      assessList: [],
      assessForm: {},
      activeName: 'paramSetting'
    }
  },
  created() {
    this.$store.dispatch('sysParam/queryCNumIndex')
    this.setRuleBaseInfo()
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
    queryRules: function(reqData) {
      this.loadingVisible[1] = true
      queryRuleList(reqData).then((response) => {
        const { data } = response
        this.ruleList = data.list
        this.pageInfo[1].total = data.total
        this.loadingVisible[1] = false
      }).catch(() => {
        this.loadingVisible[1] = false
      })
    },
    queryAssess: function(reqData) {
      this.loadingVisible[2] = true
      queryAssessList(reqData).then((response) => {
        const { data } = response
        this.assessList = data.list
        this.pageInfo[2].total = data.total
        this.loadingVisible[2] = false
      }).catch(() => {
        this.loadingVisible[2] = false
      })
    },
    paramEdit: function(data) {
      if (data) {
        this.paramForm = {
          id: data.row.id,
          ruleName: data.row.ruleName,
          cType: data.row.cType,
          cNum: data.row.cNum,
          cOption: data.row.cOption,
          rulesSettingIds: data.row.rulesSettingIds,
          valueType: data.row.valueType,
          paramValue: data.row.paramValue,
          columnName: data.row.columnName,
          remark: data.row.remark
        }
      } else {
        this.paramForm = this.getDefaultParamForm()
      }
      this.checkRuleIdList = this.getRuleIds(this.paramForm.rulesSettingIds)
      this.editComponentVisible[0] = true
    },
    paramDelete: function(data) {
      this.$confirm('确定删除？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.loadingVisible[0] = true
        const param = { id: data.row.id }
        deleteParamRules(param).then(() => {
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
          this.loadingVisible[0] = false
          this.handleCurrentChange(0, 0)
        }).catch(error => {
          console.log(error)
          this.loadingVisible[0] = false
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    },
    editEnsureOrCancel: function(index, isCancel) {
      if (isCancel) {
        this.editComponentVisible[index] = false
        return
      }
      switch (index) {
        case 0: {
          const param = this.paramForm
          if (param.cType && param.cOption && param.valueType) {
            editParamRules(param).then((res) => {
              this.$message({
                type: 'success',
                message: '编辑成功!'
              })
              this.editComponentVisible[index] = false
              this.$store.dispatch('sysParam/queryCNumIndex')
              this.handleCurrentChange(index, 0)
            }).catch(err => {
              console.log(err)
              this.loadingVisible[index] = false
            })
          } else {
            this.$message({
              type: 'warn',
              message: '数据不完整!'
            })
          }
          break
        }
        case 1: {
          const param = this.ruleForm
          if (param.cType && param.valueName && param.ruleType) {
            editRuleSetting(param).then((res) => {
              this.$message({
                type: 'success',
                message: '编辑成功!'
              })
              this.loadingVisible[index] = false
              this.editComponentVisible[index] = false
              this.setRuleBaseInfo()
              this.handleCurrentChange(index, 0)
            }).catch(err => {
              console.log(err)
              this.loadingVisible[index] = false
            })
          } else {
            this.$message({
              type: 'warn',
              message: '数据不完整!'
            })
          }
          break
        }
        case 2: {
          const param = this.assessForm
          if (param.cType && param.assessDetail) {
            editAssessRule(param).then((res) => {
              this.$message({
                type: 'success',
                message: '编辑成功!'
              })
              this.loadingVisible[index] = false
              this.editComponentVisible[index] = false
              this.handleCurrentChange(index, 0)
            }).catch(err => {
              console.log(err)
              this.loadingVisible[index] = false
            })
          } else {
            this.$message({
              type: 'warn',
              message: '数据不完整!'
            })
          }
          break
        }
        default: {
          this.editComponentVisible[index] = false
        }
      }
    },
    ruleEdit: function(data) {
      if (data) {
        this.ruleForm = {
          id: data.row.id,
          ruleName: data.row.ruleName,
          cType: data.row.cType,
          valueName: data.row.valueName,
          ruleType: data.row.ruleType,
          leftValue: data.row.leftValue,
          rightValue: data.row.rightValue,
          remark: data.row.remark
        }
      } else {
        this.ruleForm = this.getDefaultRuleForm()
      }
      this.editComponentVisible[1] = true
    },
    ruleDelete: function(data) {
      this.$confirm('确定删除？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.loadingVisible[1] = true
        const param = { id: data.row.id }
        deleteRuleSetting(param).then(() => {
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
          this.loadingVisible[1] = false
          this.setRuleBaseInfo()
          this.handleCurrentChange(1, 0)
        }).catch(error => {
          console.log(error)
          this.loadingVisible[1] = false
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    },
    assessEdit: function(data) {
      if (data) {
        this.assessForm = {
          id: data.row.id,
          cType: data.row.cType,
          assessDetail: data.row.assessDetail,
          remark: data.row.remark,
          ruleSettingIds: data.row.ruleSettingIds
        }
      } else {
        this.assessForm = this.getDefaultAssessForm()
      }
      this.checkRuleIdList = this.getRuleIds(this.assessForm.ruleSettingIds)
      this.editComponentVisible[2] = true
    },
    assessDelete: function(data) {
      this.$confirm('确定删除？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.loadingVisible[2] = true
        const param = { id: data.row.id }
        deleteAssessRule(param).then(() => {
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
          this.loadingVisible[2] = false
          this.handleCurrentChange(2, 0)
        }).catch(error => {
          console.log(error)
          this.loadingVisible[2] = false
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    },
    getDefaultParamForm: function() {
      return {
        id: undefined,
        cType: undefined,
        cNum: undefined,
        cOption: '',
        rulesSettingIds: '',
        valueType: undefined,
        paramValue: 0,
        columnName: '',
        remark: ''
      }
    },
    getDefaultRuleForm: function() {
      return {
        id: undefined,
        ruleName: '',
        cType: undefined,
        valueName: undefined,
        ruleType: undefined,
        leftValue: 0,
        rightValue: 0,
        remark: ''
      }
    },
    getDefaultAssessForm: function() {
      return {
        id: undefined,
        cType: undefined,
        assessDetail: '',
        remark: '',
        ruleSettingIds: ''
      }
    },
    setRuleBaseInfo: function() {
      getRuleList().then((res) => {
        const { data } = res
        this.ruleBaseInfo = data
      })
    },
    handleCheckedParamRuleIds(index) {
      switch (index) {
        case 0: {
          const allowSetting = this.ruleBaseInfo.filter(it => it.cType === this.paramForm.cType).map(it => it.id)
          this.paramForm.rulesSettingIds = this.checkRuleIdList.filter(it => allowSetting.includes(it)).join(',')
          break
        }
        case 2: {
          const allowSetting = this.ruleBaseInfo.filter(it => it.cType === this.assessForm.cType).map(it => it.id)
          this.assessForm.ruleSettingIds = this.checkRuleIdList.filter(it => allowSetting.includes(it)).join(',')
          break
        }
      }
    },
    getRuleIds: function(data) {
      return string2List(data).map(item => parseInt(item))
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
