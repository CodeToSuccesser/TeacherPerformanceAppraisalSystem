<template>
  <div class="app-container">
    <el-form ref="form" :model="searchData">
      <el-input v-model="searchData.title" placeholder="公示标题" clearable class="selector" style="width: 200px" />
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
    </el-form>

    <el-row v-for="(o,index) in publicityList" :key="index" class="el-row">
      <el-card class="box-card" shadow="hover">
        <div slot="header" class="clearfix">
          <el-row>
            <el-col>
              <span class="title" :title="o.title">{{ o.title }}</span>
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

  </div>
</template>

<script>
import { getPublicity } from '@/api/publicity'

export default {
  name: 'IndexVue',
  data() {
    return {
      loadingVisible: false,
      editDialogVisible: false,
      pageSize: 3,
      curPageNum: 1,
      total: 0,
      searchData: {
        title: '',
        timeRange: []
      },
      publicityList: []
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
    }
  }
}
</script>

<style scoped>
  .text {
    font-size: 14px;
  }

  .item {
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
    width: 100%;
    background-color: #EFF5FB;
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

  .selector {
    margin-left: 10px;
    margin-bottom: 10px;
  }

  .button-find {
    margin-left: 30px;
  }

  .title{
    overflow: hidden;
    text-overflow: ellipsis;
    width:300px;
    white-space:nowrap;
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
