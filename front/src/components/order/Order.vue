<template>
  <div>
    <order-header></order-header>
    <div class="order-list">
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
    showOrderOf: function (order, index) {
      let self = this

      let targetPath = '/api/example/orders' + '/' + order.order_id
      let params = order
      console.log(targetPath)
      ApiClient.update(targetPath, params, (response) => {
        console.log(response)
        self.orders[index] = response.data
        self.orders[index].workflow_id = AppConst.data().workflowId
      }, (error) => {
        console.log(error)
      })
      this.$refs['tbRef' + order.id].childMethod(this.orders[index])

      let modal = this.$refs['orderRef' + order.id]
      modal.show()
    },
    searchOrders: function () {
      let self = this

      let targetPath = '/api/example/orders'
      ApiClient.search(targetPath, (response) => {
        console.log(response)
        self.orders = response.data.map(function (data) {
          data.workflow_id = AppConst.data().workflowId
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
