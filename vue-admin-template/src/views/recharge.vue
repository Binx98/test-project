<template>
  <div class="dashboard-container">
    当前账户余额：
    <el-tag style="padding-right: 10px">
      {{ loginUser.money }}￥
    </el-tag>
    <div style="margin-top: 16px">
      <el-image :src="wechatImg"
                style="width: 12vw; height: 12vh;border-radius:16px;cursor:pointer;border: 1px solid darkgrey;margin-right: 2%;"
                @click="clickWechat"
      ></el-image>
      <el-image :src="aliImg"
                style="width: 12vw; height: 12vh;border-radius:16px;cursor:pointer;border: 1px solid darkgrey"
                @click="clickAli"
      ></el-image>
    </div>

    <el-dialog
      title="账户充值"
      :visible.sync="centerDialogVisible"
      width="30%"
      center
    >
      输入金额：
      <el-input v-model="charge.money" style="width: 70%"/>
      <span slot="footer" class="dialog-footer">
    <el-button type="primary" @click="chargeMoney">充 值</el-button>
  </span>
    </el-dialog>

    <el-table
      :data="tableData"
      style="width: 100%"
      :row-class-name="tableRowClassName"
    >
      <el-table-column
        prop="accountId"
        label="账号"
        width="220"
      >
      </el-table-column>
      <el-table-column
        prop="money"
        label="充值金额"
        width="220"
      >
      </el-table-column>
      <el-table-column
        prop="type"
        label="充值方式"
        width="220"
      >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.type === 2">支付宝</el-tag>
          <el-tag v-if="scope.row.type === 1" type="success">微信</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="createTime"
        label="时间"
        width="220"
      >
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import urlApi from '@/api/url'
import { mapGetters } from 'vuex'

export default {
  name: 'Dashboard',

  data() {
    return {
      imageUrl: '',
      tableData: [],
      centerDialogVisible: false,
      wechatImg: require('@/assets/wechatpay.png'),
      aliImg: require('@/assets/alipay.png'),
      charge: {
        accountId: '',
        type: '',
        money: ''
      },
      loginUser: {
        accountId: '',
        role: '',
        money: ''
      }
    }
  },

  created() {
    this.getLoginUser()
  },

  methods: {
    getList() {
      urlApi.getRechargeList(this.loginUser.accountId).then(res => {
        this.tableData = res.data
      })
    },

    clickWechat() {
      this.centerDialogVisible = true
      this.charge.accountId = this.loginUser.accountId
      this.charge.type = 1
    },

    clickAli() {
      this.centerDialogVisible = true
      this.charge.accountId = this.loginUser.accountId
      this.charge.type = 2
    },

    chargeMoney() {
      urlApi.chargeMoney(this.charge).then(res => {
        if (res.code === 200) {
          this.getList()
          this.getLoginUser()
          this.$message.success('账户充值成功')
          this.centerDialogVisible = false
        }
      })
    },

    tableRowClassName({ row, rowIndex }) {
      if (rowIndex === 1) {
        return 'warning-row'
      } else if (rowIndex === 3) {
        return 'success-row'
      }
      return ''
    },

    getLoginUser() {
      urlApi.getLoginUser().then(res => {
        if (res.code == 200) {
          this.loginUser.accountId = res.data.accountId
          this.loginUser.role = res.data.role
          this.loginUser.money = res.data.money
        }
        this.getList()
      })
    }
  },

  computed: {
    ...mapGetters([
      'name'
    ])
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

.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}

.avatar {
  width: 178px;
  height: 178px;
  display: block;
}

.el-table .warning-row {
  background: oldlace;
}

.el-table .success-row {
  background: #f0f9eb;
}
</style>
