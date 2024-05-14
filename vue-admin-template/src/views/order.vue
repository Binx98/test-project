<template>
  <div class="dashboard-container">
    <div class="block">
      <div class="dashboard-text">
        <el-input placeholder="请输入订单号" style="width: 200px;margin-right: 10px" v-model="orderId"/>
        <el-input placeholder="请输入客户名称" style="width: 200px;margin-right: 10px" v-model="userName"/>
        <el-button size="medium" type="success" @click="getList">
          查询
        </el-button>
      </div>
    </div>
    <el-table
      :data="tableData"
      style="width: 100%"
    >
      <el-table-column
        prop="id"
        label="订单id"
        width="180"
      >
      </el-table-column>
      <el-table-column
        prop="vipId"
        label="会员编号"
        width="180"
      >
      </el-table-column>
      <el-table-column
        prop="userName"
        label="客户名"
        width="100"
      >
      </el-table-column>
      <el-table-column
        prop="goodName"
        label="商品名称"
        width="120"
      >
      </el-table-column>
      <el-table-column
        prop="url"
        label="商品图片"
        width="150"
      >
        <template slot-scope="scope">
          <img :src="scope.row.url" min-width="70" height="70"/>
        </template>
      </el-table-column>
      <el-table-column
        prop="buyMoney"
        label="售价"
        width="80"
      >
      </el-table-column>
      <el-table-column
        prop="money"
        label="原价"
        width="80"
      >
      </el-table-column>
      <el-table-column
        prop="status"
        label="状态"
        width="80"
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
        width="100"
      >
      </el-table-column>
      <el-table-column
        label="操作"
      >
        <template slot-scope="scope">
          <el-button size="mini" type="primary" @click="finish(scope.row.id)">完成</el-button>
          <el-button size="mini" type="success" @click="back(scope.row.id)">退货</el-button>
          <el-button size="mini" type="danger" @click="deleteOrder(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--  购买商品  -->
    <el-dialog title="购买商品" :visible.sync="dialogFormVisible2" width="500px">
      <!--   会员   -->
      <el-form :model="detail1" v-if="radio === '1'">
        <el-input v-model="detail1.id" autocomplete="off" style="display: none"></el-input>
        <el-form-item label="会员身份" :label-width="formLabelWidth">
          <el-radio v-model="radio" label="1">是</el-radio>
          <el-radio v-model="radio" label="2">否</el-radio>
        </el-form-item>
        <el-form-item label="电话号" :label-width="formLabelWidth">
          <el-input v-model="detail1.phone" autocomplete="off" placeholder="请输入会员手机号"
          ></el-input>
        </el-form-item>
        <el-form-item label="数量" :label-width="formLabelWidth">
          <el-input v-model="detail1.count" autocomplete="off" placeholder="请输入商品数量"></el-input>
        </el-form-item>
      </el-form>
      <!--   非会员   -->
      <el-form :model="detail1" v-if="radio === '2'">
        <el-input v-model="detail1.id" autocomplete="off" style="display: none"></el-input>
        <el-form-item label="会员身份" :label-width="formLabelWidth">
          <el-radio v-model="radio" label="1">是</el-radio>
          <el-radio v-model="radio" label="2">否</el-radio>
        </el-form-item>
        <el-form-item label="姓名" :label-width="formLabelWidth">
          <el-input v-model="detail1.userName" autocomplete="off" placeholder="请填写购买人姓名"
          ></el-input>
        </el-form-item>
        <el-form-item label="数量" :label-width="formLabelWidth">
          <el-input v-model="detail1.count" autocomplete="off" placeholder="请输入商品数量"></el-input>
        </el-form-item>
        <el-form-item label="地址" :label-width="formLabelWidth">
          <el-input v-model="detail1.address" autocomplete="off" placeholder="请输入收货地址"></el-input>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="buyGood()">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog
      title="提示"
      :visible.sync="dialogVisible"
      width="30%"
    >
      <span>请输入加购数量</span>
      <el-input v-model="carDetail.count"/>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addCar(carDetail)">确 定</el-button>
      </span>
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
      radio: '1',
      userName: '',
      orderId: '',
      tableData: [],
      changeCount: {
        goodId: '',
        currentCount: '',
        changeCount: '',
        type: '1'
      },
      dialogVisible: false,
      dialogVisible1: false,
      dialogFormVisible: false,
      dialogFormVisible1: false,
      dialogFormVisible2: false,
      dialogTableVisible: false,
      bannerList: [],
      commentList: [],
      carDetail: {
        accountId: '',
        goodId: '',
        count: ''
      },
      form: {
        url: '',
        price: '',
        type: '1',
        count: '',
        description: '',
        material: ''
      },
      detail: {
        id: '',
        content: '',
        url: '',
        price: '',
        count: '',
        material: ''
      },
      detail1: {
        radio: '',
        id: '',
        userName: '',
        phone: '',
        count: '',
        address: ''
      },
      loginUser: {
        accountId: '',
        role: ''
      },
      formLabelWidth: '120px',
      options: [
        {
          value: '',
          label: '所有'
        }, {
          value: '1',
          label: '衣服'
        }, {
          value: '2',
          label: '裤子'
        }, {
          value: '3',
          label: '鞋子'
        }, {
          value: '4',
          label: '其他'
        }],
      value: ''
    }
  },

  created() {
    this.getList()
  },

  mounted() {
    setInterval(() => this.getList(), 1000)
  },

  methods: {
    getList() {
      urlApi.getOrderList(this.userName, this.orderId).then(res => {
        this.tableData = res.data
      })
    },

    clickBuy(id) {
      this.dialogFormVisible2 = true
      this.detail1.id = id
    },

    goKuCun1() {
      this.$confirm('当【门店库存】数量 > 0 时会显示在商品管理， 是否调整库存?', '添加商品', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$router.push('/stock/stock')
      })
    },

    goKuCun2() {
      this.$confirm('【调整库存】操作需要在库存管理中完成, 是否跳转?', '调整库存', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$router.push('/stock/stock')
      })
    },

    finish(id) {
      urlApi.finish(id).then(res => {
        this.$message.success('销售单已完成')
        this.getList()
      })
    },

    back(id) {
      urlApi.back(id).then(res => {
        this.$message.success('销售单退货成功')
        this.getList()
      })
    },

    deleteOrder(id) {
      this.$confirm('此操作将删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        urlApi.deleteOrder(id).then(res => {
          this.$message.success('删除成功')
          this.getList()
        })
      })
    },

    clickChangeCount(id, count) {
      this.dialogVisible1 = true
      this.changeCount.goodId = id
      this.changeCount.currentCount = count
    },

    updateGood() {
      urlApi.updateGood(this.detail).then(res => {
        if (res.code === 200) {
          this.dialogFormVisible1 = false
          this.getList()
          this.$message.success('修改成功！')
        }
      })
    },

    getDetail(row) {
      this.dialogFormVisible1 = true
      this.detail = row
    },

    changeKuCun(data) {
      urlApi.changeKuCun(data).then(res => {
        this.getList()
        this.dialogVisible1 = false
        this.$message.success('库存修改成功')
        this.changeCount.changeCount = null
      })
    },

    buyGood() {
      this.$confirm('此操作将生成销售单, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(() => {
        urlApi.buyGood(this.detail1, this.radio).then(res => {
          if (res.code === 200) {
            this.$message.success('下单成功！')
            this.dialogFormVisible2 = false
            this.getList()
          }
        })
      })
    },

    openCar(goodId) {
      this.dialogVisible = true
      this.carDetail.accountId = this.loginUser.accountId
      this.carDetail.goodId = goodId
    },

    addCar(data) {
      console.log(data)
      urlApi.addCar(data).then(res => {
        this.getList()
        this.$message.success('加入购物车成功')
        this.dialogVisible = false
      })
    },

    getCommentList(goodId) {
      urlApi.getCommentList(goodId).then(res => {
        this.dialogTableVisible = true
        this.commentList = res.data
      })
    },

    saveGood() {
      urlApi.saveGood(this.form).then(res => {
        if (res.code === 200) {
          this.dialogFormVisible = false
          this.getList()
          this.$message.success('添加商品成功')
        }
      })
    },

    getLoginUser() {
      urlApi.getLoginUser().then(res => {
        if (res.data.length === 0) {
          this.$router.push('/login')
          this.$message.error('您还未登陆账号！')
        }
        this.loginUser.accountId = res.data.accountId
        this.loginUser.role = res.data.role
      })
    },

    deleteGood(id) {
      this.$confirm('此操作将删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        urlApi.deleteGood(id).then(res => {
          if (res.code === 200) {
            urlApi.getGoodList().then(res => this.tableData = res.data)
            this.$message.success('删除商品成功')
          }
        })
      })
    },

    handleAvatarSuccess(res, file) {
      this.form.url = res.data
    },

    handleAvatarSuccess1(res, file) {
      this.detail.url = res.data
    },

    beforeAvatarUpload(file) {
      const isLt2M = file.size / 1024 / 1024 < 2
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return isLt2M
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

.el-dropdown-link {
  cursor: pointer;
  color: #409EFF;
}

.el-icon-arrow-down {
  font-size: 12px;
}

.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 150px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}
</style>
