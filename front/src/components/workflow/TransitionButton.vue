<template>
  <div id="transition-button">
    <b-card class="t-btn-list">
      <div v-for="(transition, index) in trans" v-bind:key="index" v-if="trans.length>0">
        <div v-if="transition.is_active">
          <b-btn class="t-btn" variant="outline-success" @click="changeStatus(transition.transition, index)">{{ transition.transition.name }}</b-btn>
        </div>
        <div v-if="!transition.is_active">
          <b-btn class="t-btn" variant="secondary" disable>{{ transition.transition.name }}</b-btn>
        </div>
      </div>
    </b-card>
  </div>
</template>

<script>
import ApiClient from '../utils/ApiClient'

export default {
  name: 'TransitionButton',
  data () {
    return {
      trans: [],
      workflowId: null,
      transactionId: null
    }
  },
  methods: {
    childMethod: function (workflowId, transactionId) {
      this.workflowId = workflowId
      this.transactionId = transactionId
      this.findTransitions()
    },
    findTransitions: function () {
      let self = this
      console.log(this.workflowId)
      let targetPath = '/api/workflow-user-transitions?workflow-id=' + this.workflowId + '&transaction-id=' + this.transactionId
      console.log(targetPath)

      ApiClient.search(targetPath, (response) => {
        self.trans = response.data
        console.log(response.data)
      }, (error) => {
        console.log(error)
      })
    },
    changeStatus: function (transition, index) {
      console.log(index)
      let targetPath = '/api/workflow-order-transactions'
      let params = {
        transaction_id: this.transactionId,
        transition: transition
      }
      ApiClient.create(targetPath, params, (response) => {
        console.log(response.data)
        this.$emit('reloadParent')
        this.findTransitions()
      }, (error) => {
        console.log(error)
      })
    }
  }
}
</script>

<style scoped>
  .t-btn-list {
    margin-top: 3px;
    margin-right: 3px;
    margin-left: 20%;
    height: 8rem;
    border: transparent 1px solid;
  }
  .t-btn {
    float: left;
    margin-bottom: 3px;
    margin-left: 5px;
    height: 2.5rem;
  }
</style>
