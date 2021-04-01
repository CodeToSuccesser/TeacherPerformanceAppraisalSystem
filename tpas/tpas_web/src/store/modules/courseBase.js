
const getDefaultState = () => {
  return {
    courseCharacterEnum: [
      {
        label: '全部',
        value: ''
      },
      {
        label: '必修',
        value: 0
      },
      {
        label: '选修',
        value: 1
      }
    ],
    courseTypeEnum: [
      {
        label: '全部',
        value: ''
      },
      {
        label: '专业方向',
        value: 0
      },
      {
        label: '专业核心',
        value: 1
      },
      {
        label: '教师教育',
        value: 2
      },
      {
        label: '实践教学',
        value: 3
      },
      {
        label: '大类教育',
        value: 4
      }
    ],
    softHardEnum: [
      {
        label: '全部',
        value: ''
      },
      {
        label: '软件',
        value: 0
      },
      {
        label: '硬件',
        value: 1
      }
    ],
    studentTypeEnum: [
      {
        label: '全部',
        value: ''
      },
      {
        label: '本科生',
        value: 0
      },
      {
        label: '专科生',
        value: 1
      },
      {
        label: '研究生',
        value: 2
      }
    ],
    bilingualEnum: [
      {
        label: '全部',
        value: ''
      },
      {
        label: '非双语',
        value: 0
      },
      {
        label: '双语',
        value: 1
      }
    ]
  }
}

const state = getDefaultState()

const mutations = {
  RESET_STATE: (state) => {
    Object.assign(state, getDefaultState())
  }
}

const actions = {
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

