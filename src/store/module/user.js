import { getInfo, login, logout } from '@/api/login'
import { getToken, removeToken, setToken } from '@/util/auth'
import { resetRouter } from '@/router'

const getDefaultState = () => {
  return {
    token: getToken(),
    name: '',
    avatar: '',
    roles: [],
    // login接口后端对象
    userInfo: {}
  }
}

const state = getDefaultState()

const mutations = {
  RESET_STATE: (state) => {
    Object.assign(state, getDefaultState())
  },
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  },
  SET_ROLES: (state, roles) => {
    state.roles = roles
  },
  SET_USER_INFO: (state, userInfo) => {
    state.userInfo = userInfo
  }
}

const actions = {
  // 登录
  login({ commit }, userInfo) {
    const formData = new FormData()
    Object.keys(userInfo).forEach(k => formData.append(k, userInfo[k]))
    return new Promise((resolve, reject) => {
      login(formData).then(res => {
        const { data } = res
        const { nickName, roles } = data
        commit('SET_USER_INFO', data)
        commit('SET_NAME', nickName)
        commit('SET_ROLES', roles)
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },
  // 获取用户信息
  getInfo({ commit }) {
    return new Promise((resolve, reject) => {
      getInfo().then(async(res) => {
        const { menuList } = res.data
        resolve(menuList)
      }).catch(error => {
        reject(error)
      })
    })
  },
  // 注销
  logout({ commit }) {
    return new Promise((resolve, reject) => {
      logout().then(() => {
        // 清理cookie token
        removeToken()
        // 清理路由
        resetRouter()
        commit('RESET_STATE')
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },
  // 设置token
  setToken({ commit }, token) {
    commit('SET_TOKEN', token)
    setToken(token)
  },
  // 重置token
  resetToken({ commit }) {
    return new Promise(resolve => {
      removeToken()
      commit('RESET_STATE')
      resolve()
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

