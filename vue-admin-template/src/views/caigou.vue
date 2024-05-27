<template>
  <div class="dashboard-container">
    <el-card style="width: 18%;margin-bottom: 12px" v-if="loginUser.role === 4">
      <img style="height: 200px" :src="image" class="image">
      <div>
        <div class="bottom clearfix">
          <div>采购商品请通过【供货商】页面<br>点击【商品采购】按钮完成</div>
          <el-button type="primary" @click="goSupplier" style="margin-top: 10px">去采购></el-button>
        </div>
      </div>
    </el-card>
    <el-input placeholder="请输入供应商" style="width: 200px;margin-right: 10px" v-model="supplierName"/>
    <el-select style="margin-right: 10px" v-model="value" placeholder="审核状态">
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
    <el-table
      :data="tableData"
      style="width: 100%"
    >
      <el-table-column
        prop="goodId"
        label="商品编号"
        width="120"
      >
      </el-table-column>
      <el-table-column
        prop="supplierName"
        label="供应商"
        width="180"
      >
      </el-table-column>
      <el-table-column
        prop="goodName"
        label="商品名称"
        width="120"
      >
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
        width="120"
      >
        <template slot-scope="scope">
          <el-tag type="primary" v-if="scope.row.type == 1">衣服</el-tag>
          <el-tag type="success" v-if="scope.row.type == 2">裤子</el-tag>
          <el-tag type="warning" v-if="scope.row.type == 3">鞋子</el-tag>
          <el-tag type="info" v-if="scope.row.type == 4">其他</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="count"
        label="总数量"
        width="120"
      >
      </el-table-column>
      <el-table-column
        prop="money"
        label="进货总价"
        width="120"
      >
      </el-table-column>
      <el-table-column
        prop="status"
        label="审核状态"
        width="120"
      >
        <template slot-scope="scope">
          <el-tag type="primary" v-if="scope.row.status == 1">进行中</el-tag>
          <el-tag type="success" v-if="scope.row.status == 2">已通过</el-tag>
          <el-tag type="danger" v-if="scope.row.status == 3">已拒绝</el-tag>
          <el-tag type="info" v-if="scope.row.status == 4">已入库</el-tag>
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
          <el-button size="small" type="primary" @click="ruku(scope.row.id)" v-if="loginUser.role === 3">入库
          </el-button>
          <el-button size="small" type="success" @click="agree(scope.row.id)" v-if="loginUser.role === 1">通过
          </el-button>
          <el-button size="small" type="danger" @click="reject(scope.row.id)" v-if="loginUser.role === 1">拒绝
          </el-button>
          <!--          <el-button size="small" type="danger" @click="deleteUser(scope.row.id)">删除</el-button>-->
        </template>
      </el-table-column>
    </el-table>

    <!--  弹框：修改会员  -->
    <el-dialog title="修改会员" :visible.sync="dialogFormVisible1">
      <el-form :model="form1">
        <el-form-item label="姓名" :label-width="formLabelWidth">
          <el-input v-model="form1.userName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="电话" :label-width="formLabelWidth">
          <el-input v-model="form1.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="地址" :label-width="formLabelWidth">
          <el-input v-model="form1.address" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="消费金额" :label-width="formLabelWidth">
          <el-input v-model="form1.money" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="updateUser2(form1)">确 定</el-button>
      </div>
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
      image: require('@/assets/caigou.jpg'),
      imageUrl: '',
      tableData: [],
      dialogFormVisible: false,
      dialogFormVisible1: false,
      supplierName: '',
      value: '',
      form: {
        id: '',
        accountId: '',
        password: '',
        role: '2',
        phone: '',
        address: ''
      },

      form1: {
        id: '',
        accountId: '',
        password: '',
        role: '2',
        phone: '',
        address: ''
      },
      formLabelWidth: '120px',
      loginUser: {
        accountId: '',
        role: ''
      },
      options: [
        {
          value: '',
          label: '所有状态'
        }, {
          value: '1',
          label: '进行中'
        }, {
          value: '2',
          label: '已通过'
        }, {
          value: '3',
          label: '已拒绝'
        }, {
          value: '4',
          label: '已入库'
        }]
    }
  },

  created() {
    this.getLoginUser()
  },

  methods: {
    getList() {
      urlApi.getCaiGouList(this.supplierName, this.value).then(res => {
        this.tableData = res.data
      })
    },

    goSupplier() {
      this.$router.push('/supplier/supplier')
    },

    ruku(id) {
      urlApi.changeStatus(id, 4).then(res => {
        this.$message.success('入库成功')
        this.getList()
      })
    },

    agree(id) {
      urlApi.changeStatus(id, 2).then(res => {
        this.$message.success('审批完毕')
        this.getList()
      })
    },

    reject(id) {
      urlApi.changeStatus(id, 3).then(res => {
        this.$message.success('审批完毕')
        this.getList()
      })
    },

    updateUser(user) {
      this.form1 = user
      this.dialogFormVisible1 = true
    },

    deleteUser(id) {
      this.$confirm('此操作将删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        urlApi.deleteUser(id).then(res => {
          if (res.code === 200) {
            this.getList()
            this.$message.success('删除账号成功')
          }
        })
      })
    },

    getLoginUser() {
      urlApi.getLoginUser().then(res => {
        this.loginUser.accountId = res.data.accountId
        this.loginUser.role = res.data.role
      })
      this.getList()
    },

    saveUser(form) {
      urlApi.register(form).then(res => {
        if (res.code == 200) {
          this.dialogFormVisible = false
          this.getList()
          this.$message.success('保存成功')
        }
      })
    },

    updateUser2(form) {
      urlApi.updateUser(form).then(res => {
        if (res.code == 200) {
          this.dialogFormVisible1 = false
          this.getList()
          this.$message.success('修改成功')
        }
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
