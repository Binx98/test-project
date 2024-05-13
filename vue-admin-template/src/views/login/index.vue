<template>
  <div class="login-container">
    <el-form ref="loginForm" :model="loginForm" class="login-form" auto-complete="on" label-position="left">
      <div class="title-container">
        <h5 class="title">慧美服装销售管理系统</h5>
      </div>

      <el-form-item prop="username">
        <span class="svg-container">
          <svg-icon icon-class="user"/>
        </span>
        <el-input
          ref="username"
          v-model="loginForm.accountId"
          placeholder="请输入账号"
          name="username"
          type="text"
          tabindex="1"
          auto-complete="on"
        />
      </el-form-item>

      <el-form-item prop="password">
        <span class="svg-container">
          <svg-icon icon-class="password"/>
        </span>
        <el-input
          :key="passwordType"
          ref="password"
          v-model="loginForm.password"
          :type="passwordType"
          placeholder="请输入密码"
          name="password"
          tabindex="2"
          auto-complete="on"
          @keyup.enter.native="handleLogin"
        />
        <span class="show-pwd" @click="showPwd">
          <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'"/>
        </span>
      </el-form-item>

      <el-form-item>
        <span class="svg-container">
          <svg-icon icon-class="user"/>
        </span>
        <el-select style="margin-right: 10px;width: 80%;opacity: 0.6" v-model="value" placeholder="请选择角色">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>

      <el-button :loading="loading" type="primary" style="width:100%;margin-bottom:30px;" @click="login">登录
      </el-button>
      <!--      <div style="font-size: 14px;margin-left: 35%;">-->
      <!--        <span class="form-font">没有账号？</span>-->
      <!--        <span class="go-login" style="color: dodgerblue;cursor:pointer;" @click="goRegister()">去注册></span>-->
      <!--      </div>-->
      <!--      <div style="font-size: 14px;margin-left: 40%;margin-top: 3%">-->
      <!--        <span class="form-font" style="color: dodgerblue;cursor:pointer;" @click="goFindBack()">找回密码></span>-->
      <!--      </div>-->
    </el-form>
  </div>
</template>

<script>
import urlApi from '@/api/url'

export default {
  name: 'Login',
  data() {
    return {
      loginForm: {
        accountId: '',
        password: ''
      },
      loading: false,
      passwordType: 'password',
      redirect: undefined,

      options: [
        {
          value: '1',
          label: '经理'
        }, {
          value: '2',
          label: '销售员'
        }, {
          value: '3',
          label: '库存管理员'
        }, {
          value: '4',
          label: '采购员'
        }],
      value: ''
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
    login() {
      urlApi.login(this.loginForm, this.value).then(res => {
        if (res.code === 200) {
          this.$router.push('/')
          this.$message.success('登陆成功')
        }
        if (res.code === 500) {
          this.$message.error('登陆失败，账号密码错错误')
        }
      })
    },

    goRegister() {
      this.$router.push('/register')
    },

    goFindBack() {
      this.$router.push('/findback')
    },

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
$bg: #283443;
$light_gray: #fff;
$cursor: #fff;

@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .login-container .el-input input {
    color: $cursor;
  }
}

/* reset element-ui css */
.login-container {
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
      color: $light_gray;
      height: 47px;
      caret-color: $cursor;

      &:-webkit-autofill {
        box-shadow: 0 0 0px 1000px $bg inset !important;
        -webkit-text-fill-color: $cursor !important;
      }
    }
  }

  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    //color: #454545;
  }
}
</style>

<style lang="scss" scoped>
$bg: #2d3a4b;
$dark_gray: #889aa4;
$light_gray: #eee;

.login-container {
  min-height: 100%;
  width: 100%;
  background-color: $bg;
  overflow: hidden;
  background-image: url("../../assets/login.jpg");
  background-repeat: no-repeat;
  background-size: cover; /* 或者可以设置为contain */

  .login-form {
    position: relative;
    width: 520px;
    max-width: 100%;
    padding: 230px 35px 0;
    margin: 0 auto;
    overflow: hidden;
  }

  .tips {
    font-size: 14px;
    color: #fff;
    margin-bottom: 10px;

    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }

  .svg-container {
    padding: 6px 5px 6px 15px;
    color: white;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }

  .title-container {
    position: relative;

    .title {
      font-size: 26px;
      color: $light_gray;
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
    color: white;
    cursor: pointer;
    user-select: none;
  }
}

</style>
