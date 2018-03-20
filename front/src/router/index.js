import Vue from 'vue'
import Router from 'vue-router'
import Workflow from '@/components/Workflow'
import Transition from '@/components/Transition'
import Status from '@/components/Status'
import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.use(Router)
Vue.use(BootstrapVue)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Workflow',
      component: Workflow
    },
    {
      path: '/workflows',
      name: 'Workflow',
      component: Workflow
    },
    {
      path: '/statuses',
      name: 'Status',
      component: Status
    },
    {
      path: '/transitions',
      name: 'Transition',
      component: Transition
    }
  ]
})
