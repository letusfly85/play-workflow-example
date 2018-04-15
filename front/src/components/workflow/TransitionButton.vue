<template>
  <div id="transition-button">
    <b-card class="t-btn-list">
      <div v-for="(transition, index) in trans" v-bind:key="index" v-if="trans.length>0">
        <div v-if="transition.is_active">
          <b-btn class="t-btn" variant="outline-success" @click="hideModal(transition.transition, index)">{{ transition.transition.name }}</b-btn>
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
      order: null
    }
  },
  methods: {
    childMethod: function (parentOrder) {
      this.order = parentOrder
      this.findTransitions(parentOrder.workflow_id, this.order.transaction_id)
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
    },
    hideModal: function (transition, index) {
      console.log(index)
      let targetPath = '/api/workflow-user-transitions'
      let params = {
        order: this.order,
        transition: transition
      }
      ApiClient.create(targetPath, params, (response) => {
        console.log(response.data)
        this.$parent.hide()
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
