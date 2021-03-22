<template>
  <div class="app-container">
    <el-form :model="ruleForm" :rules="rules" label-width="120px" style="width:300px" enctype=“multipart/form-data”>
      <el-form-item label="用户名">
        <el-input v-model="ruleForm.userName" :disabled="true" maxlength="30" />
      </el-form-item>
      <el-form-item label="教师姓名" prop="realName">
        <el-input v-model="ruleForm.realName" :disabled="true" maxlength="20" />
      </el-form-item>
      <el-form-item label="联系方式" prop="contact">
        <el-input v-model="ruleForm.contact" :disabled="!modifyInfo.modifyInfoVisible" />
      </el-form-item>
      <el-form-item v-if="modifyInfo.modifyInfoVisible" label="密码" prop="password">
        <el-input v-model="ruleForm.password" maxlength="30" show-password />
      </el-form-item>
      <el-form-item v-if="modifyInfo.modifyInfoVisible" label="确认密码" prop="checkPassword">
        <el-input v-model="ruleForm.checkPassword" maxlength="30" show-password />
      </el-form-item>
      <el-form-item v-if="modifyInfo.modifyInfoVisible" label="头像">
        <el-upload
          class="avatar-uploader"
          action="#"
          :http-request="httpRequest"
          :limit="1"
          :auto-upload="false"
          :on-change="fileChange"
          :file-list="fileList"
          accept=".jpg,.png,.jpeg">
          <img v-if="ruleForm.imageUrl" :src="ruleForm.imageUrl" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon" />
        </el-upload>
      </el-form-item>
      <el-form-item v-if="modifyInfo.modifyInfoVisible">
        <el-button :disabled="modifyInfo.modifyInfoLoading" type="primary" @click="modifyInfoStatus()">取消</el-button>
        <el-button v-loading="modifyInfo.modifyInfoLoading" type="primary" @click="submitForm()">确定</el-button>
      </el-form-item>
      <el-form-item v-else>
        <el-button type="primary" @click="modifyInfoStatus()">修改</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { passwordReg, passwordTip } from '@/settings'

export default {
  computed: {
    ...mapGetters([
      'portrait',
      'name',
      'account',
      'contact'
    ])
  },
  data() {
    const validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.ruleForm.password) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    const validateRequire = (rule, value, callback) => {
      if (value === '') {
        this.$message({
          message: rule.field + '为必传项',
          type: 'error'
        })
        callback(new Error(rule.field + '为必传项'))
      } else {
        callback()
      }
    }
    const validatePassword = (rule, value, callback) => {
      if (value === undefined || value.length < 6) {
        callback(new Error(passwordTip))
      } else {
        // eslint-disable-next-line no-undef
        if (value.match(new RegExp(passwordReg))) {
          callback()
        } else {
          callback(new Error(passwordTip))
        }
      }
    }

    return {
      fileList: [],
      ruleForm: {
        realName: this.$store.getters.name === '' ? JSON.parse(sessionStorage.getItem('stateStore')).user.name : this.$store.getters.name,
        userName: this.$store.getters.account === '' ? JSON.parse(sessionStorage.getItem('stateStore')).user.name : this.$store.getters.account,
        password: '',
        checkPassword: '',
        contact: this.$store.getters.contact === '' ? JSON.parse(sessionStorage.getItem('stateStore')).user.contact : this.$store.getters.contact,
        imageUrl: this.$store.getters.portrait === '' ? JSON.parse(sessionStorage.getItem('stateStore')).user.portrait : this.$store.getters.portrait
      },
      rules: {
        realName: [
          { required: true, message: '请输入用户名', trigger: 'blur', validator: validateRequire },
          { min: 2, max: 30, message: '长度在 2 到 30 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码：' + passwordTip, trigger: 'blur', validator: validatePassword },
          { min: 6, max: 20, message: passwordTip, trigger: 'blur' }
        ],
        checkPassword: [
          { required: true, validator: validatePass2, trigger: 'blur' }
        ],
        contact: [
          { required: true, message: '请输入邮箱', trigger: 'blur', validator: validateRequire }
        ]
      },
      modifyInfo: {
        modifyInfoVisible: false,
        modifyInfoLoading: false
      }
    }
  },
  methods: {
    fileChange(file, fileList) {
      this.fileList = [...fileList]
      this.ruleForm.imageUrl = URL.createObjectURL(file.raw)
    },
    submitForm() {
      this.modifyInfo.modifyInfoLoading = true
      var requestData = new FormData()
      console.log('submitForm: ', this.ruleForm.imageUrl)
      if (this.fileList && this.fileList.length > 0) {
        requestData.append('file', this.fileList[0].raw)
      }
      requestData.append('contact', this.ruleForm.contact)
      requestData.append('password', this.ruleForm.password)
      this.$store.dispatch('user/modifyUserInfo', requestData).then((newInfo) => {
        this.modifyInfo.modifyInfoLoading = false
        this.modifyInfoStatus()
        this.$message('修改成功！')
      }).catch(() => {
        this.modifyInfo.modifyInfoLoading = false
      })
    },
    // 用户头像上传
    httpRequest(data) {
      const _this = this
      const rd = new FileReader() // 创建文件读取对象
      const file = data.file
      rd.readAsDataURL(file) // 文件读取装换为base64类型
      rd.onloadend = function(e) {
        _this.formData.imageUrl = this.result // this指向当前方法onloadend的作用域
      }
    },
    modifyInfoStatus() {
      this.ruleForm.contact = this.$store.getters.contact === '' ? JSON.parse(sessionStorage.getItem('stateStore')).user.contact : this.$store.getters.contact
      this.ruleForm.password = ''
      this.ruleForm.checkPassword = ''
      this.imageUrl = this.$store.getters.portrait === '' ? JSON.parse(sessionStorage.getItem('stateStore')).user.portrait : this.$store.getters.portrait
      this.modifyInfo.modifyInfoVisible = !this.modifyInfo.modifyInfoVisible
    }
  }
}
</script>

<style scoped>
.line{
  text-align: center;
}
</style>

/*更改el-button样式*/
<style scoped>
.el-button--primary {
  color: #fff;
  background-color: #3F72AF;
  border-color: #3F72AF;
}
</style>
