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
          <th scope="col">Name</th>
          <th scope="col">Description</th>
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
          <td align="left" v-if="!row.toggle" v-on:click="updateToggle(index)">{{ row.description }}</td>
          <div v-if="row.toggle">
            <input type="text" class="col-lg-8 form-control" style="margin-top: 1rem; float: left;" v-model="row.description" />
            <button class="btn-primary" style="margin-top: 1.5rem;" v-on:click="saveWorkflow(index)">Save</button>
          </div>
          <td>
            <button class="btn-primary" v-on:click="showOrderOf(index)">Edit</button>
            <div class="modal" size="lg" :ref="'orderRef'+row.id">
              <div class="modal-dialog" role="document">
                <div class="modal-content">
                  <div class="modal-header">
                    <h5 class="modal-title">"編集する</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                      <span aria-hidden="true">&times;</span>
                    </button>
                  </div>
                  <div class="modal-body">
                    <p>Modal body text goes here.</p>
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
import AppConst from '../utils/AppConst'

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
      let self = this

      console.log(this.orders)
      console.log(index)
      console.log(this.orders[index])
      const order = this.orders[index]
      let targetPath = `/api/example/orders/${order.order_id}`
      let params = order
      console.log(targetPath)
      ApiClient.update(targetPath, params, (response) => {
        console.log(response)
        self.orders[index] = response.data
        self.orders[index].workflow_id = AppConst.data().orderExampleWorkflowId
      }, (error) => {
        console.log(error)
      })
      // let transactionId = this.orders[index].transaction_id
      // this.$refs['tbRef' + order.id].childMethod(AppConst.data().orderExampleWorkflowId, transactionId)

      const modalRef = this.$refs[`orderRef${order.id}`]
      modalRef.modal('show')
    },
    searchOrders: function () {
      let self = this

      let targetPath = '/api/example/orders'
      ApiClient.search(targetPath, (response) => {
        console.log(response)
        self.orders = response.data.map(function (data) {
          data.workflow_id = AppConst.data().orderExampleWorkflowId
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
