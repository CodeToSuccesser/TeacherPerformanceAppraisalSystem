<template>
  <div class="app-container">
    <el-form ref="form" :model="searchData">
      <el-select v-model="searchData.selectedSchoolYear" placeholder="学年" class="selector-first" clearable>
        <el-option v-for="item in schoolYearOptions" :key="item.value" :label="item.label" :value="item.value" />
      </el-select>

      <el-select v-model="searchData.semester" placeholder="学期" class="selector" clearable>
        <el-option v-for="item in semesterOptions" :key="item.value" :label="item.label" :value="item.value" />
      </el-select>

      <el-input v-model="searchData.selectedTitle" placeholder="标题" clearable class="selector" style="width: 120px" />

      <el-button :v-loading="loadingVisible" type="primary" size="small" class="button-find" @click="searchOther">查找</el-button>

      <el-button
        v-if="permissionMap && permissionMap['applyOther-Button']"
        type="primary"
        size="small"
        class="button-find"
        @click="applyOtherDialogVisible = true"
      >新增</el-button>
    </el-form>

    <el-table :data="otherInfo" stripe style="width: 100% " :border="true" fit>
      <el-table-column :resizable="false" prop="id" sortable label="序号" align="center" width="60px">
        <template slot-scope="scope">
          <span>{{ (pageSize - 1) * (curPageNum - 1) + scope.$index + 1 }}</span>
        </template>
      </el-table-column>
      <el-table-column :resizable="false" prop="title" sortable label="工作标题" align="center" />
      <el-table-column :resizable="false" prop="content" sortable label="工作内容" align="center" />
      <el-table-column :resizable="false" prop="score" sortable label="工作评分" align="center" />
      <el-table-column :resizable="false" prop="schoolYear" sortable label="学年" align="center" />
      <el-table-column :resizable="false" prop="semester" sortable label="学期" align="center">
        <template slot-scope="scope">
          {{ Number(otherInfo[scope.$index].semester) === 0 ? '第一学期' : '第二学期' }}
        </template>
      </el-table-column>
      <el-table-column :resizable="false" prop="remark" sortable label="备注" align="center" />
      <el-table-column :resizable="false" prop="createTime" sortable label="创建日期" align="center" />
      <el-table-column :resizable="false" label="操作" align="center">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="modifyOther(scope)">修改</el-button>
          <el-button type="text" size="small" @click="deleteOther(scope)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

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
      title="其他教务工作信息修改"
      :visible.sync="otherDialogVisible"
      top="5vh"
      :append-to-body="true"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
    >
      <el-form :model="otherForm">
        <el-form-item label="工作标题" :label-width="formLabelWidth">
          <el-input v-model="otherForm.title" autocomplete="off" />
        </el-form-item>
        <el-form-item label="工作内容" :label-width="formLabelWidth" type="textarea">
          <el-input v-model="otherForm.content" autocomplete="off" />
        </el-form-item>
        <el-form-item
          v-if="permissionMap && permissionMap['modifyOtherScore-FormItem']"
          label="工作评分"
          :label-width="formLabelWidth"
        >
          <el-input v-model="otherForm.score" autocomplete="off" />
        </el-form-item>
        <el-form-item label="学期" :label-width="formLabelWidth">
          <el-select v-model="otherForm.semester" placeholder="学期" disabled>
            <el-option label="第一学期" value="0" />
            <el-option label="第二学期" value="1" />
          </el-select>
        </el-form-item>
        <el-form-item label="学年" :label-width="formLabelWidth" disabled="">
          <el-input v-model="otherForm.schoolYear" autocomplete="off" :disabled="otherEditDisable" />
        </el-form-item>
        <el-form-item label="备注" :label-width="formLabelWidth">
          <el-input v-model="otherForm.remark" autocomplete="off" :disabled="otherEditDisable" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="modifyOtherEditEnsure">确 定</el-button>
        <el-button @click="modifyOtherEditCancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog
      title="其他教务工作信息新增"
      :visible.sync="applyOtherDialogVisible"
      top="5vh"
      :append-to-body="true"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
    >
      <el-form :model="applyOtherForm">
        <el-form-item label="教师编号" :label-width="formLabelWidth">
          <el-input v-model="applyOtherForm.teacherCode" autocomplete="off" />
        </el-form-item>
        <el-form-item label="工作标题" :label-width="formLabelWidth">
          <el-input v-model="applyOtherForm.title" autocomplete="off" />
        </el-form-item>
        <el-form-item label="工作内容" :label-width="formLabelWidth">
          <el-input v-model="applyOtherForm.content" autocomplete="off" type="textarea" />
        </el-form-item>
        <el-form-item label="工作评分" :label-width="formLabelWidth">
          <el-input v-model="applyOtherForm.score" autocomplete="off" />
        </el-form-item>
        <el-form-item label="学期" :label-width="formLabelWidth">
          <el-select v-model="applyOtherForm.semester" placeholder="学期">
            <el-option label="第一学期" value="0" />
            <el-option label="第二学期" value="1" />
          </el-select>
        </el-form-item>
        <el-form-item label="学年" :label-width="formLabelWidth">
          <el-input v-model="applyOtherForm.schoolYear" autocomplete="off" />
        </el-form-item>
        <el-form-item label="备注" :label-width="formLabelWidth">
          <el-input v-model="applyOtherForm.remark" autocomplete="off" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="applyOther">申 请</el-button>
        <el-button @click="applyOtherDialogVisible = false">取 消</el-button>
      </div>
    </el-dialog>

  </div>

</template>

<script>
import { mapGetters } from 'vuex'
import { hideFullScreenLoading, showFullScreenLoading } from '@/utils/loading'
import { getOtherPerformance, modifyOtherPerformance, insertOtherPerformance, deleteOtherPerformance } from '@/api/other'

export default {
  data() {
    return {
      pageSize: 25,
      curPageNum: 1,
      total: 0,
      otherInfo: [],
      searchData: {
        semester: '',
        schoolYear: '',
        selectedTitle: ''
      },
      loadingVisible: false,
      otherDialogVisible: false,
      otherEditDisable: true,
      applyOtherDialogVisible: false,
      otherForm: {
        major: '',
        studentNumber: '',
        semester: '',
        schoolYear: '',
        remark: ''
      },
      applyOtherForm: {
        majorName: '',
        studentNumber: '',
        semester: '',
        schoolYear: '',
        remark: ''
      },
      formLabelWidth: '120px',
      permissionMap: {}
    }
  },
  computed: {
    ...mapGetters([
      'semesterOptions',
      'schoolYearOptions'
    ])
  },
  created() {
    this.permissionMap = this.$store.getters.permissionMap['AssessmentInfo-Other']
    const data = {
      pageNum: this.pageNum,
      pageSize: this.pageNum
    }
    this.getOtherPerformance(data)
  },
  methods: {
    getOtherPerformance(data) {
      if (this.permissionMap && this.permissionMap['getOtherPerformanceSearch-teacherCode'] &&
        this.permissionMap['getOtherPerformanceSearch-teacherCode']['teacherCode']) {
        data.teacherCode = this.$store.getters.account === '' ? JSON.parse(sessionStorage.getItem('stateStore')).user.account
          : this.$store.getters.account
      }
      showFullScreenLoading()
      getOtherPerformance(data)
        .then(response => {
          hideFullScreenLoading()
          const { data } = response
          this.otherInfo = data.list
          this.total = data.total
        })
        .catch(error => {
          console.log(error)
          hideFullScreenLoading()
        })
    },
    prePage() {
      const param = {
        pageNum: this.curPageNum - 1,
        pageSize: this.pageSize
      }
      this.getOtherPerformance(param)
      this.curPageNum = this.curPageNum - 1
    },
    nextPage() {
      const param = {
        pageNum: this.curPageNum + 1,
        pageSize: this.pageSize
      }
      this.getOtherPerformance(param)
      this.curPageNum = this.curPageNum + 1
    },
    handleCurrentChange(val) {
      const param = {
        pageNum: this.curPageNum,
        pageSize: this.pageSize
      }

      this.getOtherPerformance(param)
      this.curPageNum = val
    },
    modifyOtherEditEnsure() {
      this.otherDialogVisible = false
      showFullScreenLoading()
      modifyOtherPerformance(this.otherForm, this.otherForm.id)
        .then(response => {
          hideFullScreenLoading()
          this.$message.success('修改成功')
          location.reload()
        })
        .catch(error => {
          hideFullScreenLoading()
          console.log(error)
        })
    },
    modifyOtherEditCancel() {
      this.otherDialogVisible = false
    },
    applyOther() {
      showFullScreenLoading()
      insertOtherPerformance(this.applyOtherForm)
        .then(response => {
          hideFullScreenLoading()
          this.$message.success('新增成功')
          location.reload()
        })
        .catch(error => {
          console.log(error)
          hideFullScreenLoading()
        })
    },
    searchOther() {
      const data = {
        semester: this.searchData.selectedSemester,
        schoolYear: this.searchData.selectedSchoolYear,
        title: this.searchData.selectedTitle,
        pageNum: this.pageNum,
        pageSize: this.pageNum
      }
      this.getOtherPerformance(data)
    },
    deleteOther(scope) {
      const ids = [scope.row.id]
      this.$confirm('确认删除该教务工作信息?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        showFullScreenLoading()
        deleteOtherPerformance(ids)
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
    modifyOther(scope) {
      this.otherDialogVisible = true
      this.otherForm = JSON.parse(JSON.stringify(this.otherInfo[scope.$index]))
      this.otherForm.semester = String(this.otherForm.semester)
    }
  }
}
</script>

<style scoped>
  .selector-year {
    margin-bottom: 20px;
  }

  .selector {
    margin-left: 10px;
    margin-bottom: 20px;
  }

  .button-find {
    margin-left: 30px;
  }

  .button-add {
    float: right;
  }

  .pagination {
    margin-top: 20px ;
    float: right ;
    margin-bottom: 20px
  }
</style>
