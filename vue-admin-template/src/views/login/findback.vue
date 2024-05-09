<!--<template>-->
<!--  <div class="login-container">-->
<!--    <el-form ref="loginForm" :model="loginForm" class="login-form" auto-complete="on" label-position="left">-->
<!--      <div class="title-container">-->
<!--        <h4 class="title">宠物烘焙零食销售平台</h4>-->
<!--      </div>-->

<!--      <el-form-item prop="username">-->
<!--        <span class="svg-container">-->
<!--          <svg-icon icon-class="user"/>-->
<!--        </span>-->
<!--        <el-input-->
<!--          ref="username"-->
<!--          v-model="loginForm.accountId"-->
<!--          placeholder="请输入账号"-->
<!--          name="username"-->
<!--          type="text"-->
<!--          tabindex="1"-->
<!--          auto-complete="on"-->
<!--        />-->
<!--      </el-form-item>-->

<!--      <el-form-item prop="password">-->
<!--        <span class="svg-container">-->
<!--          <svg-icon icon-class="password"/>-->
<!--        </span>-->
<!--        <el-input-->
<!--          :key="passwordType"-->
<!--          ref="password"-->
<!--          v-model="loginForm.password"-->
<!--          :type="passwordType"-->
<!--          placeholder="请输入密码"-->
<!--          name="password"-->
<!--          tabindex="2"-->
<!--          auto-complete="on"-->
<!--          @keyup.enter.native="handleLogin"-->
<!--        />-->
<!--        <span class="show-pwd" @click="showPwd">-->
<!--          <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'"/>-->
<!--        </span>-->
<!--      </el-form-item>-->

<!--      <el-form-item prop="password">-->
<!--        <span class="svg-container">-->
<!--          <i class="el-icon-message"></i>-->
<!--        </span>-->
<!--        <el-input-->
<!--          :key="passwordType"-->
<!--          ref="password"-->
<!--          v-model="loginForm.code"-->
<!--          placeholder="请输入邮箱验证码"-->
<!--          name="password"-->
<!--          tabindex="2"-->
<!--          auto-complete="on"-->
<!--          @keyup.enter.native="handleLogin"-->
<!--        />-->
<!--      </el-form-item>-->

<!--      <el-button :loading="loading" type="success" style="width:40%;" @click="sendCode(loginForm.accountId)">-->
<!--        发送验证码-->
<!--      </el-button>-->
<!--      <el-button :loading="loading" type="primary" style="width:40%;"-->
<!--                 @click="findBack(loginForm.accountId, loginForm.password, loginForm.code)"-->
<!--      >找回密码-->
<!--      </el-button>-->

<!--      <div style="font-size: 14px;margin-left: 35%;padding-top: 3%">-->
<!--        <span class="form-font">已有账号？</span>-->
<!--        <span class="go-login" style="color: dodgerblue;cursor:pointer;" @click="goLogin()">去登录></span>-->
<!--      </div>-->
<!--    </el-form>-->
<!--  </div>-->
<!--</template>-->

<!--<script>-->
<!--import urlApi from '@/api/url'-->

<!--export default {-->
<!--  name: 'Reigster',-->
<!--  data() {-->
<!--    return {-->
<!--      loginForm: {-->
<!--        accountId: '',-->
<!--        password: '',-->
<!--        code: ''-->
<!--      },-->
<!--      loading: false,-->
<!--      passwordType: 'password',-->
<!--      redirect: undefined-->
<!--    }-->
<!--  },-->
<!--  watch: {-->
<!--    $route: {-->
<!--      handler: function(route) {-->
<!--        this.redirect = route.query && route.query.redirect-->
<!--      },-->
<!--      immediate: true-->
<!--    }-->
<!--  },-->
<!--  methods: {-->
<!--    register() {-->
<!--      urlApi.register(this.loginForm).then(res => {-->
<!--        if (res.code === 200) {-->
<!--          this.$router.push('/login')-->
<!--          this.$message.success('注册成功')-->
<!--        }-->
<!--        if (res.code === 500) {-->
<!--          this.$message.error('注册功能异常')-->
<!--        }-->
<!--      })-->
<!--    },-->

<!--    sendCode(accountId) {-->
<!--      urlApi.sendCode(accountId).then(res => {-->
<!--        this.$message.success('发送验证码到当前账号对应邮箱，请查收，3分钟内有效')-->
<!--      })-->
<!--    },-->

<!--    findBack(accountId, password, code) {-->
<!--      urlApi.findBack(accountId, password, code).then(res => {-->
<!--        this.$router.push('/login')-->
<!--        this.$message.success('密码修改完毕，请重新登录')-->
<!--      })-->
<!--    },-->

<!--    goLogin() {-->
<!--      this.$router.push('/login')-->
<!--    },-->

<!--    showPwd() {-->
<!--      if (this.passwordType === 'password') {-->
<!--        this.passwordType = ''-->
<!--      } else {-->
<!--        this.passwordType = 'password'-->
<!--      }-->
<!--      this.$nextTick(() => {-->
<!--        this.$refs.password.focus()-->
<!--      })-->
<!--    },-->

<!--    handleLogin() {-->
<!--      this.$refs.loginForm.validate(valid => {-->
<!--        if (valid) {-->
<!--          this.loading = true-->
<!--          this.$store.dispatch('user/login', this.loginForm).then(() => {-->
<!--            this.$router.push({ path: this.redirect || '/' })-->
<!--            this.loading = false-->
<!--          }).catch(() => {-->
<!--            this.loading = false-->
<!--          })-->
<!--        } else {-->
<!--          console.log('error submit!!')-->
<!--          return false-->
<!--        }-->
<!--      })-->
<!--    }-->
<!--  }-->
<!--}-->
<!--</script>-->

<!--<style lang="scss">-->
<!--$bg: #283443;-->
<!--$light_gray: #fff;-->
<!--$cursor: #fff;-->

<!--@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {-->
<!--  .login-container .el-input input {-->
<!--    color: $cursor;-->
<!--  }-->
<!--}-->

<!--/* reset element-ui css */-->
<!--.login-container {-->
<!--  .el-input {-->
<!--    display: inline-block;-->
<!--    height: 47px;-->
<!--    width: 85%;-->

<!--    input {-->
<!--      background: transparent;-->
<!--      border: 0px;-->
<!--      -webkit-appearance: none;-->
<!--      border-radius: 0px;-->
<!--      padding: 12px 5px 12px 15px;-->
<!--      color: $light_gray;-->
<!--      height: 47px;-->
<!--      caret-color: $cursor;-->

<!--      &:-webkit-autofill {-->
<!--        box-shadow: 0 0 0px 1000px $bg inset !important;-->
<!--        -webkit-text-fill-color: $cursor !important;-->
<!--      }-->
<!--    }-->
<!--  }-->

<!--  .el-form-item {-->
<!--    border: 1px solid rgba(255, 255, 255, 0.1);-->
<!--    background: rgba(0, 0, 0, 0.1);-->
<!--    border-radius: 5px;-->
<!--    color: #454545;-->
<!--  }-->
<!--}-->
<!--</style>-->

<!--<style lang="scss" scoped>-->
<!--$bg: #2d3a4b;-->
<!--$dark_gray: #889aa4;-->
<!--$light_gray: #eee;-->

<!--.login-container {-->
<!--  min-height: 100%;-->
<!--  width: 100%;-->
<!--  background-color: $bg;-->
<!--  overflow: hidden;-->
<!--  background-image: url("../../assets/login.jpg");-->
<!--  background-repeat: no-repeat;-->
<!--  background-size: cover; /* 或者可以设置为contain */-->

<!--  .login-form {-->
<!--    position: relative;-->
<!--    width: 520px;-->
<!--    max-width: 100%;-->
<!--    padding: 130px 35px 0;-->
<!--    margin: 0 auto;-->
<!--    overflow: hidden;-->
<!--  }-->

<!--  .tips {-->
<!--    font-size: 14px;-->
<!--    color: #fff;-->
<!--    margin-bottom: 10px;-->

<!--    span {-->
<!--      &:first-of-type {-->
<!--        margin-right: 16px;-->
<!--      }-->
<!--    }-->
<!--  }-->

<!--  .svg-container {-->
<!--    padding: 6px 5px 6px 15px;-->
<!--    color: $dark_gray;-->
<!--    vertical-align: middle;-->
<!--    width: 30px;-->
<!--    display: inline-block;-->
<!--  }-->

<!--  .title-container {-->
<!--    position: relative;-->

<!--    .title {-->
<!--      font-size: 26px;-->
<!--      color: $light_gray;-->
<!--      margin: 0px auto 40px auto;-->
<!--      text-align: center;-->
<!--      font-weight: bold;-->
<!--    }-->
<!--  }-->

<!--  .show-pwd {-->
<!--    position: absolute;-->
<!--    right: 10px;-->
<!--    top: 7px;-->
<!--    font-size: 16px;-->
<!--    color: $dark_gray;-->
<!--    cursor: pointer;-->
<!--    user-select: none;-->
<!--  }-->
<!--}-->
<!--</style>-->
