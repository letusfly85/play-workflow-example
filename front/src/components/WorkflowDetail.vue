<template>
  <div>
    <workflow-header></workflow-header>
    <div class="card border-light mb-4 card-workflow-list">
      <div class="card-body ">
        <table class="table table-hover">
          <thead>
          <tr align="left">
            <th scope="col">Id</th>
            <th scope="col">Status</th>
            <th scope="col">Step Label</th>
          </tr>
          </thead>
          <tbody v-if="workflows.details">
          <tr v-for="row in workflows.details" v-bind:key="row.step_id">
            <td align="left">{{ row.step_id }}</td>
            <td align="left">{{ row.status.name }}</td>
            <td align="left">{{ row.step_label }}</td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
    <button class="btn btn-outline-info"><a :href="`#/workflows/${workflowId}/transitions`">Edit Transition</a></button>
    <button v-on:click="toggleChange(addToggle)" class="btn btn-outline-success">
      {{ addToggle ? 'Cancel' : ''}} Add Workflow Record
    </button>
    <br/>
    <br/>
    <div v-if="addToggle" class="form-add-workflow">
      <select v-model="form.status" class="mb-3">
        <option v-for="option in statuses"
                v-bind:value="option.value"
                v-bind:key="option.id">
          {{ option.text }}
        </option>
      </select>
      <input value="" v-model="form.step_label" class="form-control" />
      <br/>
      <input type="checkbox" value=true v-model="form.is_first_step" class="form-control" style="float: left; width: 40%">
      <input type="checkbox" value=true v-model="form.is_last_step" class="form-control" style="width: 40%;">
      <br/><br/>
      <button class="btn btn-success" v-on:click="createRecord">Add Workflow Record</button>
    </div>
    <app-footer></app-footer>
  </div>
</template>

<script>
import AppFooter from './utils/AppFooter'
import WorkflowHeader from './workflow/WorkflowHeader'
import WorkflowService from './service/WorkflowService'
import WorkflowStatusService from './service/WorkflowStatusService'

export default {
  name: 'Workflow',
  components: { WorkflowHeader, AppFooter },
  data () {
    return {
      workflowId: '',
      statuses: [],
      workflows: { details: undefined },
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
      if (this.workflows.details.length > 0) {
        maxStepId = Math.max(...this.workflows.details.map((detail) => detail.step_id))
      }
      let workflowId = Number(this.$store.state.workflowId)
      let detail = {
        workflow_id: workflowId,
        name: 'example',
        status: this.form.status,
        step_id: (maxStepId + 1),
        step_label: this.form.step_label,
        is_first_step: Boolean(this.form.is_first_step),
        is_last_step: Boolean(this.form.is_last_step)
      }
      this.workflows.details.push(detail)
      let targetWorkflow = {
        id: this.workflows.id,
        name: this.workflows.name,
        workflow_id: workflowId,
        details: this.workflows.details,
        description: this.workflows.description,
        service_id: this.workflows.service_id
      }
      this.logger.info(targetWorkflow)

      const self = this
      WorkflowService.update(this.$store.state.workflowId, targetWorkflow, (response) => {
        self.logger.info(response)
      }, (error) => {
        self.logger.error(error)
      })
    }
  },
  created: function () {
    this.workflowId = this.$route.params.workflowId
    this.$store.commit('updateWorkflowId', this.$route.params.workflowId)
    const self = this

    WorkflowService.find(this.$store.state.workflowId, (response) => {
      self.workflows.details = []
      if (response.data) {
        self.workflows = response.data
      }
    }, (error) => {
      self.logger.error(error)
    })

    WorkflowStatusService.list((response) => {
      console.log(response.data)
      self.statuses = response.data.map(function (record) {
        record.value = { id: record.id, name: record.name }
        record.text = record.name

        return record
      })
    }, (error) => {
      self.logger.error(error)
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
