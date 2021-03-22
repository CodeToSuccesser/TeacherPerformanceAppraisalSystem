<template>
  <div class="app-container">
    <el-input
      v-model="searchData.name"
      placeholder="输入用户名"
      clearable
      class="input-username"
    />

    <el-button type="primary" size="small" class="button-find" :disabled="loadingVisible" @click="skipPage(0)">查找</el-button>

    <el-button type="primary" size="small" class="button-add" :disabled="loadingVisible" @click="editRole(null)">新增</el-button>

    <el-table :data="roleList" stripe style="width: 100% " :border="true" fit>
      <el-table-column :resizable="false" prop="id" sortable label="序号" width="100px">
        <template slot-scope="scope">
          <span>{{ (pageSize - 1) * (curPageNum - 1) + scope.$index + 1 }}</span>
        </template>
      </el-table-column>
      <el-table-column :resizable="false" prop="name" sortable label="角色名称" align="center"/>
      <el-table-column :resizable="false" prop="menusValue" sortable label="菜单权限" align="center"/>
      <el-table-column :resizable="false" prop="remark" sortable label="备注" align="center"/>
      <el-table-column :resizable="false" prop="createTime" sortable label="创建日期" align="center"/>
      <el-table-column :resizable="false" label="操作" align="center">
        <template slot-scope="scope">
          <el-button type="text" size="small" :disabled="loadingVisible" @click="editRole(scope)">修改</el-button>
          <el-button type="text" size="small" :disabled="loadingVisible" @click="deleteRole(scope)">删除</el-button>
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
      @prev-click="skipPage(-1)"
      @next-click="skipPage(1)"
      @current-change="handleCurrentChange"
    />

    <el-dialog title="角色信息编辑" :visible.sync="editDialogVisible" top="5vh" :append-to-body="true" :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form :model="roleForm">
        <el-form-item v-if="roleForm.id" label="编码" :label-width="formLabelWidth">
          <el-input v-model="roleForm.id" autocomplete="off" :disabled="true" />
        </el-form-item>
        <el-form-item label="角色名" :label-width="formLabelWidth">
          <el-input v-model="roleForm.name" autocomplete="off" />
        </el-form-item>
        <el-form-item label="菜单权限" :label-width="formLabelWidth">
          <el-tree
            ref="tree"
            checkable
            :data="menuTree"
            default-expand-all
            show-checkbox
            :check-strictly="true"
            node-key="value"
            :props="defaultProps"
            @check-change="handleCheckChange"/>
        </el-form-item>
        <el-form-item label="备注" :label-width="formLabelWidth">
          <el-input v-model="roleForm.remark" autocomplete="off" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="editEnsureOrCancel(false)">确 定</el-button>
        <el-button @click="editEnsureOrCancel(true)">取 消</el-button>
      </div>
    </el-dialog>

  </div>

</template>

<script>
import { querySystemRoles } from '@/api/systemRole'
import { mapGetters } from 'vuex'
import { groupToMenuTree } from '@/utils'

export default {
  computed: {
    ...mapGetters([
      'systemMenus'
    ])
  },
  data() {
    return {
      pageSize: 25,
      curPageNum: 1,
      total: 0,
      editDialogVisible: false,
      loadingVisible: false,
      formLabelWidth: '140px',
      searchData: {
        name: ''
      },
      roleList: [],
      roleForm: this.getDefaultForm(),
      menuTree: [],
      defaultProps: {
        children: 'children',
        label: 'label'
      }
    }
  },
  created() {
    if (this.$store.getters.systemMenus === undefined || this.$store.getters.systemMenus.length === 0) {
      this.$store.dispatch('systemRole/querySystemMenus')
    }
    this.menuTree = this.JsonToTree(this.$store.getters.systemMenus)
    this.skipPage(0)
  },
  methods: {
    skipPage: function(addPage) {
      const param = {
        pageNum: this.curPageNum + addPage,
        pageSize: this.pageSize
      }
      this.searchList(param)
      this.curPageNum = this.curPageNum + addPage
    },
    handleCurrentChange: function(val) {
      const param = {
        pageSize: this.pageSize,
        pageNum: val
      }
      this.searchList(param)
      this.curPageNum = val
    },
    searchList: function(param) {
      this.loadingVisible = true
      const searchModel = param
      if (this.searchData.name) {
        searchModel.name = this.searchData.name
      }
      querySystemRoles(searchModel).then(response => {
        const { data } = response
        this.roleList = data.list
        this.total = data.total
        this.loadingVisible = false
      }).catch(error => {
        console.log(error)
        this.loadingVisible = false
      })
    },
    deleteRole: function(data) {

    },
    editRole: function(data) {
      if (data) {
        this.roleForm = data
      } else {
        this.roleForm = this.getDefaultForm()
      }
      this.editDialogVisible = true
    },
    editEnsureOrCancel: function(isCancel) {
      if (isCancel) {
        this.editDialogVisible = false
        return
      }
      console.log('edit')
    },
    JsonToTree: function(list) {
      if (list) {
        const sortList = list.sort((a, b) => { return a.id - b.id })
        return groupToMenuTree(sortList, '')
      } else {
        return {}
      }
    },
    getDefaultForm: function() {
      return {
        id: undefined,
        name: '',
        menusValue: '',
        permission: undefined,
        remark: ''
      }
    },
    handleCheckChange: function(data, checked, indeterminate) {
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

  .input-username {
    width: 200px;
    margin-bottom: 20px;
  }

  .button-find {
    margin-left: 30px;
  }

  .button-add {
    float: right;
  }

</style>
