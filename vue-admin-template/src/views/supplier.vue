<template>
  <div class="dashboard-container">
    <el-input placeholder="请输入供货商名" style="width: 200px;margin-right: 10px" v-model="name"/>
    <el-button size="medium" type="success" @click="getList">
      查询
    </el-button>
    <el-button type="primary" @click="dialogFormVisible = true" size="medium">添加供货商</el-button>
    <el-table
      :data="tableData"
      style="width: 100%"
    >
      <el-table-column
        prop="name"
        label="供货商名"
        width="180"
      >
      </el-table-column>
      <el-table-column
        prop="phone"
        label="电话号"
        width="120"
      >
      </el-table-column>
      <el-table-column
        prop="address"
        label="供货商地址"
        width="280"
      >
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
          <el-button size="small" type="primary" @click="getGoodList(scope.row.id)">商品采购</el-button>
          <el-button size="small" type="success" @click="updateUser(scope.row)">修改</el-button>
          <el-button size="small" type="danger" @click="deleteSupplier(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--  弹框：创建  -->
    <el-dialog title="添加供货商" :visible.sync="dialogFormVisible" width="30vw">
      <el-form :model="form">
        <el-form-item label="名称" :label-width="formLabelWidth">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="电话" :label-width="formLabelWidth">
          <el-input v-model="form.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="地址" :label-width="formLabelWidth">
          <el-input v-model="form.address" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="save(form)">确 定</el-button>
      </div>
    </el-dialog>

    <!--  弹框：修改  -->
    <el-dialog title="修改账户" :visible.sync="dialogFormVisible1" width="30vw">
      <el-form :model="form1">
        <el-form-item label="供货商名" :label-width="formLabelWidth">
          <el-input v-model="form1.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="电话" :label-width="formLabelWidth">
          <el-input v-model="form1.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="供货商地址" :label-width="formLabelWidth">
          <el-input v-model="form1.address" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="updateSupplier(form1)">确 定</el-button>
      </div>
    </el-dialog>

    <!--  弹框：采购商品  -->
    <el-dialog title="供应商品列表" :visible.sync="dialogFormVisible2">
      <el-table
        :data="goodData"
        style="width: 100%"
      >
        <el-table-column
          prop="goodName"
          label="商品名称"
          width="180"
        >
        </el-table-column>
        <el-table-column
          prop="type"
          label="商品类型"
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
          prop="money"
          label="商品进货价"
          width="120"
        >
        </el-table-column>
        <el-table-column
          prop="url"
          label="图片"
          width="160"
        >
          <template slot-scope="scope">
            <img :src="scope.row.url" min-width="70" height="70"/>
          </template>
        </el-table-column>
        <el-table-column
          prop="size"
          label="尺码"
          width="120"
        >
        </el-table-column>
        <el-table-column
          prop="material"
          label="材质"
          width="170"
        >
        </el-table-column>
        <el-table-column
          label="操作"
        >
          <template slot-scope="scope">
            <el-button size="small" type="primary" @click="caigou(scope.row)">采购</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="updateSupplier(form1)">确 定</el-button>
      </div>
    </el-dialog>

    <!--  弹框：填写采购数量  -->
    <el-dialog title="采购数量" :visible.sync="dialogFormVisible3" width="30vw">
      <el-form>
        <el-form-item>
          <el-input v-model="caigouObj.count" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="caigouGood()">确 定</el-button>
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
      name: '',
      imageUrl: '',
      tableData: [],
      goodData: [],
      count: '',
      dialogFormVisible: false,
      dialogFormVisible1: false,
      dialogFormVisible2: false,
      dialogFormVisible3: false,
      form: {
        name: '',
        phone: '',
        address: ''
      },
      form1: {
        id: '',
        name: '',
        phone: '',
        address: ''
      },
      formLabelWidth: '100px',
      caigouObj: {
        count: ''
      },
      loginUser: {
        accountId: '',
        role: ''
      },
      options: [
        {
          value: '2',
          label: '销售员'
        }, {
          value: '3',
          label: '库存管理员'
        }, {
          value: '4',
          label: '采购员'
        }]
    }
  },

  created() {
    this.getLoginUser()
  },

  mounted() {
    setInterval(() => this.getList(), 1000)
  },

  methods: {
    getList() {
      urlApi.getSupplierList(this.name).then(res => {
        this.tableData = res.data
      })
    },

    getGoodList(supplierId) {
      this.dialogFormVisible2 = true
      urlApi.getSupplierGoodList(supplierId).then(res => {
        this.goodData = res.data
      })
    },

    updateUser(user) {
      this.form1 = user
      this.dialogFormVisible1 = true
    },

    deleteSupplier(id) {
      this.$confirm('此操作将删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        urlApi.deleteSupplier(id).then(res => {
          if (res.code === 200) {
            this.getList()
            this.$message.success('删除成功')
          }
        })
      })
    },

    caigou(row) {
      this.dialogFormVisible3 = true
      this.caigouObj = row
      this.caigouObj.accountId = this.loginUser.accountId
    },

    caigouGood() {
      urlApi.caigouGood(this.caigouObj).then(res => {
        this.dialogFormVisible3 = false
        this.$message.success('采购单生成成功，可在采购单管理中查看详情')
      })
    },

    getLoginUser() {
      urlApi.getLoginUser().then(res => {
        this.loginUser.accountId = res.data.accountId
        this.loginUser.role = res.data.role
      })
      this.getList()
    },

    save(form) {
      urlApi.saveSupplier(form).then(res => {
        if (res.code == 200) {
          this.dialogFormVisible = false
          this.getList()
          this.$message.success('保存成功')
        }
      })
    },

    updateSupplier(form) {
      urlApi.updateSupplier(form).then(res => {
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
