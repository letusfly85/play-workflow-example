import Vue from 'vue'
import Router from 'vue-router'
import Workflow from '@/components/Workflow'
import WorkflowSummary from '@/components/WorkflowSummary'
import Transition from '@/components/Transition'
import Status from '@/components/Status'
import Order from '@/components/order/Order'
import CraftLine from '@/components/craft/CraftLine'
import CraftProduct from '@/components/craft/CraftProduct'
import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.use(Router)
Vue.use(BootstrapVue)

export default new Router({
  routes: [
    {
      path: '/workflows',
      name: 'WorkflowSummary',
      component: WorkflowSummary
    },
    {
      path: '/workflows/:workflowId',
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
    },
    {
      path: '/orders',
      name: 'Order',
      component: Order
    },
    {
      path: '/craft-lines',
      name: 'CraftLines',
      component: CraftLine
    },
    {
      path: '/craft-products/:craftLineId',
      component: CraftProduct
    }
  ]
})
