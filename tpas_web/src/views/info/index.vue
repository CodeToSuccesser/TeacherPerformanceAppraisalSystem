<template>
  <div class="app-container">
    <el-form ref="ruleForm" :model="ruleForm" :rules="rules" label-width="120px" style="width:300px">
      <el-form-item label="用户名">
        <el-input v-model="ruleForm.userName" :disabled="true" maxlength="30"/>
      </el-form-item>
      <el-form-item label="教师姓名" prop="realName">
        <el-input v-model="ruleForm.realName" :disabled="true" maxlength="30"/>
      </el-form-item>
      <el-form-item label="联系方式" prop="contact">
        <el-input v-model="ruleForm.contact" />
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
          action="https://jsonplaceholder.typicode.com/posts/"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload">
          <img v-if="imageUrl" :src="imageUrl" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon" />
        </el-upload>
      </el-form-item>
      <el-form-item v-if="modifyInfo.modifyInfoVisible">
        <el-button type="primary" @click="modifyInfoStatus()">取消</el-button>
        <el-button type="primary" @click="submitForm('ruleForm')">确定</el-button><!--disabled功能未完成-->
      </el-form-item>
      <el-form-item v-else>
        <el-button type="primary" @click="modifyInfoStatus()">修改</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  data() {
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.ruleForm.password) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }

    return {
      ruleForm: {
        realName: this.$store.getters.name,
        userName: this.$store.getters.account,
        password: '',
        checkPassword: '',
        contact: this.$store.getters.contact,
        imageUrl: this.$store.getters.avatar
      },
      rules: {
        realName: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 2, max: 30, message: '长度在 2 到 30 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ],
        checkPassword: [
          { required: true, validator: validatePass2, trigger: 'blur' }
        ],
        contact: [
          { required: true, message: '请输入邮箱', trigger: 'blur' }
        ]
      },
      modifyInfo: {
        modifyInfoVisible: false,
        modifyInfoLoading: false
      }
    }
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$message('修改成功！')
        } else {
          this.$message('修改失败！')
          return false
        }
      })
    },

    // 用户头像上传
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw)
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg'
      const isLt2M = file.size / 1024 / 1024 < 2

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return isJPG && isLt2M
    },
    modifyInfoStatus() {
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
