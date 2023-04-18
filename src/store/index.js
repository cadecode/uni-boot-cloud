import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'
import app from '@/store/module/app'
import permission from '@/store/module/permission'
import settings from '@/store/module/settings'
import user from '@/store/module/user'

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    app,
    permission,
    settings,
    user
  },
  getters
})

export default store
