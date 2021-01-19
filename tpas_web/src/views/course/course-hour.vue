<template>
  <div class="app-container">
    <el-select v-model="value" placeholder="年度" class="selector-year">
      <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
    </el-select>

    <el-select v-model="value" placeholder="学期" class="selector-term">
      <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
    </el-select>

    <el-button type="primary" size="small" class="button-find">查找</el-button>

    <el-button type="primary" size="small" class="button-add">申请新增</el-button>

    <el-table :data="tableData" stripe style="width: 100% " :border="true" fit>
      <el-table-column :resizable="false" prop="id" sortable label="序号" width="75px" />
      <el-table-column :resizable="false" prop="courseName" sortable label="课程名称" width="160px" />
      <el-table-column :resizable="false" prop="selectedStudent" sortable label="已选学生人数" width="150px" />
      <el-table-column :resizable="false" prop="studentInstitute" sortable label="学生学院" width="150px" />
      <el-table-column :resizable="false" prop="schoolYear" sortable label="学年" width="150px" />
      <el-table-column :resizable="false" prop="semester" sortable label="学期" width="150px" />
      <el-table-column :resizable="false" prop="primarySecondary" sortable label="是否主讲" width="105px" />

      <el-table-column fixed="right" label="详情">
        <template>
          <el-button type="text" size="small" @click="courseDetail">查看详情</el-button>
        </template>
      </el-table-column>

      <el-table-column fixed="right" label="操作">
        <template>
          <el-button type="text" size="small">修改</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--    <el-dialog title="课时详情" :visible.sync="dialogTableVisible" height="81vh" custom-class="courseHourDialog">-->
    <!--      <el-scrollbar>-->
    <!--        <el-form size="small" status-icon label-width="100px" class="demo-ruleForm">-->
    <!--          <el-form-item label="密码" prop="pass">-->
    <!--            <el-input style="width: 50px" autocomplete="off"></el-input>-->
    <!--          </el-form-item>-->
    <!--          <el-form-item label="确认密码" prop="checkPass">-->
    <!--            <el-input autocomplete="off"></el-input>-->
    <!--          </el-form-item>-->
    <!--          <el-form-item label="年龄" prop="age">-->
    <!--            <el-input></el-input>-->
    <!--          </el-form-item>-->
    <!--          <el-form-item label="年龄" prop="age">-->
    <!--            <el-input></el-input>-->
    <!--          </el-form-item>-->
    <!--          <el-form-item label="年龄" prop="age">-->
    <!--            <el-input></el-input>-->
    <!--          </el-form-item>-->
    <!--          <el-form-item label="年龄" prop="age">-->
    <!--            <el-input></el-input>-->
    <!--          </el-form-item>-->
    <!--          <el-form-item label="年龄" prop="age">-->
    <!--            <el-input></el-input>-->
    <!--          </el-form-item>-->
    <!--          <el-form-item label="年龄" prop="age">-->
    <!--            <el-input></el-input>-->
    <!--          </el-form-item>-->
    <!--          <el-form-item label="年龄" prop="age">-->
    <!--            <el-input></el-input>-->
    <!--          </el-form-item>-->
    <!--          <el-form-item label="年龄" prop="age">-->
    <!--            <el-input></el-input>-->
    <!--          </el-form-item>-->
    <!--          <el-form-item label="年龄" prop="age">-->
    <!--            <el-input></el-input>-->
    <!--          </el-form-item>-->
    <!--          <el-form-item>-->
    <!--            <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>-->
    <!--            <el-button @click="resetForm('ruleForm')">重置</el-button>-->
    <!--          </el-form-item>-->
    <!--        </el-form>-->
    <!--      </el-scrollbar>-->
    <!--      &lt;!&ndash;      <el-table>&ndash;&gt;-->
    <!--      &lt;!&ndash;        <el-table-column :resizable="false" prop="studentNumber" sortable label="课程人数" width="105px"></el-table-column>&ndash;&gt;-->
    <!--      &lt;!&ndash;        <el-table-column :resizable="false" prop="totalCapacity" sortable label="课程总容量" width="105px"></el-table-column>&ndash;&gt;-->
    <!--      &lt;!&ndash;        <el-table-column :resizable="false" prop="weekHours" sortable label="周学时" width="105px"></el-table-column>&ndash;&gt;-->
    <!--      &lt;!&ndash;        <el-table-column :resizable="false" prop="period" sortable label="起止周" width="105px"></el-table-column>&ndash;&gt;-->
    <!--      &lt;!&ndash;        <el-table-column :resizable="false" prop="classed" sortable label="教学班组成" width="105px"></el-table-column>&ndash;&gt;-->
    <!--      &lt;!&ndash;        <el-table-column :resizable="false" prop="totalCapacity" sortable label="课程总容量" width="105px"></el-table-column>&ndash;&gt;-->

    <!--      &lt;!&ndash;        <el-table-column :resizable="false" prop="teachingHours" sortable label="讲课学时" width="105px"></el-table-column>&ndash;&gt;-->
    <!--      &lt;!&ndash;        <el-table-column :resizable="false" prop="computerHours" sortable label="上机学时" width="105px"></el-table-column>&ndash;&gt;-->
    <!--      &lt;!&ndash;        <el-table-column :resizable="false" prop="experimentHours" sortable label="实验学时"&ndash;&gt;-->
    <!--      &lt;!&ndash;                         width="105px"></el-table-column>&ndash;&gt;-->
    <!--      &lt;!&ndash;        <el-table-column :resizable="false" prop="expNumber" sortable label="实验批数" width="105px"></el-table-column>&ndash;&gt;-->
    <!--      &lt;!&ndash;        <el-table-column :resizable="false" prop="expPerNumber" sortable label="实验每批人数" width="105px"></el-table-column>&ndash;&gt;-->

    <!--      &lt;!&ndash;      </el-table>&ndash;&gt;-->
    <!--    </el-dialog>-->
  </div>
</template>

<script>
export default {
  data() {
    return {
      tableData: [{
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
      dialogFormVisible: false
    }
  },
  methods: {
    courseDetail: function() {
      this.$router.push('course-hour-detail')
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

.courseHourDialog {
  width: 70%;
  height: 80%;
  top: 5vh;
}
</style>
