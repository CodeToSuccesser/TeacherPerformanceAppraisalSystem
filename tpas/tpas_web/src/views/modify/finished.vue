<template>
  <div class="app-container">
    <el-tabs v-model="activeName">
      <el-tab-pane label="课程课时修改" name="courseHour">
        <el-form ref="form" :model="courseSearchForm">
          <el-select v-model="courseSearchForm.selectedSchoolYear" placeholder="学年" class="selector-year">
            <el-option v-for="item in schoolYearOptions" :key="item.key" :label="item.key" :value="item.value" />
          </el-select>
          <el-select v-model="courseSearchForm.selectedSemester" placeholder="学期" class="selector-term">
            <el-option v-for="item in semesterOptions" :key="item.key" :label="item.key" :value="item.value" />
          </el-select>
          <el-button type="primary" size="small" class="button-find">查找</el-button>
        </el-form>
        <el-table :data="courseHoursModifyRecord" style="width: 100% " :border="true">
          <el-table-column type="expand" align="center">
            <template slot-scope="props">
              <el-form label-position="left" inline class="demo-table-expand">
                <el-form-item label="课程编号">
                  <span>{{ props.row.courseCode }}</span>
                </el-form-item>
                <el-form-item label="开课学院">
                  <span>{{ props.row.institute }}</span>
                </el-form-item>
                <el-divider />
                <el-form-item label="已选学生人数">
                  <span>{{ props.row.selectedStudent }}</span>
                </el-form-item>
                <el-form-item label="已选学生人数修改">
                  <span>{{ props.row.modifySelectedStudent }}</span>
                </el-form-item>
                <el-form-item label="讲课学时">
                  <span>{{ props.row.teachingHours }}</span>
                </el-form-item>
                <el-form-item label="讲课学时修改">
                  <span>{{ props.row.modifyTeachingHours }}</span>
                </el-form-item>
                <el-form-item label="上机学时">
                  <span>{{ props.row.computerHours }}</span>
                </el-form-item>
                <el-form-item label="上机学时修改">
                  <span>{{ props.row.modifyComputerHours }}</span>
                </el-form-item>
                <el-form-item label="实验学时">
                  <span>{{ props.row.experimentHours }}</span>
                </el-form-item>
                <el-form-item label="实验学时修改">
                  <span>{{ props.row.modifyExperimentHours }}</span>
                </el-form-item>
                <el-form-item label="总学时">
                  <span>{{ props.row.totalHours }}</span>
                </el-form-item>
                <el-form-item label="总学时修改">
                  <span>{{ props.row.modifyTotalHours }}</span>
                </el-form-item>
                <el-form-item label="学分">
                  <span>{{ props.row.courseCridet }}</span>
                </el-form-item>
                <el-form-item label="学分修改">
                  <span>{{ props.row.modifyCourseCridet }}</span>
                </el-form-item>
                <el-form-item label="起止周">
                  <span>{{ props.row.peroid }}</span>
                </el-form-item>
                <el-form-item label="起止周修改">
                  <span>{{ props.row.modifyPeroid }}</span>
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

          <el-table-column label="序号" prop="id" align="center">
            <template slot-scope="scope">
              <span>{{ (coursePageSize - 1) * (courseCurPageNum - 1) + scope.$index + 1 }}</span>
            </template>
          </el-table-column>
          <el-table-column label="课程名称" prop="courseName" align="center" />
          <el-table-column label="学年" prop="schoolYear" align="center" />
          <el-table-column label="学期" prop="semester" align="center" />
          <el-table-column label="申请时间" prop="createTime" align="center" />
          <el-table-column label="审核时间" prop="checkTime" align="center" />
          <el-table-column label="审核结果" prop="checkResult" align="center" />
        </el-table>

      </el-tab-pane>

      <el-tab-pane label="论文指导修改" name="paper">
        <el-form ref="form" :model="paperSearchForm">
          <el-select v-model="paperSearchForm.selectedSchoolYear" placeholder="学年" class="selector-year">
            <el-option v-for="item in schoolYearOptions" :key="item.key" :label="item.key" :value="item.value" />
          </el-select>
          <el-select v-model="paperSearchForm.selectedSemester" placeholder="学期" class="selector-term">
            <el-option v-for="item in semesterOptions" :key="item.key" :label="item.key" :value="item.value" />
          </el-select>
          <el-button type="primary" size="small" class="button-find">查找</el-button>
        </el-form>
        <el-table :data="paperModifyRecord" stripe style="width: 100% " :border="true" fit>
          <el-table-column type="expand" align="center">
            <template slot-scope="props">
              <el-form label-position="left" inline class="demo-table-expand">
                <el-form-item label="专业代码号">
                  <span>{{ props.row.majorCode }}</span>
                </el-form-item>
                <el-form-item label="专业名称">
                  <span>{{ props.row.majorName }}</span>
                </el-form-item>
                <el-divider />
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
          <el-table-column label="序号" prop="id" width="110px" align="center">
            <template slot-scope="scope">
              <span>{{ (paperPageSize - 1) * (paperCurPageNum - 1) + scope.$index + 1 }}</span>
            </template>
          </el-table-column>
          <el-table-column label="专业代码号" prop="majorCode" align="center"/>
          <el-table-column label="专业名称" prop="majorName" align="center"/>
          <el-table-column label="学年" prop="schoolYear" align="center"/>
          <el-table-column label="学期" prop="semester" align="center"/>
          <el-table-column label="申请时间" prop="createTime" align="center"/>
          <el-table-column label="审核结果" prop="checkResult" align="center"/>
          <el-table-column label="审核时间" prop="checkTime" align="center"/>
        </el-table>

      </el-tab-pane>

      <el-tab-pane label="实习带队修改" name="intern">
        <el-form ref="form" :model="internSearchForm">

          <el-select v-model="internSearchForm.selectedSchoolYear" placeholder="学年" class="selector-year">
            <el-option v-for="item in schoolYearOptions" :key="item.key" :label="item.key" :value="item.value" />
          </el-select>
          <el-select v-model="internSearchForm.selectedSemester" placeholder="学期" class="selector-term">
            <el-option v-for="item in semesterOptions" :key="item.key" :label="item.key" :value="item.value" />
          </el-select>
          <el-button type="primary" size="small" class="button-find">查找</el-button>
        </el-form>

        <el-table :data="internModifyRecord" stripe style="width: 100% " :border="true" fit>
          <el-table-column type="expand" align="center">
            <template slot-scope="props">
              <el-form label-position="left" inline class="demo-table-expand">
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
          <el-table-column label="序号" prop="id" width="110px" align="center">
            <template slot-scope="scope">
              <span>{{ (internPageSize - 1) * (internCurPageNum - 1) + scope.$index + 1 }}</span>
            </template>
          </el-table-column>
          <el-table-column label="学年" prop="schoolYear" align="center"/>
          <el-table-column label="学期" prop="semester" align="center"/>
          <el-table-column label="申请时间" prop="createTime" align="center"/>
          <el-table-column label="审核结果" prop="checkResult" align="center"/>
          <el-table-column label="审核时间" prop="checkTime" align="center"/>
        </el-table>
      </el-tab-pane>
    </el-tabs>

  </div>
</template>

<script>
import { getCourseHoursModifyRecord } from '@/api/course'
import { getPaperModifyRecord } from '@/api/paper'
import { getInternModifyRecord } from '@/api/intern'

export default {
  name: 'Finished',
  data() {
    return {
      courseTotal: 0,
      coursePageSize: 25,
      courseCurPageNum: 1,
      courseSearchForm: {
        selectedSchoolYear: '',
        selectedSemester: '',
        selectedCourseName: '',
        selectedStudentInstitute: ''
      },
      internTotal: 0,
      internPageSize: 25,
      internCurPageNum: 1,
      internSearchForm: {
        selectedSchoolYear: '',
        selectedSemester: '',
        selectedCourseName: '',
        selectedStudentInstitute: ''
      },
      paperTotal: 0,
      paperPageSize: 25,
      paperCurPageNum: 1,
      paperSearchForm: {
        selectedSchoolYear: '',
        selectedSemester: '',
        selectedCourseName: '',
        selectedStudentInstitute: ''
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
      courseHoursModifyRecord: [],
      paperModifyRecord: [],
      internModifyRecord: [],
      activeName: 'courseHour'
    }
  },
  created() {
    this.getCourseModifyRecord()
    this.getPaperModifyRecord()
    this.getInternModifyRecord()
  },
  methods: {
    getCourseModifyRecord: function() {
      const body = {
        checkResult: 1
      }
      getCourseHoursModifyRecord(body)
        .then(response => {
          const { data } = response
          this.courseHoursModifyRecord = data.list
          this.courseTotal = data.total
        }).catch(error => {
          console.log(error)
        })
    },
    getPaperModifyRecord: function() {
      const body = {
        checkResult: 1
      }
      getPaperModifyRecord(body)
        .then(response => {
          const { data } = response
          this.paperModifyRecord = data.list
          this.paperTotal = data.total
        }).catch(error => {
          console.log(error)
        })
    },
    getInternModifyRecord: function() {
      const body = {
        checkResult: 1
      }
      getInternModifyRecord(body)
        .then(response => {
          const { data } = response
          this.internModifyRecord = data.list
          this.internTotal = data.total
        }).catch(error => {
          console.log(error)
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
