<template>
  <div class="dashboard-container">
    <el-input placeholder="请输入账号" style="width: 200px;margin-right: 10px" v-model="name"/>
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
        label="供应商名"
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
      dialogFormVisible: false,
      dialogFormVisible1: false,
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
