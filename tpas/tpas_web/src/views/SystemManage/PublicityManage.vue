<template>
  <div class="app-container">

    <el-form ref="form" :model="searchData">
      <el-input v-model="searchData.publisherCode" placeholder="发布账号" clearable class="selector" style="width: 200px" />
      <el-input v-model="searchData.title" placeholder="标题" clearable class="selector" style="width: 200px" />
      <el-date-picker
        v-model="searchData.timeRange"
        format="yyyy-MM-dd"
        value-format="yyyy-MM-dd HH:mm:ss"
        type="daterange"
        range-separator="-"
        start-placeholder="开始日期"
        end-placeholder="结束日期"
        clearable
        class="selector"
      />
      <el-button type="primary" size="small" class="button-find" @click="handleCurrentChange(0)">查找</el-button>
      <el-button type="primary" size="small" class="publish-button" @click="editPublicity">公示发布</el-button>
    </el-form>

    <el-row v-for="(o,index) in publicityList" :key="index" class="el-row">
      <el-card class="box-card" shadow="hover">
        <div slot="header" class="clearfix">
          <el-row>
            <el-col>
              <span class="title" :title="o.title">{{ o.title }}</span>
              <el-button style="float: right; padding: 3px 0" type="text" @click="deletePublicity(o)">删除</el-button>
            </el-col>
            <el-col>
              <span class="date">发布时间: {{ o.createTime }}|发布账号: {{ o.publisherCode }}</span>
            </el-col>
          </el-row>
        </div>
        <div class="text item">
          {{ o.content }}
        </div>
        <el-image
          v-if="o.pictureUrl"
          style="width: 100px; height: 100px ; margin-top: 5px"
          :src="o.pictureUrl"
          :fit="fit"
        />
      </el-card>
    </el-row>

    <el-pagination
      background
      layout="prev, pager, next"
      :total="total"
      :page-size="pageSize"
      :current-page="curPageNum"
      class="pagination"
      @prev-click="skipPage(-1)"
      @next-click="skipPage(1)"
      @current-change="handleCurrentChange"
    />

    <el-dialog
      title="公告发布"
      :visible.sync="editDialogVisible"
      top="5vh"
      :append-to-body="true"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      class="el-dialog__body"
    >
      <el-form :model="publicityForm">
        <el-form-item label="公告标题：" label-width="120px">
          <el-input v-model="publicityForm.title" autocomplete="off" />
        </el-form-item>
        <el-form-item label="公告内容：" prop="desc" label-width="120px">
          <el-input v-model="publicityForm.content" type="textarea" :autosize="{ minRows: 9, maxRows: 9}" style="resize: none" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="editEnsureOrCancel(false)">确 定</el-button>
        <el-button @click="editEnsureOrCancel(true)">取 消</el-button>
      </div>
    </el-dialog>

  </div>

</template>

<script>
import { getPublicity, deletePublicity, insertPublicity } from '@/api/publicity'

export default {
  data() {
    return {
      loadingVisible: false,
      editDialogVisible: false,
      pageSize: 3,
      curPageNum: 1,
      total: 0,
      searchData: {
        publisherCode: undefined,
        title: '',
        timeRange: []
      },
      publicityList: [],
      publicityForm: {}
    }
  },
  created() {
    this.skipPage(0)
  },
  methods: {
    getDefaultForm: function() {
      return {
        title: '',
        content: '',
        createTime: undefined,
        pictureUrl: undefined
      }
    },
    skipPage: function(addPage) {
      const param = {
        pageNum: this.curPageNum + addPage,
        pageSize: this.pageSize
      }
      this.searchList(param)
      this.curPageNum = this.curPageNum + addPage
    },
    handleCurrentChange: function(val) {
      const param = {
        pageSize: this.pageSize,
        pageNum: val
      }
      this.searchList(param)
      this.curPageNum = val
    },
    /**
     * 查询列表
     * @param param
     */
    searchList: function(param) {
      this.loadingVisible = true
      const searchModel = param
      if (this.searchData.publisherCode) {
        searchModel.publisherCode = this.searchData.publisherCode
      }
      if (this.searchData.title) {
        searchModel.title = this.searchData.title
      }
      if (this.searchData.timeRange && this.searchData.timeRange.length > 0) {
        if (this.searchData.timeRange[0]) {
          searchModel.startTime = this.searchData.timeRange[0]
        }
        if (this.searchData.timeRange[1]) {
          searchModel.endTime = this.searchData.timeRange[1]
        }
      }
      getPublicity(searchModel).then(response => {
        const { data } = response
        this.publicityList = data.list
        this.total = data.total
        this.loadingVisible = false
      }).catch(error => {
        console.log(error)
        this.loadingVisible = false
      })
    },
    editPublicity: function() {
      this.publicityForm = this.getDefaultForm()
      this.editDialogVisible = true
    },
    deletePublicity: function(data) {
      this.$confirm('确定删除？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.loadingVisible = true
        const param = [data.row.id]
        deletePublicity(param).then(() => {
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
          this.loadingVisible = false
          this.handleCurrentChange(0)
        }).catch(error => {
          console.log(error)
          this.loadingVisible = false
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    },
    editEnsureOrCancel: function(isCancel) {
      if (isCancel) {
        this.editDialogVisible = false
        return
      }
      if (this.publicityForm.title && this.publicityForm.title !== '' &&
      this.publicityForm.content && this.publicityForm.content !== '') {
        insertPublicity(this.publicityForm).then((res) => {
          this.$message({
            type: 'success',
            message: '编辑成功!'
          })
          this.loadingVisible = false
          this.editDialogVisible = false
          this.handleCurrentChange(0)
        }).catch(error => {
          console.log(error)
          this.loadingVisible = false
        })
      } else {
        this.$message({
          type: 'info',
          message: '数据不规范!'
        })
      }
    }
  }
}
</script>

<style scoped>
  .text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 18px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }

  .box-card {
    margin-top: 20px;
    background-color: #EFF5FB;
    width: 100%;
    height: 200px;
    padding: 10px;
    overflow: auto;
    margin-left: 20px;
  }

  .date{
    font-size: small;
    color: #7f7f7f;
  }

  .title{
    overflow: hidden;
    text-overflow: ellipsis;
    width:300px;
    white-space:nowrap;
  }

  .publish-button{
    margin-left: 30px;
  }

  .selector {
    margin-left: 10px;
    margin-bottom: 10px;
  }

  .button-find {
    margin-left: 30px;
  }

  .el-dialog__body{
    height: 150vh;
    overflow: auto;
  }

  .el-row {
    display: flex;
    flex-wrap: wrap
  }

  .pagination {
    margin-top: 20px ;
    float: right ;
    margin-bottom: 20px
  }

</style>
