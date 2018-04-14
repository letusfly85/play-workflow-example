<template>
  <div id="transition-button">
    <!-- <b-card class="card-workflow-list"> -->
    <b-card>
      <div v-for="transition in transitions" v-bind:key="transition.id">
        <div style="margin-bottom: 3px; height: 5rem;">
          <b-card style="width: 55%; height: 100%;">{{ transition.name }}</b-card>
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
    getTransitions: function (workflowId) {
      var targetPath = '/api/workflow/transitions?workflow-id=' + workflowId

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
    this.getTransitions(AppConst.data().workflowId)
  }
}
</script>

<style scoped>
  .card-workflow-list {
    width: 80%;
    margin-top: 5px;
    margin-left: 10%;
    border: transparent 1px solid;
  }

  #transition-btn {
    width: 80%;
    margin-top: 5px;
    margin-left: 10%;
    border: transparent 1px solid;
  }
</style>
