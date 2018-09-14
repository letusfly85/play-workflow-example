<template>
  <div>
    <workflow-header></workflow-header>
    <div class="card border-secondary mb-3">
      <div class="card-body text-left">
        <div v-for="workflow in workflows" v-bind:key="workflow.step_id">
          <div class="card border-secondary mb-2">
            <p>{{ workflow.step_id }}</p>
            <p>{{ workflow.status.name }}</p>
            <p>{{ workflow.step_label }}</p>
          </div>
        </div>
      </div>
    </div>
    <button v-on:click="toggleChange(addToggle)" class="btn btn-outline-success">
      {{ addToggle ? 'Cancel' : ''}} Add Workflow Record
    </button>
    <br/>
    <br/>
    <form @submit="createRecord">
      <div v-if="addToggle" class="form-add-workflow">
        <select v-model="form.status" :options="statuses" class="mb-3"></select>
        <input value="" v-model="form.step_label" class="form-control" />
        <br/>
        <checkbox value=true v-model="form.is_first_step" class="form-control" style="float: left; width: 40%"></checkbox>
        <checkbox value=true v-model="form.is_last_step" class="form-control" style="width: 40%;"></checkbox>
        <br/><br/>
        <button type="submit" class="btn btn-success">Add Workflow Record</button>
      </div>
    </form>
    <app-footer></app-footer>
  </div>
</template>

<script>
import ApiClient from './utils/ApiClient'
import AppFooter from './utils/AppFooter'
import WorkflowHeader from './workflow/WorkflowHeader'
import WorkflowService from './service/WorkflowService'

export default {
  name: 'Workflow',
  components: { WorkflowHeader, AppFooter },
  data () {
    return {
      statuses: [],
      workflows: [],
      addToggle: false,
      form: {
        workflow_id: null,
        status: null,
        step_id: null,
        step_label: null,
        is_first_step: false,
        is_last_step: false
      }
    }
  },
  methods: {
    toggleChange: function (toggle) {
      if (toggle === true) {
        this.addToggle = false
      } else {
        this.addToggle = true
      }
    },
    createRecord: function () {
      var maxStepId = 0
      if (this.workflows.length > 0) {
        maxStepId = Math.max(...this.workflows.map((workflow) => workflow.step_id))
      }
      let param = {
        workflow_id: this.$store.state.workflowId,
        name: 'example',
        step_id: (maxStepId + 1),
        step_label: this.form.step_label,
        status: this.form.status,
        is_first_step: Boolean(this.form.is_first_step),
        is_last_step: Boolean(this.form.is_last_step)
      }
      console.log(param)

      const self = this
      let targetPath = '/api/workflow/definitions'
      ApiClient.create(targetPath, param, (response) => {
        console.log(response)
        self.workflows.push(param)
      }, (error) => {
        console.log(error)
      })
    }
  },
  created: function () {
    this.$store.commit('updateWorkflowId', this.$route.params.workflowId)
    const self = this

    WorkflowService.find(this.$store.state.workflowId, (response) => {
      console.log(response)
      self.workflows = response.data
    }, (error) => {
      console.log(error)
    })

    let targetPath = '/api/workflow-statuses/'
    ApiClient.search(targetPath, (response) => {
      console.log(response.data)
      self.statuses = response.data.map(function (record) {
        record.value = { id: record.id, name: record.name }
        record.text = record.name

        return record
      })
    }, (error) => {
      console.log(error)
    })
  }
}
</script>

<style scoped>
.form-add-workflow {
  width: 70%;
  margin-left: 15%;
  border: transparent 1px solid;
}
</style>
