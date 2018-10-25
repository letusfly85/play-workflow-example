<template>
  <div id="t-btn">
    <div class="card border-light mb-4 card-workflow-list">
      <div class="card-body ">
        <div v-for="(transition, index) in trans" v-bind:key="index" v-if="trans.length>0">
          <div v-if="transition.is_active">
            <button class="btn-info" style="float: left; margin-left: 2rem;" v-on:click="changeStatus(transition.transition, index)">{{ transition.transition.name }}</button>
          </div>
          <div v-if="!transition.is_active">
            <button class="btn-dark" style="float: left; margin-left: 2rem;"  disable>{{ transition.transition.name }}</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import ApiClient from '../utils/ApiClient'

export default {
  name: 'TransitionButton',
  data () {
    return {
      trans: [],
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
      const userId = '1' // this is dummy value
      let targetPath = `/api/users/${userId}/workflows/${this.workflowId}/transactions/${this.transactionId}/transitions`
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
      let targetPath = '/api/workflow-user-transactions'
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

<style>
  .t-btn {
    float: left;
    margin-bottom: 3px;
    margin-left: 5px;
    height: 2.5rem;
  }
</style>
