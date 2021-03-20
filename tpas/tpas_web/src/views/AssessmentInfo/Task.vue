<template>
  <div class="app-container">
    <el-form ref="form" :model="searchForm">
      <el-select v-model="searchForm.taskState" placeholder="任务状态" clearable class="selector-first">
        <el-option v-for="item in taskStateOptions" :key="item.value" :label="item.key" :value="item.value" />
      </el-select>
      <el-input v-model="searchForm.taskTitle" placeholder="任务标题" clearable class="selector" style="width: 120px" />
      <el-button type="primary" size="small" class="button-find" @click="searchTask">查找</el-button>
    </el-form>

    <el-row class="el-row">
      <el-col v-for="(o,index) in taskInfo" :key="o" span="11">
        <el-card :data="taskInfo" class="box-card" shadow="hover">
          <div slot="header">
            <el-row>
              <el-col>
                <span class="title" :title="taskInfo[index].title">{{ taskInfo[index].title }}</span>
                <el-button style="float: right; padding: 3px 0" type="text" @click="taskFeedback(index)">进度反馈</el-button>
              </el-col>
              <el-col>
                <span class="date">{{ taskInfo[index].createTime }}</span>
              </el-col>
            </el-row>
          </div>
          <div class="text item">
            <el-row>
              <el-col class="task-content">
                开始日期： {{ taskInfo[index].startTime }}
              </el-col>
              <el-col class="task-content">
                截止日期： {{ taskInfo[index].deadlineTime }}
              </el-col>
              <el-col class="task-content">
                任务详情： {{ taskInfo[index].content }}
              </el-col>
            </el-row>
          </div>
          <el-image
            v-if="taskInfo[index].pictureUrl"
            style="width: 100px; height: 100px ; margin-top: 5px"
            :src="taskInfo[index].pictureUrl"
            :fit="fit"
          />
        </el-card>
      </el-col>
    </el-row>

    <el-dialog
      title="任务进度反馈"
      :visible.sync="feedbackVisible"
      top="5vh"
      :append-to-body="true"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      class="el-dialog__body"
    >
      <el-form :model="taskDetailInfo">
        <el-form-item label="任务完成百分比：">
          <el-progress style="margin-bottom: 10px" :percentage="taskDetailInfo.percentage" :color="colors" />
          <div>
            <el-button-group>
              <el-button icon="el-icon-minus" @click="decrease" />
              <el-button icon="el-icon-plus" @click="increase" />
            </el-button-group>
          </div>
        </el-form-item>
        <el-form-item label="任务完成反馈：">
          <el-input v-model="taskDetailInfo.content" type="textarea" :autosize="{ minRows: 6, maxRows: 6}" style="resize: none" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="feedbackEnsure">确 定</el-button>
        <el-button @click="feedbackCancel">取 消</el-button>
      </div>
    </el-dialog>

  </div>

</template>

<script>
export default {
  data() {
    return {
      percentage: 0,
      taskDetailInfo: {
      },
      colors: [
        { color: '#f56c6c', percentage: 20 },
        { color: '#6f7ad3', percentage: 40 },
        { color: '#e6a23c', percentage: 60 },
        { color: '#1989fa', percentage: 80 },
        { color: '#5cb87a', percentage: 100 }
      ],
      feedbackVisible: false,
      taskInfo: [{
        title: '任务标题',
        content: '任务内容任务内容任务内容任务内容任务内容任务内容任务内容任务内容任务内容任务内容任务内容任务内容任务内容任务内容',
        createTime: '创建日期',
        startTime: '开始日期',
        deadlineTime: '截止日期',
        percentage: 0
      },
      {
        title: '任务标题',
        content: '任务内容任务内容任务内容任务内容任务内容任务内容任务内容任务内容任务内容任务内容任务内容任务内容任务内容任务内容',
        createTime: '创建日期',
        startTime: '开始日期',
        deadlineTime: '截止日期',
        percentage: 0
      }, {
        title: '任务标题',
        content: '任务内容任务内容任务内容任务内容任务内容任务内容任务内容任务内容任务内容任务内容任务内容任务内容任务内容任务内容',
        createTime: '创建日期',
        startTime: '开始日期',
        deadlineTime: '截止日期',
        percentage: 0
      }],
      searchForm: {
        taskState: '',
        taskTitle: ''
      },
      taskStateOptions: [
        {
          key: '未截止',
          value: 0
        },
        {
          key: '已截止',
          value: 1
        },
        {
          key: '已取消',
          value: 2
        }
      ]
    }
  },
  methods: {
    searchTask: function() {

    },
    taskFeedback: function(index) {
      this.feedbackVisible = true
      this.taskDetailInfo = this.taskInfo[index]
    },
    feedbackEnsure: function() {
      this.feedbackVisible = false
    },
    feedbackCancel: function() {
      this.feedbackVisible = false
    },
    increase() {
      this.taskDetailInfo.percentage += 10
      if (this.taskDetailInfo.percentage > 100) {
        this.taskDetailInfo.percentage = 100
      }
    },
    decrease() {
      this.taskDetailInfo.percentage -= 10
      if (this.taskDetailInfo.percentage < 0) {
        this.taskDetailInfo.percentage = 0
      }
    }
  }
}
</script>

<style scoped>
  .selector-first {
    margin-bottom: 20px;
  }

  .selector {
    margin-left: 10px;
    margin-bottom: 20px;
  }

  .button-find {
    margin-left: 30px;
  }

  .box-card {
    margin-top: 20px;
    width: 500px;
    height: 250px;
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

  .el-row {
    display: flex;
    flex-wrap: wrap
  }

  .task-content{
    height: 30px;
  }

  .el-dialog__body{
    height: 150vh;
    overflow: auto;
  }

  .text {
    font-size: 14px;
  }

</style>
