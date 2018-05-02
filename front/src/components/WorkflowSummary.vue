<template>
  <div>
    <app-header></app-header>
    <b-card class="card-workflow-list">
      <b-table :items="summaries">
        <template slot="name" slot-scope="row">
          <b-btn v-if="row.item.isDefault" variant="info" size="sm" @click="setAsDefault(row.item)"  disabled> {{ row.item.name }} </b-btn>
          <b-btn v-if="!row.item.isDefault" variant="info" size="sm" @click="setAsDefault(row.item)"> {{ row.item.name }} </b-btn>
        </template>
      </b-table>
    </b-card>
    <app-footer></app-footer>
  </div>
</template>

<script>
import ApiClient from './utils/ApiClient'
import AppHeader from './utils/AppHeader'
import AppFooter from './utils/AppFooter'

export default {
  name: 'WorkflowSummary',
  components: { AppHeader, AppFooter },
  data () {
    return {
      summaries: []
    }
  },
  methods: {
    setAsDefault: function (summary) {
      this.$store.commit('updateWorkflowId', summary.workflow_id)

      this.searchSummaries()
    },
    searchSummaries: function () {
      const self = this

      let targetPath = '/api/workflow/summaries'
      ApiClient.search(targetPath, (response) => {
        self.summaries = response.data.map(function (data) {
          if (self.$store.state.workflowId !== null && self.$store.state.workflowId === data.workflow_id) {
            data.isDefault = true
          } else {
            data.isDefault = false
          }
          return data
        })
      }, (error) => {
        console.log(error)
      })
    }
  },
  created: function () {
    this.searchSummaries()
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
</style>
