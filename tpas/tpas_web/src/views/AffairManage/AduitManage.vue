<template>
  <div class="app-container">
    <el-tabs v-model="activeName">
      <el-tab-pane label="课程课时操作" name="courseHour">
        <el-form ref="form" :model="courseSearchForm">
          <el-select v-model="courseSearchForm.selectedSchoolYear" clearable placeholder="学年" class="selector-year">
            <el-option v-for="item in schoolYearOptions" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
          <el-select v-model="courseSearchForm.selectedSemester" clearable placeholder="学期" class="selector-term">
            <el-option v-for="item in semesterOptions" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
          <el-input v-model="courseSearchForm.selectedTeacherCode" clearable placeholder="教师编码" class="input-name" />
          <el-button type="primary" size="small" class="button-find" @click="searchCourseHourModifyRecord">查找</el-button>
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
          <el-table-column label="学期" prop="semester" align="center">
            <template slot-scope="scope">
              {{ Number(courseHoursModifyRecord[scope.$index].semester) === 0 ? '第一学期' : '第二学期' }}
            </template>
          </el-table-column>
          <el-table-column label="申请时间" prop="createTime" align="center" />
          <el-table-column label="审核时间" prop="checkTime" align="center" />
          <el-table-column label="操作" align="center">
            <template slot-scope="props">
              <el-button
                type="text"
                size="small"
                @click="modifyEdit(0, props.$index)"
              >{{ Number(props.row.checkResult) === 0 ? '处理' : '查看' }}</el-button>
            </template>
          </el-table-column>
        </el-table>

        <el-pagination
          background
          layout="prev, pager, next"
          :total="courseTotal"
          :page-size="coursePageSize"
          :current-page="courseCurPageNum"
          class="pagination"
          @prev-click="coursePrePage"
          @next-click="courseNextPage"
          @current-change="courseHandleCurrentChange"
        />

        <el-dialog
          title="课程课时操作处理"
          :visible.sync="courseModifyEditVisible"
          top="5vh"
          :append-to-body="true"
          :close-on-click-modal="false"
          :close-on-press-escape="false"
          :model="courseDetailForm"
        >
          <div class="dialog-content-item">
            <span class="span-title">操作类型:</span>
            <span class="span-content"> {{ modifyType[0] }}</span>
          </div>
          <div class="dialog-content-item">
            <span class="span-title">日期:</span>
            <span class="span-content">{{ courseDetailForm.createTime }}</span>
          </div>
          <div class="dialog-content-item">
            <span class="span-title">申请人:</span>
            <span class="span-content">{{ courseDetailForm.teacherCode }}</span>
          </div>
          <div class="dialog-content-item">
            <span class="span-title">处理人:</span>
            <span class="span-content">{{ courseDetailForm.checkName }}</span>
          </div>
          <div class="dialog-content-item">
            <span class="span-title">申请状态:</span>
            <span class="span-content">{{ Number(courseDetailForm.checkResult) === 0 ? '待审批' : '已审批' }}</span>
          </div>
          <div v-if="Number(courseDetailForm.checkResult) === 0" slot="footer" class="dialog-footer">
            <el-button type="danger" @click="editEnsureOrCancel(0, false)">不通过</el-button>
            <el-button type="primary" @click="editEnsureOrCancel(0, true)">通 过</el-button>
          </div>
          <div v-else slot="footer" class="dialog-footer">
            <el-button type="primary" @click="editEnsureOrCancel(0, true)">确 定</el-button>
          </div>
        </el-dialog>

      </el-tab-pane>

      <el-tab-pane label="论文指导操作" name="paper">
        <el-form ref="form" :model="paperSearchForm">
          <el-select v-model="paperSearchForm.selectedSchoolYear" placeholder="学年" class="selector-year">
            <el-option v-for="item in schoolYearOptions" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
          <el-select v-model="paperSearchForm.selectedSemester" placeholder="学期" class="selector-term">
            <el-option v-for="item in semesterOptions" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
          <el-input v-model="paperSearchForm.teacherCode" placeholder="教师编码" class="input-name" />
          <el-button type="primary" size="small" class="button-find" @click="searchPaperModifyRecord">查找</el-button>
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
                  <span>{{ Number(props.row.semester) === 0 ? '第一学期' : '第二学期' }}</span>
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
          <el-table-column label="专业代码号" prop="majorCode" align="center" />
          <el-table-column label="专业名称" prop="majorName" align="center" />
          <el-table-column label="学年" prop="schoolYear" align="center" />
          <el-table-column label="学期" prop="semester" align="center">
            <template slot-scope="scope">
              {{ Number(paperModifyRecord[scope.$index].semester) === 0 ? '第一学期' : '第二学期' }}
            </template>
          </el-table-column>
          <el-table-column label="申请时间" prop="createTime" align="center" />
          <el-table-column label="审核时间" prop="checkTime" align="center" />
          <el-table-column label="操作" align="center">
            <template slot-scope="props">
              <el-button
                type="text"
                size="small"
                @click="modifyEdit(1, props.$index)"
              >{{ Number(props.row.checkResult) === 0 ? '处理' : '查看' }}</el-button>
            </template>
          </el-table-column>
        </el-table>

        <el-pagination
          background
          layout="prev, pager, next"
          :total="paperTotal"
          :page-size="paperPageSize"
          :current-page="paperCurPageNum"
          class="pagination"
          @prev-click="paperPrePage"
          @next-click="paperNextPage"
          @current-change="paperHandleCurrentChange"
        />

        <el-dialog
          title="论文指导操作处理"
          :visible.sync="paperModifyEditVisible"
          top="5vh"
          :append-to-body="true"
          :close-on-click-modal="false"
          :close-on-press-escape="false"
          :model="paperDetailForm"
        >
          <div class="dialog-content-item">
            <span class="span-title">操作类型:</span>
            <span class="span-content">{{ modifyType[0] }}</span>
          </div>
          <div class="dialog-content-item">
            <span class="span-title">日期:</span>
            <span class="span-content">{{ paperDetailForm.createTime }}</span>
          </div>
          <div class="dialog-content-item">
            <span class="span-title">申请人:</span>
            <span class="span-content">{{ paperDetailForm.teacherCode }}</span>
          </div>
          <div class="dialog-content-item">
            <span class="span-title">处理人:</span>
            <span class="span-content">{{ paperDetailForm.checkName }}</span>
          </div>
          <div class="dialog-content-item">
            <span class="span-title">申请状态:</span>
            <span class="span-content">{{ Number(paperDetailForm.checkResult) === 0 ? '待审批' : '已审批' }}</span>
          </div>
          <div v-if="Number(paperDetailForm.checkResult) === 0" slot="footer" class="dialog-footer">
            <el-button type="danger" @click="editEnsureOrCancel(1, false)">不通过</el-button>
            <el-button type="primary" @click="editEnsureOrCancel(1, true)">通 过</el-button>
          </div>
          <div v-else slot="footer" class="dialog-footer">
            <el-button type="primary" @click="editEnsureOrCancel(1, true)">确 定</el-button>
          </div>
        </el-dialog>

      </el-tab-pane>

      <el-tab-pane label="实习带队操作" name="intern">
        <el-form ref="form" :model="internSearchForm">
          <el-select v-model="internSearchForm.selectedSchoolYear" placeholder="学年" class="selector-year">
            <el-option v-for="item in schoolYearOptions" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
          <el-select v-model="internSearchForm.selectedSemester" placeholder="学期" class="selector-term">
            <el-option v-for="item in semesterOptions" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
          <el-input v-model="internSearchForm.teacherCode" placeholder="教师编码" class="input-name" />
          <el-button type="primary" size="small" class="button-find" @click="searchInternModifyRecord">查找</el-button>
        </el-form>
        <el-table :data="internModifyRecord" stripe style="width: 100% " :border="true" fit>
          <el-table-column type="expand" align="center">
            <template slot-scope="props">
              <el-form label-position="left" inline class="demo-table-expand">
                <el-form-item label="学期">
                  <span>{{ Number(props.row.semester) === 0 ? '第一学期' : '第二学期' }}</span>
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
          <el-table-column label="学年" prop="schoolYear" align="center" />
          <el-table-column label="学期" prop="semester" align="center">
            <template slot-scope="scope">
              {{ Number(internModifyRecord[scope.$index].semester) === 0 ? '第一学期' : '第二学期' }}
            </template>
          </el-table-column>
          <el-table-column label="申请时间" prop="createTime" align="center" />
          <el-table-column label="审核时间" prop="checkTime" align="center" />
          <el-table-column label="操作" align="center">
            <template slot-scope="props">
              <el-button
                type="text"
                size="small"
                @click="modifyEdit(2, props.$index)"
              >{{ Number(props.row.checkResult) === 0? '处理' : '查看' }}</el-button>
            </template>
          </el-table-column>
        </el-table>

        <el-pagination
          background
          layout="prev, pager, next"
          :total="internTotal"
          :page-size="internPageSize"
          :current-page="internCurPageNum"
          class="pagination"
          @prev-click="internPrePage"
          @next-click="internNextPage"
          @current-change="internHandleCurrentChange"
        />

        <el-dialog
          title="实习带队操作处理"
          :visible.sync="internModifyEditVisible"
          top="5vh"
          :append-to-body="true"
          :close-on-click-modal="false"
          :close-on-press-escape="false"
          :model="internDetailForm"
        >
          <div class="dialog-content-item">
            <span class="span-title">操作类型:</span>
            <span class="span-content">{{ modifyType[0] }}</span>
          </div>
          <div class="dialog-content-item">
            <span class="span-title">日期:</span>
            <span class="span-content">{{ internDetailForm.createTime }}</span>
          </div>
          <div class="dialog-content-item">
            <span class="span-title">申请人:</span>
            <span class="span-content">{{ internDetailForm.teacherCode }}</span>
          </div>
          <div class="dialog-content-item">
            <span class="span-title">处理人:</span>
            <span class="span-content">{{ internDetailForm.checkName }}</span>
          </div>
          <div class="dialog-content-item">
            <span class="span-title">申请状态:</span>
            <span class="span-content">{{ Number(internDetailForm.checkResult) === 0 ? '待审批' : '已审批' }}</span>
          </div>
          <div v-if="Number(internDetailForm.checkResult) ===0" slot="footer" class="dialog-footer">
            <el-button type="danger" @click="editEnsureOrCancel(2, false)">不通过</el-button>
            <el-button type="primary" @click="editEnsureOrCancel(2, true)">通 过</el-button>
          </div>
          <div v-else slot="footer" class="dialog-footer">
            <el-button type="primary" @click="editEnsureOrCancel(2, true)">确 定</el-button>
          </div>
        </el-dialog>
      </el-tab-pane>
    </el-tabs>

  </div>
</template>

<script>
import { getCourseHoursModifyRecord, auditCourseHoursModify } from '@/api/course'
import { getPaperModifyRecord, auditPaperModify } from '@/api/paper'
import { getInternModifyRecord, auditInternModify } from '@/api/intern'
import { hideFullScreenLoading, showFullScreenLoading } from '@/utils/loading'
import { mapGetters } from 'vuex'

export default {
  name: 'AuditRecord',
  data() {
    return {
      courseSearchForm: {
        selectedSchoolYear: '',
        selectedSemester: '',
        selectedTeacherCode: ''
      },
      internSearchForm: {
        selectedSchoolYear: '',
        selectedSemester: '',
        selectedTeacherCode: ''
      },
      paperSearchForm: {
        selectedSchoolYear: '',
        selectedSemester: '',
        selectedTeacherCode: ''
      },
      courseTotal: 0,
      coursePageSize: 25,
      courseCurPageNum: 1,
      internTotal: 0,
      internPageSize: 25,
      internCurPageNum: 1,
      paperTotal: 0,
      paperPageSize: 25,
      paperCurPageNum: 1,
      activeName: 'courseHour',
      courseHoursModifyRecord: [],
      courseModifyEditVisible: false,
      courseDetailForm: {
        applyType: 0,
        checkResult: 0
      },
      paperModifyRecord: [],
      paperModifyEditVisible: false,
      paperDetailForm: {
        applyType: 0,
        checkResult: 0
      },
      internModifyRecord: [],
      internModifyEditVisible: false,
      internDetailForm: {
        applyType: 0,
        checkResult: 0
      },
      applyType: ['教师申请', '教务员修改'],
      modifyType: ['课时修改', '指导论文修改', '专业带队管理']
    }
  },
  computed: {
    ...mapGetters([
      'semesterOptions',
      'schoolYearOptions'
    ])
  },
  created() {
    this.getCourseHoursModifyRecord({})
    this.getInternModifyRecord({})
    this.getPaperModifyRecord({})
  },
  methods: {
    modifyEdit: function(type, index) {
      switch (type) {
        case 0: {
          this.courseModifyEditVisible = true
          this.courseDetailForm = this.courseHoursModifyRecord[index]
          break
        }
        case 1: {
          this.paperModifyEditVisible = true
          this.paperDetailForm = this.paperModifyRecord[index]
          break
        }
        case 2: {
          this.internModifyEditVisible = true
          this.internDetailForm = this.internModifyRecord[index]
          break
        }
      }
    },
    modifyDelete: function(index, id) {

    },
    editEnsureOrCancel: function(index, result) {
      switch (index) {
        case 0: {
          this.courseModifyEditVisible = false
          showFullScreenLoading('审批中')
          auditCourseHoursModify(this.courseDetailForm.id, result)
            .then(response => {
              hideFullScreenLoading()
              this.$message.success('审批成功')
              location.reload()
            })
            .catch(error => {
              console.log(error)
              hideFullScreenLoading()
            })
          break
        }
        case 1: {
          this.paperModifyEditVisible = false
          showFullScreenLoading('审批中')
          auditPaperModify(this.paperDetailForm.id, result)
            .then(response => {
              hideFullScreenLoading()
              this.$message.success('审批成功')
              location.reload()
            })
            .catch(error => {
              console.log(error)
              hideFullScreenLoading()
            })
          break
        }
        case 2: {
          this.internModifyEditVisible = false
          showFullScreenLoading('审批中')
          auditInternModify(this.internDetailForm.id, result)
            .then(response => {
              hideFullScreenLoading()
              this.$message.success('审批成功')
              location.reload()
            })
            .catch(error => {
              console.log(error)
              hideFullScreenLoading()
            })
          break
        }
      }
    },
    getCourseHoursModifyRecord(body) {
      body.checkResult = [0]
      getCourseHoursModifyRecord(body)
        .then(response => {
          const { data } = response
          this.courseHoursModifyRecord = data.list
          this.courseTotal = data.total
        }).catch(error => {
          console.log(error)
        })
    },
    getPaperModifyRecord(body) {
      body.checkResult = [0]
      getPaperModifyRecord(body)
        .then(response => {
          const { data } = response
          this.paperModifyRecord = data.list
          this.paperTotal = data.total
        }).catch(error => {
          console.log(error)
        })
    },
    getInternModifyRecord(body) {
      body.checkResult = [0]
      getInternModifyRecord(body)
        .then(response => {
          const { data } = response
          this.internModifyRecord = data.list
          this.internTotal = data.total
        }).catch(error => {
          console.log(error)
        })
    },
    coursePrePage() {
      const param = {
        pageNum: this.curPageNum - 1,
        pageSize: this.pageSize
      }
      this.getCourseHoursModifyRecord(param)
      this.curPageNum = this.curPageNum - 1
    },
    courseNextPage() {
      const param = {
        pageNum: this.curPageNum + 1,
        pageSize: this.pageSize
      }
      this.getCourseHoursModifyRecord(param)
      this.curPageNum = this.curPageNum + 1
    },
    courseHandleCurrentChange(val) {
      const param = {
        pageNum: this.curPageNum,
        pageSize: this.pageSize
      }

      this.getCourseHoursModifyRecord(param)
      this.curPageNum = val
    },
    paperPrePage() {
      const param = {
        pageNum: this.curPageNum - 1,
        pageSize: this.pageSize
      }
      this.getPaperModifyRecord(param)
      this.curPageNum = this.curPageNum - 1
    },
    paperNextPage() {
      const param = {
        pageNum: this.curPageNum + 1,
        pageSize: this.pageSize
      }
      this.getPaperModifyRecord(param)
      this.curPageNum = this.curPageNum + 1
    },
    paperHandleCurrentChange(val) {
      const param = {
        pageNum: this.curPageNum,
        pageSize: this.pageSize
      }

      this.getPaperModifyRecord(param)
      this.curPageNum = val
    },
    internPrePage() {
      const param = {
        pageNum: this.curPageNum - 1,
        pageSize: this.pageSize
      }
      this.getInternModifyRecord(param)
      this.curPageNum = this.curPageNum - 1
    },
    internNextPage() {
      const param = {
        pageNum: this.curPageNum + 1,
        pageSize: this.pageSize
      }
      this.getInternModifyRecord(param)
      this.curPageNum = this.curPageNum + 1
    },
    internHandleCurrentChange(val) {
      const param = {
        pageNum: this.curPageNum,
        pageSize: this.pageSize
      }

      this.getInternModifyRecord(param)
      this.curPageNum = val
    },
    searchCourseHourModifyRecord() {
      const param = {
        pageNum: this.courseCurPageNum,
        pageSize: this.coursePageSize
      }
      if (this.courseSearchForm.selectedSemester !== undefined && this.courseSearchForm.selectedSemester !== '') {
        param.semester = this.courseSearchForm.selectedSemester
      }
      if (this.courseSearchForm.selectedSchoolYear !== undefined && this.courseSearchForm.selectedSchoolYear !== '') {
        param.schoolYear = this.courseSearchForm.selectedSchoolYear
      }
      if (this.courseSearchForm.selectedTeacherCode !== undefined && this.courseSearchForm.selectedTeacherCode !== '') {
        param.applyName = this.courseSearchForm.selectedTeacherCode
      }
      this.getCourseHoursModifyRecord(param)
    },
    searchPaperModifyRecord() {
      const param = {
        pageNum: this.paperCurPageNum,
        pageSize: this.paperPageSize
      }
      if (this.paperSearchForm.selectedSemester !== undefined && this.paperSearchForm.selectedSemester !== '') {
        param.semester = this.paperSearchForm.selectedSemester
      }
      if (this.paperSearchForm.selectedSchoolYear !== undefined && this.paperSearchForm.selectedSchoolYear !== '') {
        param.schoolYear = this.paperSearchForm.selectedSchoolYear
      }
      if (this.paperSearchForm.selectedTeacherCode !== undefined && this.paperSearchForm.selectedTeacherCode !== '') {
        param.applyName = this.paperSearchForm.selectedTeacherCode
      }
      this.getPaperModifyRecord(param)
    },
    searchInternModifyRecord() {
      const param = {
        pageNum: this.internCurPageNum,
        pageSize: this.internPageSize
      }
      if (this.internSearchForm.selectedSemester !== undefined && this.internSearchForm.selectedSemester !== '') {
        param.semester = this.internSearchForm.selectedSemester
      }
      if (this.internSearchForm.selectedSchoolYear !== undefined && this.internSearchForm.selectedSchoolYear !== '') {
        param.schoolYear = this.internSearchForm.selectedSchoolYear
      }
      if (this.internSearchForm.selectedTeacherCode !== undefined && this.internSearchForm.selectedTeacherCode !== '') {
        param.applyName = this.internSearchForm.selectedTeacherCode
      }
      this.getInternModifyRecord(param)
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

  .pagination {
    margin-top: 20px ;
    float: right ;
    margin-bottom: 20px
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

