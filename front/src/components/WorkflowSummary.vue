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
    <br/>
    <br/>
    <at-btn v-bind:button-title="'Workflow'" v-bind:toggle-value="toggleValue" @child-event="toggleObserve"></at-btn>
    <!-- TODO create a component -->
    <b-form @submit="createSummary">
      <div v-if="toggleValue" class="form-add-summary">
        <b-form-select v-model="form.serviceId" :options="serviceIdList" class="mb-3"></b-form-select>
        <b-form-input value="" v-model="form.name" class="form-control"></b-form-input>
        <b-button type="submit" class="btn-success">Save Summary</b-button>
      </div>
    </b-form>
    <app-footer></app-footer>
  </div>
</template>

<script>
import ApiClient from './utils/ApiClient'
import AppHeader from './utils/AppHeader'
import AppFooter from './utils/AppFooter'
import AddToggleButton from './ui/AddToggleButton'

export default {
  name: 'WorkflowSummary',
  components: { AppHeader, AppFooter, 'at-btn': AddToggleButton },
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
