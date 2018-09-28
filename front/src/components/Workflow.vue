<template>
  <div>
    <workflow-header></workflow-header>

    <div class="card border-light mb-4 card-workflow-list">
      <div class="card-body ">
        <table class="table table-hover">
          <thead>
          <tr align="left">
            <th scope="col">Name</th>
            <th scope="col">Description</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="(row, index) in workflows" v-bind:key="row.id">
            <td align="left">
              <div class="btn-group" role="group">
                <button type="button" class="btn btn-primary">{{ row.name }}</button>
                <div class="btn-group" role="group">
                  <button :id="'btnGroupDrop'+row.id" type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"></button>
                  <div class="dropdown-menu" :aria-labelledby="'btnGroupDrop'+row.id" x-placement="bottom-start" style="position: absolute; transform: translate3d(0px, 36px, 0px); top: 0px; left: 0px; will-change: transform;">
                    <a class="dropdown-item" :href="'#/workflows/' + row.id">ワークフローを作成・編集</a>
                    <button class="dropdown-item alert-danger">このワークフローを削除</button>
                  </div>
                </div>
              </div>
            </td>
            <td align="left" v-if="!row.toggle" v-on:click="updateToggle(index)">{{ row.description }}</td>
            <div v-if="row.toggle">
              <input type="text" class="col-lg-8 form-control" style="margin-top: 1rem; float: left;" v-model="row.description" />
              <button class="btn-primary" style="margin-top: 1.5rem;" v-on:click="saveWorkflow(index)">Save</button>
            </div>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
    <at-btn v-bind:button-title="'Workflow'" v-bind:toggle-value="toggleValue" @child-event="toggleObserve"></at-btn>
    <!-- TODO create a component -->
    <div style="width: 80%; margin-left: 40%; margin-top: 2rem;">
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
          <button type="submit" class="btn btn-success" @click="createWorkflow">save</button>
        </div>
      </div>
    </div>
    <app-footer></app-footer>
  </div>
</template>

<script>
import ApiClient from './utils/ApiClient'
import AppFooter from './utils/AppFooter'
import AddToggleButton from './ui/AddToggleButton'
import WorkflowHeader from './workflow/WorkflowHeader'
import WorkflowService from './service/WorkflowService'

export default {
  name: 'Workflow',
  components: { WorkflowHeader, AppFooter, 'at-btn': AddToggleButton },
  data () {
    return {
      workflows: [],
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
    createWorkflow: function () {
      let targetPath = '/api/workflow/summaries'
      let params = {
        id: 0,
        workflow_id: 0,
        name: this.form.name,
        description: this.form.description,
        service_id: this.form.serviceId
      }

      const self = this
      ApiClient.create(targetPath, params, (response) => {
        console.log(response)
        self.searchWorkflows()
        self.toggleValue = false
      }, (error) => {
        console.log(error)
      })
    },
    searchWorkflows: function () {
      const self = this
      WorkflowService.list((response) => {
        self.workflows = response.data.map(function (workflow) {
          workflow.toggle = false
          return workflow
        })
      }, (error) => {
        console.log(error)
      })
    },
    updateToggle: function (index) {
      let workflow = this.workflows[index]
      if (workflow.toggle) {
        workflow.toggle = false
      } else {
        workflow.toggle = true
      }
      const self = this
      WorkflowService.update(workflow.id, workflow, (response) => {
        console.log(response)
        self.$set(this.workflows, index, workflow)
      }, (error) => {
        console.log(error)
      })
    },
    saveWorkflow: function (index) {
      this.updateToggle(index)
    }
  },
  created: function () {
    this.searchWorkflows()
  }
}
</script>

<style>
.card-workflow-list {
  width: 80%;
  margin-top: 5px;
  border: transparent 1px solid;
}
</style>
