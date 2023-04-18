import settings from '@/settings'

const { showSettings, fixedHeaderFlag, sidebarLogoFlag } = settings

const state = {
  showSettings: showSettings,
  fixedHeaderFlag: fixedHeaderFlag,
  sidebarLogoFlag: sidebarLogoFlag
}

const mutations = {
  CHANGE_SETTING: (state, { key, value }) => {
    // eslint-disable-next-line no-prototype-builtins
    if (state.hasOwnProperty(key)) {
      state[key] = value
    }
  }
}

const actions = {
  changeSetting({ commit }, data) {
    commit('CHANGE_SETTING', data)
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

