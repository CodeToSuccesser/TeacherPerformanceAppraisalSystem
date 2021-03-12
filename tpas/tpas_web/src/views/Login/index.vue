<template>
  <div :class="isMobile">
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" auto-complete="on" label-position="left">

      <div class="title-container">
        <h3 class="title">
          {{ "教师绩效考核系统" }}
        </h3>
      </div>

      <el-form-item prop="username" class="input">
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
        <el-input
          ref="username"
          v-model="loginForm.username"
          placeholder="Username"
          name="username"
          type="text"
          tabindex="1"
          auto-complete="on"
        />
      </el-form-item>

      <el-form-item prop="password" class="input">
        <span class="svg-container">
          <svg-icon icon-class="password" />
        </span>
        <el-input
          :key="passwordType"
          ref="password"
          v-model="loginForm.password"
          :type="passwordType"
          placeholder="Password"
          name="password"
          tabindex="2"
          auto-complete="on"
          @keyup.enter.native="handleLogin"
        />
        <span class="show-pwd" @click="showPwd">
          <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
        </span>
      </el-form-item>

      <el-link type="primary" class="tips">Forget Password?</el-link>

      <el-form-item style="background: transparent">
        <el-radio-group v-model="loginForm.type">
          <el-radio label="0">Teacher</el-radio>
          <el-radio label="1">Admin</el-radio>
        </el-radio-group>
      </el-form-item>

      <el-button :loading="loading" type="primary" class="buttom" @click.native.prevent="handleLogin">Login</el-button>

    </el-form>
  </div>
</template>

<script>

export default {
  name: 'Login',
  data() {
    const validateUsername = (rule, value, callback) => {
      // if (!validUsername(value)) {
      //   callback(new Error('Please enter the correct user name'))
      // } else {
      //   callback()
      // }
      callback()
    }
    const validatePassword = (rule, value, callback) => {
      // if (value.length < 6) {
      //   callback(new Error('The password can not be less than 6 digits'))
      // } else {
      //   callback()
      // }
      callback()
    }
    return {
      loginForm: {
        // username: 'admin',
        // password: '111111',
        type: '0'
      },
      loginRules: {
        username: [{ required: true, trigger: 'blur', validator: validateUsername }],
        password: [{ required: true, trigger: 'blur', validator: validatePassword }]
      },
      loading: false,
      passwordType: 'password',
      redirect: undefined,
      radio: '0'
    }
  },
  computed: {
    isMobile() {
      return this.$store.state.app.device === 'mobile' ? 'login-container-mobile' : 'login-container'
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  methods: {
    showPwd() {
      if (this.passwordType === 'password') {
        this.passwordType = ''
      } else {
        this.passwordType = 'password'
      }
      this.$nextTick(() => {
        this.$refs.password.focus()
      })
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loginForm.type = Number(this.loginForm.type)
          this.loading = true
          this.$store.dispatch('user/login', this.loginForm).then(() => {
            this.$router.push({ path: this.redirect || '/' })
            this.loading = false
          }).catch(() => {
            this.loading = false
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    }
  }
}
</script>

<style lang="scss">
/* 修复input 背景不协调 和光标变色 */
/* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

$bg:#283443;
$light_gray:#fff;
$cursor: #fff;
$bg_drak: #3F72AF;
$bg_light: #DBE2EF;
$bg_from: #F9F7F7;

@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .login-container .el-input .login-container-mobile input {
    color: $cursor;
  }
}

/* reset element-ui css */
.login-container ,.login-container-mobile{
  .el-input {
    display: inline-block;
    height: 47px;
    width: 85%;

    input {
      background: transparent;
      border: 0px;
      -webkit-appearance: none;
      border-radius: 0px;
      padding: 12px 5px 12px 15px;
      color: $bg;
      height: 47px;
      caret-color: $bg;

      &:-webkit-autofill {
        box-shadow: 0 0 0px 1000px white inset !important;
        -webkit-text-fill-color: $bg !important;
      }
    }
  }

  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: white;
    border-radius: 5px;
    color: #454545;
  }
}
</style>

<style lang="scss" scoped>
$bg:#2d3a4b;
$dark_gray:#889aa4;
$light_gray:#eee;

$bg_drak: #3F72AF;
$bg_light: #DBE2EF;
$bg_from: #F9F7F7;
$form_shadow: #d7d1db;
$input_shadow: #F9F0FE;

.login-container {
  min-height: 100%;
  width: 100%;
  max-height: 100%;
  background: linear-gradient(to bottom, $bg_drak 50%, $bg_light 50%);
  overflow: hidden;
  justify-content: center;
  display: flex;

  .login-form {
    position: relative;
    width: 520px;
    max-width: 100%;
    margin: auto;
    padding: 65px 50px 0;
    overflow: hidden;
    background-color: $bg_from;
    box-shadow: 0 0 1px 1px $form_shadow;
    border-radius: 20px;

    .input {
    }

    .buttom {
      width:100%;
      margin-bottom:30px;
      background-color: $bg_drak;
      border-radius: 4px;
    }
  }

  .tips {
    font-size: 14px;
    margin-bottom: 10px;

    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }

  .svg-container {
    padding: 6px 5px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }

  .title-container {
    position: relative;

    .title {
      font-size: 26px;
      color: #454545;
      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: bold;
    }
  }

  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }
}

.login-container-mobile {
  min-height: 100%;
  width: 100%;
  max-height: 100%;
  background: linear-gradient(to bottom, $bg_drak 50%, $bg_light 50%);
  overflow: hidden;
  justify-content: center;
  display: flex;

  .login-form {
    position: relative;
    width: 520px;
    max-width: 100%;
    margin: auto;
    padding: 65px 50px 0;
    overflow: hidden;

    .input {
    }

    .buttom {
      width:100%;
      margin-bottom:30px;
      background-color: $bg_drak;
      border-radius: 4px;
    }
  }

  .tips {
    font-size: 14px;
    margin-bottom: 10px;

    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }

  .svg-container {
    padding: 6px 5px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }

  .title-container {
    position: relative;

    .title {
      font-size: 26px;
      color: #454545;
      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: bold;
    }
  }

  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }
}
</style>
