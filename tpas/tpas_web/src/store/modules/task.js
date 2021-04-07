
const getDefaultState = () => {
  return {
    /**
     * 任务状态
     */
    taskStateOptions: [
      {
        key: '未截止',
        value: 0
      },
      {
        key: '已截止',
        value: 1
      },
      {
        key: '已取消',
        value: 2
      }
    ],
    /**
     * 任务反馈进度条颜色
     */
    taskProgressColors: [
      { color: '#f56c6c', percentage: 20 },
      { color: '#6f7ad3', percentage: 40 },
      { color: '#e6a23c', percentage: 60 },
      { color: '#1989fa', percentage: 80 },
      { color: '#5cb87a', percentage: 100 }
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

