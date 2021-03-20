<template>
  <div class="app-container">

    <el-form ref="form" :model="searchForm">
      <el-input v-model="searchForm.title" placeholder="公示标题" clearable class="selector" style="width: 200px" />
      <el-button type="primary" size="small" class="button-find" @click="searchPublicity">查找</el-button>
      <el-button type="primary" size="small" class="publish-button" @click="publishPublicityVisible=true">公示发布</el-button>
    </el-form>

    <el-row class="el-row">
      <el-col v-for="(o,index) in publicityInfo" :key="o" span="11">
        <el-card :data="publicityInfo" class="box-card" shadow="hover">
          <div slot="header" class="clearfix">
            <el-row>
              <el-col>
                <span class="title" :title="publicityInfo[index].title">{{ publicityInfo[index].title }}</span>
                <el-button style="float: right; padding: 3px 0" type="text">删除</el-button>
              </el-col>
              <el-col>
                <span class="date">{{ publicityInfo[index].createTime }}</span>
              </el-col>
            </el-row>
          </div>
          <div class="text item">
            {{ publicityInfo[index].content }}
          </div>
          <el-image
            v-if="publicityInfo[index].pictureUrl"
            style="width: 100px; height: 100px ; margin-top: 5px"
            :src="publicityInfo[index].pictureUrl"
            :fit="fit"
          />
        </el-card>
      </el-col>
    </el-row>

    <el-dialog
      title="公告发布"
      :visible.sync="publishPublicityVisible"
      top="5vh"
      :append-to-body="true"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      class="el-dialog__body"
    >
      <el-form :model="newPublicictyInfo">
        <el-form-item label="公告标题：" label-width="120px">
          <el-input v-model="newPublicictyInfo.title" autocomplete="off" />
        </el-form-item>
        <el-form-item label="公告内容：" prop="desc" label-width="120px">
          <el-input v-model="newPublicictyInfo.content" type="textarea" :autosize="{ minRows: 9, maxRows: 9}" style="resize: none" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="publishPublicityEnsure">确 定</el-button>
        <el-button @click="publishPublicityCancel">取 消</el-button>
      </div>
    </el-dialog>

  </div>

</template>

<script>
export default {
  data() {
    return {
      publishPublicityVisible: false,
      newPublicictyInfo: {

      },
      searchForm: {
        title: ''
      },
      publicityInfo: [
        {
          title: '2020-2021第一学期计算机学院优秀教师评比',
          content: '2020-2021第一学期计算机学院优秀教师评比',
          createTime: '2020-11-15'
        },
        {
          title: '关于组织我院学生参加网易开发者培养赛S2的通知',
          content: '关于组织我院学生参加网易开发者培养赛S2的通知，详情见学者网链接：https://www.scholat.com/teamwork/showPostMessage.html?id=9261',
          createTime: '2020-10-15'
        }
      ]
    }
  },
  methods: {
    searchPublicity: function() {

    },
    publishPublicityEnsure: function() {
      this.publishPublicityVisible = false
    },
    publishPublicityCancel: function() {
      this.publishPublicityVisible = false
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
    width: 500px;
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

</style>
