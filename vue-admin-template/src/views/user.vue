<template>
  <div class="dashboard-container">
    <el-button type="primary" @click="dialogFormVisible = true" size="medium">创建用户</el-button>
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
        prop="email"
        label="邮箱"
      >
      </el-table-column>
      <el-table-column
        prop="money"
        label="余额"
      >
      </el-table-column>
      <el-table-column
        prop="role"
        label="角色"
      >
        <template slot-scope="scope">
          <el-tag type="success" v-if="scope.row.role == 1">用户</el-tag>
          <el-tag type="primary" v-if="scope.row.role == 2">员工</el-tag>
          <el-tag type="success" v-if="scope.row.role == 3">管理员</el-tag>
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
          <el-button size="small" type="success" @click="updateUser(scope.row.accountId)">切换</el-button>
          <el-button size="small" type="danger" @click="deleteUser(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--  弹框  -->
    <el-dialog title="添加账户" :visible.sync="dialogFormVisible">
      <el-form :model="form">
        <el-form-item label="账号" :label-width="formLabelWidth">
          <el-input v-model="form.accountId" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" :label-width="formLabelWidth">
          <el-input v-model="form.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" :label-width="formLabelWidth">
          <el-input v-model="form.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="角色" :label-width="formLabelWidth">
          <el-input v-model="form.role" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveUser(form)">确 定</el-button>
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
      form: {
        accountId: '',
        password: '',
        role: '',
        phone: ''
      },
      formLabelWidth: '120px',
      loginUser: {
        accountId: '',
        role: ''
      }
    }
  },

  created() {
    this.getList()
    this.getLoginUser()
  },

  methods: {
    getList() {
      urlApi.getUserList().then(res => this.tableData = res.data)
    },

    updateUser(accountId) {
      const params = {
        accountId: accountId
      }
      urlApi.updateUserRole(params).then(res => {
        this.getList()
        this.$message.success('修改成功')
      })
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
