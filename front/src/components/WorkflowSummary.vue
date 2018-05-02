<template>
  <div>
    <app-header></app-header>
    <b-card class="card-workflow-list">
      <b-table :items="summaries">
        <template slot="name" slot-scope="row">
          <b-btn variant="info" size="sm" @click="setAsDefault(row.item)"> {{ row.item.name }} </b-btn>
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
      this.$store.commit('updateWorkflowId', summary)
      console.log(this.$store.state.workflow)
    }
  },
  created: function () {
    const self = this

    let targetPath = '/api/workflow/summaries'
    ApiClient.search(targetPath, (response) => {
      console.log(response)
      self.summaries = response.data
    }, (error) => {
      console.log(error)
    })
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

.form-add-workflow {
  width: 70%;
  margin-left: 15%;
  border: transparent 1px solid;
}
</style>
