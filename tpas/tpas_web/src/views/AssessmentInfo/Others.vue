<template>
  <div class="app-container">
    <el-form ref="form" :model="searchForm">
      <el-select v-model="searchForm.selectedSchoolYear" placeholder="学年" class="selector-first">
        <el-option v-for="item in schoolYearOptions" :key="item.value" :label="item.label" :value="item.value" />
      </el-select>

      <el-select v-model="searchForm.selectedSemester" placeholder="学期" class="selector">
        <el-option v-for="item in semesterOptions" :key="item.key" :label="item.key" :value="item.value" />
      </el-select>

      <el-button type="primary" size="small" class="button-find">查找</el-button>

      <el-button type="primary" size="small" class="button-add" @click="applyOtherDialogVisible = true">新增</el-button>
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
      <el-table-column :resizable="false" prop="semester" sortable label="学期" align="center" />
      <el-table-column :resizable="false" prop="remark" sortable label="备注" align="center" />
      <el-table-column :resizable="false" prop="createTime" sortable label="创建日期" align="center" />
      <el-table-column :resizable="false" label="操作" align="center">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="otherEdit(scope)">修改</el-button>
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

    <el-dialog title="其他教务工作信息修改" :visible.sync="otherDialogVisible" top="5vh" :append-to-body="true" :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form :model="otherForm">
        <el-form-item label="工作标题" :label-width="formLabelWidth">
          <el-input v-model="otherForm.majorName" autocomplete="off" :disabled="otherEditDisable" />
        </el-form-item>
        <el-form-item label="工作内容" :label-width="formLabelWidth" type="textarea">
          <el-input v-model="otherForm.content" autocomplete="off" :disabled="otherEditDisable" />
        </el-form-item>
        <el-form-item label="工作评分" :label-width="formLabelWidth">
          <el-input v-model="otherForm.score" autocomplete="off" :disabled="otherEditDisable" />
        </el-form-item>
        <el-form-item label="学期" :label-width="formLabelWidth">
          <el-input v-model="otherForm.semester" autocomplete="off" :disabled="otherEditDisable" />
        </el-form-item>
        <el-form-item label="学年" :label-width="formLabelWidth">
          <el-input v-model="otherForm.schoolYear" autocomplete="off" :disabled="otherEditDisable" />
        </el-form-item>
        <el-form-item label="备注" :label-width="formLabelWidth">
          <el-input v-model="otherForm.remark" autocomplete="off" :disabled="otherEditDisable" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="otherEditEnsureOrCancel">确 定</el-button>
        <el-button @click="otherEditEnsureOrCancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog title="其他教务工作信息新增" :visible.sync="applyOtherDialogVisible" top="5vh" :append-to-body="true" :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form :model="applyOtherForm">
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
          <el-input v-model="applyOtherForm.semester" autocomplete="off" />
        </el-form-item>
        <el-form-item label="学年" :label-width="formLabelWidth">
          <el-input v-model="applyOtherForm.schoolYear" autocomplete="off" />
        </el-form-item>
        <el-form-item label="备注" :label-width="formLabelWidth">
          <el-input v-model="applyOtherForm.remark" autocomplete="off" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary">申 请</el-button>
        <el-button @click="applyOtherDialogVisible = false">取 消</el-button>
      </div>
    </el-dialog>

  </div>

</template>

<script>
export default {
  data() {
    return {
      pageSize: 25,
      curPageNum: 1,
      total: 0,
      otherInfo: [],
      searchForm: {
        selectedSemester: '',
        selectedSchoolYear: ''
      },
      schoolYearOptions: {},
      semesterOptions: [
        {
          key: '第一学期',
          value: 0
        },
        {
          key: '第二学期',
          value: 1
        }
      ],
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
      isAdmin: this.$store.getters.userType === '' ? sessionStorage.getItem('userType') : this.$store.getters.userType
    }
  },
  methods: {
    otherEdit() {

    },
    prePage() {

    },
    nextPage() {

    },
    handleCurrentChange() {

    },
    otherEditEnsureOrCancel() {

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

  .button-add {
    float: right;
  }

  .pagination {
    margin-top: 20px ;
    float: right ;
    margin-bottom: 20px
  }
</style>
