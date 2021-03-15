<template>
  <div class="app-container">

    <el-select v-model="value" placeholder="年度" class="selector-year">
      <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
    </el-select>

    <el-select v-model="value" placeholder="学期" class="selector-term">
      <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
    </el-select>

    <el-button type="primary" size="small" class="button-find">查找</el-button>

    <el-button type="primary" size="small" class="button-add" @click="applyCourseDialogVisiable = true">新增</el-button>
    <el-button type="primary" size="small" class="button-add">下载导入模板</el-button>
    <el-button type="primary" size="small" class="button-add">导入</el-button>
    <el-button type="primary" size="small" class="button-add">导出</el-button>

    <el-table :data="courseInfo" stripe style="width: 100% " :border="true" fit>
      <el-table-column :resizable="false" prop="id" sortable label="序号" width="100px"/>
      <el-table-column :resizable="false" prop="courseName" sortable label="课程名称" />
      <el-table-column :resizable="false" prop="courseCharacter" sortable label="课程性质" />
      <el-table-column :resizable="false" prop="courseType" sortable label="课程类别" />
      <el-table-column :resizable="false" prop="courseCridet" sortable label="学分" />
      <el-table-column :resizable="false" prop="totalHours" sortable label="总学时数" />
      <el-table-column :resizable="false" prop="institute" sortable label="开课学院" />
      <el-table-column :resizable="false" prop="firstClassTime" sortable label="首次开课时间" />
      <el-table-column :resizable="false" prop="isBilingual" sortable label="是否双语授课" />
      <el-table-column :resizable="false" prop="newTextbookTime" sortable label="选用教材时间" />
      <el-table-column :resizable="false" prop="softHard" sortable label="软硬件课程" />
      <el-table-column :resizable="false" prop="studentType" sortable label="学生类型" />
      <el-table-column :resizable="false" prop="remark" sortable label="备注" />
      <el-table-column :resizable="false" prop="createTime" sortable label="创建日期" />
      <el-table-column :resizable="false" label="操作">
        <template>
          <el-button type="text" size="small" @click="courseDialogVisible = true">修改</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="课程修改" :visible.sync="courseDialogVisible" top="5vh" :append-to-body="true" :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form :model="courseForm">
        <el-form-item label="课程名称" :label-width="formLabelWidth">
          <el-input v-model="courseForm.courseName" autocomplete="off" />
        </el-form-item>
        <el-form-item label="课程性质" :label-width="formLabelWidth">
          <el-input v-model="courseForm.courseCharacter" autocomplete="off" />
        </el-form-item>
        <el-form-item label="课程类别" :label-width="formLabelWidth">
          <el-input v-model="courseForm.courseType" autocomplete="off" />
        </el-form-item>
        <el-form-item label="学分" :label-width="formLabelWidth">
          <el-input v-model="courseForm.courseCridet" autocomplete="off" />
        </el-form-item>
        <el-form-item label="总学时数" :label-width="formLabelWidth">
          <el-input v-model="courseForm.totalHours" autocomplete="off" />
        </el-form-item>
        <el-form-item label="开课学院" :label-width="formLabelWidth">
          <el-input v-model="courseForm.institute" autocomplete="off" />
        </el-form-item>
        <el-form-item label="首次开课时间" :label-width="formLabelWidth">
          <el-input v-model="courseForm.firstClassTime" autocomplete="off" />
        </el-form-item>
        <el-form-item label="是否双语授课" :label-width="formLabelWidth">
          <el-input v-model="courseForm.isBilingual" autocomplete="off" />
        </el-form-item>
        <el-form-item label="选用教材时间" :label-width="formLabelWidth">
          <el-input v-model="courseForm.newTextbookTime" autocomplete="off" />
        </el-form-item>
        <el-form-item label="软硬件课程" :label-width="formLabelWidth">
          <el-input v-model="courseForm.softHard" autocomplete="off" />
        </el-form-item>
        <el-form-item label="学生类型" :label-width="formLabelWidth">
          <el-input v-model="courseForm.studentType" autocomplete="off" />
        </el-form-item>
        <el-form-item label="备注" :label-width="formLabelWidth">
          <el-input v-model="courseForm.remark" autocomplete="off" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="courseEditEnsureOrCancel">确 定</el-button>
        <el-button @click="courseEditEnsureOrCancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog title="新增课程" :visible.sync="applyCourseDialogVisiable" top="5vh" :append-to-body="true" :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form :model="applyCourseForm">
        <el-form-item label="课程名称" :label-width="formLabelWidth">
          <el-input v-model="applyCourseForm.courseName" autocomplete="off" />
        </el-form-item>
        <el-form-item label="课程性质" :label-width="formLabelWidth">
          <el-input v-model="applyCourseForm.courseCharacter" autocomplete="off" />
        </el-form-item>
        <el-form-item label="课程类别" :label-width="formLabelWidth">
          <el-input v-model="applyCourseForm.courseType" autocomplete="off" />
        </el-form-item>
        <el-form-item label="学分" :label-width="formLabelWidth">
          <el-input v-model="applyCourseForm.courseCridet" autocomplete="off" />
        </el-form-item>
        <el-form-item label="总学时数" :label-width="formLabelWidth">
          <el-input v-model="applyCourseForm.totalHours" autocomplete="off" />
        </el-form-item>
        <el-form-item label="开课学院" :label-width="formLabelWidth">
          <el-input v-model="applyCourseForm.institute" autocomplete="off" />
        </el-form-item>
        <el-form-item label="首次开课时间" :label-width="formLabelWidth">
          <el-input v-model="applyCourseForm.firstClassTime" autocomplete="off" />
        </el-form-item>
        <el-form-item label="是否双语授课" :label-width="formLabelWidth">
          <el-input v-model="applyCourseForm.isBilingual" autocomplete="off" />
        </el-form-item>
        <el-form-item label="选用教材时间" :label-width="formLabelWidth">
          <el-input v-model="applyCourseForm.newTextbookTime" autocomplete="off" />
        </el-form-item>
        <el-form-item label="软硬件课程" :label-width="formLabelWidth">
          <el-input v-model="applyCourseForm.softHard" autocomplete="off" />
        </el-form-item>
        <el-form-item label="学生类型" :label-width="formLabelWidth">
          <el-input v-model="applyCourseForm.studentType" autocomplete="off" />
        </el-form-item>
        <el-form-item label="备注" :label-width="formLabelWidth">
          <el-input v-model="applyCourseForm.remark" autocomplete="off" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="applyCourseEditEnsureOrCancel">确 定</el-button>
        <el-button @click="applyCourseEditEnsureOrCancel">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>
<script>
export default {
  name: 'CourseManage',
  data() {
    return {
      courseInfo: [{
        id: ''
      }],
      courseForm: {
        courseNameL: '',
        courseCharacter: '',
        courseType: '',
        courseCridet: '',
        totalHours: '',
        institute: '',
        firstClassTime: '',
        isBilingual: '',
        newTextbookTime: '',
        softHard: '',
        studentType: '',
        remark: ''
      },
      applyCourseForm: {
        courseNameL: '',
        courseCharacter: '',
        courseType: '',
        courseCridet: '',
        totalHours: '',
        institute: '',
        firstClassTime: '',
        isBilingual: '',
        newTextbookTime: '',
        softHard: '',
        studentType: '',
        remark: ''
      },
      courseDialogVisible: false,
      applyCourseDialogVisiable: false,
      formLabelWidth: '120px'
    }
  },
  methods: {
    courseEditEnsureOrCancel: function() {
      this.courseDialogVisible = false
    },
    applyCourseEditEnsureOrCancel: function() {
      this.applyCourseDialogVisiable = false
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

</style>
