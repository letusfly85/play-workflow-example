import Vue from 'vue'
import Router from 'vue-router'
import Workflow from '@/components/Workflow'
import WorkflowDetail from '@/components/WorkflowDetail'
import WorkflowTransition from '@/components/WorkflowTransition'
import Status from '@/components/Status'
import Order from '@/components/order/Order'
import CraftLine from '@/components/craft/CraftLine'
import CraftProduct from '@/components/craft/CraftProduct'

Vue.use(Router)

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
      path: '/workflows/:workflowId',
      name: 'WorkflowDetail',
      component: WorkflowDetail
    },
    {
      path: '/workflow-statuses',
      name: 'Status',
      component: Status
    },
    {
      path: '/workflows/:workflowId/transitions',
      name: 'WorkflowTransition',
      component: WorkflowTransition
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
