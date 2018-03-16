<template>
  <div>
    <app-header></app-header>
    <b-card class="card-workflow-list">
      <div v-for="workflow in workflows" v-bind:key="workflow.step_id">
        <div style="margin-bottom: 3px; height: 5rem;">
          <b-card style="float: left; width: 10%; height: 100%; margin-right: 2px;">{{ workflow.step_id }}</b-card>
          <b-card style="float: left; width: 70%; height: 100%; margin-right: 2px;">{{ workflow.step_label }}</b-card>
          <b-card style="float: left; width: 18%; height: 100%; ">{{ workflow.status.name }}</b-card>
        </div>
      </div>
    </b-card>
    <button v-on:click="toggleChange(addToggle)" class="btn-outline-success">
      {{ addToggle ? 'Cancel' : ''}} Add Workflow Record
    </button>
    <br/>
    <br/>
    <b-form @submit="createRecord">
      <div v-if="addToggle" class="form-add-workflow">
        <b-form-select v-model="form.status" :options="statuses" class="mb-3"></b-form-select>
        <b-form-input value="" v-model="form.step_label" class="form-control"></b-form-input>
        <br/>
        <b-form-checkbox value="" v-model="form.is_first_step" class="form-control" style="float: left; width: 40%"></b-form-checkbox>
        <b-form-checkbox value="" v-model="form.is_last_step" class="form-control" style="width: 40%;"></b-form-checkbox>
        <br/><br/>
        <b-button type="submit" class="btn-success">Add Workflow Record</b-button>
      </div>
    </b-form>
    <app-footer></app-footer>
  </div>
</template>

<script>
import ApiClient from './utils/ApiClient'
import AppHeader from './utils/AppHeader'
import AppFooter from './utils/AppFooter'

export default {
  name: 'Workflow',
  components: { AppHeader, AppFooter },
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
      let maxStepId = Math.max(...this.workflows.map((workflow) => workflow.step_id))
      let param = {
        workflow_id: 1,
        name: 'example',
        step_id: (maxStepId + 1),
        step_label: this.form.step_label,
        status: this.form.status,
        is_first_step: this.form.is_first_step,
        is_last_step: this.form.is_last_step
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
    const self = this

    var targetPath = '/api/workflow/definitions?workflow-id=1'
    ApiClient.search(targetPath, (response) => {
      console.log(response)
      self.workflows = response.data
    }, (error) => {
      console.log(error)
    })

    targetPath = '/api/workflow/statuses'
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
