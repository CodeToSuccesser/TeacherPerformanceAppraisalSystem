<template>
  <div class="app-container">

    <el-select v-model="searchData.courseCharacter" placeholder="课程性质" class="selector-year">
      <el-option v-for="item in courseCharacterEnum" :key="item.value" :label="item.label" :value="item.value" />
    </el-select>
    <el-select v-model="searchData.courseType" placeholder="课程类别" class="selector-term">
      <el-option v-for="item in courseTypeEnum" :key="item.value" :label="item.label" :value="item.value" />
    </el-select>
    <el-select v-model="searchData.softHard" placeholder="课程类别" class="selector-term">
      <el-option v-for="item in softHardEnum" :key="item.value" :label="item.label" :value="item.value" />
    </el-select>
    <el-select v-model="searchData.studentType" placeholder="课程类别" class="selector-term">
      <el-option v-for="item in studentTypeEnum" :key="item.value" :label="item.label" :value="item.value" />
    </el-select>
    <el-select v-model="searchData.isBilingual" placeholder="课程类别" class="selector-term">
      <el-option v-for="item in bilingualEnum" :key="item.value" :label="item.label" :value="item.value" />
    </el-select>

    <el-button :v-loading="loadingVisible" type="primary" size="small" class="button-find" @click="handleCurrentChange(0, 0)">查找</el-button>

    <el-button type="primary" size="small" class="button-add" :disabled="loadingVisible" @click="editCourseBase(null)">新增</el-button>
    <el-button type="primary" size="small" class="button-add" :disabled="loadingVisible" @click="downloadTemplate">下载导入模板</el-button>
    <el-button type="primary" size="small" class="button-add" :disabled="loadingVisible" @click="importCourseInfo">导入</el-button>
    <el-button type="primary" size="small" class="button-add" :disabled="loadingVisible" @click="exportCourseInfo">导出</el-button>

    <el-table :data="courseBaseList" stripe style="width: 100% " :border="true" fit>
      <el-table-column :resizable="false" prop="id" sortable label="编码" align="center" width="60px" />
      <el-table-column :resizable="false" prop="courseCode" sortable label="课程编号" align="center" />
      <el-table-column :resizable="false" prop="courseName" sortable label="课程名称" align="center" />
      <el-table-column :resizable="false" prop="courseCridet" sortable label="学分" align="center" width="60px" />
      <el-table-column :resizable="false" prop="totalHours" sortable label="总学时数" align="center" width="60px" />
      <el-table-column :resizable="false" prop="institute" sortable label="开课学院" align="center" />
      <el-table-column :resizable="false" prop="courseCharacter" sortable label="课程性质" align="center">
        <template slot-scope="scope">
          {{ courseCharacterEnum.filter(it => it.value === scope.row.courseCharacter).length>0?
          courseCharacterEnum.filter(it => it.value === scope.row.courseCharacter)[0].label:'?'}}
        </template>
      </el-table-column>
      <el-table-column :resizable="false" prop="courseType" sortable label="课程类别" align="center" >
        <template slot-scope="scope">
          {{ courseTypeEnum.filter(it => it.value === scope.row.courseType).length>0?
          courseTypeEnum.filter(it => it.value === scope.row.courseType)[0].label:'?'}}
        </template>
      </el-table-column>
      <el-table-column :resizable="false" prop="isBilingual" sortable label="授课语言" align="center">
        <template slot-scope="scope">
          {{ bilingualEnum.filter(it => it.value === scope.row.isBilingual).length>0?
          bilingualEnum.filter(it => it.value === scope.row.isBilingual)[0].label:'?'}}
        </template>
      </el-table-column>
      <el-table-column :resizable="false" prop="softHard" sortable label="软硬件课程" align="center">
        <template slot-scope="scope">
          {{ softHardEnum.filter(it => it.value === scope.row.softHard).length>0?
          softHardEnum.filter(it => it.value === scope.row.softHard)[0].label:'?'}}
        </template>
      </el-table-column>
      <el-table-column :resizable="false" prop="studentType" sortable label="学生类型" align="center">
        <template slot-scope="scope">
          {{ studentTypeEnum.filter(it => it.value === scope.row.studentType).length>0?
          studentTypeEnum.filter(it => it.value === scope.row.studentType)[0].label:'?'}}
        </template>
      </el-table-column>
      <el-table-column :resizable="false" prop="firstClassTime" sortable label="首次开课时间" align="center" />
      <el-table-column :resizable="false" prop="newTextbookTime" sortable label="选用教材时间" align="center" />
      <el-table-column :resizable="false" prop="remark" label="备注" />
      <el-table-column :resizable="false" prop="createTime" sortable label="创建日期" />
      <el-table-column :resizable="false" label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" :disabled="loadingVisible" @click="editCourseBase(scope)">修改</el-button>
          <el-button type="text" size="small" :disabled="loadingVisible" @click="deleteCourseBase(scope)">删除</el-button>
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
      @prev-click="skipPage(-1)"
      @next-click="skipPage(1)"
      @current-change="handleCurrentChange"
    />

    <el-dialog title="课程信息编辑" :visible.sync="editDialogVisible" top="5vh" :append-to-body="true" :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form :model="editForm">
        <el-form-item v-if="editForm.id" label="编码" :label-width="formLabelWidth">
          <el-input v-model="editForm.id" autocomplete="off" :disabled="true" />
        </el-form-item>
        <el-form-item label="课程编号" :label-width="formLabelWidth">
          <el-input v-model="editForm.courseCode" autocomplete="off" :disabled="editForm.id!==undefined" />
        </el-form-item>
        <el-form-item label="课程名称" :label-width="formLabelWidth">
          <el-input v-model="editForm.courseName" autocomplete="off" />
        </el-form-item>
        <el-form-item label="学分" :label-width="formLabelWidth">
          <el-input v-model="editForm.courseCridet" autocomplete="off" />
        </el-form-item>
        <el-form-item label="总学时数" :label-width="formLabelWidth">
          <el-input v-model="editForm.totalHours" autocomplete="off" />
        </el-form-item>
        <el-form-item label="开课学院" :label-width="formLabelWidth">
          <el-input v-model="editForm.institute" autocomplete="off" />
        </el-form-item>
        <el-form-item label="课程性质" :label-width="formLabelWidth">
          <el-select v-model="editForm.courseCharacter" class="selector-year">
            <el-option
              v-for="item in courseCharacterEnum.filter(it => it.value !== '')"
              :key="item.value"
              :label="item.label"
              :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="课程类别" :label-width="formLabelWidth">
          <el-select v-model="editForm.courseType" class="selector-year">
            <el-option
              v-for="item in courseTypeEnum.filter(it => it.value !== '')"
              :key="item.value"
              :label="item.label"
              :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="是否双语授课" :label-width="formLabelWidth">
          <el-select v-model="editForm.isBilingual" class="selector-year">
            <el-option
              v-for="item in bilingualEnum.filter(it => it.value !== '')"
              :key="item.value"
              :label="item.label"
              :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="软硬件课程" :label-width="formLabelWidth">
          <el-select v-model="editForm.softHard" class="selector-year">
            <el-option
              v-for="item in softHardEnum.filter(it => it.value !== '')"
              :key="item.value"
              :label="item.label"
              :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="学生类型" :label-width="formLabelWidth">
          <el-select v-model="editForm.studentType" class="selector-year">
            <el-option
              v-for="item in studentTypeEnum.filter(it => it.value !== '')"
              :key="item.value"
              :label="item.label"
              :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="首次开课时间" :label-width="formLabelWidth">
          <el-date-picker
            v-model="editForm.firstClassTime"
            format="yyyy-MM-dd HH:mm:ss"
            value-format="yyyy-MM-dd HH:mm:ss"
            type="datetime"
            placeholder="选择时间"
          />
        </el-form-item>
        <el-form-item label="选用教材时间" :label-width="formLabelWidth">
          <el-date-picker
            v-model="editForm.newTextbookTime"
            format="yyyy-MM-dd HH:mm:ss"
            value-format="yyyy-MM-dd HH:mm:ss"
            type="datetime"
            placeholder="选择时间"
          />
        </el-form-item>
        <el-form-item label="备注" :label-width="formLabelWidth">
          <el-input v-model="editForm.remark" autocomplete="off" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="editEnsureOrCancel(false)">确 定</el-button>
        <el-button @click="editEnsureOrCancel(true)">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog
      title="导入文件信息"
      :visible.sync="importDialogVisible"
      width="30%"
    >
      <el-form>
        <el-form-item label="选择上传文件：" style="margin-top: 10px">
          <el-upload
            action="#"
            :on-change="getFile"
            :on-remove="handleRemove"
            :multiple="false"
            :limit="1"
            :on-exceed="handleExceed"
            :file-list="fileList"
            accept=".xlsx,.xls"
            :auto-upload="false"
          >
            <el-button size="small" type="primary">选择文件</el-button>
          </el-upload>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelImportFile">取 消</el-button>
        <el-button type="primary" @click="importFile">确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>
<script>
import { mapGetters } from 'vuex'
import {
  queryCourseBase,
  downloadCourseInfoTemplate,
  uploadCourseInfo,
  exportCourseInfo,
  editCourseBaseInfo,
  addCourseBaseInfo,
  deleteCourseInfos
} from '@/api/course'
import { hideFullScreenLoading, showFullScreenLoading } from '@/utils/loading'
import { downloadExcel } from '@/utils/file'

export default {
  name: 'CourseManage',
  computed: {
    ...mapGetters([
      'courseCharacterEnum',
      'courseTypeEnum',
      'softHardEnum',
      'studentTypeEnum',
      'bilingualEnum'
    ])
  },
  data() {
    return {
      formLabelWidth: '130px',
      loadingVisible: false,
      editDialogVisible: false,
      importDialogVisible: false,
      pageSize: 25,
      curPageNum: 1,
      total: 0,
      searchData: {
        courseCharacter: undefined,
        courseType: undefined,
        softHard: undefined,
        studentType: undefined,
        isBilingual: undefined
      },
      courseBaseList: [],
      editForm: {},
      fileList: []
    }
  },
  created() {
    this.skipPage(0)
  },
  methods: {
    getDefaultForm: function() {
      return {
        id: undefined,
        courseCode: '',
        courseName: '',
        courseCharacter: undefined,
        courseType: undefined,
        courseCridet: '',
        totalHours: '',
        institute: '',
        firstClassTime: '',
        isBilingual: undefined,
        newTextbookTime: '',
        softHard: undefined,
        studentType: undefined,
        remark: ''
      }
    },
    editCourseBase: function(data) {
      if (data) {
        this.editForm = {
          id: data.row.id,
          courseCode: data.row.courseCode,
          courseName: data.row.courseName,
          courseCharacter: data.row.courseCharacter,
          courseType: data.row.courseType,
          courseCridet: data.row.courseCridet,
          totalHours: data.row.totalHours,
          institute: data.row.institute,
          firstClassTime: data.row.firstClassTime,
          isBilingual: data.row.isBilingual,
          newTextbookTime: data.row.newTextbookTime,
          softHard: data.row.softHard,
          studentType: data.row.studentType,
          remark: data.row.remark
        }
      } else {
        this.editForm = this.getDefaultForm()
      }
      this.editDialogVisible = true
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
      if (this.searchData.courseCharacter && this.searchData.courseCharacter !== '') {
        searchModel.courseCharacter = this.searchData.courseCharacter
      }
      if (this.searchData.courseType && this.searchData.courseType !== '') {
        searchModel.courseType = this.searchData.courseType
      }
      if (this.searchData.softHard && this.searchData.softHard !== '') {
        searchModel.softHard = this.searchData.softHard
      }
      if (this.searchData.studentType && this.searchData.studentType !== '') {
        searchModel.studentType = this.searchData.studentType
      }
      if (this.searchData.isBilingual && this.searchData.isBilingual !== '') {
        searchModel.isBilingual = this.searchData.isBilingual
      }
      queryCourseBase(searchModel).then(response => {
        const { data } = response
        this.courseBaseList = data.list
        this.total = data.total
        this.loadingVisible = false
      }).catch(error => {
        console.log(error)
        this.loadingVisible = false
      })
    },
    /**
     * 删除
     **/
    deleteCourseBase: function(data) {
      this.$confirm('确定删除？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.loadingVisible = true
        const param = [data.row.id]
        deleteCourseInfos(param).then(() => {
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
    downloadTemplate: function() {
      showFullScreenLoading('模板文件下载中')
      downloadCourseInfoTemplate().then(response => {
        downloadExcel(response, '课程信息导入模板文件.xls')
        hideFullScreenLoading()
      })
        .catch(error => {
          console.log(error)
          hideFullScreenLoading()
        })
    },
    importCourseInfo: function() {
      this.importDialogVisible = true
    },
    exportCourseInfo: function() {
      const searchModel = {}
      if (this.searchData.courseCharacter && this.searchData.courseCharacter !== '') {
        searchModel.courseCharacter = this.searchData.courseCharacter
      }
      if (this.searchData.courseType && this.searchData.courseType !== '') {
        searchModel.courseType = this.searchData.courseType
      }
      if (this.searchData.softHard && this.searchData.softHard !== '') {
        searchModel.softHard = this.searchData.softHard
      }
      if (this.searchData.studentType && this.searchData.studentType !== '') {
        searchModel.studentType = this.searchData.studentType
      }
      if (this.searchData.isBilingual && this.searchData.isBilingual !== '') {
        searchModel.isBilingual = this.searchData.isBilingual
      }
      showFullScreenLoading('导出文件下载中')
      exportCourseInfo(searchModel).then(response => {
        downloadExcel(response, '课程信息导出文件.xlsx')
        hideFullScreenLoading()
      }).catch(error => {
        console.log(error)
        hideFullScreenLoading()
      })
    },
    /**
     * 提交编辑或取消
     * **/
    editEnsureOrCancel: function(isCancel) {
      if (isCancel) {
        this.editDialogVisible = false
        return
      }
      if (this.checkData(this.editForm)) {
        this.loadingVisible = true
        if (this.editForm.id) {
          editCourseBaseInfo(this.editForm, this.editForm.id).then(() => {
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
          addCourseBaseInfo(this.editForm).then(() => {
            this.$message({
              type: 'success',
              message: '添加成功!'
            })
            this.loadingVisible = false
            this.editDialogVisible = false
            this.handleCurrentChange(0)
          }).catch(error => {
            console.log(error)
            this.loadingVisible = false
          })
        }
      } else {
        this.$message({
          type: 'info',
          message: '数据不规范!'
        })
      }
    },
    checkData: function(data) {
      if (data.courseCharacter === undefined ||
        data.courseType === undefined ||
        data.isBilingual === undefined ||
        data.softHard === undefined ||
        data.studentType === undefined) {
        return false
      }
      if (data.courseCode && data.courseCode !== '' &&
        data.courseName && data.courseName !== '' &&
        data.courseCridet && data.courseCridet !== '' &&
        data.totalHours && data.totalHours !== '' &&
        data.institute && data.institute !== '' &&
        data.firstClassTime && data.firstClassTime !== '' &&
        data.newTextbookTime && data.newTextbookTime !== ''
      ) {
        return true
      }
      return false
    },
    /**
     * 文件上传处理
     **/
    handleRemove() {
      this.fileList = []
    },
    handleExceed(files, fileList) {
      this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`)
    },
    getFile(file, fileList) {
      this.fileList = fileList
    },
    cancelImportFile() {
      this.fileList = []
      this.importDialogVisible = false
    },
    importFile() {
      if (!this.fileList || !this.fileList.length) {
        this.$notify.error({
          title: '错误',
          message: '请选择上传的文件',
          duration: 2000
        })
        return
      }
      const body = new FormData()
      body.append('file', this.fileList[0].raw)
      showFullScreenLoading('文件上传中')
      uploadCourseInfo(body)
        .then(response => {
          this.$notify.success({
            title: '导入成功',
            message: '课程信息导入成功' + response.data.successCount + '条，导入失败' + response.data.failCount + '条'
          })
          this.importDialogVisible = false
          hideFullScreenLoading()
          this.handleCurrentChange(0)
        })
        .catch(error => {
          console.log(error)
          hideFullScreenLoading()
        })
    }
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

  .selector-year {
    margin-bottom: 20px;
  }

  .selector-term {
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
