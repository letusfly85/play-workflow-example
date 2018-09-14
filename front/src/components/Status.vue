<template>
  <div>
    <workflow-header></workflow-header>
    <b-card class="card-status-list">
      <div v-for="status in statuses" v-bind:key="status.id">
        <div style="margin-bottom: 3px;">
          <b-card style="float: left; margin-right: 2px;">{{ status.id }}</b-card>
          <b-card>{{ status.name }}</b-card>
        </div>
      </div>
    </b-card>
    <app-footer></app-footer>
  </div>
</template>

<script>
import ApiClient from './utils/ApiClient'
import AppFooter from './utils/AppFooter'
import WorkflowHeader from './workflow/WorkflowHeader'

export default {
  name: 'Status',
  data () {
    return {
      statuses: []
    }
  },
  components: { WorkflowHeader, AppFooter },
  created: function () {
    let targetPath = '/api/workflow-statuses'

    const self = this
    ApiClient.search(targetPath, (response) => {
      console.log(response)
      self.statuses = response.data
    }, (error) => {
      console.log(error)
    })
  }
}
</script>

<style>
.card-status-list {
  width: 60%;
  margin-top: 5px;
  margin-left: 20%;
  border: transparent 1px solid;
}
</style>
