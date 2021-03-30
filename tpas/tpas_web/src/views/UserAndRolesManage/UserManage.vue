<template>
  <div class="app-container">
    <el-input v-model="searchData.id" placeholder="用户编码" clearable class="selector-first" style="width: 120px" />

    <el-input v-model="searchData.userName" placeholder="姓名" clearable class="selector" style="width: 120px"/>

    <el-input v-model="searchData.logName" placeholder="工号" clearable class="selector" style="width: 120px" />

    <el-button type="primary" size="small" class="button-find" :disabled="loadingVisible" @click="skipPage(0)">查找</el-button>

    <el-button type="primary" size="small" class="button-add" :disabled="loadingVisible" @click="editUser(null)">新增</el-button>
    <el-button type="primary" size="small" class="button-add" :disabled="loadingVisible" @click="downloadTemplate">下载导入模板</el-button>
    <el-button type="primary" size="small" class="button-add" :disabled="loadingVisible" @click="importUserInfo">导入</el-button>
    <el-button type="primary" size="small" class="button-add" :disabled="loadingVisible" @click="exportUserInfo">导出</el-button>

    <el-table :data="userInfoList" stripe style="width: 100% " :border="true" fit>
      <el-table-column :resizable="false" prop="id" sortable label="编码" align="center" width="60px"/>
      <el-table-column :resizable="false" prop="userName" sortable label="姓名" align="center"/>
      <el-table-column :resizable="false" prop="logName" sortable label="登陆名/工号" align="center"/>
      <el-table-column :resizable="false" prop="contact" sortable label="联系方式" align="center"/>
      <el-table-column :resizable="false" label="角色名称" align="center">
        <template slot-scope="props">
          <div v-for="item in getCheckRoleNames(props.row.rolesName)" :key="item">
            {{ item }}
          </div>
        </template>
      </el-table-column>
      <el-table-column :resizable="false" label="头像" align="center" width="100px">
        <template slot-scope="props">
          <img v-if="props.row.portrait" :src="props.row.portrait" class="user-avatar" />
        </template>
      </el-table-column>
      <el-table-column :resizable="false" prop="createTime" sortable label="创建日期" />
      <el-table-column :resizable="false" label="操作" align="center">
        <template slot-scope="scope">
          <el-button type="text" size="small" :disabled="loadingVisible" @click="resetPassword(scope)">重置密码</el-button>
          <el-button type="text" size="small" :disabled="loadingVisible" @click="editUser(scope)">修改</el-button>
          <el-button type="text" size="small" :disabled="loadingVisible" @click="deleteUser(scope)">删除</el-button>
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

    <el-dialog title="用户信息编辑" :visible.sync="editDialogVisible" top="5vh" :append-to-body="true" :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form :model="userForm">
        <el-form-item v-if="userForm.id" label="编码" :label-width="formLabelWidth">
          <el-input v-model="userForm.id" autocomplete="off" :disabled="true" />
        </el-form-item>
        <el-form-item label="登录名/工号" :label-width="formLabelWidth">
          <el-input v-model="userForm.logName" autocomplete="off" :disabled="userForm.id!==undefined" />
        </el-form-item>
        <el-form-item label="姓名" :label-width="formLabelWidth">
          <el-input v-model="userForm.userName" autocomplete="off" />
        </el-form-item>
        <el-form-item label="联系方式" :label-width="formLabelWidth">
          <el-input v-model="userForm.contact" autocomplete="off" />
        </el-form-item>
        <el-form-item label="角色名称" :label-width="formLabelWidth">
          <el-tree
            ref="tree"
            :data="roleData"
            default-expand-all
            show-checkbox
            :check-strictly="true"
            node-key="label"
            :default-checked-keys="getCheckRoleNames(userForm.rolesName)"
            :props="defaultProps"
            @check-change="handleCheckChange"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="editEnsureOrCancel(false)">确 定</el-button>
        <el-button @click="editEnsureOrCancel(true)">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog
      title="导入文件信息"
      :visible.sync="importDialogVisible"
      width="30%"
    >
      <el-form>
        <el-form-item label="选择上传文件：" style="margin-top: 10px">
          <el-upload
            action="#"
            :on-change="getFile"
            :on-remove="handleRemove"
            :multiple="false"
            :limit="1"
            :on-exceed="handleExceed"
            :file-list="fileList"
            accept=".xlsx,.xls"
            :auto-upload="false"
          >
            <el-button size="small" type="primary">选择文件</el-button>
          </el-upload>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelImportFile">取 消</el-button>
        <el-button type="primary" @click="importFile">确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
import {
  queryUserInfo,
  deleteUser,
  editUserInfo,
  resetPassword,
  importUserInfo,
  downloadUserInfoTemplate,
  exportUserFile
} from '@/api/user'
import { getAllRole } from '@/api/systemRole'
import { removeObject, string2List } from '@/utils'
import { hideFullScreenLoading, showFullScreenLoading } from '@/utils/loading'
import { downloadExcel } from '@/utils/file'

export default {
  name: 'UserManage',
  data() {
    return {
      formLabelWidth: '130px',
      loadingVisible: false,
      editDialogVisible: false,
      importDialogVisible: false,
      pageSize: 25,
      curPageNum: 1,
      total: 0,
      searchData: {
        id: undefined,
        userName: '',
        logName: ''
      },
      roleList: [],
      roleData: [],
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      userInfoList: [],
      userForm: this.getDefaultForm(),
      fileList: []
    }
  },
  created() {
    getAllRole().then(response => {
      const { data } = response
      this.roleList = data
      this.roleData = this.jsonToTree(this.roleList)
    }).catch(error => {
      console.log(error)
    })
    this.skipPage(0)
    const that = this
    this.$nextTick(() => {
      that.$refs.tree.setCheckedNodes(that.getCheckRoleNames(that.userForm.rolesName))
    })
  },
  methods: {
    getDefaultForm: function() {
      return {
        id: undefined,
        userName: '',
        logName: '',
        contact: '',
        portrait: '',
        rolesName: '',
        createTime: ''
      }
    },
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
      if (this.searchData.id) {
        searchModel.id = this.searchData.id
      }
      if (this.searchData.userName) {
        searchModel.userName = this.searchData.userName
      }
      if (this.searchData.logName) {
        searchModel.logName = this.searchData.logName
      }
      queryUserInfo(searchModel).then(response => {
        const { data } = response
        this.userInfoList = data.list
        this.total = data.total
        this.loadingVisible = false
      }).catch(error => {
        console.log(error)
        this.loadingVisible = false
      })
    },
    /**
     * 重置密码
     **/
    resetPassword: function(data) {
      this.$confirm('确定重置密码为【123456】？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.loadingVisible = true
        const param = [data.row.logName]
        resetPassword(param).then(() => {
          this.$message({
            type: 'success',
            message: '重置成功!'
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
     * 删除用户
     **/
    deleteUser: function(data) {
      this.$confirm('确定删除用户？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.loadingVisible = true
        const param = [data.row.logName]
        deleteUser(param).then(() => {
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
    editUser: function(data) {
      if (data) {
        this.userForm = {
          id: data.row.id,
          userName: data.row.userName,
          logName: data.row.logName,
          contact: data.row.contact,
          rolesName: data.row.rolesName
        }
      } else {
        this.userForm = this.getDefaultForm()
      }
      this.editDialogVisible = true
      const that = this
      that.$refs.tree.setCheckedNodes(that.getCheckRoleNames(this.userForm.rolesName))
    },
    downloadTemplate: function() {
      showFullScreenLoading('模板文件下载中')
      downloadUserInfoTemplate().then(response => {
        downloadExcel(response, '用户信息导入模板文件.xls')
        hideFullScreenLoading()
      })
        .catch(error => {
          console.log(error)
          hideFullScreenLoading()
        })
    },
    importUserInfo: function() {
      this.importDialogVisible = true
    },
    exportUserInfo: function() {
      const searchModel = {}
      if (this.searchData.id) {
        searchModel.id = this.searchData.id
      }
      if (this.searchData.userName) {
        searchModel.userName = this.searchData.userName
      }
      if (this.searchData.logName) {
        searchModel.logName = this.searchData.logName
      }
      showFullScreenLoading('导出文件下载中')
      exportUserFile(searchModel).then(response => {
        downloadExcel(response, '用户信息导出文件.xlsx')
        hideFullScreenLoading()
      }).catch(error => {
        console.log(error)
        hideFullScreenLoading()
      })
    },
    /**
     * 提交编辑或取消
     * **/
    editEnsureOrCancel: function(isCancel) {
      if (isCancel) {
        this.editDialogVisible = false
        return
      }
      if (this.userForm.userName !== '' && this.userForm.rolesName !== '') {
        this.loadingVisible = true
        editUserInfo(this.userForm).then(() => {
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
        })
      } else {
        this.$message({
          type: 'info',
          message: '请输入用户姓名并选择角色!'
        })
      }
    },
    /**
     * 文件上传处理
     **/
    handleRemove() {
      this.fileList = []
    },
    handleExceed(files, fileList) {
      this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`)
    },
    getFile(file, fileList) {
      this.fileList = fileList
    },
    cancelImportFile() {
      this.fileList = []
      this.importDialogVisible = false
    },
    importFile() {
      if (!this.fileList || !this.fileList.length) {
        this.$notify.error({
          title: '错误',
          message: '请选择上传的文件',
          duration: 2000
        })
        return
      }
      const body = new FormData()
      body.append('file', this.fileList[0].raw)
      showFullScreenLoading('文件上传中')
      importUserInfo(body)
        .then(response => {
          this.$notify.success({
            title: '导入成功',
            message: '用户信息导入成功' + response.data.successCount + '条，导入失败' + response.data.failCount + '条'
          })
          this.importDialogVisible = false
          hideFullScreenLoading()
          this.skipPage(0)
        })
        .catch(error => {
          console.log(error)
          hideFullScreenLoading()
        })
    },
    /**
     * 角色显示处理函数
     */
    getCheckRoleNames: function(rolesName) {
      return string2List(rolesName)
    },
    jsonToTree: function(roles) {
      const target = []
      if (roles) {
        roles.forEach(item => {
          const data = {
            id: item.id,
            label: item.name,
            children: []
          }
          target.push(data)
        })
      }
      return target
    },
    handleCheckChange: function(data, checked, indeterminate) {
      var roleNameList = string2List(this.userForm.rolesName)
      if (checked) {
        roleNameList.push(data.label)
      } else {
        roleNameList = removeObject(roleNameList, data.label)
      }
      this.userForm.rolesName = roleNameList.join(',')
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

  .user-avatar {
    cursor: pointer;
    width: 40px;
    height: 40px;
    border-radius: 10px;
  }

  .selector-first {
    margin-bottom: 20px;
  }

  .selector {
    margin-left: 10px;
    margin-bottom: 20px;
  }

</style>
