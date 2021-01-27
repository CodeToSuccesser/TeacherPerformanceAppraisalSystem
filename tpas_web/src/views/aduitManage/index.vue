<template>
  <div class="app-container">
    <el-tabs v-model="activeName">
      <el-tab-pane label="课程课时操作" name="courseHour">
        <el-select v-model="value" placeholder="年度" class="selector-year">
          <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
        <el-select v-model="value" placeholder="学期" class="selector-term">
          <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
        <el-input v-model="value" placeholder="教师姓名" class="input-name" />
        <el-button type="primary" size="small" class="button-find">查找</el-button>

        <el-table :data="courseHoursModifyRecord" style="width: 100% " :border="true">
          <el-table-column label="序号" prop="id" />
          <el-table-column label="课时信息编号" prop="hoursId" />
          <el-table-column label="课程名称" prop="courseName" />
          <el-table-column label="课程类别" prop="courseType" />
          <el-table-column label="学年" prop="schoolYear" />
          <el-table-column label="学期" prop="semester" />
          <el-table-column label="学生学院" prop="studentsInstitute" />
          <el-table-column label="上课时间" prop="courseTime" />
          <el-table-column label="学时修改" prop="modifyCourseCridet" />
          <el-table-column label="总学时修改" prop="modifyTotalHours" />
          <el-table-column label="起止周修改" prop="modifyPeriod" />
          <el-table-column label="教学班修改" prop="modifyClassed" />
          <el-table-column label="申请时间" prop="createTime" />
          <el-table-column label="审核时间" prop="checkTime" />
          <el-table-column label="审核结果" prop="checkResult" />
          <el-table-column :resizable="false" label="操作">
            <template slot-scope="props">
              <el-button type="text" size="small" @click="modifyEdit(0, props.id)">{{ props.row.checkResult===0? '处理' : '查看' }}</el-button>
            </template>
          </el-table-column>
        </el-table>

        <el-dialog title="课程课时操作处理" :visible.sync="courseModifyEditVisible" top="5vh" :append-to-body="true" :close-on-click-modal="false" :close-on-press-escape="false">
          <div class="dialog-content-item">
            <span class="span-title">操作类型:</span>
            <span class="span-content">{{ applyType[courseDetailForm.applyType] }} - {{ modifyType[0] }}</span>
          </div>
          <div class="dialog-content-item">
            <span class="span-title">日期:</span>
            <span class="span-content">{{ courseDetailForm.createTime }}</span>
          </div>
          <div class="dialog-content-item">
            <span class="span-title">申请人:</span>
            <span class="span-content">{{ courseDetailForm.applyName }}</span>
          </div>
          <div class="dialog-content-item">
            <span class="span-title">处理人:</span>
            <span class="span-content">{{ courseDetailForm.checkName }}</span>
          </div>
          <div class="dialog-content-item">
            <span class="span-title">申请状态:</span>
            <span class="span-content">{{ courseDetailForm.checkResult }}</span>
          </div>
          <div v-if="courseDetailForm.checkResult===0" slot="footer" class="dialog-footer">
            <el-button type="danger" @click="editEnsureOrCancel(0, false)">不通过</el-button>
            <el-button type="primary" @click="editEnsureOrCancel(0, true)">通 过</el-button>
          </div>
          <div v-else slot="footer" class="dialog-footer">
            <el-button type="primary" @click="editEnsureOrCancel(0, true)">确 定</el-button>
          </div>
        </el-dialog>

      </el-tab-pane>

      <el-tab-pane label="论文指导操作" name="paper">
        <el-select v-model="value" placeholder="年度" class="selector-year">
          <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
        <el-select v-model="value" placeholder="学期" class="selector-term">
          <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
        <el-input v-model="value" placeholder="教师姓名" class="input-name" />
        <el-button type="primary" size="small" class="button-find">查找</el-button>

        <el-table :data="paperModifyRecord" stripe style="width: 100% " :border="true" fit class="score-table">
          <el-table-column label="序号" prop="id" width="110px" />
          <el-table-column label="专业代码号" prop="majorCode" />
          <el-table-column label="专业名称" prop="majorName" />
          <el-table-column label="学年修改" prop="modifySchoolYear" />
          <el-table-column label="学期修改" prop="modifySemester" />
          <el-table-column label="专业信息编码修改" prop="modifyMajorId" />
          <el-table-column label="指导学生人数修改" prop="modifyStudentNumber" />
          <el-table-column label="申请时间" prop="createTime" />
          <el-table-column label="审核结果" prop="checkResult" />
          <el-table-column label="审核时间" prop="checkTime" />
          <el-table-column :resizable="false" label="操作">
            <template slot-scope="props">
              <el-button type="text" size="small" @click="modifyEdit(1, props.id)">{{ props.row.checkResult===0? '处理' : '查看' }}</el-button>
            </template>
          </el-table-column>
        </el-table>

        <el-dialog title="论文指导操作处理" :visible.sync="paperModifyEditVisible" top="5vh" :append-to-body="true" :close-on-click-modal="false" :close-on-press-escape="false">
          <div class="dialog-content-item">
            <span class="span-title">操作类型:</span>
            <span class="span-content">{{ applyType[paperDetailForm.applyType] }} - {{ modifyType[0] }}</span>
          </div>
          <div class="dialog-content-item">
            <span class="span-title">日期:</span>
            <span class="span-content">{{ paperDetailForm.createTime }}</span>
          </div>
          <div class="dialog-content-item">
            <span class="span-title">申请人:</span>
            <span class="span-content">{{ paperDetailForm.applyName }}</span>
          </div>
          <div class="dialog-content-item">
            <span class="span-title">处理人:</span>
            <span class="span-content">{{ paperDetailForm.checkName }}</span>
          </div>
          <div class="dialog-content-item">
            <span class="span-title">申请状态:</span>
            <span class="span-content">{{ paperDetailForm.checkResult }}</span>
          </div>
          <div v-if="paperDetailForm.checkResult===0" slot="footer" class="dialog-footer">
            <el-button type="danger" @click="editEnsureOrCancel(2, false)">不通过</el-button>
            <el-button type="primary" @click="editEnsureOrCancel(2, true)">通 过</el-button>
          </div>
          <div v-else slot="footer" class="dialog-footer">
            <el-button type="primary" @click="editEnsureOrCancel(2, true)">确 定</el-button>
          </div>
        </el-dialog>

      </el-tab-pane>

      <el-tab-pane label="实习带队操作" name="intern">
        <el-select v-model="value" placeholder="年度" class="selector-year">
          <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
        <el-select v-model="value" placeholder="学期" class="selector-term">
          <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
        <el-input v-model="value" placeholder="教师姓名" class="input-name" />
        <el-button type="primary" size="small" class="button-find">查找</el-button>

        <el-table :data="internModifyRecord" stripe style="width: 100% " :border="true" fit class="score-table">
          <el-table-column label="序号" prop="id" width="110px" />
          <el-table-column label="实习指导信息编码" prop="internId" />
          <el-table-column label="学年修改" prop="modifySchoolYear" />
          <el-table-column label="学期修改" prop="modifySemester" />
          <el-table-column label="师范实习带队人数修改" prop="modifyNormalPractice" />
          <el-table-column label="非师范实习带队人数修改" prop="modifyNonNormalPractice" />
          <el-table-column label="校内实习带队人数修改" prop="modifySchoolPractice" />
          <el-table-column label="申请时间" prop="createTime" />
          <el-table-column label="审核结果" prop="checkResult" />
          <el-table-column label="审核时间" prop="checkTime" />
          <el-table-column :resizable="false" label="操作">
            <template slot-scope="props">
              <el-button type="text" size="small" @click="modifyEdit(2, props.id)">{{ props.row.checkResult===0? '处理' : '查看' }}</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

      <el-dialog title="实习带队操作处理" :visible.sync="internModifyEditVisible" top="5vh" :append-to-body="true" :close-on-click-modal="false" :close-on-press-escape="false">
        <div class="dialog-content-item">
          <span class="span-title">操作类型:</span>
          <span class="span-content">{{ applyType[internDetailForm.applyType] }} - {{ modifyType[0] }}</span>
        </div>
        <div class="dialog-content-item">
          <span class="span-title">日期:</span>
          <span class="span-content">{{ internDetailForm.createTime }}</span>
        </div>
        <div class="dialog-content-item">
          <span class="span-title">申请人:</span>
          <span class="span-content">{{ internDetailForm.applyName }}</span>
        </div>
        <div class="dialog-content-item">
          <span class="span-title">处理人:</span>
          <span class="span-content">{{ internDetailForm.checkName }}</span>
        </div>
        <div class="dialog-content-item">
          <span class="span-title">申请状态:</span>
          <span class="span-content">{{ internDetailForm.checkResult }}</span>
        </div>
        <div v-if="internDetailForm.checkResult===0" slot="footer" class="dialog-footer">
          <el-button type="danger" @click="editEnsureOrCancel(2, false)">不通过</el-button>
          <el-button type="primary" @click="editEnsureOrCancel(2, true)">通 过</el-button>
        </div>
        <div v-else slot="footer" class="dialog-footer">
          <el-button type="primary" @click="editEnsureOrCancel(2, true)">确 定</el-button>
        </div>
      </el-dialog>

    </el-tabs>

  </div>
</template>

<script>
export default {
  name: 'AuditRecord',
  data() {
    return {
      activeName: 'courseHour',
      courseHoursModifyRecord: [{
        id: 1,
        selectionNumber: 0,
        modifyWeekHours: '',
        modifySemester: '',
        modifySchoolYear: '',
        modifyTeachingHours: '',
        modifyComputerHours: '',
        modifyExperimentHours: '',
        modifyExpNumber: '',
        modifyExpPerNumber: '',
        checkResult: 0
      }, {
        id: 2,
        selectionNumber: 0,
        checkResult: 1
      }],
      courseModifyEditVisible: false,
      courseDetailForm: {
        applyType: 0,
        checkResult: 0
      },
      paperModifyRecord: [{
        id: '',
        major: '',
        studentNumber: '',
        semester: '',
        schoolYear: '',
        remark: '',
        modifyMajor: '',
        modifyStudentNumber: '',
        modifySemester: '',
        modifySchoolYear: '',
        checkResult: 1
      }, {
        id: 2,
        checkResult: 1
      }],
      paperModifyEditVisible: false,
      paperDetailForm: {
        applyType: 0,
        checkResult: 0
      },
      internModifyRecord: [{
        id: '',
        modifyNormalPractice: '',
        modifyNonNormalPractice: '',
        modifySchoolPractice: ''
      }, {
        id: 2,
        checkResult: 1
      }],
      internModifyEditVisible: false,
      internDetailForm: {
        applyType: 0,
        checkResult: 0
      },
      applyType: ['教师申请', '教务员修改'],
      modifyType: ['课时修改', '指导论文修改', '专业带队管理']
    }
  },
  methods: {
    modifyEdit: function(index) {
      switch (index) {
        case 0: {
          this.courseModifyEditVisible = true
          break
        }
        case 1: {
          this.paperModifyEditVisible = true
          break
        }
        case 2: {
          this.internModifyEditVisible = true
          break
        }
      }
    },
    modifyDelete: function(index, id) {

    },
    editEnsureOrCancel: function(index, flag) {
      switch (index) {
        case 0: {
          this.courseModifyEditVisible = false
          break
        }
        case 1: {
          this.paperModifyEditVisible = false
          break
        }
        case 2: {
          this.internModifyEditVisible = false
          break
        }
      }
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

  .input-name {
    margin-left: 10px;
    margin-bottom: 20px;
    width: auto;
  }

  .dialog-content-item {
    margin-left: 20px;
    margin-bottom: 20px;
  }

  .span-title {
    font-weight:bold;
  }

  .span-content {
    margin-left: 10px;
  }

  .button-find {
    margin-left: 30px;
  }

  .button-add {
    float: right;
  }
  .el-row {
    margin-bottom: 20px;
    &:last-child {
      margin-bottom: 0;
    }
  }
  .el-col {
  }
</style>

