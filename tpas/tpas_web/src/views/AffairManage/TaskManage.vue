<template>
  <div class="app-container">
    <el-form ref="form" :model="searchForm">
      <el-select v-model="searchForm.taskState" placeholder="任务状态" clearable class="selector-first">
        <el-option v-for="item in taskStateOptions" :key="item.value" :label="item.key" :value="item.value" />
      </el-select>
      <el-input v-model="searchForm.taskTitle" placeholder="任务标题" clearable class="selector" style="width: 120px" />
      <el-input v-model="searchForm.receiverCode" placeholder="发布对象" clearable class="selector" style="width: 120px" />

      <el-button type="primary" size="small" class="button-find" @click="searchTask">查找</el-button>
      <el-button type="primary" size="small" class="publish-task" @click="taskPublishVisible=true">任务分配</el-button>
    </el-form>

    <el-row class="el-row">
      <el-col v-for="(o,index) in taskInfo" :key="index">
        <el-card :data="taskInfo" class="box-card" shadow="hover">
          <div slot="header">
            <el-row>
              <el-col>
                <span class="title" :title="taskInfo[index].title">{{ taskInfo[index].title }}</span>
                <el-button style="float: right; padding: 3px 0" type="text" @click="taskModify(index)">任务修改</el-button>
              </el-col>
              <el-col>
                <span class="date">{{ taskInfo[index].createTime }}</span>
                <el-button style="float: right; padding: 3px 0" type="text" @click="taskDelete(taskInfo[index].id)">删除任务</el-button>
              </el-col>
              <el-col>
                <el-button style="float: right; padding: 3px 0" type="text" @click="showTaskFeedback(index)">查看任务反馈</el-button>
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
                任务指派对象： {{ taskInfo[index].receiverName }}
              </el-col>
              <el-col class="task-content">
                任务指派对象编码： {{ taskInfo[index].receiverCode }}
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

    <el-pagination
      background
      layout="prev, pager, next"
      :total="total"
      :page-size="pageSize"
      :current-page="curPageNum"
      class="pagination"
      @prev-click="prePage"
      @next-click="nextPage"
      @current-change="handleCurrentChange"
    />

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
          <el-date-picker v-model="taskDetailInfo.endTime" type="date" placeholder="选择日期" style="width: 100%;" />
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
          <el-input v-model="taskPublishInfo.receiverCode" autocomplete="off" />
        </el-form-item>
        <el-form-item label="开始日期：" label-width="120px">
          <el-date-picker v-model="taskPublishInfo.startTime" type="date" placeholder="选择日期" style="width: 100%;" />
        </el-form-item>
        <el-form-item label="完成截止日期：" label-width="120px">
          <el-date-picker v-model="taskPublishInfo.endTime" type="date" placeholder="选择日期" style="width: 100%;" />
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

    <el-dialog
      title="任务进度反馈"
      :visible.sync="feedbackVisible"
      top="5vh"
      :append-to-body="true"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      class="el-dialog__body"
    >
      <el-form :model="taskFeedbackInfo">
        <el-form-item label="任务完成百分比：">
          <el-progress style="margin-bottom: 10px" :percentage="taskFeedbackInfo.completeDegree" :color="taskProgressColors" />
        </el-form-item>
        <el-form-item label="任务完成反馈：">
          <el-input
            v-model="taskFeedbackInfo.feedbackContent"
            type="textarea"
            :autosize="{ minRows: 6, maxRows: 6}"
            style="resize: none"
            :disabled="true"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="feedbackVisible = false">确 定</el-button>
      </div>
    </el-dialog>

  </div>

</template>

<script>
import { mapGetters } from 'vuex'
import { getTask, insertTask, deleteTask, modifyTask, taskFeedback } from '@/api/task'
import { hideFullScreenLoading, showFullScreenLoading } from '@/utils/loading'
import { renderTime } from '@/utils/index'

export default {
  data() {
    return {
      pageSize: 25,
      curPageNum: 1,
      total: 0,
      taskModifyVisible: false,
      taskPublishVisible: false,
      percentage: 0,
      taskDetailInfo: {
      },
      taskFeedbackInfo: {
      },
      taskPublishInfo: {
      },
      taskInfo: [],
      searchForm: {
        taskState: '',
        taskTitle: '',
        userLogName: ''
      },
      feedbackVisible: false
    }
  },
  computed: {
    ...mapGetters([
      'taskStateOptions',
      'taskProgressColors'
    ])
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
      const data = {
        state: this.searchForm.taskState,
        title: this.searchForm.taskTitle,
        receiverCode: this.searchForm.receiverCode,
        pageSize: this.pageSize,
        pageNum: this.curPageNum
      }
      this.getTask(data)
    },
    taskModify: function(index) {
      this.taskModifyVisible = true
      this.taskDetailInfo = this.taskInfo[index]
    },
    taskModifyEnsure: function() {
      this.taskModifyVisible = false
      this.taskDetailInfo.startTime = renderTime(this.taskDetailInfo.startTime)
      this.taskDetailInfo.endTime = renderTime(this.taskDetailInfo.endTime)
      showFullScreenLoading()
      modifyTask(this.taskDetailInfo, this.taskDetailInfo.id)
        .then(response => {
          hideFullScreenLoading()
          this.$message.success('修改成功')
          location.reload()
        })
        .catch(error => {
          console.log(error)
          hideFullScreenLoading()
        })
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
      const body = {
        content: this.taskPublishInfo.content,
        title: this.taskPublishInfo.title,
        receiverCode: this.taskPublishInfo.receiverCode,
        startTime: renderTime(this.taskPublishInfo.startTime),
        endTime: renderTime(this.taskPublishInfo.endTime)
      }
      body.publisherCode = this.$store.getters.account === ''
        ? JSON.parse(sessionStorage.getItem('stateStore')).user.account : this.$store.getters.account
      showFullScreenLoading()
      insertTask(body)
        .then(response => {
          hideFullScreenLoading()
          this.$message.success('发布任务成功')
          location.reload()
        })
        .catch(error => {
          console.log(error)
          hideFullScreenLoading()
        })
    },
    taskPublishCancel() {
      this.taskPublishVisible = false
    },
    prePage() {

    },
    nextPage() {

    },
    handleCurrentChange() {

    },
    taskDelete(id) {
      const ids = [id]
      this.$confirm('确认删除该任务?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        showFullScreenLoading()
        deleteTask(ids)
          .then(response => {
            hideFullScreenLoading()
            this.$message({
              type: 'success',
              message: '删除成功!'
            })
            location.reload()
          })
          .catch(error => {
            console.log(error)
            hideFullScreenLoading()
          })
      })
    },
    showTaskFeedback(index) {
      this.feedbackVisible = true
      this.taskFeedbackInfo = this.taskInfo[index]
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
    width: 95%;
    height: 300px;
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

  .pagination {
    margin-top: 20px ;
    float: right ;
    margin-bottom: 20px
  }

</style>
