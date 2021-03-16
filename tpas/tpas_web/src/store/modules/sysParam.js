import { queryCNumIndex } from '@/api/sysParam'

const getDefaultState = () => {
  return {
    paramCNumList: new Map([
      [undefined, '全部']
    ]),
    paramType: new Map([
      [undefined, '全部'],
      [1, '授课'],
      [2, '论文指导'],
      [3, '实习指导'],
      [4, '其他']
    ]), // 权值类型
    paramValueType: new Map([
      [undefined, '全部'],
      [1, '参数值'],
      [2, '定值']
    ]),
    paramColumnName: [
      {
        paramType: 1,
        columnName: 'courseCharacter',
        text: '课程性质: 0表示必修, 1表示选修'
      }, {
        paramType: 1,
        columnName: 'courseType',
        text: '课程类别: 0表示专业方向, 1表示专业核心, 2表示教师教育, 3表示实践教学, 4表示大类教育'
      }, {
        paramType: 1,
        columnName: 'courseCridet',
        text: '学分'
      }, {
        paramType: 1,
        columnName: 'totalHours',
        text: '总学时数'
      }, {
        paramType: 1,
        columnName: 'isBilingual',
        text: '授课语种: 0表示非双语, 1表示双语'
      }, {
        paramType: 1,
        columnName: 'softHard',
        text: '软硬课程类型: 0表示软件课程, 1表示硬件课程'
      }, {
        paramType: 1,
        columnName: 'studentType',
        text: '学生类型, 默认0 本科生, 1 专科生, 2 研究生'
      }, {
        paramType: 1,
        columnName: 'studentNumber',
        text: '课程人数'
      }, {
        paramType: 1,
        columnName: 'totalCapacity',
        text: '课程总容量'
      }, {
        paramType: 1,
        columnName: 'selectedStudent',
        text: '已选学生数'
      }, {
        paramType: 1,
        columnName: 'teachingHours',
        text: '讲课学时'
      }, {
        paramType: 1,
        columnName: 'computerHours',
        text: '上机学时'
      }, {
        paramType: 1,
        columnName: 'experimentHours',
        text: '实验学时'
      }, {
        paramType: 1,
        columnName: 'primarySecondary',
        text: '主讲辅讲标记, 默认0 主讲, 1 辅讲'
      }, {
        paramType: 1,
        columnName: 'expNumber',
        text: '实验批数'
      }, {
        paramType: 1,
        columnName: 'expPerNumber',
        text: '实验每批次人数'
      }, {
        paramType: 1,
        columnName: 'studentNumber',
        text: '指导学生人数'
      }, {
        paramType: 1,
        columnName: 'normalPractice',
        text: '师范实习带队人数'
      }, {
        paramType: 1,
        columnName: 'nonNormalPractice',
        text: '非师范实习带队人数'
      }, {
        paramType: 1,
        columnName: 'schoolPractice',
        text: '校内实习带队人数'
      }
    ]
  }
}

const state = getDefaultState()

const mutations = {
  RESET_STATE: (state) => {
    Object.assign(state, getDefaultState())
  },
  SET_C_NUM_LIST: (state, list) => {
    const aMap = state.paramCNumList
    list.forEach(row => {
      aMap.set(row, row)
    })
    state.paramCNumList = aMap
  }
}

const actions = {
  // 查找绩效元素列表
  queryCNumIndex({ commit }) {
    return new Promise((resolve, reject) => {
      queryCNumIndex({}).then(response => {
        const { data } = response
        console.log('queryCNumIndex data: ', data)
        commit('SET_C_NUM_LIST', data)
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

