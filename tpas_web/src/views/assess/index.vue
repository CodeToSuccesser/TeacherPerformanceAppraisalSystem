<template>
  <div class="app-container">

    <el-tabs v-model="activeName">
      <el-tab-pane label="授课学时" name="courseHour">
        <el-select v-model="value" placeholder="年度" class="selector-year">
          <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>

        <el-select v-model="value" placeholder="学期" class="selector-term">
          <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>

        <el-button type="primary" size="small" class="button-find">查找</el-button>

        <el-button v-if="isAdmin" type="primary" size="small" class="button-add" @click="calculationResults">计算结果</el-button>
        <el-button type="primary" size="small" class="button-add" @click="exportAssessResult">导出结果</el-button>

        <el-table :data="courseScoreInfo" stripe style="width: 100% " :border="true" fit class="score-table">
          <el-table-column :resizable="false" prop="id" sortable label="序号" width="110px" />
          <el-table-column :resizable="false" prop="courseHourId" sortable label="课时信息编号" />
          <el-table-column :resizable="false" prop="total" sortable label="授课总分" />
          <el-table-column :resizable="false" prop="semester" sortable label="学期" />
          <el-table-column :resizable="false" prop="schoolYear" sortable label="学年" />
          <el-table-column :resizable="false" prop="valueDetail" sortable label="权值信息" />
          <el-table-column :resizable="false" prop="createTime" sortable label="创建日期" />
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="论文指导" name="paper">
        <el-select v-model="value" placeholder="年度" class="selector-year">
          <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>

        <el-select v-model="value" placeholder="学期" class="selector-term">
          <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>

        <el-button type="primary" size="small" class="button-find">查找</el-button>

        <el-button v-if="isAdmin" type="primary" size="small" class="button-add" @click="calculationResults">计算结果</el-button>
        <el-button type="primary" size="small" class="button-add" @click="exportAssessResult">导出结果</el-button>

        <el-table :data="paperScoreInfo" stripe style="width: 100% " :border="true" fit class="score-table">
          <el-table-column :resizable="false" prop="id" sortable label="序号" width="110px" />
          <el-table-column :resizable="false" prop="paperId" sortable label="论文指导信息编号" />
          <el-table-column :resizable="false" prop="total" sortable label="论文指导总分" />
          <el-table-column :resizable="false" prop="semester" sortable label="学期" />
          <el-table-column :resizable="false" prop="schoolYear" sortable label="学年" />
          <el-table-column :resizable="false" prop="valueDetail" sortable label="权值信息" />
          <el-table-column :resizable="false" prop="createTime" sortable label="创建日期" />
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="实习带队" name="intern">
        <el-select v-model="value" placeholder="年度" class="selector-year">
          <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>

        <el-select v-model="value" placeholder="学期" class="selector-term">
          <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>

        <el-button type="primary" size="small" class="button-find">查找</el-button>

        <el-button v-if="isAdmin" type="primary" size="small" class="button-add" @click="calculationResults">计算结果</el-button>
        <el-button type="primary" size="small" class="button-add" @click="exportAssessResult">导出结果</el-button>

        <el-table :data="internScoreInfo" stripe style="width: 100% " :border="true" fit class="score-table">
          <el-table-column :resizable="false" prop="id" sortable label="序号" width="110px" />
          <el-table-column :resizable="false" prop="internId" sortable label="实习指信息编码" />
          <el-table-column :resizable="false" prop="total" sortable label="实习指导总分" />
          <el-table-column :resizable="false" prop="semester" sortable label="学期" />
          <el-table-column :resizable="false" prop="schoolYear" sortable label="学年" />
          <el-table-column :resizable="false" prop="valueDetail" sortable label="权值信息" />
          <el-table-column :resizable="false" prop="createTime" sortable label="创建日期" />
        </el-table>
      </el-tab-pane>
    </el-tabs>

  </div>
</template>

<script>
export default {
  name: 'Assess',
  data() {
    return {
      courseScoreInfo: [{
        id: ''
      },
      {
        id: ''
      }],
      paperScoreInfo: [{
        id: ''
      }],
      internScoreInfo: [{
        id: ''
      }],
      activeName: 'courseHour',
      isAdmin: this.$store.getters.userType === '' ? sessionStorage.getItem('userType') : this.$store.getters.userType

    }
  },
  methods: {
    exportAssessResult: function() {

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

</style>
