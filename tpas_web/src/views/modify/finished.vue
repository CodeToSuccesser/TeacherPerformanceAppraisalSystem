<template>
  <div class="app-container">
    <el-tabs v-model="activeName">
      <el-tab-pane label="课程课时修改" name="courseHour">
        <el-select v-model="value" placeholder="年度" class="selector-year">
          <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
        <el-select v-model="value" placeholder="学期" class="selector-term">
          <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
        <el-button type="primary" size="small" class="button-find">查找</el-button>

        <el-table :data="courseHoursModifyRecord" style="width: 100% " :border="true">
          <el-table-column type="expand">
            <template slot-scope="props">
              <el-form label-position="left" inline class="demo-table-expand">
                <el-form-item label="选课编号">
                  <span>{{ props.row.selectionNumber }}</span>
                </el-form-item>
                <el-form-item label="课程总容量">
                  <span>{{ props.row.totalCapacity }}</span>
                </el-form-item>
                <el-form-item label="讲课学时">
                  <span>{{ props.row.teachingHours }}</span>
                </el-form-item>
                <el-form-item label="课程人数">
                  <span>{{ props.row.studentNumber }}</span>
                </el-form-item>
                <el-form-item label="上机学时">
                  <span>{{ props.row.computerHours }}</span>
                </el-form-item>
                <el-form-item label="已选学生人数">
                  <span>{{ props.row.selectStudent }}</span>
                </el-form-item>
                <el-form-item label="实验学时">
                  <span>{{ props.row.experimentHours }}</span>
                </el-form-item>
                <el-form-item label="实验批数">
                  <span>{{ props.row.expNumber }}</span>
                </el-form-item>
                <el-form-item label="是否主讲">
                  <span>{{ props.row.primarySecondary }}</span>
                </el-form-item>
                <el-form-item label="每批实验人数">
                  <span>{{ props.row.expPerNumber }}</span>
                </el-form-item>
                <el-divider></el-divider>
                <el-form-item label="学时">
                  <span>{{ props.row.courseCridet }}</span>
                </el-form-item>
                <el-form-item label="学时修改">
                  <span>{{ props.row.modifyCourseCridet }}</span>
                </el-form-item>
                <el-form-item label="总学时">
                  <span>{{ props.row.totalHours }}</span>
                </el-form-item>
                <el-form-item label="总学时修改">
                  <span>{{ props.row.modifyTotalHours }}</span>
                </el-form-item>
                <el-form-item label="起止周">
                  <span>{{ props.row.period }}</span>
                </el-form-item>
                <el-form-item label="起止周修改">
                  <span>{{ props.row.modifyPeriod }}</span>
                </el-form-item>
                <el-form-item label="教学班">
                  <span>{{ props.row.classed }}</span>
                </el-form-item>
                <el-form-item label="教学班修改">
                  <span>{{ props.row.modifyClassed }}</span>
                </el-form-item>
              </el-form>
            </template>
          </el-table-column>

          <el-table-column label="序号" prop="id" />
          <el-table-column label="课时信息编号" prop="hoursId" />
          <el-table-column label="课程名称" prop="courseName" />
          <el-table-column label="课程类别" prop="courseType" />
          <el-table-column label="学年" prop="schoolYear" />
          <el-table-column label="学期" prop="semester" />
          <el-table-column label="学生学院" prop="studentsInstitute" />
          <el-table-column label="上课时间" prop="courseTime" />
          <el-table-column label="申请时间" prop="createTime" />
          <el-table-column label="审核时间" prop="checkTime" />
          <el-table-column label="审核结果" prop="checkResult" />
        </el-table>

      </el-tab-pane>

      <el-tab-pane label="论文指导修改" name="paper">
        <el-select v-model="value" placeholder="年度" class="selector-year">
          <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
        <el-select v-model="value" placeholder="学期" class="selector-term">
          <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
        <el-button type="primary" size="small" class="button-find">查找</el-button>

        <el-table :data="paperModifyRecord" stripe style="width: 100% " :border="true" fit class="score-table">
          <el-table-column type="expand">
            <template slot-scope="props">
              <el-form label-position="left" inline class="demo-table-expand">
                <el-form-item label="专业代码号">
                  <span>{{ props.row.majorCode }}</span>
                </el-form-item>
                <el-form-item label="专业名称">
                  <span>{{ props.row.majorName }}</span>
                </el-form-item>
                <el-divider></el-divider>
                <el-form-item label="学期">
                  <span>{{ props.row.semester }}</span>
                </el-form-item>
                <el-form-item label="学期修改">
                  <span>{{ props.row.modifySemester }}</span>
                </el-form-item>
                <el-form-item label="学年">
                  <span>{{ props.row.schoolYear }}</span>
                </el-form-item>
                <el-form-item label="学年修改">
                  <span>{{ props.row.modifySchoolYear }}</span>
                </el-form-item>
                <el-form-item label="专业信息编码">
                  <span>{{ props.row.majorId }}</span>
                </el-form-item>
                <el-form-item label="专业信息编码修改">
                  <span>{{ props.row.modifyMajorId }}</span>
                </el-form-item>
                <el-form-item label="指导学生人数">
                  <span>{{ props.row.studentNumber }}</span>
                </el-form-item>
                <el-form-item label="指导学生人数修改">
                  <span>{{ props.row.modifyStudentNumber }}</span>
                </el-form-item>
              </el-form>
            </template>
          </el-table-column>
          <el-table-column label="序号" prop="id" width="110px" />
          <el-table-column label="专业代码号" prop="majorCode" />
          <el-table-column label="专业名称" prop="majorName" />
          <el-table-column label="学年" prop="schoolYear" />
          <el-table-column label="学期" prop="semester" />
          <el-table-column label="申请时间" prop="createTime" />
          <el-table-column label="审核结果" prop="checkResult" />
          <el-table-column label="审核时间" prop="checkTime" />
        </el-table>

      </el-tab-pane>

      <el-tab-pane label="实习带队修改" name="intern">
        <el-select v-model="value" placeholder="年度" class="selector-year">
          <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
        <el-select v-model="value" placeholder="学期" class="selector-term">
          <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
        <el-button type="primary" size="small" class="button-find">查找</el-button>

        <el-table :data="internModifyRecord" stripe style="width: 100% " :border="true" fit class="score-table">
          <el-table-column type="expand">
            <template slot-scope="props">
              <el-form label-position="left" inline class="demo-table-expand">
                <el-form-item label="序号">
                  <span>{{ props.row.id }}</span>
                </el-form-item>
                <el-form-item label="实习指导信息编码">
                  <span>{{ props.row.internId }}</span>
                </el-form-item>
                <el-divider></el-divider>
                <el-form-item label="学期">
                  <span>{{ props.row.semester }}</span>
                </el-form-item>
                <el-form-item label="学期修改">
                  <span>{{ props.row.modifySemester }}</span>
                </el-form-item>
                <el-form-item label="学年">
                  <span>{{ props.row.schoolYear }}</span>
                </el-form-item>
                <el-form-item label="学年修改">
                  <span>{{ props.row.modifySchoolYear }}</span>
                </el-form-item>
                <el-form-item label="师范实习带队人数">
                  <span>{{ props.row.normalPractice }}</span>
                </el-form-item>
                <el-form-item label="师范实习带队人数修改">
                  <span>{{ props.row.modifyNormalPractice }}</span>
                </el-form-item>
                <el-form-item label="非师范实习带队人数">
                  <span>{{ props.row.nonNormalPractice }}</span>
                </el-form-item>
                <el-form-item label="非师范实习带队人数修改">
                  <span>{{ props.row.modifyNonNormalPractice }}</span>
                </el-form-item>
                <el-form-item label="校内实习带队人数">
                  <span>{{ props.row.schoolPractice }}</span>
                </el-form-item>
                <el-form-item label="校内实习带队人数修改">
                  <span>{{ props.row.modifySchoolPractice }}</span>
                </el-form-item>
              </el-form>
            </template>
          </el-table-column>
          <el-table-column label="序号" prop="id" width="110px" />
          <el-table-column label="实习指导信息编码" prop="internId" />
          <el-table-column label="学年" prop="schoolYear" />
          <el-table-column label="学期" prop="semester" />
          <el-table-column label="申请时间" prop="createTime" />
          <el-table-column label="审核结果" prop="checkResult" />
          <el-table-column label="审核时间" prop="checkTime" />
        </el-table>
      </el-tab-pane>
    </el-tabs>

  </div>
</template>

<script>
export default {
  name: 'Finished',
  data() {
    return {
      courseHoursModifyRecord: [{
        id: 1,
        selectionNumber: 0
      },
      {
        id: 2,
        selectionNumber: 0
      }],
      paperModifyRecord: [{
        id: ''
      }],
      internModifyRecord: [{
        id: ''
      }],
      activeName: 'courseHour'
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

  .score-table{
    margin-top: 15px;
  }

  .demo-table-expand {
    font-size: 0;
  }
  .demo-table-expand label {
    width: 90px;
    color: #99a9bf;
  }
  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 50%;
  }
</style>
