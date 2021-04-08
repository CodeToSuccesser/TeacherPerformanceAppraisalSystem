<template>
  <div class="app-container">
    <el-form ref="form" :model="searchForm">

      <el-input v-model="searchForm.majorName" placeholder="专业名称" clearable class="selector" style="width: 200px" />
      <el-input v-model="searchForm.majorCode" placeholder="专业编码" clearable class="selector" style="width: 200px" />

      <el-button type="primary" size="small" class="button-find" @click="searchMajor">查找</el-button>

      <el-col>
        <el-button type="primary" size="small" class="button-add" @click="applyMajorDialogVisible = true">新增</el-button>
        <el-button type="primary" size="small" class="button-add" @click="downloadTemplate">下载导入模板</el-button>
        <el-button type="primary" size="small" class="button-add" @click="importMajor">导入</el-button>
        <el-button type="primary" size="small" class="button-add" @click="exportMajor">导出</el-button>
      </el-col>
    </el-form>

    <el-table :data="majorInfo" stripe style="width: 100% " :border="true" fit>
      <el-table-column :resizable="false" prop="id" sortable label="序号" width="100px" align="center" />
      <el-table-column :resizable="false" prop="majorCode" sortable label="专业编码" align="center" />
      <el-table-column :resizable="false" prop="majorName" sortable label="专业名称" align="center" />
      <el-table-column :resizable="false" prop="createTime" sortable label="创建日期" align="center" />
      <el-table-column :resizable="false" label="操作" align="center">
        <template scope="scope">
          <el-button type="text" size="small" @click="modifyMajor(scope)">修改</el-button>
          <el-button type="text" size="small" @click="deleteMajor(scope)">删除</el-button>
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
      title="专业修改"
      :visible.sync="majorDialogVisible"
      top="5vh"
      :append-to-body="true"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
    >
      <el-form :model="majorForm">
        <el-form-item label="专业编码" :label-width="formLabelWidth">
          <el-input v-model="majorForm.majorCode" autocomplete="off" />
        </el-form-item>
        <el-form-item label="专业名称" :label-width="formLabelWidth">
          <el-input v-model="majorForm.majorName" autocomplete="off" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="modifyMajorEditEnsure">确 定</el-button>
        <el-button @click="modifyMajorEditCancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog
      title="新增专业"
      :visible.sync="applyMajorDialogVisible"
      top="5vh"
      :append-to-body="true"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
    >
      <el-form :model="applyMajorForm">
        <el-form-item label="专业编码" :label-width="formLabelWidth">
          <el-input v-model="applyMajorForm.majorCode" autocomplete="off" />
        </el-form-item>
        <el-form-item label="专业名称" :label-width="formLabelWidth">
          <el-input v-model="applyMajorForm.majorName" autocomplete="off" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="applyMajorEditEnsure">确 定</el-button>
        <el-button @click="applyMajorEditCancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog
      title="导入专业信息"
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

import { getMajorInfo, insertMajor, exportMajor, downloadTemplate, modifyMajor, deleteMajor, importMajorFile } from '@/api/major'
import { hideFullScreenLoading, showFullScreenLoading } from '@/utils/loading'
import { downloadExcel } from '@/utils/file'

export default {
  data() {
    return {
      total: 0,
      pageSize: 25,
      curPageNum: 1,
      searchForm: {},
      majorInfo: [],
      majorForm: {
      },
      applyMajorForm: {
      },
      majorDialogVisible: false,
      applyMajorDialogVisible: false,
      formLabelWidth: '120px',
      importDialogVisible: false,
      fileList: []
    }
  },
  created() {
    const data = {
      pageNum: this.pageNum,
      pageSize: this.pageSize
    }
    this.getMajorInfo(data)
  },
  methods: {
    getMajorInfo(body) {
      showFullScreenLoading()
      getMajorInfo(body)
        .then(response => {
          hideFullScreenLoading()
          const { data } = response
          this.majorInfo = data.list
          this.total = data.total
        })
        .catch(error => {
          hideFullScreenLoading()
          console.log(error)
        })
    },
    applyMajorEditEnsure() {
      this.applyMajorDialogVisible = false
      const data = {
        majorCode: this.applyMajorForm.majorCode,
        majorName: this.applyMajorForm.majorName
      }
      showFullScreenLoading()
      insertMajor(data)
        .then(response => {
          hideFullScreenLoading()
          this.$message.success('插入专业信息成功')
          location.reload()
        })
        .catch(error => {
          console.log(error)
          hideFullScreenLoading()
        })
    },
    applyMajorEditCancel() {
      this.applyMajorDialogVisible = false
    },
    modifyMajor(scope) {
      this.majorDialogVisible = true
      this.majorForm = JSON.parse(JSON.stringify(this.majorInfo[scope.$index]))
    },
    modifyMajorEditEnsure() {
      this.majorDialogVisible = false
      showFullScreenLoading()
      modifyMajor(this.majorForm, this.majorForm.id)
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
    modifyMajorEditCancel() {
      this.majorDialogVisible = false
    },
    prePage() {
      const param = {
        pageNum: this.curPageNum - 1,
        pageSize: this.pageSize
      }
      this.getMajorInfo(param)
      this.curPageNum = this.curPageNum - 1
    },
    nextPage() {
      const param = {
        pageNum: this.curPageNum + 1,
        pageSize: this.pageSize
      }
      this.getMajorInfo(param)
      this.curPageNum = this.curPageNum + 1
    },
    handleCurrentChange(val) {
      const param = {
        pageNum: this.curPageNum,
        pageSize: this.pageSize
      }

      this.getMajorInfo(param)
      this.curPageNum = val
    },
    exportMajor() {
      const param = {}
      if (this.searchForm.majorName && this.searchForm.majorName !== '') {
        param.majorName = this.searchForm.majorName
      }
      if (this.searchForm.majorCode && this.searchForm.majorCode !== '') {
        param.majorCode = this.searchForm.majorCode
      }
      showFullScreenLoading('导出文件下载中')
      exportMajor(param)
        .then(response => {
          downloadExcel(response, '专业信息导出文件.xlsx')
          hideFullScreenLoading()
        }).catch(error => {
          console.log(error)
          hideFullScreenLoading()
        })
    },
    searchMajor() {
      const data = {
        majorCode: this.searchForm.majorCode,
        majorName: this.searchForm.majorName,
        pageNum: this.pageNum,
        pageSize: this.pageSize
      }
      this.getMajorInfo(data)
    },
    downloadTemplate() {
      showFullScreenLoading('模板文件下载中')
      downloadTemplate()
        .then(response => {
          downloadExcel(response, '专业信息导入模板文件.xls')
          hideFullScreenLoading()
        })
        .catch(error => {
          console.log(error)
          hideFullScreenLoading()
        })
    },
    importMajor() {
      this.importDialogVisible = true
    },
    deleteMajor(scope) {
      const ids = [scope.row.id]
      this.$confirm('确认删除该专业信息?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        showFullScreenLoading()
        deleteMajor(ids)
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
    getFile(file, fileList) {
      this.fileList = fileList
    },
    handleRemove() {
      this.fileList = []
    },
    handleExceed(files, fileList) {
      this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`)
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
      importMajorFile(body)
        .then(response => {
          this.$notify.success({
            title: '导入成功',
            message: '专业信息导入成功' + response.data.successCount + '条，导入失败' + response.data.failCount + '条'
          })
          this.importDialogVisible = false
          hideFullScreenLoading()
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

  .selector {
    margin-left: 10px;
    margin-bottom: 20px;
  }

  .button-find {
    margin-left: 30px;
  }

  .button-add {
    margin-bottom: 10px;
    margin-left: 10px;
  }

  .pagination {
    margin-top: 20px ;
    float: right ;
    margin-bottom: 20px
  }

</style>
