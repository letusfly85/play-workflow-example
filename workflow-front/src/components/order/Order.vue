<template>
  <div>
    <order-header></order-header>
    <div class="order-list">
      <!--
      <b-table :items="orders" :fields="fields">
        <template slot="id" slot-scope="row">
          <b-btn variant="info" size="sm" @click="showOrderOf(row.item, row.index)"> {{ row.item.id }} </b-btn>
          <b-modal :ref="'orderRef'+row.item.id" title="編集する" size="lg">
            <div class="modal-content">
              <t-btn :ref="'tbRef'+row.item.id" v-on:reloadParent="searchOrders"></t-btn>
            </div>
          </b-modal>
        </template>
      </b-table>
      -->
      <table class="table table-hover">
        <thead>
        <tr align="left">
          <th scope="col">Id</th>
          <th scope="col">Order Id</th>
          <th scope="col">Status</th>
          <th scope="col">Customer</th>
          <th scope="col">Assign</th>
          <th scope="col">Edit</th>
          <th scope="col">Destroy</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(row, index) in orders" v-bind:key="row.id">
          <td align="left">{{ row.id }}</td>
          <td align="left">{{ row.order_id }}</td>
          <td align="left">{{ row.status_name }}</td>
          <td align="left">{{ row.customer_name }}</td>
          <td align="left">{{ row.assigned_member_name }}</td>
          <td>
            <button class="btn-primary"  v-on:click="showOrderOf(index)">Edit</button>
            <div class="modal fade" style="width: 1500px;" :ref="`orderRef${row.id}`">
              <div class="modal-dialog modal-lg">
                <div class="modal-content">
                  <div class="modal-header">
                    <div class="modal-body">
                      <t-btn :ref="`tbRef${row.id}`" v-on:reloadParent="searchOrders"></t-btn>
                    </div>
                  </div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-primary">Save changes</button>
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                  </div>
                </div>
              </div>
            </div>
          </td>
          <td>
            <button class="btn-danger" v-on:click="destroyWorkflow(index)">Destroy</button>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import ApiClient from '../utils/ApiClient'
import OrderHeader from './OrderHeader'
import TransitionButton from '../workflow/TransitionButton'
// eslint-disable-next-line
import jQuery from 'jquery'

export default {
  name: 'Order',
  components: { OrderHeader, 't-btn': TransitionButton },
  data () {
    return {
      orders: [],
      fields: [
        {key: 'id', sortable: true},
        {key: 'order_id', sortable: true},
        {key: 'status_name', sortable: true},
        {key: 'customer_name', sortable: true},
        {key: 'assigned_member_name', sortable: true}
      ]
    }
  },
  methods: {
    showOrderOf: function (index) {
      const workflowId = this.$store.state.workflowId
      let self = this

      const order = this.orders[index]
      let targetPath = `/api/example/workflows/${workflowId}/orders/${order.order_id}`
      let params = order
      console.log(targetPath)
      ApiClient.update(targetPath, params, (response) => {
        console.log(response)
        self.orders[index] = response.data
        self.orders[index].workflow_id = workflowId

        const modalRef = this.$refs[`orderRef${order.id}`]
        jQuery(modalRef).modal('show')
      }, (error) => {
        console.log(error)
      })
      let transactionId = this.orders[index].transaction_id
      const refName = 'tbRef' + order.id
      const targetRef = this.$refs[refName]
      targetRef[0].childMethod(workflowId, transactionId)
    },
    searchOrders: function () {
      let self = this

      let targetPath = '/api/example/orders'
      ApiClient.search(targetPath, (response) => {
        console.log(response)
        self.orders = response.data.map(function (data) {
          data.workflow_id = self.$store.state.workflowId
          return data
        })
        console.log(self.orders)
      }, (error) => {
        console.log(error)
      })
    }
  },
  created: function () {
    this.searchOrders()
  }
}
</script>

<style scoped>
  .order-list {
    width: 80%;
    margin-top: 5px;
    margin-left: 10%;
    border: transparent 1px solid;
  }
  .modal-content {
    margin-top: 0px;
    height:400px !important;
  }
</style>
