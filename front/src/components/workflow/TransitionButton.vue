<template>
  <div id="transition-button">
    <b-card class="t-btn-list">
      <div v-for="(transition, index) in trans" v-bind:key="index" v-if="trans.length>0">
        <div v-if="transition.is_active">
          <b-btn class="t-btn" variant="outline-success">{{ transition.transition.name }}</b-btn>
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
  props: ['ts'],
  data () {
    return {
      trans: []
    }
  },
  methods: {
    childMethod: function (pTrans) {
      console.log(pTrans)
      console.log(pTrans.order_id)
      console.log(pTrans.workflow_id)
      console.log(pTrans.transaction_id)
      this.findTransitions(pTrans.workflow_id, pTrans.transaction_id)
      console.log(this.trans)
    },
    findTransitions: function (workflowId, transactionId) {
      let self = this
      var wId = workflowId.toString()
      let targetPath = '/api/workflow-user-transitions?workflow-id=' + wId + '&transaction-id=' + transactionId
      console.log(targetPath)

      ApiClient.search(targetPath, (response) => {
        self.trans = response.data
        console.log(response.data)
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
