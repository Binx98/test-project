<template>
  <div class="dashboard-container">
    <el-tabs v-model="activeName" type="card">
      <el-tab-pane label="订单" name="first">
        <el-table
          :data="tableData"
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
              <el-button size="mini" type="success" @click="finish(scope.row.id)">
                完成
              </el-button>
              <el-button size="mini" type="danger" @click="cancel(scope.row.id)">
                取消
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
    </el-tabs>

    <el-dialog
      title="评论框"
      :visible.sync="centerDialogVisible"
      width="30%"
      center
    >
      <el-input v-model="commentContent"></el-input>
      <el-button type="primary" @click="comment()" style="margin-top: 15px;margin-left: 40%">确 定</el-button>
    </el-dialog>

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
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button size="mini" type="warning" @click="clickComment(scope.row.goodId, scope.row.orderId)"
            >
              评价
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
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
      gridData: [],
      tableData: [],
      dialogTableVisible: false,
      dialogFormVisible: false,
      centerDialogVisible: false,
      commentGoodId: '',
      commentOrderId: '',
      form: {
        content: '',
        url: '',
        price: ''
      },
      loginUser: {
        accountId: '',
        role: ''
      },
      activeName: 'first',
      formLabelWidth: '120px',
      commentContent: ''
    }
  },

  created() {
    this.getList()
    this.getLoginUser()
  },

  methods: {
    getList() {
      if (this.loginUser.role === 1) {
        urlApi.getOrderList(this.loginUser.accountId).then(res => {
          this.tableData = res.data
        })
      } else {
        urlApi.getOrderList().then(res => {
          this.tableData = res.data
        })
      }
    },

    getOrderDetail(orderId) {
      urlApi.getOrderDetail(orderId).then(res => {
        this.dialogTableVisible = true
        this.gridData = res.data
      })
    },

    comment() {
      urlApi.saveComment(this.loginUser.accountId, this.commentOrderId, this.commentGoodId, this.commentContent).then(res => {
        this.centerDialogVisible = false
        this.$message.success('评论成功')
      })
    },

    clickComment(goodId, orderId) {
      this.centerDialogVisible = true
      this.commentOrderId = orderId
      this.commentGoodId = goodId
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
          this.$message.success('取消成功')
          this.getList()
        }
      })
    },

    getLoginUser() {
      urlApi.getLoginUser().then(res => {
        if (res.code === 200) {
          this.loginUser.accountId = res.data.accountId
          this.loginUser.role = res.data.role
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
</style>
