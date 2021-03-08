<template>
  <div class="app-container">

    <el-input
      v-model="inputUserName"
      placeholder="输入用户名"
      clearable
      class="input-username"
    />

    <el-button type="primary" size="small" class="button-find">查找</el-button>

    <el-button type="primary" size="small" class="button-add" @click="addUserDialogVisible = true">新增</el-button>
    <el-button type="primary" size="small" class="button-add">下载导入模板</el-button>
    <el-button type="primary" size="small" class="button-add">导入</el-button>
    <el-button type="primary" size="small" class="button-add">导出</el-button>

    <el-table :data="userInfo" stripe style="width: 100% " :border="true" fit>
      <el-table-column :resizable="false" prop="id" sortable label="序号" width="100px" />
      <el-table-column :resizable="false" prop="userType" sortable label="用户类型" />
      <el-table-column :resizable="false" prop="teacherName" sortable label="用户姓名" />
      <el-table-column :resizable="false" prop="logName" sortable label="登陆名" />
      <el-table-column :resizable="false" prop="contact" sortable label="联系方式" />
      <el-table-column :resizable="false" prop="portrait" sortable label="头像" />
      <el-table-column :resizable="false" prop="adminName" sortable label="创建账号的管理员" />
      <el-table-column :resizable="false" prop="createTime" sortable label="创建日期" />
      <el-table-column :resizable="false" label="操作">
        <template>
          <el-button type="text" size="small" @click="userEditDialogVisible = true">修改</el-button>
          <el-button type="text" size="small" @click="userDeleteDialogVisible">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="用户信息修改" :visible.sync="userEditDialogVisible" top="5vh" :append-to-body="true" :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form :model="userForm">
        <el-form-item label="用户类型" :label-width="formLabelWidth">
          <el-input v-model="userForm.userType" autocomplete="off" :disabled="userEditDisable" />
        </el-form-item>
        <el-form-item label="教师姓名" :label-width="formLabelWidth">
          <el-input v-model="userForm.teacherName" autocomplete="off" :disabled="userEditDisable" />
        </el-form-item>
        <el-form-item label="唯一登录名" :label-width="formLabelWidth">
          <el-input v-model="userForm.logName" autocomplete="off" :disabled="userEditDisable" />
        </el-form-item>
        <el-form-item label="联系方式" :label-width="formLabelWidth">
          <el-input v-model="userForm.contact" autocomplete="off" :disabled="userEditDisable" />
        </el-form-item>
        <el-form-item label="头像" :label-width="formLabelWidth">
          <el-input v-model="userForm.portrait" autocomplete="off" :disabled="userEditDisable" />
        </el-form-item>
        <el-form-item label="创建账号的管理员" :label-width="formLabelWidth">
          <el-input v-model="userForm.adminName" autocomplete="off" :disabled="userEditDisable" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="userEditDisable = false">修 改</el-button>
        <el-button type="primary" @click="userEditEnsureOrCancel">确 定</el-button>
        <el-button @click="userEditEnsureOrCancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog title="新增用户" :visible.sync="addUserDialogVisible" top="5vh" :append-to-body="true" :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form :model="addUserForm">
        <el-form-item label="用户类型" :label-width="formLabelWidth">
          <el-radio v-model="addUserForm.userType" label="0" class="userTypeRadio">教师</el-radio>
          <el-radio v-model="addUserForm.userType" label="1">管理员</el-radio>
        </el-form-item>
        <el-form-item label="教师姓名" :label-width="formLabelWidth">
          <el-input v-model="addUserForm.teacherName" autocomplete="off" />
        </el-form-item>
        <el-form-item label="唯一登录名" :label-width="formLabelWidth">
          <el-input v-model="addUserForm.logName" autocomplete="off" />
        </el-form-item>
        <el-form-item label="登录密码" :label-width="formLabelWidth">
          <el-input v-model="addUserForm.logPassword" autocomplete="off" />
        </el-form-item>
        <el-form-item label="联系方式" :label-width="formLabelWidth">
          <el-input v-model="addUserForm.contact" autocomplete="off" />
        </el-form-item>
        <el-form-item label="头像" :label-width="formLabelWidth">
          <el-input v-model="addUserForm.portrait" autocomplete="off" />
        </el-form-item>
        <el-form-item label="创建账号的管理员" :label-width="formLabelWidth">
          <el-input v-model="addUserForm.adminName" autocomplete="off" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="userAddEnsureOrCancel">确 定</el-button>
        <el-button @click="userAddEnsureOrCancel">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
export default {
  name: 'UserManage',
  data() {
    return {
      inputUserName: '',
      userInfo: [{
        id: '1',
        userType: '教师',
        teacherName: '小明',
        logName: '2020080201',
        contact: '2020080201@m.scnu.edu.cn',
        portrait: '',
        adminName: 'admin'
      }, {
        id: '2',
        userType: '教师,管理员',
        teacherName: 'admin',
        logName: 'admin',
        contact: 'admin_tpas@m.scnu.edu.cn',
        portrait: '',
        adminName: '教务员'
      }],
      userForm: {
        userType: '',
        teacherName: '',
        logName: '',
        logPassword: '',
        contact: '',
        portrait: '',
        adminName: ''
      },
      addUserForm: {
        userType: '',
        teacherName: '',
        logName: '',
        logPassword: '',
        contact: '',
        portrait: '',
        adminName: ''
      },
      formLabelWidth: '130px',
      userEditDisable: true,
      userEditDialogVisible: false,
      addUserDialogVisible: false
    }
  },
  methods: {
    userEditEnsureOrCancel: function() {
      this.userEditDisable = true
      this.userEditDialogVisible = false
    },
    userAddEnsureOrCancel: function() {
      this.addUserDialogVisible = false
    },
    userDeleteDialogVisible: function() {
      this.$confirm('确定删除该用户？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$message({
          type: 'success',
          message: '删除成功!'
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
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
