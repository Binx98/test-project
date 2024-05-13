<template>
  <div class="dashboard-container">
    <el-input placeholder="请输入账号" style="width: 200px;margin-right: 10px" v-model="accountId"/>
    <el-select style="margin-right: 10px" v-model="value" placeholder="请选择角色">
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
    <el-button type="primary" @click="dialogFormVisible = true" size="medium">创建账号</el-button>
    <el-table
      :data="tableData"
      style="width: 100%"
    >
      <el-table-column
        prop="accountId"
        label="账号"
      >
      </el-table-column>
      <el-table-column
        prop="phone"
        label="电话"
      >
      </el-table-column>
      <el-table-column
        prop="address"
        label="地址"
      >
      </el-table-column>
      <el-table-column
        prop="role"
        label="角色"
      >
        <template slot-scope="scope">
          <el-tag type="primary" v-if="scope.row.role == 2">销售员</el-tag>
          <el-tag type="success" v-if="scope.row.role == 3">库存管理员</el-tag>
          <el-tag type="warning" v-if="scope.row.role == 4">采购员</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="createTime"
        label="创建时间"
      >
      </el-table-column>
      <el-table-column
        label="操作"
      >
        <template slot-scope="scope">
          <el-button size="small" @click="updateUser(scope.row)">修改</el-button>
          <el-button size="small" type="danger" @click="deleteUser(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--  弹框：创建  -->
    <el-dialog title="添加账户" :visible.sync="dialogFormVisible">
      <el-form :model="form">
        <el-form-item label="账号" :label-width="formLabelWidth">
          <el-input v-model="form.accountId" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" :label-width="formLabelWidth">
          <el-input v-model="form.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="电话" :label-width="formLabelWidth">
          <el-input v-model="form.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="地址" :label-width="formLabelWidth">
          <el-input v-model="form.address" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="角色" :label-width="formLabelWidth">
          <el-select style="margin-right: 10px;width: 80%;opacity: 0.6" v-model="form.role" placeholder="请选择角色">
            <el-option
              v-for="item in options1"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="saveUser(form)">确 定</el-button>
      </div>
    </el-dialog>

    <!--  弹框：修改  -->
    <el-dialog title="修改账户" :visible.sync="dialogFormVisible1">
      <el-form :model="form1">
        <el-form-item label="账号" :label-width="formLabelWidth">
          <el-input v-model="form1.accountId" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" :label-width="formLabelWidth">
          <el-input v-model="form1.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="电话" :label-width="formLabelWidth">
          <el-input v-model="form1.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="地址" :label-width="formLabelWidth">
          <el-input v-model="form1.address" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="角色" :label-width="formLabelWidth">
          <el-select style="margin-right: 10px;width: 80%;opacity: 0.6" v-model="form1.role" placeholder="请选择角色">
            <el-option
              v-for="item in options1"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
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
      imageUrl: '',
      tableData: [],
      dialogFormVisible: false,
      dialogFormVisible1: false,
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
      accountId: '',
      value: '',
      options: [
        {
          value: '',
          label: '所有角色'
        }, {
          value: '2',
          label: '销售员'
        }, {
          value: '3',
          label: '库存管理员'
        }, {
          value: '4',
          label: '采购员'
        }],
      options1: [{
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
    this.getList()
    this.getLoginUser()
  },

  mounted() {
    setInterval(() => this.getList(), 1000)
  },

  methods: {
    getList() {
      urlApi.getUserList(this.accountId, this.value).then(res => {
        this.tableData = res.data
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
      console.log(this.loginUser)
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
