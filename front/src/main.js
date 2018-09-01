// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from 'vuex-persistedstate'
import App from './App'
import router from './router'
import jQuery from 'jquery'
import 'bootstrap/dist/js/bootstrap'
import 'bootswatch/dist/flatly/bootstrap.css'

Vue.config.productionTip = false
Vue.use(Vuex)

const store = new Vuex.Store({
  state: {
    workflowId: null
  },
  mutations: {
    updateWorkflowId: function (state, workflowId) {
      state.workflowId = workflowId
    }
  },
  getters: {},
  actions: {},
  plugins: [
    createPersistedState({
      key: 'workflowId',
      paths: ['workflowId'],
      storage: window.sessionStorage
    })
  ]
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  store,
  template: '<App/>'
})
