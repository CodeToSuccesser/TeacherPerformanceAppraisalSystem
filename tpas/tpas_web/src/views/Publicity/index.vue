<template>
  <div class="app-container">
    <el-form ref="form" :model="searchForm">
      <el-input v-model="searchForm.title" placeholder="公示标题" clearable class="selector" style="width: 200px" />
      <el-button type="primary" size="small" class="button-find" @click="searchPublicity">查找</el-button>
    </el-form>
    <el-row class="el-row">
      <el-col v-for="(o,index) in publicityInfo" :key="o" span="11" >
        <el-card :data="publicityInfo" class="box-card" shadow="hover">
          <div slot="header" class="clearfix">
            <el-row>
              <el-col>
                <span class="title" :title="publicityInfo[index].title">{{ publicityInfo[index].title }}</span>
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

  </div>
</template>

<script>
export default {
  name: 'IndexVue',
  data() {
    return {
      searchForm: {
        title: ''
      },
      publicityInfo: [
        {
          title: '2020-2021第一学期计算机学院优秀教师评比',
          content: '2020-2021第一学期计算机学院优秀教师评比，本次评比参考课时、实习带队、论文指导以及日常的获奖情况进行评比，具体评比细则见下图。本次评比以系统绩效考核为准，选出前2%的教师参与学年的分享会议',
          createTime: '2020-11-15'
        },
        {
          title: '关于组织我院教师参加教务技能培训活动',
          content: '关于组织我院教师参加教务技能培训活动，关于组织我院教师参加教务技能培训活动,关于组织我院教师参加教务技能培训活动,详情见学者网链接：https://www.scholat.com/teamwork/showPostMessage.html?id=9261',
          createTime: '2020-10-15'
        },
        {
          title: '关于组织我院教师参加教务技能培训活动',
          content: '关于组织我院教师参加教务技能培训活动，详情见学者网链接：https://www.scholat.com/teamwork/showPostMessage.html?id=9261',
          createTime: '2020-10-15'
        },
        {
          title: '关于组织我院教师参加教务技能培训活动',
          content: '关于组织我院教师参加教务技能培训活动，详情见学者网链接：https://www.scholat.com/teamwork/showPostMessage.html?id=9261',
          createTime: '2020-10-15'
        },
        {
          title: '2020-2021第一学期计算机学院优秀教师评比',
          content: '2020-2021第一学期计算机学院优秀教师评比，本次评比参考课时、实习带队、论文指导以及日常的获奖情况进行评比，具体评比细则见下图。本次评比以系统绩效考核为准，选出前2%的教师参与学年的分享会议',
          createTime: '2020-11-15'
        },
        {
          title: '2020-2021第一学期计算机学院优秀教师评比',
          content: '2020-2021第一学期计算机学院优秀教师评比，本次评比参考课时、实习带队、论文指导以及日常的获奖情况进行评比，具体评比细则见下图。本次评比以系统绩效考核为准，选出前2%的教师参与学年的分享会议',
          createTime: '2020-11-15'
        }
      ]
    }
  },
  methods: {
    searchPublicity: function() {

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

</style>
