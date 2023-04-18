import Vue from 'vue';
import Vuex from 'vuex';
import getters from './getters';
import app from '@/store/module/app';
import user from '@/store/module/user';

Vue.use(Vuex);

const store = new Vuex.Store({
  modules: {
    app,
    user
  },
  getters
});

export default store;
