<template>
  <div>
    <workflow-header></workflow-header>
    <b-card class="card-workflow-list">
      <b-table :items="summaries">
        <template slot="name" slot-scope="row">
          <button type="button" class="btn btn-primary"><a :href="'#/workflows/' + row.item.id">{{ row.item.name }}</a></button>
        </template>
      </b-table>
    </b-card>
    <at-btn v-bind:button-title="'Workflow'" v-bind:toggle-value="toggleValue" @child-event="toggleObserve"></at-btn>
    <!-- TODO create a component -->
    <b-form @submit="createSummary">
      <div v-if="toggleValue" class="form-add-summary">
        <b-form-select v-model="form.serviceId" :options="serviceIdList" class="mb-3"></b-form-select>
        <b-form-input value="" v-model="form.name" class="form-control"></b-form-input>
        <button type="button" class="btn btn-success">save</button>
      </div>
    </b-form>
    <app-footer></app-footer>
  </div>
</template>

<script>
import ApiClient from './utils/ApiClient'
import AppFooter from './utils/AppFooter'
import AddToggleButton from './ui/AddToggleButton'
import WorkflowHeader from './workflow/WorkflowHeader'

export default {
  name: 'WorkflowSummary',
  components: { WorkflowHeader, AppFooter, 'at-btn': AddToggleButton },
  data () {
    return {
      summaries: [],
      toggleValue: false,
      form: {
        name: '',
        serviceId: 0
      },
      serviceIdList: [ { text: '受注サービス', value: 0 }, { text: '工房サービス', value: 1 } ]
    }
  },
  methods: {
    toggleObserve: function (toggleValue) {
      this.toggleValue = toggleValue
    },
    createSummary: function () {
      let targetPath = '/api/workflow/summaries'
      let params = {
        id: 0,
        workflow_id: 0,
        name: this.form.name,
        service_id: this.form.serviceId
      }

      ApiClient.create(targetPath, params, (response) => {
        console.log(response)
      }, (error) => {
        console.log(error)
      })
    },
    searchSummaries: function () {
      const self = this

      let targetPath = '/api/workflow/summaries'
      ApiClient.search(targetPath, (response) => {
        self.summaries = response.data
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
