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
      <el-col v-for="(o,index) in taskInfo">
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
                截止日期： {{ taskInfo[index].endTime }}
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
          <el-progress style="margin-bottom: 10px" :percentage="taskDetailInfo.completeDegree" :color="taskProgressColors" />
          <div>
            <el-button-group>
              <el-button icon="el-icon-minus" @click="decrease" />
              <el-button icon="el-icon-plus" @click="increase" />
            </el-button-group>
          </div>
        </el-form-item>
        <el-form-item label="任务完成反馈：">
          <el-input v-model="taskDetailInfo.feedbackContent" type="textarea" :autosize="{ minRows: 6, maxRows: 6}" style="resize: none" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="feedbackEnsure(taskDetailInfo.id)">确 定</el-button>
        <el-button @click="feedbackCancel">取 消</el-button>
      </div>
    </el-dialog>

  </div>

</template>

<script>
import { mapGetters } from 'vuex'
import { getTask, taskFeedback } from '@/api/task'
import { hideFullScreenLoading, showFullScreenLoading } from '@/utils/loading'

export default {
  computed: {
    ...mapGetters([
      'taskStateOptions',
      'taskProgressColors'
    ])
  },
  data() {
    return {
      pageSize: 25,
      curPageNum: 1,
      total: 0,
      completeDegree: 0,
      taskDetailInfo: {
      },
      feedbackVisible: false,
      taskInfo: [],
      searchForm: {
        taskState: '',
        taskTitle: ''
      }
    }
  },
  created() {
    const param = {
      pageSize: this.pageSize,
      pageNum: this.curPageNum
    }
    this.getTask(param)
  },
  methods: {
    getTask(body) {
      if (this.permissionMap && this.permissionMap['getTaskSearch-teacherCode'] && this.permissionMap['getTaskSearch-teacherCode']['teacherCode']) {
        body.receiverCode = this.$store.getters.account === '' ? JSON.parse(sessionStorage.getItem('stateStore')).user.account : this.$store.getters.account
      }
      getTask(body)
        .then(response => {
          const { data } = response
          this.taskInfo = data.list
          this.total = data.total
        })
        .catch(error => {
          console.log(error)
        })
    },
    searchTask: function() {

    },
    taskFeedback: function(index) {
      this.feedbackVisible = true
      this.taskDetailInfo = this.taskInfo[index]
    },
    feedbackEnsure: function(id) {
      this.feedbackVisible = false
      const data = {
        feedbackContent: this.taskDetailInfo.feedbackContent,
        completeDegree: this.taskDetailInfo.completeDegree
      }
      showFullScreenLoading()
      taskFeedback(data, id)
        .then(response => {
          hideFullScreenLoading()
          this.$message.success('反馈成功')
        })
        .catch(error => {
          hideFullScreenLoading()
          console.log(error)
        })
    },
    feedbackCancel: function() {
      this.feedbackVisible = false
    },
    increase() {
      this.taskDetailInfo.completeDegree += 10
      if (this.taskDetailInfo.completeDegree > 100) {
        this.taskDetailInfo.completeDegree = 100
      }
    },
    decrease() {
      this.taskDetailInfo.completeDegree -= 10
      if (this.taskDetailInfo.completeDegree < 0) {
        this.taskDetailInfo.completeDegree = 0
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
    width: 90%;
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
