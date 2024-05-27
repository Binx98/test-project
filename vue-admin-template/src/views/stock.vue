<template>
  <div class="dashboard-container">
    <div class="block">
      <div class="dashboard-text">
        <el-input placeholder="请输入商品名称" style="width: 200px;margin-right: 10px" v-model="goodName"/>
        <el-input placeholder="最小仓库库存" style="width: 120px;" v-model="min"/>
        <span style="font-size: 15px"> ~ </span>
        <el-input placeholder="最大仓库库存" style="width: 120px;margin-right: 10px" v-model="max"/>
        <el-select style="margin-right: 10px" v-model="value" placeholder="请选择类型">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
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
        label="商品编号"
        width="100"
      >
      </el-table-column>
      <el-table-column
        prop="name"
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
        prop="size"
        label="尺码"
        width="80"
      >
      </el-table-column>
      <el-table-column
        prop="type"
        label="类型"
        width="80"
      >
        <template slot-scope="scope">
          <el-tag type="primary" v-if="scope.row.type == 1">衣服</el-tag>
          <el-tag type="success" v-if="scope.row.type == 2">裤子</el-tag>
          <el-tag type="warning" v-if="scope.row.type == 3">鞋子</el-tag>
          <el-tag type="info" v-if="scope.row.type == 4">其他</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="stock"
        label="门店库存（个）"
        width="120"
      >
      </el-table-column>
      <el-table-column
        prop="totalStock"
        label="仓库库存（个）"
        width="120"
      >
      </el-table-column>
      <el-table-column
        label="操作"
      >
        <template slot-scope="scope">
          <el-button size="mini" type="primary" @click="clickKuCun(scope.row)" v-if="loginUser.role === 3">库存调整</el-button>
          <el-button size="mini" type="warning" @click="getRuChuList(scope.row.id)">变更日志</el-button>
          <el-button size="mini" type="danger" @click="deleteStock(scope.row.id)" v-if="loginUser.role === 3">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--  商品详情  -->
    <el-dialog title="商品详情" :visible.sync="dialogFormVisible1">
      <el-form :model="detail">
        <el-input v-model="detail.id" autocomplete="off" style="display: none"></el-input>
        <el-form-item label="名称" :label-width="formLabelWidth">
          <el-input v-model="detail.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="价格" :label-width="formLabelWidth">
          <el-input v-model="detail.price" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="描述" :label-width="formLabelWidth">
          <el-input v-model="detail.description" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="配料表" :label-width="formLabelWidth">
          <el-input v-model="detail.material" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="图片" :label-width="formLabelWidth">
          <el-upload
            class="avatar-uploader"
            action="http://localhost:8080/file/upload"
            :show-file-list="false"
            :on-success="handleAvatarSuccess1"
            :before-upload="beforeAvatarUpload"
          >
            <img v-if="detail.url" :src="detail.url" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible1 = false">取 消</el-button>
        <el-button type="primary" @click="updateGood()">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog
      title="修改库存"
      :visible.sync="dialogVisible1"
      width="30%"
    >
      <div style="margin-bottom: 20px">门店库存：{{ kucunObj.stock }}</div>
      <div style="margin-bottom: 20px">仓库库存：{{ kucunObj.totalStock }}</div>
      <div style="margin-bottom: 20px">
        <el-radio v-model="type" label="1" border v-if="loginUser.role === 3">仓库——>门店</el-radio>
        <el-radio v-model="type" label="2" border v-if="loginUser.role === 2">门店——>仓库</el-radio>
        <el-radio v-model="type" label="3" border v-if="loginUser.role === 3">仓库——>供货商</el-radio>
      </div>
      <el-input style="width: 50%" v-model="changeCount" placeholder="请输入数量"/>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible1 = false">取 消</el-button>
        <el-button type="primary" @click="changeKuCun(goodId,type,changeCount)">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog
      title="出入库存记录"
      :visible.sync="dialogVisible2"
      width="40%"
    >
      <el-table
        :data="ruChuList"
        style="width: 100%"
      >
        <el-table-column
          prop="note"
          label="变更描述"
        >
        </el-table-column>
        <el-table-column
          prop="count"
          label="数量"
          width="100px"
        >
        </el-table-column>
        <el-table-column
          prop="createTime"
          label="创建时间"
        >
        </el-table-column>
      </el-table>
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
      goodName: '',
      min: '',
      max: '',
      goodId: '',
      tableData: [],
      ruChuList: [],
      changeCount: '',
      type: '',
      kucunObj: '',
      dialogVisible: false,
      dialogVisible1: false,
      dialogVisible2: false,
      dialogFormVisible: false,
      dialogFormVisible1: false,
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
    this.getLoginUser()
    this.getList()
  },

  mounted() {
    setInterval(() => this.getList(), 1000)
  },

  methods: {
    getList() {
      urlApi.getKuCunList(this.goodName, this.value, this.min, this.max).then(res => this.tableData = res.data)
    },

    clickKuCun(row) {
      this.goodId = row.id
      this.dialogVisible1 = true
      this.kucunObj = row
    },

    getRuChuList(goodId) {
      this.dialogVisible2 = true
      urlApi.getKuCunById(goodId).then(res => {
        this.ruChuList = res.data
      })
    },

    goKuCun1() {
      this.$confirm('【添加商品】只能基于库存中已有商品进行添加, 是否跳转?', '添加商品', {
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

    getDetail(id) {
      this.dialogFormVisible1 = true
      urlApi.getDetail(id).then(res => {
        if (res.code === 200) {
          this.detail = res.data
        }
      })
    },

    changeKuCun(goodId, type, changeCount) {
      urlApi.changeKuCun(goodId, type, changeCount).then(res => {
        this.getList()
        this.dialogVisible1 = false
        this.$message.success('库存修改成功')
        this.changeCount = null
        this.type = null
      })
    },

    buyGood(id) {
      this.$confirm('此操作将生成订单并扣费, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(() => {
        urlApi.buyGood(id, this.loginUser.accountId).then(res => {
          if (res.code === 200) {
            this.$message.success('下单成功！')
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

    deleteStock(id) {
      this.$confirm('此操作将删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        urlApi.deleteStock(id).then(res => {
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
