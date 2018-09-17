import Vue from 'vue'
import Router from 'vue-router'
import Workflow from '@/components/Workflow'
import WorkflowDetail from '@/components/WorkflowDetail'
import Transition from '@/components/Transition'
import Status from '@/components/Status'
import Order from '@/components/order/Order'
import CraftLine from '@/components/craft/CraftLine'
import CraftProduct from '@/components/craft/CraftProduct'
// import 'bootstrap/dist/css/bootstrap.css'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/workflows',
      name: 'Workflow',
      component: Workflow
    },
    {
      path: '/workflows/:workflowId',
      name: 'WorkflowDetail',
      component: WorkflowDetail
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
