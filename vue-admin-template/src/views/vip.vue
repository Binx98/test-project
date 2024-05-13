<template>
  <div class="dashboard-container">
    <el-input placeholder="请输入姓名" style="width: 200px;margin-right: 10px" v-model="userName"/>
    <el-select style="margin-right: 10px" v-model="value" placeholder="请选择等级">
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
    <el-button type="primary" @click="dialogFormVisible = true" size="medium">注册会员</el-button>
    <el-table
        :data="tableData"
        style="width: 100%"
    >
      <el-table-column
          prop="userName"
          label="姓名"
          width="120"
      >
      </el-table-column>
      <el-table-column
          prop="phone"
          label="电话"
          width="160"
      >
      </el-table-column>
      <el-table-column
          prop="money"
          label="消费金额"
          width="120"
      >
      </el-table-column>
      <el-table-column
          prop="level"
          label="会员等级"
          width="120"
      >
        <template slot-scope="scope">
          <el-tag type="primary" v-if="scope.row.level == 1">等级1</el-tag>
          <el-tag type="success" v-if="scope.row.level == 2">等级2</el-tag>
          <el-tag type="info" v-if="scope.row.level == 3">等级3</el-tag>
          <el-tag type="danger" v-if="scope.row.level == 4">等级4</el-tag>
          <el-tag type="warning" v-if="scope.row.level == 5">等级5</el-tag>
        </template>
      </el-table-column>
      <el-table-column
          prop="discount"
          label="折扣"
          width="100"
      >
      </el-table-column>
      <el-table-column
          prop="address"
          label="地址"
          width="150"
      >
      </el-table-column>
      <el-table-column
          prop="createTime"
          label="创建时间"
          width="170"
      >
      </el-table-column>
      <el-table-column
          label="操作"
      >
        <template slot-scope="scope">
          <el-button size="small"  @click="updateVip(scope.row)">修改</el-button>
          <el-button size="small" type="danger" @click="deleteUser(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--  弹框：注册会员  -->
    <el-dialog title="注册会员" :visible.sync="dialogFormVisible">
      <el-form :model="form">
        <el-form-item label="姓名" :label-width="formLabelWidth">
          <el-input v-model="form.userName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="电话" :label-width="formLabelWidth">
          <el-input v-model="form.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="地址" :label-width="formLabelWidth">
          <el-input v-model="form.address" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="消费金额" :label-width="formLabelWidth">
          <el-input v-model="form.money" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="registerVip(form)">确 定</el-button>
      </div>
    </el-dialog>

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
import {mapGetters} from 'vuex'

export default {
  name: 'Dashboard',

  data() {
    return {
      imageUrl: '',
      tableData: [],
      dialogFormVisible: false,
      dialogFormVisible1: false,
      form: {
        userName: '',
        phone: '',
        address: '',
        money: '',
      },
      userName: '',

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
          label: '所有等级'
        }, {
          value: '1',
          label: '等级1'
        }, {
          value: '2',
          label: '等级2'
        }, {
          value: '3',
          label: '等级3'
        }, {
          value: '4',
          label: '等级4'
        }],
      value: '',
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
      urlApi.getVipList(this.userName, this.value).then(res => this.tableData = res.data)
    },

    updateVip(data) {
      this.form1 = data
      this.dialogFormVisible1 = true
    },

    registerVip() {
      urlApi.saveOrUpdateVip(this.form).then(res => {
        this.$message.success('创建会员成功')
        this.dialogFormVisible = false
        this.getList()
      })
    },

    deleteUser(id) {
      this.$confirm('此操作将删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        urlApi.deleteVip(id).then(res => {
          if (res.code === 200) {
            this.getList()
            this.$message.success('删除成功')
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
      urlApi.saveOrUpdateVip(form).then(res => {
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
