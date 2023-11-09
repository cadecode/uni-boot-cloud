import Vue from 'vue';
import Vuex from 'vuex';
import getters from './getters';
import app from '@/store/module/app';
import user from '@/store/module/user';
import view from '@/store/module/view';

Vue.use(Vuex);

const store = new Vuex.Store({
  modules: {
    app,
    user,
    view
  },
  getters
});

export default store;
