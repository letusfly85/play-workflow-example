<template>
  <div>
    <craft-header></craft-header>
    <div class="order-list">
      <b-table :items="craftLines" :fields="fields">
        <template slot="status_name" slot-scope="row">
          <b-btn variant="outline-success" size="sm" :href="'#/craft-products/'+row.item.id">{{ row.item.status_name }}</b-btn>
        </template>
      </b-table>
    </div>
  </div>
</template>

<script>
import ApiClient from '../utils/ApiClient'
import CraftHeader from './CraftHeader'
import AppConst from '../utils/AppConst'

export default {
  name: 'Order',
  components: { CraftHeader },
  data () {
    return {
      craftLines: [],
      fields: [
        {key: 'id', sortable: true},
        {key: 'status_name', sortable: true},
        {key: 'product_name', sortable: true},
        {key: 'assigned_member_name', sortable: true}
      ]
    }
  },
  methods: {
    searchCraftLines: function () {
      let self = this

      let targetPath = '/api/example/craft-lines'
      ApiClient.search(targetPath, (response) => {
        console.log(response)
        self.craftLines = response.data.map(function (data) {
          data.workflow_id = AppConst.data().orderExampleWorkflowId
          data.status_name = data.status_name ? data.status_name : '対応開始する'
          return data
        })
      }, (error) => {
        console.log(error)
      })
    }
  },
  created: function () {
    this.searchCraftLines()
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
