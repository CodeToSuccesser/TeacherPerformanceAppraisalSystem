<template>
  <div class="app-container">
    <el-select v-model="value" placeholder="年度" class="selector-year">
      <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
    </el-select>

    <el-select v-model="value" placeholder="学期" class="selector-term">
      <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
    </el-select>

    <el-button type="primary" size="small" class="button-find">查找</el-button>

    <el-button type="primary" size="small" class="button-add" @click="applyCourseHoursVisible = true">申请新增</el-button>

    <el-button v-if="isAdmin" type="primary" size="small" class="button-add" @click="importCourseHour">导入</el-button>
    <el-button v-if="isAdmin" type="primary" size="small" class="button-add" @click="exportCourseHour">导出</el-button>

    <el-table :data="courseHourInfo" stripe style="width: 100% " :border="true" fit>
      <el-table-column :resizable="false" prop="id" sortable label="序号" />
      <el-table-column :resizable="false" prop="courseName" sortable label="课程名称" />
      <el-table-column :resizable="false" prop="selectedStudent" sortable label="已选学生人数" />
      <el-table-column :resizable="false" prop="studentInstitute" sortable label="学生学院" />
      <el-table-column :resizable="false" prop="schoolYear" sortable label="学年" />
      <el-table-column :resizable="false" prop="semester" sortable label="学期" />
      <el-table-column :resizable="false" prop="primarySecondary" sortable label="是否主讲" />
      <el-table-column :resizable="false" label="操作">
        <template>
          <el-button type="text" size="small" @click="courseDetailVisible = true">查看详情</el-button>
          <el-button type="text" size="small" @click="courseDetailEdit">修改</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="课时详情" :visible.sync="courseDetailVisible" top="5vh" :append-to-body="true" :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form :model="courseDetailForm">
        <el-form-item label="课程名称" :label-width="formLabelWidth">
          <el-input v-model="courseDetailForm.courseName" autocomplete="off" :disabled="courseDetailEditDisable" />
        </el-form-item>
        <el-form-item label="课程容量" :label-width="formLabelWidth">
          <el-input v-model="courseDetailForm.totalCapacity" autocomplete="off" :disabled="courseDetailEditDisable" />
        </el-form-item>
        <el-form-item label="已选学生人数" :label-width="formLabelWidth">
          <el-input v-model="courseDetailForm.selectedStudent" autocomplete="off" :disabled="courseDetailEditDisable" />
        </el-form-item>
        <el-form-item label="学生学院" :label-width="formLabelWidth">
          <el-input v-model="courseDetailForm.studentInstitute" autocomplete="off" :disabled="courseDetailEditDisable" />
        </el-form-item>
        <el-form-item label="学年" :label-width="formLabelWidth">
          <el-input v-model="courseDetailForm.studentInstitute" autocomplete="off" :disabled="courseDetailEditDisable" />
        </el-form-item>
        <el-form-item label="学期" :label-width="formLabelWidth">
          <el-input v-model="courseDetailForm.studentInstitute" autocomplete="off" :disabled="courseDetailEditDisable" />
        </el-form-item>
        <el-form-item label="是否主讲" :label-width="formLabelWidth">
          <el-input v-model="courseDetailForm.studentInstitute" autocomplete="off" :disabled="courseDetailEditDisable" />
        </el-form-item>
        <el-form-item label="周学时" :label-width="formLabelWidth">
          <el-input v-model="courseDetailForm.weekHours" autocomplete="off" :disabled="courseDetailEditDisable" />
        </el-form-item>
        <el-form-item label="学期" :label-width="formLabelWidth">
          <el-input v-model="courseDetailForm.semester" autocomplete="off" :disabled="courseDetailEditDisable" />
        </el-form-item>
        <el-form-item label="学年" :label-width="formLabelWidth">
          <el-input v-model="courseDetailForm.schoolYear" autocomplete="off" :disabled="courseDetailEditDisable" />
        </el-form-item>
        <el-form-item label="讲课学时" :label-width="formLabelWidth">
          <el-input v-model="courseDetailForm.teachingHours" autocomplete="off" :disabled="courseDetailEditDisable" />
        </el-form-item>
        <el-form-item label="上机学时" :label-width="formLabelWidth">
          <el-input v-model="courseDetailForm.computerHours" autocomplete="off" :disabled="courseDetailEditDisable" />
        </el-form-item>
        <el-form-item label="实验学时" :label-width="formLabelWidth">
          <el-input v-model="courseDetailForm.experimentHours" autocomplete="off" :disabled="courseDetailEditDisable" />
        </el-form-item>
        <el-form-item label="实验次数" :label-width="formLabelWidth">
          <el-input v-model="courseDetailForm.expNumber" autocomplete="off" :disabled="courseDetailEditDisable" />
        </el-form-item>
        <el-form-item label="每次实验人数" :label-width="formLabelWidth">
          <el-input v-model="courseDetailForm.expPerNumber" autocomplete="off" :disabled="courseDetailEditDisable" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="courseDetailEditDisable = false">修 改</el-button>
        <el-button type="primary" @click="courseDetailEditEnsureOrCancel">确 定</el-button>
        <el-button @click="courseDetailEditEnsureOrCancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog title="申请新增课时记录" :visible.sync="applyCourseHoursVisible" top="5vh" :append-to-body="true" :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form :model="applyCourseHourForm">
        <el-form-item label="课程名称" :label-width="formLabelWidth">
          <el-input v-model="applyCourseHourForm.courseName" autocomplete="off" />
        </el-form-item>
        <el-form-item label="课程容量" :label-width="formLabelWidth">
          <el-input v-model="applyCourseHourForm.totalCapacity" autocomplete="off" />
        </el-form-item>
        <el-form-item label="已选学生人数" :label-width="formLabelWidth">
          <el-input v-model="applyCourseHourForm.selectedStudent" autocomplete="off" />
        </el-form-item>
        <el-form-item label="学生学院" :label-width="formLabelWidth">
          <el-input v-model="applyCourseHourForm.studentInstitute" autocomplete="off" />
        </el-form-item>
        <el-form-item label="学年" :label-width="formLabelWidth">
          <el-input v-model="applyCourseHourForm.studentInstitute" autocomplete="off" />
        </el-form-item>
        <el-form-item label="学期" :label-width="formLabelWidth">
          <el-input v-model="applyCourseHourForm.studentInstitute" autocomplete="off" />
        </el-form-item>
        <el-form-item label="是否主讲" :label-width="formLabelWidth">
          <el-input v-model="applyCourseHourForm.studentInstitute" autocomplete="off" />
        </el-form-item>
        <el-form-item label="周学时" :label-width="formLabelWidth">
          <el-input v-model="applyCourseHourForm.weekHours" autocomplete="off" />
        </el-form-item>
        <el-form-item label="学期" :label-width="formLabelWidth">
          <el-input v-model="applyCourseHourForm.semester" autocomplete="off" />
        </el-form-item>
        <el-form-item label="学年" :label-width="formLabelWidth">
          <el-input v-model="applyCourseHourForm.schoolYear" autocomplete="off" />
        </el-form-item>
        <el-form-item label="讲课学时" :label-width="formLabelWidth">
          <el-input v-model="applyCourseHourForm.teachingHours" autocomplete="off" />
        </el-form-item>
        <el-form-item label="上机学时" :label-width="formLabelWidth">
          <el-input v-model="applyCourseHourForm.computerHours" autocomplete="off" />
        </el-form-item>
        <el-form-item label="实验学时" :label-width="formLabelWidth">
          <el-input v-model="applyCourseHourForm.experimentHours" autocomplete="off" />
        </el-form-item>
        <el-form-item label="实验次数" :label-width="formLabelWidth">
          <el-input v-model="applyCourseHourForm.expNumber" autocomplete="off" />
        </el-form-item>
        <el-form-item label="每次实验人数" :label-width="formLabelWidth">
          <el-input v-model="applyCourseHourForm.expPerNumber" autocomplete="off" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary">申 请</el-button>
        <el-button @click="applyCourseHoursVisible = false">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
export default {
  data() {
    return {
      courseHourInfo: [{
        date: '2016-05-02',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-04',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1517 弄'
      }, {
        date: '2016-05-01',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1519 弄'
      }, {
        date: '2016-05-03',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1516 弄'
      }],
      dialogTableVisible: false,
      courseDetailVisible: false,
      courseDetailEditDisable: true,
      applyCourseHoursVisible: false,
      courseDetailForm: {
        courseName: '',
        selectedStudent: '',
        studentInstitute: '',
        totalCapacity: '',
        weekHours: '',
        semester: '',
        teachingHours: '',
        computerHours: '',
        experimentHours: '',
        schoolYear: '',
        expNumber: '',
        expPerNumber: ''
      },
      applyCourseHourForm: {
        courseName: '',
        selectedStudent: '',
        studentInstitute: '',
        totalCapacity: '',
        weekHours: '',
        semester: '',
        teachingHours: '',
        computerHours: '',
        experimentHours: '',
        schoolYear: '',
        expNumber: '',
        expPerNumber: ''
      },
      formLabelWidth: '120px',
      isAdmin: this.$store.getters.userType === '' ? sessionStorage.getItem('userType') : this.$store.getters.userType
    }
  },
  methods: {
    courseDetailEditEnsureOrCancel: function() {
      this.courseDetailEditDisable = true
      this.courseDetailVisible = false
    },
    courseDetailEdit: function() {
      this.courseDetailEditDisable = false
      this.courseDetailVisible = true
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
