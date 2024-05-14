<template>
  <div class="dashboard-container">
    <div class="box-pie" style="height: 400px" ref="chart"></div>

    <h3>退货订单：</h3>
    <el-table
      :data="tableData"
      style="width: 82%"
    >
      <el-table-column
        prop="id"
        label="订单id"
      >
      </el-table-column>
      <el-table-column
        prop="userName"
        label="客户名"
      >
      </el-table-column>
      <el-table-column
        prop="goodName"
        label="商品名称"
      >
      </el-table-column>
      <el-table-column
        prop="url"
        label="商品图片"
      >
        <template slot-scope="scope">
          <img :src="scope.row.url" min-width="70" height="70"/>
        </template>
      </el-table-column>
      <el-table-column
        prop="buyMoney"
        label="售价"
      >
      </el-table-column>
      <el-table-column
        prop="money"
        label="原价"
      >
      </el-table-column>
      <el-table-column
        prop="status"
        label="状态"
      >
        <template slot-scope="scope">
          <el-tag type="primary" v-if="scope.row.status == 1">进行中</el-tag>
          <el-tag type="success" v-if="scope.row.status == 2">已完成</el-tag>
          <el-tag type="danger" v-if="scope.row.status == 3">已退货</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="count"
        label="数量"
      >
      </el-table-column>
<!--      <el-table-column-->
<!--        label="操作"-->
<!--      >-->
<!--        <template slot-scope="scope">-->
<!--          <el-button size="mini" type="primary" @click="finish(scope.row.id)">完成</el-button>-->
<!--          <el-button size="mini" type="success" @click="back(scope.row.id)">退货</el-button>-->
<!--          <el-button size="mini" type="danger" @click="deleteOrder(scope.row.id)">删除</el-button>-->
<!--        </template>-->
<!--      </el-table-column>-->
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
      },
      option: {
        title: {
          text: '销售数量统计',
          subtext: '商品维度',
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left'
        },
        series: [
          {
            name: '销售数量',
            type: 'pie',
            radius: '50%',
            data: [
              { value: 1048, name: 'Search Engine' },
              { value: 735, name: 'Direct' },
              { value: 580, name: 'Email' },
              { value: 484, name: 'Union Ads' },
              { value: 300, name: 'Video Ads' }
            ],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      }
    }
  },

  created() {
    this.getLoginUser()
  },

  mounted() {
    this.getBing()
    this.getOrderList()
  },

  methods: {
    getOrderList() {
      urlApi.getBackOrderList().then(res => {
        this.tableData = res.data
      })
    },

    getBing() {
      urlApi.getBing().then(res => {
        this.option.series[0].data = res.data
        this.getPage()
      })
    },

    getPage() {
      this.chart = this.$echarts.init(this.$refs.chart)
      this.chart.setOption(this.option)
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
