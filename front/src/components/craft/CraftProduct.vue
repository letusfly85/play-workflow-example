<template>
  <div>
    <craft-header></craft-header>
    <div v-for="(transition, index) in trans" v-bind:key="index" v-if="trans.length>0" class="t-btns">
      <div v-if="transition.is_active">
        <b-btn class="t-btn" variant="outline-success" @click="changeStatus(transition.transition, index)">{{ transition.transition.name }}</b-btn>
      </div>
      <div v-if="!transition.is_active">
        <b-btn class="t-btn" variant="secondary" disable>{{ transition.transition.name }}</b-btn>
      </div>
    </div>
    <br/><br/>
    <div style="width:50%; margin-left: 25%;">
      <b-card :sub-title="craftProduct.status_name" style="width: 10rem; background-color: linen; margin-top: 1rem;">
      </b-card>
      <b-card :title="craftProduct.product_name" style="width: 10rem; background-color: aliceblue; margin-top: 1rem;">
        {{ craftProduct.assigned_member_name }} が依頼
      </b-card>
    </div>
  </div>
</template>

<script>
import ApiClient from '../utils/ApiClient'
import CraftHeader from './CraftHeader'

export default {
  name: 'CraftProduct',
  components: { CraftHeader },
  data () {
    return {
      craftProduct: {
        product_name: '',
        assigned_member_name: ''
      },
      transactionId: null,
      workflowId: 3,
      trans: []
    }
  },
  methods: {
    searchCraftProducts: function (craftlineId) {
      let self = this

      let targetPath = '/api/example/craft-lines/' + craftlineId
      ApiClient.search(targetPath, (response) => {
        console.log(response)
        self.craftProduct = response.data
        self.transactionId = response.data.transaction_id
        self.findTransitions()
      }, (error) => {
        console.log(error)
      })
    },
    findTransitions: function () {
      console.log(this.craftProduct)
      let targetPath = '/api/workflow-user-transitions?workflow-id=' + this.workflowId + '&transaction-id=' + this.transactionId
      console.log(targetPath)

      let self = this
      ApiClient.search(targetPath, (response) => {
        self.trans = response.data
        console.log(response.data)
      }, (error) => {
        console.log(error)
      })
    },
    changeStatus: function (transition, index) {
      console.log(index)
      let targetPath = '/api/workflow-user-transactions'
      let params = {
        transaction_id: this.transactionId,
        transition: transition
      }
      ApiClient.create(targetPath, params, (response) => {
        console.log(response.data)
        this.$emit('reloadParent')
        this.findTransitions()
        this.searchCraftProducts(this.$route.params.craftLineId)
      }, (error) => {
        console.log(error)
      })
    }
  },
  created: function () {
    this.searchCraftProducts(this.$route.params.craftLineId)
  }
}
</script>

<style scoped>
  .t-btns {
    width: 80%;
    margin-left: 10%;
    margin-top: 1rem;
  }
  .t-btn {
    float: left;
    margin-left: 1rem;
  }
</style>
