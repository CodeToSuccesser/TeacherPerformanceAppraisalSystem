import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'
import app from './modules/app'
import settings from './modules/settings'
import user from './modules/user'
import permission from '@/store/modules/permission'
import sysParam from '@/store/modules/sysParam'
import systemRole from '@/store/modules/systemRole'
import courseBase from '@/store/modules/courseBase'

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    app,
    settings,
    user,
    permission,
    sysParam,
    systemRole,
    courseBase
  },
  getters
})

export default store
