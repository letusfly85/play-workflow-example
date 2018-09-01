<template>
  <div>
    <workflow-header></workflow-header>
    <b-card class="card-workflow-list">
      <table class="table table-hover">
        <thead>
        <tr align="left">
          <th scope="col">Name</th>
          <th scope="col">Description</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="row in summaries" v-bind:key="row.id">
          <td align="left">
            <button type="button" class="btn btn-primary"><a :href="'#/workflows/' + row.id">{{ row.name }}</a></button>
          </td>
          <td></td>
        </tr>
        </tbody>
      </table>
    </b-card>
    <at-btn v-bind:button-title="'Workflow'" v-bind:toggle-value="toggleValue" @child-event="toggleObserve"></at-btn>
    <!-- TODO create a component -->
    <form @submit="createSummary" style="width: 80%; margin-left: 40%; margin-top: 2rem;">
      <div v-if="toggleValue" >
        <div class="form-group col-3">
          <select class="custom-select">
            <option v-for="option in serviceIdList" v-bind:key="option.id">{{ option.text }}</option>
          </select>
        </div>
        <div class="form-group col-3">
          <label class="col-form-label" for="inputName">ワークフロー名</label>
          <input type="text" class="form-control" v-model="form.name" placeholder="ワークフロー名を記入" id="inputName">
        </div>
        <div class="form-group col-6">
          <label for="descriptionInput">説明</label>
          <textarea class="form-control" v-model="form.description" id="descriptionInput" rows="3"></textarea>
        </div>
        <div class="form-group col-3">
          <button type="button" class="btn btn-success">save</button>
        </div>
      </div>
    </form>
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
        serviceId: 0,
        description: ''
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
