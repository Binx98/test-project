<template>
  <div class="dashboard-container">
    <el-carousel height="250px" style="width: 50%">
      <el-carousel-item v-for="item in bannerList" :key="item">
        <img :src="item" style="height:100%;width:100%;">
      </el-carousel-item>
    </el-carousel>

    <div class="dashboard-text">
      <el-button size="medium" type="primary" @click="dialogFormVisible = true">
        创建公告
      </el-button>
    </div>
    <el-table
      :data="tableData"
      style="width: 100%"
    >
      <el-table-column
        prop="content"
        label="公告内容"
      >
      </el-table-column>
      <el-table-column
        prop="url"
        label="公告图片"
      >
        <template slot-scope="scope">
          <img :src="scope.row.url" min-width="70" height="70"/>
        </template>
      </el-table-column>
      <el-table-column
        prop="createTime"
        label="创建时间"
      >
      </el-table-column>
      <el-table-column
        label="其他操作"
      >
        <template slot-scope="scope">
          <el-button size="small" type="danger" @click="deleteBanner(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--  弹框  -->
    <el-dialog title="添加公告" :visible.sync="dialogFormVisible">
      <el-form :model="form">
        <el-form-item label="公告内容" :label-width="formLabelWidth">
          <el-input v-model="form.content" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="公告图片" :label-width="formLabelWidth">
          <el-upload
            class="avatar-uploader"
            action="http://localhost:8080/file/upload"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
          >
            <img v-if="form.url" :src="form.url" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveBanner(form.content, form.url)">确 定</el-button>
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
      bannerList: [],
      tableData: [],
      dialogFormVisible: false,
      form: {
        content: '',
        url: ''
      },
      loginUser: {
        accountId: '',
        role: ''
      },
      formLabelWidth: '120px'
    }
  },

  created() {
    this.getBannerList1()
  },

  methods: {
    getBannerList() {
      urlApi.getBannerList().then(res => this.tableData = res.data)
    },

    getBannerList1() {
      urlApi.getBannerList().then(res => {
        for (let i = 0; i < res.data.length; i++) {
          this.bannerList[i] = res.data[i].url
        }
        this.getBannerList()
        this.getLoginUser()
      })
    },

    saveBanner(content, url) {
      urlApi.saveBanner(content, url).then(res => {
        if (res.code === 200) {
          this.dialogFormVisible = false
          this.getBannerList()
          this.$message.success('创建公告成功')
        }
      })
    },

    getLoginUser() {
      urlApi.getLoginUser().then(res => {
        this.loginUser.accountId = res.data.accountId
        this.loginUser.role = res.data.role
      })
    },

    deleteBanner(id) {
      this.$confirm('此操作将删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        urlApi.deleteBanner(id).then(res => {
          if (res.code === 200) {
            urlApi.getBannerList().then(res => {
              this.tableData = res.data
              this.$message.success('删除公告成功')
            })
          }
        })
      })
    },

    handleAvatarSuccess(res, file) {
      this.form.url = res.data
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
</style>
