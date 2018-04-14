<template>
  <div id="transition-button">
    <b-card class="t-btn-list">
      <div v-for="transition in transitions" v-bind:key="transition.id">
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
import AppConst from '../utils/AppConst'

export default {
  name: 'TransitionButton',
  data () {
    return {
      transitions: []
    }
  },
  methods: {
    getTransitions: function (workflowId, transactionId) {
      var targetPath = '/api/workflow-user-transitions?workflow-id=' + workflowId + '&transaction-id=' + transactionId

      const self = this
      ApiClient.search(targetPath, (response) => {
        console.log(response)
        self.transitions = response.data
      }, (error) => {
        console.log(error)
      })
    }
  },
  created: function () {
    // todo get transaction id from a parent component
    this.getTransitions(AppConst.data().workflowId, '11')
  }
}
</script>

<style scoped>
  .t-btn-list {
    margin-left: 60%;
    margin-top: 5px;
    border: transparent 1px solid;
    height: 5rem;
  }
  .t-btn {
    float: left;
    margin-bottom: 3px;
    margin-left: 5px;
    height: 2.5rem;
  }
</style>
