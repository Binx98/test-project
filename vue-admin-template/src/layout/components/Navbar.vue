<template>
  <div class="navbar">
    <hamburger :is-active="sidebar.opened" class="hamburger-container" @toggleClick="toggleSideBar"/>

    <breadcrumb class="breadcrumb-container"/>

    <div class="right-menu">
      <!--      <el-badge :value=carTotal class="item" style="margin-right: 40px;margin-top: 12px">-->
      <!--        <i class="el-icon-shopping-cart-2" style="font-size: 30px;cursor:pointer;color: darkgrey;"-->
      <!--           @click="dialogTableVisible = true"-->
      <!--        ></i>-->
      <!--      </el-badge>-->

      <el-dropdown class="avatar-container" trigger="click">
        <div class="avatar-wrapper">
          <el-tag
            type="success"
            effect="plain"
          >
            {{ loginUser.accountId }}
          </el-tag>
          <i class="el-icon-caret-bottom"/>
        </div>
        <el-dropdown-menu slot="dropdown" class="user-dropdown">
          <!--          <el-dropdown-item divided @click.native="getUserInfo(true)">-->
          <!--            <span style="display:block;">个人信息</span>-->
          <!--          </el-dropdown-item>-->
          <!--          <el-dropdown-item divided>-->
          <!--            <span style="display:block;" @click="dialogVisible = true">联系客服</span>-->
          <!--          </el-dropdown-item>-->
          <el-dropdown-item divided @click.native="logout">
            <span style="display:block;">退出登录</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>

    <el-dialog
      :visible.sync="dialogVisible"
      width="30%"
    >
      <!--      <el-image :src=kefuImg></el-image>-->
    </el-dialog>

    <el-dialog
      title="用户信息"
      :visible.sync="centerDialogVisible"
      width="30%"
      center
    >
      <el-input v-model="loginUser.id" :disabled="true" style="display: none"></el-input>
      账号：
      <el-input v-model="loginUser.accountId" :disabled="true"></el-input>
      余额：
      <el-input v-model="loginUser.money" :disabled="true"></el-input>
      邮箱：
      <el-input v-model="loginUser.email"></el-input>
      地址：
      <el-input v-model="loginUser.address"></el-input>
      <el-button type="primary" @click="updateUser" style="margin-top: 2%;margin-left: 38%">修改</el-button>
    </el-dialog>

    <el-dialog title="购物车列表" style="width: 100%" :visible.sync="dialogTableVisible">
      <el-table :data="carList">
        <el-table-column property="goodName" label="商品名" width="100"></el-table-column>
        <el-table-column property="goodUrl" label="图片" width="150">
          <template slot-scope="scope">
            <el-image :src="scope.row.goodUrl" style="width: 30%;height: 30%"></el-image>
          </template>
        </el-table-column>
        <el-table-column property="count" label="数量（个）" width="100"></el-table-column>
        <el-table-column property="money" label="价格（￥/个）" width="100"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button size="small" type="success" @click="changeCount(scope.row.goodId, 1)"
            >➕
            </el-button>
            <el-button size="small" type="warning" @click="changeCount(scope.row.goodId, 2)"
            >➖
            </el-button>
            <el-button size="small" type="danger" @click="deleteCar(scope.row.goodId)"
            >移除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-button style="margin-top: 10px;margin-left: 40%" size="small" type="primary" @click="calc()">
        结算（总价格：{{ carMoneyTotal }}）
      </el-button>
    </el-dialog>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import Hamburger from '@/components/Hamburger'
import url from '@/api/url'

export default {
  components: {
    Breadcrumb,
    Hamburger
  },
  computed: {
    ...mapGetters([
      'sidebar',
      'avatar'
    ])
  },

  created() {
    this.getUserInfo(false)
  },

  mounted() {
  },

  data() {
    return {
      centerDialogVisible: false,
      loginUser: {
        accountId: '',
        email: '',
        money: '',
        address: '',
        role: ''
      },
      carList: [],
      carTotal: null,
      carMoneyTotal: 0,
      // kefuImg: require('@/assets/kefu.jpg'),
      dialogVisible: false,
      dialogTableVisible: false,
      dialogFormVisible: false,
      form: {
        name: '',
        region: '',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: ''
      },
      formLabelWidth: '120px'
    }
  },

  methods: {
    toggleSideBar() {
      this.$store.dispatch('app/toggleSideBar')
    },

    async logout() {
      url.logout()
      this.$router.push(`/login`)
    },

    getUserInfo(val) {
      // 打开弹框
      this.centerDialogVisible = val

      // 赋值数据
      url.getLoginUser().then(res => {
        this.loginUser = res.data

        // 设置localStorage
        console.log(this.loginUser)
        if (this.loginUser.role === 1) {
          localStorage.setItem('role', '1')
        } else if (this.loginUser.role === 2) {
          localStorage.setItem('role', '2')
        } else if (this.loginUser.role === 3) {
          localStorage.setItem('role', '3')
        } else if (this.loginUser.role === 4) {
          localStorage.setItem('role', '4')
        }

        this.getCarList()
      })
    },

    changeCount(goodId, type) {
      url.changeCount(goodId, this.loginUser.accountId, type).then(res => {
      })
    },

    calc() {
      url.calc(this.loginUser.accountId).then(res => {
        this.dialogTableVisible = false
        this.$message.success('购物车商品结算下单完毕！')
        this.getCarList()
      })
    },

    deleteCar(goodId) {
      url.deleteCar(this.loginUser.accountId, goodId).then(res => {
        this.getCarList()
        this.$message.success('移除成功...')
      })
    },

    async updateUser() {
      url.updateUser(this.loginUser).then(res => {
        this.centerDialogVisible = false
        this.$message.success('修改信息成功')
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.navbar {
  height: 50px;
  overflow: hidden;
  position: relative;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0, 21, 41, .08);

  .hamburger-container {
    line-height: 46px;
    height: 100%;
    float: left;
    cursor: pointer;
    transition: background .3s;
    -webkit-tap-highlight-color: transparent;

    &:hover {
      background: rgba(0, 0, 0, .025)
    }
  }

  .breadcrumb-container {
    float: left;
  }

  .right-menu {
    float: right;
    height: 100%;
    line-height: 50px;

    &:focus {
      outline: none;
    }

    .right-menu-item {
      display: inline-block;
      padding: 0 8px;
      height: 100%;
      font-size: 18px;
      color: #5a5e66;
      vertical-align: text-bottom;

      &.hover-effect {
        cursor: pointer;
        transition: background .3s;

        &:hover {
          background: rgba(0, 0, 0, .025)
        }
      }
    }

    .avatar-container {
      margin-right: 30px;

      .avatar-wrapper {
        margin-top: 5px;
        position: relative;

        .user-avatar {
          cursor: pointer;
          width: 40px;
          height: 40px;
          border-radius: 10px;
        }

        .el-icon-caret-bottom {
          cursor: pointer;
          position: absolute;
          right: -20px;
          top: 25px;
          font-size: 12px;
        }
      }
    }
  }
}
</style>
