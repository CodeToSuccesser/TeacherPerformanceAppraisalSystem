<template>
  <div class="app-container">
    <el-form ref="form" :model="searchForm">
      <el-select v-model="searchForm.taskState" placeholder="任务状态" clearable class="selector-first">
        <el-option v-for="item in taskStateOptions" :key="item.value" :label="item.key" :value="item.value" />
      </el-select>
      <el-input v-model="searchForm.taskTitle" placeholder="任务标题" clearable class="selector" style="width: 120px" />
      <el-input v-model="searchForm.userLogName" placeholder="发布对象" clearable class="selector" style="width: 120px" />

      <el-button type="primary" size="small" class="button-find" @click="searchTask">查找</el-button>
      <el-button type="primary" size="small" class="publish-task" @click="taskPublishVisible=true">任务分配</el-button>
    </el-form>

    <el-row class="el-row">
      <el-col v-for="(o,index) in taskInfo" :key="o" span="11">
        <el-card :data="taskInfo" class="box-card" shadow="hover">
          <div slot="header">
            <el-row>
              <el-col>
                <span class="title" :title="taskInfo[index].title">{{ taskInfo[index].title }}</span>
                <el-button style="float: right; padding: 3px 0" type="text" @click="taskModify(index)">任务修改</el-button>
              </el-col>
              <el-col>
                <span class="date">{{ taskInfo[index].createTime }}</span>
                <el-button style="float: right; padding: 3px 0" type="text" @click="taskDelete(index)">删除任务</el-button>
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
                任务指派对象： {{ taskInfo[index].userName }}
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
      title="任务内容修改"
      :visible.sync="taskModifyVisible"
      top="5vh"
      :append-to-body="true"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
    >
      <el-form :model="taskDetailInfo">
        <el-form-item label="任务标题：" label-width="120px">
          <el-input v-model="taskDetailInfo.title" autocomplete="off" />
        </el-form-item>
        <el-form-item label="开始日期：" label-width="120px">
          <el-date-picker v-model="taskDetailInfo.startTime" type="date" placeholder="选择日期" style="width: 100%;" />
        </el-form-item>
        <el-form-item label="完成截止日期：" label-width="120px">
          <el-date-picker v-model="taskDetailInfo.deadlineTime" type="date" placeholder="选择日期" style="width: 100%;" />
        </el-form-item>
        <el-form-item label="内容描述：" label-width="120px">
          <el-input v-model="taskDetailInfo.content" type="textarea" :autosize="{ minRows: 9, maxRows: 9}" style="resize: none" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="taskModifyEnsure">确 定</el-button>
        <el-button @click="taskModifyCancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog
      title="任务分配"
      :visible.sync="taskPublishVisible"
      top="5vh"
      :append-to-body="true"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
    >
      <el-form :model="taskPublishInfo">
        <el-form-item label="任务标题：" label-width="120px">
          <el-input v-model="taskPublishInfo.title" autocomplete="off" />
        </el-form-item>
        <el-form-item label="任务分配用户：" label-width="120px">
          <el-input v-model="taskDetailInfo.userName" autocomplete="off" />
        </el-form-item>
        <el-form-item label="开始日期：" label-width="120px">
          <el-date-picker v-model="taskPublishInfo.startTime" type="date" placeholder="选择日期" style="width: 100%;" />
        </el-form-item>
        <el-form-item label="完成截止日期：" label-width="120px">
          <el-date-picker v-model="taskPublishInfo.deadlineTime" type="date" placeholder="选择日期" style="width: 100%;" />
        </el-form-item>
        <el-form-item label="内容描述：" label-width="120px">
          <el-input v-model="taskPublishInfo.content" type="textarea" :autosize="{ minRows: 9, maxRows: 9}" style="resize: none" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="taskPublishEnsure">确 定</el-button>
        <el-button @click="taskPublishCancel">取 消</el-button>
      </div>
    </el-dialog>

  </div>

</template>

<script>
export default {
  data() {
    return {
      taskModifyVisible: false,
      taskPublishVisible: false,
      percentage: 0,
      taskDetailInfo: {
      },
      taskPublishInfo: {
      },
      colors: [
        { color: '#f56c6c', percentage: 20 },
        { color: '#6f7ad3', percentage: 40 },
        { color: '#e6a23c', percentage: 60 },
        { color: '#1989fa', percentage: 80 },
        { color: '#5cb87a', percentage: 100 }
      ],
      taskInfo: [{
        title: '任务标题',
        content: '任务内容任务内容任务内容任务内容任务内容任务内容任务内容任务内容任务内容任务内容任务内容任务内容任务内容任务内容',
        createTime: '创建日期',
        startTime: '开始日期',
        deadlineTime: '截止日期',
        userName: 'yonghu',
        percentage: 0
      },
      {
        title: '任务标题',
        content: '任务内容任务内容任务内容任务内容任务内容任务内容任务内容任务内容任务内容任务内容任务内容任务内容任务内容任务内容',
        createTime: '创建日期',
        startTime: '开始日期',
        deadlineTime: '截止日期',
        userName: 'yonghu',
        percentage: 0
      }, {
        title: '任务标题',
        content: '任务内容任务内容任务内容任务内容任务内容任务内容任务内容任务内容任务内容任务内容任务内容任务内容任务内容任务内容',
        createTime: '创建日期',
        startTime: '开始日期',
        userName: 'yonghu',
        deadlineTime: '截止日期',
        percentage: 0
      }],
      searchForm: {
        taskState: '',
        taskTitle: '',
        userLogName: ''
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
    taskModify: function(index) {
      this.taskModifyVisible = true
      this.taskDetailInfo = this.taskInfo[index]
    },
    taskModifyEnsure: function() {
      this.taskModifyVisible = false
    },
    taskModifyCancel: function() {
      this.taskModifyVisible = false
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
    },
    taskPublishEnsure() {
      this.taskPublishVisible = false
    },
    taskPublishCancel() {
      this.taskPublishVisible = false
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

  .publish-task{
    margin-left: 20px;
  }

  .box-card {
    margin-top: 20px;
    width: 500px;
    height: 280px;
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
