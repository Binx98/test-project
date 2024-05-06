<template>
  <div class="dashboard-container">
    <el-table :data="commentList">
      <el-table-column property="goodId" label="商品id" width="200"></el-table-column>
      <el-table-column property="goodName" label="商品名称" width="200"></el-table-column>
      <el-table-column property="content" label="评价内容" width="200"></el-table-column>
      <el-table-column property="createTime" label="时间" width="200"></el-table-column>
      <el-table-column label="操作" width="200">
        <template slot-scope="scope">
          <el-button type="danger" size="mini" @click="deleteComment(scope.row.id)">删除评论</el-button>
        </template>
      </el-table-column>
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
      commentList: [],
      dialogFormVisible: false,
      form: {
        content: '',
        url: '',
        price: ''
      },
      loginUser: {
        accountId: '',
        role: ''
      },
      activeName: 'first',
      formLabelWidth: '120px'
    }
  },

  created() {
    this.getLoginUser()
  },

  methods: {
    getList() {
      urlApi.getCommentListByAccountId(this.loginUser.accountId).then(res => {
        this.commentList = res.data
      })
    },

    getLoginUser() {
      urlApi.getLoginUser().then(res => {
        if (res.code == 200) {
          this.loginUser.accountId = res.data.accountId
          this.loginUser.role = res.data.role
        }
        this.getList(this.loginUser.accountId)
      })
    },

    deleteComment(id) {
      this.$confirm('此操作将删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        urlApi.deleteComment(id).then(res => {
          this.getList()
          this.$message.success('删除评论成功')
        })
      })
    },

    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg'
      const isLt2M = file.size / 1024 / 1024 < 2

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return isJPG && isLt2M
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
