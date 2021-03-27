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
      <el-table-column :resizable="false" prop="id" sortable label="序号" align="center">
        <template slot-scope="scope">
          <span>{{ (pageSize - 1) * (curPageNum - 1) + scope.$index + 1 }}</span>
        </template>
      </el-table-column>
      <el-table-column :resizable="false" prop="name" label="角色名称" align="center"/>
      <el-table-column :resizable="false" label="菜单权限" align="center">
        <template slot-scope="props">
          <el-tree
            ref="tree"
            default-expand-all
            :data="getRowMenuTree(props.row.menusValue, props.row.permissionKeys)"
            :check-strictly="true"
            node-key="value"
            :props="defaultProps"/>
        </template>
      </el-table-column>
      <el-table-column :resizable="false" prop="remark" label="备注" align="center"/>
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
            :data="menuTree"
            default-expand-all
            show-checkbox
            :check-strictly="true"
            node-key="value"
            :default-checked-keys="getCheckMenusIds(roleForm.menusValue, roleForm.permissionKeys)"
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
import { querySystemRoles, editRole, deleteRole } from '@/api/systemRole'
import { mapGetters } from 'vuex'
import { groupToMenuTree, removeObject, string2List } from '@/utils'

export default {
  computed: {
    ...mapGetters([
      'systemMenus',
      'systemPermission'
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
    this.menuTree = this.JsonToTree(this.$store.getters.systemMenus, this.$store.getters.systemPermission)
    this.skipPage(0)
  },
  methods: {
    /**
     * 分页查询
     **/
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
    /**
     * 查询列表
     * @param param
     */
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
      this.$confirm('确定删除该角色？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.loadingVisible = true
        const param = {
          name: data.row.name
        }
        deleteRole(param).then(() => {
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
          this.loadingVisible = false
          this.skipPage(0)
        }).catch(error => {
          console.log(error)
          this.loadingVisible = false
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    },
    /**
     * 新增编辑角色
     * **/
    editRole: function(data) {
      if (data) {
        this.roleForm = {
          id: data.row.id,
          name: data.row.name,
          menusValue: data.row.menusValue,
          permissionKeys: data.row.permissionKeys,
          remark: data.row.remark
        }
      } else {
        this.roleForm = this.getDefaultForm()
      }
      const that = this
      this.$refs.tree.setCheckedNodes(that.getCheckMenusIds(this.roleForm.menusValue, this.roleForm.permissionKeys))
      this.editDialogVisible = true
    },
    /**
     * 提交编辑或取消
     * **/
    editEnsureOrCancel: function(isCancel) {
      if (isCancel) {
        this.editDialogVisible = false
        return
      }
      if (this.roleForm.name !== '' && this.roleForm.menusValue !== '') {
        this.loadingVisible = true
        editRole(this.roleForm).then(() => {
          this.$message({
            type: 'success',
            message: '编辑成功!'
          })
          this.loadingVisible = false
          this.editDialogVisible = false
          this.skipPage(0)
        }).catch(error => {
          console.log(error)
          this.loadingVisible = false
          this.editDialogVisible = false
        })
      } else {
        this.$message({
          type: 'info',
          message: '请输入角色名称并选择权限!'
        })
      }
    },
    getDefaultForm: function() {
      return {
        id: undefined,
        name: '',
        menusValue: '',
        permissionKeys: '',
        remark: ''
      }
    },
    /**
     * 复选操作监听
     * **/
    handleCheckChange: function(data, checked, indeterminate) {
      if (data.type === 'M') {
        var menuList = string2List(this.roleForm.menusValue)
        if (checked) {
          menuList.push(data.value)
        } else {
          menuList = removeObject(menuList, data.value)
        }
        this.roleForm.menusValue = menuList.join(',')
      } else if (data.type === 'P') {
        var permissionList = string2List(this.roleForm.permissionKeys)
        if (checked) {
          permissionList.push(data.value)
        } else {
          permissionList = removeObject(permissionList, data.value)
        }
        this.roleForm.permissionKeys = permissionList.join(',')
      }
    },
    /**
     * 查询目录id
     * @param menusValue
     * @returns {*[]|*}
     */
    getCheckMenusIds: function(menusValue, permissionKeys) {
      const target = []
      if (menusValue) {
        const menuList = string2List(menusValue)
        menuList.forEach(it => target.push(it))
      }
      if (permissionKeys) {
        const permissionList = string2List(permissionKeys)
        permissionList.forEach(it => target.push(it))
      }
      return target
    },
    /**
     * 由目录获取目录树
     * @param list
     * @returns {{}|[]}
     * @constructor
     */
    JsonToTree: function(menus, permissions) {
      if (menus || permissions) {
        const sortList = menus.sort((a, b) => { return a.id - b.id })
        return groupToMenuTree(sortList, permissions, '')
      } else {
        return {}
      }
    },
    /**
     * 获取单条数据目录树, menusValue: string
     * @param menuValue
     */
    getRowMenuTree: function(menuValueStr, permissionKeysStr) {
      if (menuValueStr || permissionKeysStr) {
        const menuList = string2List(menuValueStr)
        const systemMenus = this.$store.getters.systemMenus
        const treeData = systemMenus.filter(it => menuList.includes(it.value))
        const permissionList = string2List(permissionKeysStr)
        const systemPermission = this.$store.getters.systemPermission
        const treePermission = systemPermission.filter(it => permissionList.includes(it.permissionKey))
        return this.JsonToTree(treeData, treePermission)
      } else {
        return []
      }
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
