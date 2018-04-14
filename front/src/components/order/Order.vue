<template>
  <div>
    <order-header></order-header>
    <div class="order-list">
      <b-table :items="orders">
        <template slot="id" slot-scope="row">
          <b-btn variant="info" size="sm" @click="showOrderOf(row.item.id)"> {{ row.item.id }} </b-btn>
          <b-modal :ref="'orderRef'+row.item.id" title="編集する" size="lg">
            <div class="modal-content">
                <t-btn></t-btn>
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

export default {
  name: 'Order',
  components: { OrderHeader, 't-btn': TransitionButton },
  data () {
    return {
      orders: []
    }
  },
  methods: {
    showOrderOf: function (id) {
      console.log(id)
      let modal = this.$refs['orderRef' + id]
      modal.show()
    }
  },
  created: function () {
    let self = this

    let targetPath = '/api/example/orders'
    ApiClient.search(targetPath, (response) => {
      console.log(response)
      self.orders = response.data
    }, (error) => {
      console.log(error)
    })
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
