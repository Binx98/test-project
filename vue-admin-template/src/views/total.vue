<template>
  <div class="dashboard-container">
    <h4>总销售额：{{ totalPrice }}元</h4>

    <div style="width: 100%">
      <h4>热门商品：</h4>
      <div v-for="item in top3">
        <el-card style="width: 30%;float: left;margin-left: 4px">
          <img style="height: 200px" :src=item.url class="image">
          <div style="padding: 14px;">
            <span>{{ item.name }}</span>
            <div class="bottom clearfix">
              <time class="time">{{ item.createTime }}</time>
            </div>
          </div>
        </el-card>
      </div>
      <div></div>
    </div>

    <div style="margin-top: 35%">
      <h4>完成订单：</h4>
      <el-table
        :data="orderList"
        style="width: 100%"
      >
        <el-table-column
          prop="id"
          label="订单号"
          width="200"
        >
        </el-table-column>
        <el-table-column
          prop="accountId"
          label="账户id"
          width="160"
        >
        </el-table-column>
        <el-table-column
          prop="money"
          label="订单价格"
          width="160"
        >
        </el-table-column>
        <el-table-column
          prop="status"
          label="订单状态"
          width="160"
        >
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.status == 3">已完成</el-tag>
            <el-tag type="warning" v-if="scope.row.status == 1">进行中</el-tag>
            <el-tag type="danger" v-if="scope.row.status == 2">已取消</el-tag>
          </template>
        </el-table-column>
        <el-table-column
          prop="createTime"
          label="创建时间"
          width="160"
        >
        </el-table-column>
        <el-table-column
          label="操作"
        >
          <template slot-scope="scope">
            <el-button size="mini" type="primary" @click="getOrderDetail(scope.row.id)"
            >
              详情
            </el-button>
            <el-button size="mini" type="warning" @click="clickComment(scope.row.goodId, scope.row.orderId)"
                       v-if="loginUser.role === 1"
            >
              评价
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-dialog title="订单明细" :visible.sync="dialogTableVisible">
        <el-table :data="gridData">
          <el-table-column property="goodId" label="商品号" width="150"></el-table-column>
          <el-table-column property="goodName" label="商品名" width="150"></el-table-column>
          <el-table-column property="goodUrl" label="商品图片" width="200">
            <template slot-scope="scope">
              <el-image :src="scope.row.goodUrl" style="width: 50px;height: 50px"/>
            </template>
          </el-table-column>
          <el-table-column property="money" label="总金额"></el-table-column>
          <el-table-column property="count" label="数量"></el-table-column>
        </el-table>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import urlApi from '@/api/url'
import { mapGetters } from 'vuex'

export default {
  name: 'Dashboard',

  data() {
    return {
      currentDate: new Date(),
      totalPrice: 0,
      orderList: '',
      top3: '',
      centerDialogVisible: false,
      dialogFormVisible: false,
      form: {
        content: '',
        url: '',
        price: ''
      },
      loginUser: {
        accountId: '',
        role: ''
      },
      dialogTableVisible: false,
      gridData: ''
    }
  },

  created() {
    this.getList()
    this.getLoginUser()
  },

  methods: {
    getList() {
      urlApi.total().then(res => {
        this.orderList = res.data.list
        this.totalPrice = res.data.money
        this.top3 = res.data.top3
        console.log(res.data)
      })
    },

    getOrderDetail(orderId) {
      urlApi.getOrderDetail(orderId).then(res => {
        this.dialogTableVisible = true
        this.gridData = res.data
      })
    },

    finish(id) {
      urlApi.finish(id).then(res => {
        if (res.code === 200) {
          this.$message.success('操作成功')
          this.getList()
        }
        if (res.code == 500) {
          this.$message.error(res.data)
        }
      })
    },

    cancel(id) {
      urlApi.cancel(id, this.loginUser.accountId).then(res => {
        if (res.code === 200) {
          this.$message.success('取消菜品成功')
          this.getList()
        }
        if (res.code === 500) {
          this.$message.error('菜品已制作完成，不可取消')
        }
      })
    },

    book(id) {
      urlApi.bookMenu(id, this.loginUser.accountId).then(res => {
        if (res.code == 200) {
          this.$message.success('下单成功！')
        }
      })
    },

    getLoginUser() {
      urlApi.getLoginUser().then(res => {
        if (res.code == 200) {
          this.loginUser.accountId = res.data.accountId
          this.loginUser.role = res.data.role
        }
        this.getList()
      })
    },

    handleAvatarSuccess(res, file) {
      this.form.url = res.data
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


.time {
  font-size: 13px;
  color: #999;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
}

.button {
  padding: 0;
  float: right;
}

.image {
  width: 100%;
  display: block;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both
}
</style>
